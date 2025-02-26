package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.validation.AppointmentValidationError;
import ca.kittle.clinic.domain.validation.BookingValidationError;
import io.jbock.util.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Appointment {
    private static final String ID_NULL_ERROR = "Appointment ID cannot be null";
    private static final String TYPE_NULL_ERROR = "Appointment type cannot be null";
    private static final String DATE_NULL_ERROR = "Appointment date cannot be null";
    private static final String TIME_NULL_ERROR = "Appointment start time cannot be null";
    private static final String PATIENT_NULL_ERROR = "Appointment patient cannot be null";
    private static final String PRACTITIONER_NULL_ERROR = "Appointment practitioner cannot be null";
    private final AppointmentType type;
    private final LocalDate date;
    private final LocalTime startTime;
    private final Patient patient;
    private final Practitioner practitioner;
    @Setter
    private String notes;

    /**
     * Create a new Appointment.
     *
     * @param type         the type of appointment (from the enumerated set)
     * @param date         the date of the appointment; must not be null or in the past
     * @param startTime    the start time of the appointment; must not be null or in the past
     * @param patient      the patient associated with the appointment; must not be null
     * @param practitioner the practitioner associated with the appointment; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    private Appointment(AppointmentType type, LocalDate date, LocalTime startTime, Patient patient, Practitioner practitioner) {
        if (type == null)
            throw new IllegalArgumentException(TYPE_NULL_ERROR);
        if (date == null)
            throw new IllegalArgumentException(DATE_NULL_ERROR);
        if (startTime == null)
            throw new IllegalArgumentException(TIME_NULL_ERROR);
        if (patient == null)
            throw new IllegalArgumentException(PATIENT_NULL_ERROR);
        if (practitioner == null)
            throw new IllegalArgumentException(PRACTITIONER_NULL_ERROR);
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.patient = patient;
        this.practitioner = practitioner;
        this.notes = "";
    }

    /**
     * This is the preferred way to create a new Appointment as it ensures the Appointment is valid.
     *
     * @param earliestDateTime  the date that the appointment must start after; must not be null
     * @param type              the type of appointment (from the enumerated set)
     * @param date              the date of the appointment; must not be null or in the past
     * @param startTime         the start time of the appointment; must not be null or in the past
     * @param patient           the patient associated with the appointment; must not be null
     * @param practitioner      the practitioner associated with the appointment; must not be null
     *
     * @return Either<List<BookingValidationError>, Appointment> a list of validate errors or an Appointment
     */
    public static Either<List<AppointmentValidationError>, Appointment> createAppointment(
            LocalDateTime earliestDateTime,
            Appointment.AppointmentType type,
            LocalDate date,
            LocalTime startTime,
            Patient patient,
            Practitioner practitioner) {

        // Do basic validation on parameters for the Booking
        List<AppointmentValidationError> errors =
                checkAppointmentParamsForNulls(
                        earliestDateTime,
                        type,
                        date,
                        startTime,
                        patient,
                        practitioner);

        return errors.isEmpty() ?
                Either.right(new Appointment(type, date, startTime, patient, practitioner)) :
                Either.left(errors);
    }

    /**
     * Ensure that the basic properties for an Appointment are not null
     *
     * @param afterThisDateTime the date that the appointment must start after; must not be null
     * @param appointmentType   the type of appointment (from the enumerated set)
     * @param date              the date of the appointment; must not be null or in the past
     * @param startTime         the start time of the appointment; must not be null or in the past
     * @param patient           the patient associated with the appointment; must not be null
     * @param practitioner      the practitioner associated with the appointment; must not be null
     * @return Optional List<AppointmentValidationError> a list of any errors found
     */
    // TODO hmmm, duplication with Booking property validation errors - need to clean this up
    private static List<AppointmentValidationError> checkAppointmentParamsForNulls(
            LocalDateTime afterThisDateTime,
            Appointment.AppointmentType appointmentType,
            LocalDate date,
            LocalTime startTime,
            Patient patient,
            Practitioner practitioner) {
        List<AppointmentValidationError> errors = new ArrayList<>();

        if (afterThisDateTime == null)
            errors.add(new AppointmentValidationError.EarliestDateNullError());
        if (appointmentType == null)
            errors.add(new AppointmentValidationError.TypeNullError());
        if (date == null)
            errors.add(new AppointmentValidationError.DateNullError());
        if (startTime == null)
            errors.add(new AppointmentValidationError.StartTimeNullError());
        if (patient == null)
            errors.add(new AppointmentValidationError.PatientNullError());
        if (practitioner == null)
            errors.add(new AppointmentValidationError.PractitionerNullError());
        return errors;
    }

    public Duration getDuration() {
        return type.getDuration();
    }

    public boolean isConsultation() {
        return type.isConsultation();
    }

    @Getter
    @AllArgsConstructor
    public enum AppointmentType {
        CONSULTATION(Duration.ofMinutes(90), true),
        STANDARD(Duration.ofMinutes(60), false),
        CHECK_IN(Duration.ofMinutes(30), false);

        private final Duration duration;
        private final boolean isConsultation;
    }


}
