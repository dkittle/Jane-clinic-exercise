package ca.kittle.clinic.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
public class Booking {

    private static final String ID_NULL_ERROR = "Booking ID cannot be null";
    private static final String DATE_NULL_ERROR = "Booking date cannot be null";
    private static final String AFTER_NULL_ERROR = "Booking earliest date cannot be null";
    private static final String TIME_NULL_ERROR = "Booking start time cannot be null";
    private static final String PATIENT_NULL_ERROR = "Booking patient cannot be null";
    private static final String PRACTITIONER_NULL_ERROR = "Booking practitioner cannot be null";
    private static final String DATE_IN_PAST_ERROR = "Booking start date cannot be in the past";
    private static final String TIME_IN_PAST_ERROR = "Booking start time cannot be in the past";

    private final UUID id;
    private final LocalDate date;
    private final LocalTime startTime;
    private final Patient patient;
    private final Practitioner practitioner;

    /**
     * This is the preferred way to create a new Booking as it ensures the Booking is in the future.
     *
     * @param afterThisDateTime the date that the booking must start after; must not be null
     * @param date the date of the booking; must not be null or in the past
     * @param startTime the start time of the booking; must not be null or in the past
     * @param patient the patient associated with the booking; must not be null
     * @param practitioner the practitioner associated with the booking; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    public static Booking createFutureBooking(
            LocalDateTime afterThisDateTime,
            LocalDate date,
            LocalTime startTime,
            Patient patient,
            Practitioner practitioner) {
        if (afterThisDateTime == null)
            throw new IllegalArgumentException(AFTER_NULL_ERROR);
        if (date == null)
            throw new IllegalArgumentException(DATE_NULL_ERROR);
        if (startTime == null)
            throw new IllegalArgumentException(TIME_NULL_ERROR);
        // The minimum start date MUST be before the requested booking date at start of day
        if (date.isBefore(afterThisDateTime.toLocalDate()))
            throw new IllegalArgumentException(DATE_IN_PAST_ERROR);
        // The minimum start date/time MUST be before the requested booking date/time
        if (afterThisDateTime.toLocalDate().isEqual(date) &&
                !afterThisDateTime.isBefore(date.atTime(startTime)))
            throw new IllegalArgumentException(TIME_IN_PAST_ERROR);
        return new Booking(date, startTime, patient, practitioner);
    }

    /**
     * This constructor is the preferred method for creating a new Booking.
     *
     * @param date the date of the booking; must not be null or in the past
     * @param startTime the start time of the booking; must not be null or in the past
     * @param patient the patient associated with the booking; must not be null
     * @param practitioner the practitioner associated with the booking; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    Booking(LocalDate date, LocalTime startTime, Patient patient, Practitioner practitioner) {
        if (date == null)
            throw new IllegalArgumentException(DATE_NULL_ERROR);
        if (startTime == null)
            throw new IllegalArgumentException(TIME_NULL_ERROR);
        if (patient == null)
            throw new IllegalArgumentException(PATIENT_NULL_ERROR);
        if (practitioner == null)
            throw new IllegalArgumentException(PRACTITIONER_NULL_ERROR);
        this.id = UUID.randomUUID();
        this.date = date;
        this.startTime = startTime;
        this.patient = patient;
        this.practitioner = practitioner;
    }

    /**
     * This constructor would typically be used by repository classes, creating a booking from a datastore
     *
     * @param id the unique identifier for the Booking
     * @param date the date of the booking; must not be null or in the past
     * @param startTime the start time of the booking; must not be null or in the past
     * @param patient the patient associated with the booking; must not be null
     * @param practitioner the practitioner associated with the booking; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    Booking(UUID id, LocalDate date, LocalTime startTime, Patient patient, Practitioner practitioner) {
        if (id == null)
            throw new IllegalArgumentException(ID_NULL_ERROR);
        if (date == null)
            throw new IllegalArgumentException(DATE_NULL_ERROR);
        if (startTime == null)
            throw new IllegalArgumentException(TIME_NULL_ERROR);
        if (patient == null)
            throw new IllegalArgumentException(PATIENT_NULL_ERROR);
        if (practitioner == null)
            throw new IllegalArgumentException(PRACTITIONER_NULL_ERROR);
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.patient = patient;
        this.practitioner = practitioner;

    }
}
