package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.fixtures.TestClinic;
import ca.kittle.clinic.domain.fixtures.TestPatients;
import ca.kittle.clinic.domain.fixtures.TestPractitioner;
import ca.kittle.clinic.domain.validation.AppointmentValidationError;
import ca.kittle.clinic.domain.validation.BookingValidationError;
import io.jbock.util.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PractitionerBusinessRuleTest {

    private static final List<Patient> patients = TestPatients.getAllPatients();
    private final Practitioner practitioner = TestPractitioner.CHERIA;
    private final Clinic clinic = TestClinic.TEST_CLINIC;

    @BeforeAll
    static void setUp() {
        assert !patients.isEmpty();
    }

    @BeforeEach
    void setUpEach() {
        practitioner.listBookings(LocalDateTime.now().plusDays(1).toLocalDate()).forEach(
                practitioner::cancelBooking
        );
    }

    @Test
    @DisplayName("Should return a booking if appointment requested in an open time slot")
    void shouldReturnABookingWhenTimeSlotIsOpen() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();

        Either<List<BookingValidationError>, Booking> result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        startTime);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
        assertEquals(type, booking.getAppointmentType());
        assertEquals(bookingDate, booking.getDate());
        assertEquals(startTime, booking.getStartTime());
    }

    @Test
    @DisplayName("Should return bookings if two appointments requested in an open time slots")
    void shouldReturnBookingsWhenTwoTimeSlotAreOpen() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();

        Either<List<BookingValidationError>, Booking> result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        startTime);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();
        assertEquals(startTime, booking.getStartTime());

        LocalTime secondStartTime = startTime.plusHours(2);
        result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        secondStartTime);
        assertTrue(result.isRight() && result.getRight().isPresent());
        List<Booking> bookings = practitioner.listBookings(bookingDate);
        assertEquals(2, bookings.size());
        assertTrue(bookings.stream().anyMatch(b -> b.getStartTime().equals(startTime)));
        assertTrue(bookings.stream().anyMatch(b -> b.getStartTime().equals(secondStartTime)));

        // Book an appointment in between the first two
        LocalTime thirdStartTime = startTime.plusHours(1);
        result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        thirdStartTime);
        assertTrue(result.isRight() && result.getRight().isPresent());
        bookings = practitioner.listBookings(bookingDate);
        assertEquals(3, bookings.size());
        assertTrue(bookings.stream().anyMatch(b -> b.getStartTime().equals(thirdStartTime)));
    }

    @Test
    @DisplayName("Should return an error if two appointments requested in overlapping time slots")
    void shouldReturnAnErrorWhenBookingsOverlap() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime().plusHours(1);

        Either<List<BookingValidationError>, Booking> result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        startTime);
        assertTrue(result.isRight() && result.getRight().isPresent());

        LocalTime secondStartTime = startTime.minusMinutes(30);
        result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        secondStartTime);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.BookingOverlapsAnotherError
                )
        );
    }

    @Test
    @DisplayName("Should be able to create an appointment from a valid booking")
    void shouldCreateAnAppointmentFromAValidBooking() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();

        Either<List<BookingValidationError>, Booking> result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        startTime);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();

        Either<List<AppointmentValidationError>, Appointment> appointmentResult =
                practitioner.createAppointment(booking);
        assertTrue(appointmentResult.isRight() && appointmentResult.getRight().isPresent());
    }


    @Test
    @DisplayName("Patient should be able to view available time slots for appointment type")
    void shouldSeeAvailableTimeSlotsForAppointmentType() {
        Patient patient = patients.get(1);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();

        Either<List<BookingValidationError>, Booking> result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        startTime);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();

        Either<List<AppointmentValidationError>, Appointment> appointmentResult =
                practitioner.createAppointment(booking);
        assertTrue(appointmentResult.isRight() && appointmentResult.getRight().isPresent());
    }

    @Test
    @DisplayName("Should return all time slots for an empty day")
    void shouldReturnAllTimeSlotsForAnEmptyDay() {
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();

        List<LocalTime> times = practitioner.availabileTimes(bookingDate, type);
        assertTrue(times.stream().anyMatch(item -> item.equals(startTime)));
        assertEquals(16, times.size());
    }

    @Test
    @DisplayName("Should return time slots for 10am onward if first hour blocked")
    void shouldReturnTimeSlotsFor10amOnwardIfFirstHourBlocked() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = clinic.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();

        Either<List<BookingValidationError>, Booking> result =
                practitioner.addBooking(
                        patient,
                        clinic,
                        type,
                        bookingDate,
                        startTime);
        assertTrue(result.isRight() && result.getRight().isPresent());

        List<LocalTime> times = practitioner.availabileTimes(bookingDate, type);
        assertTrue(times.stream().anyMatch(item -> item.equals(LocalTime.of(10, 0))));
        assertTrue(times.stream().anyMatch(item -> item.isAfter(LocalTime.of(9, 59))));
        assertEquals(14, times.size());
    }

}
