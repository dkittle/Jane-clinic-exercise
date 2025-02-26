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

class AppointmentTest {

    private static final List<Patient> patients = TestPatients.getAllPatients();

    @BeforeAll
    static void setUp() {
        assert !patients.isEmpty();
    }

    @Test
    @DisplayName("Should be able to create a consultation appointment with valid data")
    void shouldCreateConsultationAppointmentWithValidData() {
        UUID id = UUID.randomUUID();
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment =
                new Appointment(
                        Appointment.AppointmentType.CONSULTATION,
                        now.toLocalDate(),
                        now.toLocalTime(),
                        patient,
                        practitioner);
        assertEquals(now.toLocalDate(), appointment.getDate());
        assertEquals(now.toLocalTime(), appointment.getStartTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals(practitioner, appointment.getPractitioner());
        assertEquals(Appointment.AppointmentType.CONSULTATION, appointment.getType());
        assertEquals(Appointment.AppointmentType.CONSULTATION.isConsultation(), appointment.isConsultation());
        assertEquals(Appointment.AppointmentType.CONSULTATION.getDuration(), appointment.getDuration());
        Appointment appointmentWithId =
                new Appointment(
                        id,
                        Appointment.AppointmentType.CONSULTATION,
                        now.toLocalDate(),
                        now.toLocalTime(),
                        patient,
                        practitioner);
        assertEquals(id, appointmentWithId.getId());
        assertEquals(now.toLocalDate(), appointmentWithId.getDate());
        assertEquals(now.toLocalTime(), appointmentWithId.getStartTime());
        assertEquals(patient, appointmentWithId.getPatient());
        assertEquals(practitioner, appointmentWithId.getPractitioner());
        assertEquals(Appointment.AppointmentType.CONSULTATION, appointmentWithId.getType());
        assertEquals(Appointment.AppointmentType.CONSULTATION.isConsultation(), appointmentWithId.isConsultation());
        assertEquals(Appointment.AppointmentType.CONSULTATION.getDuration(), appointmentWithId.getDuration());
    }

    @Test
    @DisplayName("Should be able to create a standard appointment with valid data")
    void shouldCreateStandardAppointmentWithValidData() {
        UUID id = UUID.randomUUID();
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment =
                new Appointment(
                        Appointment.AppointmentType.STANDARD,
                        now.toLocalDate(),
                        now.toLocalTime(),
                        patient,
                        practitioner);
        assertEquals(now.toLocalDate(), appointment.getDate());
        assertEquals(now.toLocalTime(), appointment.getStartTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals(practitioner, appointment.getPractitioner());
        assertEquals(Appointment.AppointmentType.STANDARD, appointment.getType());
        assertEquals(Appointment.AppointmentType.STANDARD.isConsultation(), appointment.isConsultation());
        assertEquals(Appointment.AppointmentType.STANDARD.getDuration(), appointment.getDuration());
        Appointment appointmentWithId =
                new Appointment(
                        id,
                        Appointment.AppointmentType.STANDARD,
                        now.toLocalDate(),
                        now.toLocalTime(),
                        patient,
                        practitioner);
        assertEquals(id, appointmentWithId.getId());
        assertEquals(now.toLocalDate(), appointmentWithId.getDate());
        assertEquals(now.toLocalTime(), appointmentWithId.getStartTime());
        assertEquals(patient, appointmentWithId.getPatient());
        assertEquals(practitioner, appointmentWithId.getPractitioner());
        assertEquals(Appointment.AppointmentType.STANDARD, appointmentWithId.getType());
        assertEquals(Appointment.AppointmentType.STANDARD.isConsultation(), appointmentWithId.isConsultation());
        assertEquals(Appointment.AppointmentType.STANDARD.getDuration(), appointmentWithId.getDuration());
    }

    @Test
    @DisplayName("Should be able to create a check-in appointment with valid data")
    void shouldCreateCheckInAppointmentWithValidData() {
        UUID id = UUID.randomUUID();
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment =
                new Appointment(
                        Appointment.AppointmentType.CHECK_IN,
                        now.toLocalDate(),
                        now.toLocalTime(),
                        patient,
                        practitioner);
        assertEquals(now.toLocalDate(), appointment.getDate());
        assertEquals(now.toLocalDate(), appointment.getDate());
        assertEquals(now.toLocalTime(), appointment.getStartTime());
        assertEquals(patient, appointment.getPatient());
        assertEquals(practitioner, appointment.getPractitioner());
        assertEquals(Appointment.AppointmentType.CHECK_IN, appointment.getType());
        assertEquals(Appointment.AppointmentType.CHECK_IN.isConsultation(), appointment.isConsultation());
        assertEquals(Appointment.AppointmentType.CHECK_IN.getDuration(), appointment.getDuration());
        Appointment appointmentWithId =
                new Appointment(
                        id,
                        Appointment.AppointmentType.CHECK_IN,
                        now.toLocalDate(),
                        now.toLocalTime(),
                        patient,
                        practitioner);
        assertEquals(id, appointmentWithId.getId());
        assertEquals(now.toLocalDate(), appointmentWithId.getDate());
        assertEquals(now.toLocalTime(), appointmentWithId.getStartTime());
        assertEquals(patient, appointmentWithId.getPatient());
        assertEquals(practitioner, appointmentWithId.getPractitioner());
        assertEquals(Appointment.AppointmentType.CHECK_IN, appointmentWithId.getType());
        assertEquals(Appointment.AppointmentType.CHECK_IN.isConsultation(), appointmentWithId.isConsultation());
        assertEquals(Appointment.AppointmentType.CHECK_IN.getDuration(), appointmentWithId.getDuration());
    }

//    @Test
//    @DisplayName("Should be able to create a appointment 10 minutes in the future")
//    void shouldCreateAppointmentWithFutureTime() {
//        // NOTE: This test is not in line with business logic - that is encapsulated in builders for a Appointment that I'll create later
//        Patient patient = patients.get(0);
//        Practitioner practitioner = TestPractitioner.CHERIA;
//        LocalDateTime now = LocalDateTime.now();
//        LocalDate nowDate = now.toLocalDate();
//        LocalTime nowTime = now.toLocalTime().plusMinutes(10);
//        Appointment appointment = Appointment.createFutureAppointment(now, nowDate, nowTime, patient, practitioner);
//        assertEquals(nowDate, appointment.getDate());
//        assertEquals(nowTime, appointment.getStartTime());
//        assertEquals(patient, appointment.getPatient());
//        assertEquals(practitioner, appointment.getPractitioner());
//    }
//
//    @Test
//    @DisplayName("Should NOT be able to create a appointment that starts this instant")
//    void shouldThrowExceptionWhenAppointmentStartsThisInstant() {
//        // NOTE: This test is not in line with business logic - that is encapsulated in builders for a Appointment that I'll create later
//        Patient patient = patients.get(0);
//        Practitioner practitioner = TestPractitioner.CHERIA;
//        LocalDateTime now = LocalDateTime.now();
//        LocalDate nowDate = now.toLocalDate();
//        LocalTime nowTime = now.toLocalTime();
//        assertThrows(IllegalArgumentException.class,
//                () -> Appointment.createFutureAppointment(now, nowDate, nowTime, patient, practitioner)
//        );
//    }
//
//    @Test
//    @DisplayName("Should NOT be able to create a appointment that starts in the past")
//    void shouldThrowExceptionWhenAppointmentStartsInThePast() {
//        // NOTE: This test is not in line with business logic - that is encapsulated in builders for a Appointment that I'll create later
//        Patient patient = patients.get(0);
//        Practitioner practitioner = TestPractitioner.CHERIA;
//        LocalDateTime now = LocalDateTime.now();
//        LocalDate nowDate = now.toLocalDate();
//        LocalTime nowTime = now.toLocalTime().minusMinutes(3);
//        assertThrows(IllegalArgumentException.class,
//                () -> Appointment.createFutureAppointment(now, nowDate, nowTime, patient, practitioner)
//        );
//    }

    @Test
    @DisplayName("Should throw exception when appointment type is null")
    void shouldThrowExceptionWhenAppointmentTypeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        LocalTime nowTime = now.toLocalTime();
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(null, nowDate, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when date is null")
    void shouldThrowExceptionWhenDateIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalTime nowTime = LocalDateTime.now().toLocalTime();
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(type, null, nowTime, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when start time is null")
    void shouldThrowExceptionWhenStartTimeIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDate nowDate = LocalDate.now();
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(type, nowDate, null, patient, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when patient is null")
    void shouldThrowExceptionWhenPatientIsNull() {
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        LocalTime nowTime = now.toLocalTime();
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(type, nowDate, nowTime, null, practitioner)
        );
    }

    @Test
    @DisplayName("Should throw exception when practitioner is null")
    void shouldThrowExceptionWhenPractitionerIsNull() {
        Patient patient = patients.get(0);
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate().plusDays(1);
        LocalTime nowTime = now.toLocalTime();
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(type, nowDate, nowTime, patient, null)
        );
    }

    @Test
    @DisplayName("Should throw exception when ID is null in repository constructor")
    void shouldThrowExceptionWhenIdIsNull() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Appointment(null, nowDate, nowTime, patient, practitioner)
        );
    }


}
