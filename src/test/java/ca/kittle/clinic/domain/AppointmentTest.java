package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.fixtures.TestClinic;
import ca.kittle.clinic.domain.fixtures.TestPatients;
import ca.kittle.clinic.domain.fixtures.TestPractitioner;
import ca.kittle.clinic.domain.validation.AppointmentValidationError;
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

class AppointmentTest {

    private static final List<Patient> patients = TestPatients.getAllPatients();

    @BeforeAll
    static void setUp() {
        assert !patients.isEmpty();
    }

    @Test
    @DisplayName("Should be able to create a consultation appointment with valid data")
    void shouldCreateConsultationAppointmentWithValidData() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.CONSULTATION,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Appointment appointment = result.getRight().get();
        assertEquals(bookingDate, appointment.getDate());
        assertEquals(startTime, appointment.getStartTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals(practitioner, appointment.getPractitioner());
        assertEquals(Appointment.AppointmentType.CONSULTATION, appointment.getType());
        assertEquals(Appointment.AppointmentType.CONSULTATION.isConsultation(), appointment.isConsultation());
        assertEquals(Appointment.AppointmentType.CONSULTATION.getDuration(), appointment.getDuration());
    }

    @Test
    @DisplayName("Should be able to create a standard appointment with valid data")
    void shouldCreateStandardAppointmentWithValidData() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.STANDARD,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Appointment appointment = result.getRight().get();
        assertEquals(bookingDate, appointment.getDate());
        assertEquals(startTime, appointment.getStartTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals(practitioner, appointment.getPractitioner());
        assertEquals(Appointment.AppointmentType.STANDARD, appointment.getType());
        assertEquals(Appointment.AppointmentType.STANDARD.isConsultation(), appointment.isConsultation());
        assertEquals(Appointment.AppointmentType.STANDARD.getDuration(), appointment.getDuration());
    }

    @Test
    @DisplayName("Should be able to create a check-in appointment with valid data")
    void shouldCreateCheckInAppointmentWithValidData() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.CHECK_IN,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isRight() && result.getRight().isPresent());
        Appointment appointment = result.getRight().get();
        assertEquals(bookingDate, appointment.getDate());
        assertEquals(startTime, appointment.getStartTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals(practitioner, appointment.getPractitioner());
        assertEquals(Appointment.AppointmentType.CHECK_IN, appointment.getType());
        assertEquals(Appointment.AppointmentType.CHECK_IN.isConsultation(), appointment.isConsultation());
        assertEquals(Appointment.AppointmentType.CHECK_IN.getDuration(), appointment.getDuration());
    }

    @Test
    @DisplayName("Should return an error when earliest date time is null")
    void shouldReturnAnErrorWhenAppointmentEarliestDateTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        null,
                        Appointment.AppointmentType.STANDARD,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<AppointmentValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof AppointmentValidationError.EarliestDateNullError
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
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        null,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<AppointmentValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof AppointmentValidationError.TypeNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when date is null")
    void shouldReturnAnErrorWhenDateIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.STANDARD,
                        null,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<AppointmentValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof AppointmentValidationError.DateNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when start time is null")
    void shouldReturnAnErrorWhenStartTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.STANDARD,
                        bookingDate,
                        null,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<AppointmentValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof AppointmentValidationError.StartTimeNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when patient is null")
    void shouldReturnAnErrorWhenPatientIsNull() {
        Practitioner practitioner = TestPractitioner.CHERIA;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.STANDARD,
                        bookingDate,
                        startTime,
                        null,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<AppointmentValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof AppointmentValidationError.PatientNullError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when practitioner is null")
    void shouldReturnAnErrorWhenPractitionerIsNull() {
        Patient patient = patients.get(0);
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime();
        Either<List<AppointmentValidationError>, Appointment> result =
                Appointment.createAppointment(
                        now,
                        Appointment.AppointmentType.STANDARD,
                        bookingDate,
                        startTime,
                        patient,
                        null);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<AppointmentValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof AppointmentValidationError.PractitionerNullError
                )
        );
    }

}
