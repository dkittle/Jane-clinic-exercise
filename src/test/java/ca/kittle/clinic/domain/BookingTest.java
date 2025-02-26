package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.fixtures.TestClinic;
import ca.kittle.clinic.domain.fixtures.TestPatients;
import ca.kittle.clinic.domain.fixtures.TestPractitioner;
import ca.kittle.clinic.domain.validation.BookingValidationError;
import io.jbock.util.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingTest {

    private static final List<Patient> patients = TestPatients.getAllPatients();

    @BeforeAll
    static void setUp() {
        assert !patients.isEmpty();
    }

    @Test
    @DisplayName("Should be able to create a booking with valid consultation appointment data")
    void shouldCreateBookingWithValidConsultationAppointmentData() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.CONSULTATION;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(now, clinicHours, type, bookingDate, startTime, patient, practitioner);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();
        assertEquals(type, booking.getAppointmentType());
        assertEquals(bookingDate, booking.getDate());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
    }

    @Test
    @DisplayName("Should be able to create a booking with valid standard appointment data")
    void shouldCreateBookingWithValidStandardAppointmentData() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(now, clinicHours, type, bookingDate, startTime, patient, practitioner);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();
        assertEquals(type, booking.getAppointmentType());
        assertEquals(bookingDate, booking.getDate());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
    }

    @Test
    @DisplayName("Should be able to create a booking with valid check-in appointment data")
    void shouldCreateBookingWithValidCheckInAppointmentData() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.CHECK_IN;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(now, clinicHours, type, bookingDate, startTime, patient, practitioner);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Booking booking = result.getRight().get();
        assertEquals(type, booking.getAppointmentType());
        assertEquals(bookingDate, booking.getDate());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
    }

    @Test
    @DisplayName("Should return an error when earliest booking date/time is null")
    void shouldReturnAnErrorWhenEarliestDateTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                null,
                clinicHours,
                type,
                bookingDate,
                startTime,
                patient,
                practitioner
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.EarliestDateNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when clinic hours is null")
    void shouldReturnAnErrorWhenAppointmentClinicHoursIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                now,
                null,
                type,
                bookingDate,
                startTime,
                patient,
                practitioner
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.ClinicHoursNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when appointment type is null")
    void shouldReturnAnErrorWhenAppointmentTypeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                now,
                clinicHours,
                null,
                bookingDate,
                startTime,
                patient,
                practitioner
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.TypeNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when date is null")
    void shouldReturnAnErrorWhenDateIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                now,
                clinicHours,
                type,
                null,
                startTime,
                patient,
                practitioner
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.DateNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when start time is null")
    void shouldReturnAnErrorWhenStartTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                now,
                clinicHours,
                type,
                bookingDate,
                null,
                patient,
                practitioner
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.StartTimeNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when patient is null")
    void shouldReturnAnErrorWhenPatientIsNull() {
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                now,
                clinicHours,
                type,
                bookingDate,
                startTime,
                null,
                practitioner
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.PatientNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when practitioner is null")
    void shouldReturnAnErrorWhenPractitionerIsNull() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                now,
                clinicHours,
                type,
                bookingDate,
                startTime,
                patient,
                null
        );
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.PractitionerNullError
                )
        );
    }

}
