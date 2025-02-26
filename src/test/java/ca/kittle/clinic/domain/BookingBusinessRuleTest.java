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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingBusinessRuleTest {

    private static final List<Patient> patients = TestPatients.getAllPatients();

    @BeforeAll
    static void setUp() {
        assert !patients.isEmpty();
    }

    @Test
    @DisplayName("Should return an error when booking date or time is not in the future")
    void shouldReturnAnErrorWhenBookingIsNotInFuture() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        LocalDate bookingDate = now.toLocalDate();
        LocalTime startTime = clinicHours.getOpeningTime().plusHours(1);

        Either<List<BookingValidationError>, Booking> result =
                Booking.createBooking(
                        now,
                        clinicHours,
                        type,
                        bookingDate.minusDays(1),
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.DateInPastError
                )
        );
        result =
                Booking.createBooking(
                        LocalDateTime.of(bookingDate, startTime.minusMinutes(1)),
                        clinicHours,
                        type,
                        bookingDate,
                        startTime.minusMinutes(30),
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.TimeInPastError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when booking time is not in on the hour or half hour")
    void shouldReturnAnErrorWhenBookingStartTimeIsInvalid() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate();
        LocalTime startTime = clinicHours.getOpeningTime();

        Either<List<BookingValidationError>, Booking> result;
        for (int i = 1; i < 30; i++) {
            result =
                    Booking.createBooking(
                            now,
                            clinicHours,
                            type,
                            bookingDate,
                            startTime.plusMinutes(i),
                            patient,
                            practitioner);
            assertTrue(result.isLeft() && result.getLeft().isPresent());
            List<BookingValidationError> errors = result.getLeft().get();
            assertTrue(errors.stream().anyMatch(
                            item -> item instanceof BookingValidationError.DesiredStartTimeError
                    )
            );
        }
        for (int i = 31; i < 60; i++) {
            result =
                    Booking.createBooking(
                            now,
                            clinicHours,
                            type,
                            bookingDate,
                            startTime.plusMinutes(i),
                            patient,
                            practitioner);
            assertTrue(result.isLeft() && result.getLeft().isPresent());
            List<BookingValidationError> errors = result.getLeft().get();
            assertTrue(errors.stream().anyMatch(
                            item -> item instanceof BookingValidationError.DesiredStartTimeError
                    )
            );
        }
    }

    @Test
    @DisplayName("Should return an error when earliest date/time is within 2 hours of booking date")
    void shouldReturnAnErrorWhenBookingIsLessThanTwoHoursInFuture() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.of(
                LocalDate.now().plusDays(1),
                clinicHours.getOpeningTime().plusHours(2)
        );
        LocalDate bookingDate = now.toLocalDate();
        LocalTime startTime = now.toLocalTime().plusHours(1);

        Either<List<BookingValidationError>, Booking> result =
                Booking.createBooking(
                        now,
                        clinicHours,
                        type,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.TooSoonToAppointmentError
                )
        );
        result =
                Booking.createBooking(
                        now,
                        clinicHours,
                        type,
                        bookingDate,
                        now.toLocalTime().plusHours(2).minusMinutes(1),
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.TooSoonToAppointmentError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when booking is before clinic opening time")
    void shouldReturnAnErrorWhenBookingIsBeforeClinicOpeningTime() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getOpeningTime().minusMinutes(1);

        Either<List<BookingValidationError>, Booking> result =
                Booking.createBooking(
                        now,
                        clinicHours,
                        type,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.OutsideBusinessHoursError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when booking start is after clinic closing time")
    void shouldReturnAnErrorWhenBookingStartIsAfterClinicClosingTime() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getClosingTime().plusMinutes(1);

        Either<List<BookingValidationError>, Booking> result =
                Booking.createBooking(
                        now,
                        clinicHours,
                        type,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.OutsideBusinessHoursError
                )
        );
    }

    @Test
    @DisplayName("Should return an error when booking ends after clinic closing time")
    void shouldReturnAnErrorWhenBookingEndsAfterClinicClosingTime() {
        Patient patient = patients.get(0);
        Practitioner practitioner = TestPractitioner.CHERIA;
        Appointment.AppointmentType type = Appointment.AppointmentType.STANDARD;
        ClinicHours clinicHours = TestClinic.TEST_CLINIC.getHours();
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = now.toLocalDate().plusDays(1);
        LocalTime startTime = clinicHours.getClosingTime().minusMinutes(59);

        Either<List<BookingValidationError>, Booking> result =
                Booking.createBooking(
                        now,
                        clinicHours,
                        type,
                        bookingDate,
                        startTime,
                        patient,
                        practitioner);
        assertTrue(result.isLeft() && result.getLeft().isPresent());
        List<BookingValidationError> errors = result.getLeft().get();
        assertTrue(errors.stream().anyMatch(
                        item -> item instanceof BookingValidationError.OutsideBusinessHoursError
                )
        );
    }

}
