package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.fixtures.TestPatients;
import ca.kittle.clinic.domain.fixtures.TestPractitioner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingTest {

    private static final List<Patient> patients = TestPatients.getAllPatients();

    @BeforeAll
    static void setUp() {
        assert !patients.isEmpty();
    }

    @Test
    @DisplayName("Should be able to create a booking with valid consultation appointment data")
    void shouldCreateBookingWithValidConsultationAppointmentData() {
        UUID id = UUID.randomUUID();
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.CONSULTATION;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = new Booking(type, now.toLocalDate(), now.toLocalTime(), patient, practitioner);
        assertEquals(type, booking.getAppointmentType());
        assertEquals(now.toLocalDate(), booking.getDate());
        assertEquals(now.toLocalTime(), booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
        Booking bookingWithId = new Booking(id, type, now.toLocalDate(), now.toLocalTime(), patient, practitioner);
        assertEquals(id, bookingWithId.getId());
        assertEquals(type, bookingWithId.getAppointmentType());
        assertEquals(now.toLocalDate(), bookingWithId.getDate());
        assertEquals(now.toLocalTime(), bookingWithId.getStartTime());
        assertEquals(patient, bookingWithId.getPatient());
        assertEquals(practitioner, bookingWithId.getPractitioner());
    }

    @Test
    @DisplayName("Should be able to create a booking with valid standard appointment data")
    void shouldCreateBookingWithValidStandardAppointmentData() {
        UUID id = UUID.randomUUID();
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = new Booking(type, now.toLocalDate(), now.toLocalTime(), patient, practitioner);
        assertEquals(type, booking.getAppointmentType());
        assertEquals(now.toLocalDate(), booking.getDate());
        assertEquals(now.toLocalTime(), booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
        Booking bookingWithId = new Booking(id, type, now.toLocalDate(), now.toLocalTime(), patient, practitioner);
        assertEquals(id, bookingWithId.getId());
        assertEquals(type, bookingWithId.getAppointmentType());
        assertEquals(now.toLocalDate(), bookingWithId.getDate());
        assertEquals(now.toLocalTime(), bookingWithId.getStartTime());
        assertEquals(patient, bookingWithId.getPatient());
        assertEquals(practitioner, bookingWithId.getPractitioner());
    }

    @Test
    @DisplayName("Should be able to create a booking with valid check-in appointment data")
    void shouldCreateBookingWithValidCheckInAppointmentData() {
        UUID id = UUID.randomUUID();
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.CHECK_IN;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = new Booking(type, now.toLocalDate(), now.toLocalTime(), patient, practitioner);
        assertEquals(type, booking.getAppointmentType());
        assertEquals(now.toLocalDate(), booking.getDate());
        assertEquals(now.toLocalTime(), booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
        Booking bookingWithId = new Booking(id, type, now.toLocalDate(), now.toLocalTime(), patient, practitioner);
        assertEquals(id, bookingWithId.getId());
        assertEquals(type, bookingWithId.getAppointmentType());
        assertEquals(now.toLocalDate(), bookingWithId.getDate());
        assertEquals(now.toLocalTime(), bookingWithId.getStartTime());
        assertEquals(patient, bookingWithId.getPatient());
        assertEquals(practitioner, bookingWithId.getPractitioner());
    }

    @Test
    @DisplayName("Should be able to create a booking 10 minutes in the future")
    void shouldCreateBookingWithFutureTime() {
        // NOTE: This test is not in line with business logic - that is encapsulated in builders for a Booking that I'll create later
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime().plusMinutes(10);
        Booking booking = Booking.createFutureBooking(now, type, nowDate, nowTime, patient, practitioner);
        assertEquals(type, booking.getAppointmentType());
        assertEquals(nowDate, booking.getDate());
        assertEquals(nowTime, booking.getStartTime());
        assertEquals(patient, booking.getPatient());
        assertEquals(practitioner, booking.getPractitioner());
    }

    @Test
    @DisplayName("Should NOT be able to create a booking that starts this instant")
    void shouldThrowExceptionWhenBookingStartsThisInstant() {
        // NOTE: This test is not in line with business logic - that is encapsulated in builders for a Booking that I'll create later
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, type, nowDate, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should NOT be able to create a booking that starts in the past")
    void shouldThrowExceptionWhenBookingStartsInThePast() {
        // NOTE: This test is not in line with business logic - that is encapsulated in builders for a Booking that I'll create later
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime().minusMinutes(3);
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, type, nowDate, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when earliest booking date/time is null")
    void shouldThrowExceptionWhenEarliestDateTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(null, type, nowDate, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when appointment type is null")
    void shouldThrowExceptionWhenAppointmentTypeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, null, nowDate, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when date is null")
    void shouldThrowExceptionWhenDateIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, type, null, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when start time is null")
    void shouldThrowExceptionWhenStartTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, type, nowDate, null, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when patient is null")
    void shouldThrowExceptionWhenPatientIsNull() {
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, type, nowDate, nowTime, null, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when practitioner is null")
    void shouldThrowExceptionWhenPractitionerIsNull() {
        Patient patient = patients.get(0);
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> Booking.createFutureBooking(now, type, nowDate, nowTime, patient, null)
        );
    }

    @Test
    @DisplayName("Should throw exception when ID is null in repository constructor")
    void shouldThrowExceptionWhenIdIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Booking(null, type, nowDate, nowTime, patient, practitioner)
        );
    }


}
