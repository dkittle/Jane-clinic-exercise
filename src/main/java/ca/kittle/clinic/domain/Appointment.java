package ca.kittle.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
public class Appointment {
    private static final String ID_NULL_ERROR = "Appointment ID cannot be null";
    private static final String TYPE_NULL_ERROR = "Appointment type cannot be null";
    private static final String DATE_NULL_ERROR = "Appointment date cannot be null";
    private static final String TIME_NULL_ERROR = "Appointment start time cannot be null";
    private static final String PATIENT_NULL_ERROR = "Appointment patient cannot be null";
    private static final String PRACTITIONER_NULL_ERROR = "Appointment practitioner cannot be null";
    private final UUID id;
    private final AppointmentType type;
    private final LocalDate date;
    private final LocalTime startTime;
    private final Patient patient;
    private final Practitioner practitioner;
    @Setter
    private String notes;

    /**
     * This constructor is the preferred constructor for creating a new Appointment.
     *
     * @param type         the type of appointment (from the enumerated set)
     * @param date         the date of the appointment; must not be null or in the past
     * @param startTime    the start time of the appointment; must not be null or in the past
     * @param patient      the patient associated with the appointment; must not be null
     * @param practitioner the practitioner associated with the appointment; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    Appointment(AppointmentType type, LocalDate date, LocalTime startTime, Patient patient, Practitioner practitioner) {
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
        this.id = UUID.randomUUID();
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.patient = patient;
        this.practitioner = practitioner;
        this.notes = "";
    }

    /**
     * This constructor would typically be used by repository classes, creating an appointment from a datastore
     *
     * @param id           the unique identifier for the Appointment
     * @param type         the type of appointment (from the enumerated set)
     * @param date         the date of the appointment; must not be null or in the past
     * @param startTime    the start time of the appointment; must not be null or in the past
     * @param patient      the patient associated with the appointment; must not be null
     * @param practitioner the practitioner associated with the appointment; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    Appointment(UUID id, AppointmentType type, LocalDate date, LocalTime startTime, Patient patient, Practitioner practitioner) {
        if (id == null)
            throw new IllegalArgumentException(ID_NULL_ERROR);
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
        this.id = id;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.patient = patient;
        this.practitioner = practitioner;
        this.notes = "";
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
