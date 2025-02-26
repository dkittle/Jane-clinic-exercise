package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.validation.BookingValidationError;
import io.jbock.util.Either;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Booking {

    private static final String TYPE_NULL_ERROR = "Appointment type cannot be null";
    private static final String DATE_NULL_ERROR = "Booking date cannot be null";
    private static final String TIME_NULL_ERROR = "Booking start time cannot be null";
    private static final String PATIENT_NULL_ERROR = "Booking patient cannot be null";
    private static final String PRACTITIONER_NULL_ERROR = "Booking practitioner cannot be null";

    private final Appointment.AppointmentType appointmentType;
    private final LocalDate date;
    private final LocalTime startTime;
    private final Patient patient;
    private final Practitioner practitioner;

    /**
     * Create a new Booking.
     *
     * @param appointmentType the type of appointment (from the enumerated set)
     * @param date            the date of the booking; must not be null or in the past
     * @param startTime       the start time of the booking; must not be null or in the past
     * @param patient         the patient associated with the booking; must not be null
     * @param practitioner    the practitioner associated with the booking; must not be null
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    private Booking(Appointment.AppointmentType appointmentType, LocalDate date, LocalTime startTime, Patient patient, Practitioner practitioner) {
        if (appointmentType == null)
            throw new IllegalArgumentException(TYPE_NULL_ERROR);
        if (date == null)
            throw new IllegalArgumentException(DATE_NULL_ERROR);
        if (startTime == null)
            throw new IllegalArgumentException(TIME_NULL_ERROR);
        if (patient == null)
            throw new IllegalArgumentException(PATIENT_NULL_ERROR);
        if (practitioner == null)
            throw new IllegalArgumentException(PRACTITIONER_NULL_ERROR);
        this.date = date;
        this.appointmentType = appointmentType;
        this.startTime = startTime;
        this.patient = patient;
        this.practitioner = practitioner;
    }

    /**
     * This is the preferred way to create a new Booking as it ensures the Booking is in the future.
     *
     * @param earliestDateTime the date that the booking must start after; must not be null
     * @param clinicHours      the hours that the clinic opens and closes; must not be null
     * @param appointmentType  the type of appointment (from the enumerated set)
     * @param date             the date of the booking; must not be null or in the past
     * @param startTime        the start time of the booking; must not be null or in the past
     * @param patient          the patient associated with the booking; must not be null
     * @param practitioner     the practitioner associated with the booking; must not be null
     *
     * @return Either<List<BookingValidationError>, Booking> a list of validate errors or a Booking
     */
    public static Either<List<BookingValidationError>, Booking> createBooking(
            LocalDateTime earliestDateTime,
            ClinicHours clinicHours,
            Appointment.AppointmentType appointmentType,
            LocalDate date,
            LocalTime startTime,
            Patient patient,
            Practitioner practitioner) {

        // Do basic validation on parameters for the Booking
        List<BookingValidationError> errors =
                checkBookingParamsForNulls(
                        earliestDateTime,
                        clinicHours,
                        appointmentType,
                        date,
                        startTime,
                        patient,
                        practitioner);

        // Validate Booking business rules
        if (date != null &&
                startTime != null &&
                earliestDateTime != null &&
                clinicHours != null &&
                appointmentType != null) {
            LocalTime endTime = startTime.plus(appointmentType.getDuration());
            Optional<List<BookingValidationError>> businessRuleErrors =
                    validateBookingStartDateTimeRules(
                            earliestDateTime,
                            clinicHours,
                            date,
                            startTime,
                            endTime
                    );
            businessRuleErrors.ifPresent(errors::addAll);
        }

        return errors.isEmpty() ?
                Either.right(new Booking(appointmentType, date, startTime, patient, practitioner)) :
                Either.left(errors);

    }

    /**
     * Ensure that the basic properties for a Booking are not null
     *
     * @param afterThisDateTime the date that the booking must start after; must not be null
     * @param clinicHours       the hours that the clinic opens and closes; must not be null
     * @param appointmentType   the type of appointment (from the enumerated set)
     * @param date              the date of the booking; must not be null or in the past
     * @param startTime         the start time of the booking; must not be null or in the past
     * @param patient           the patient associated with the booking; must not be null
     * @param practitioner      the practitioner associated with the booking; must not be null
     * @return Optional List<BookingValidationError> a list of any errors found
     */
    private static List<BookingValidationError> checkBookingParamsForNulls(
            LocalDateTime afterThisDateTime,
            ClinicHours clinicHours,
            Appointment.AppointmentType appointmentType,
            LocalDate date,
            LocalTime startTime,
            Patient patient,
            Practitioner practitioner) {
        List<BookingValidationError> errors = new ArrayList<>();

        if (afterThisDateTime == null)
            errors.add(new BookingValidationError.EarliestDateNullError());
        if (clinicHours == null)
            errors.add(new BookingValidationError.ClinicHoursNullError());
        if (appointmentType == null)
            errors.add(new BookingValidationError.TypeNullError());
        if (date == null)
            errors.add(new BookingValidationError.DateNullError());
        if (startTime == null)
            errors.add(new BookingValidationError.StartTimeNullError());
        if (patient == null)
            errors.add(new BookingValidationError.PatientNullError());
        if (practitioner == null)
            errors.add(new BookingValidationError.PractitionerNullError());
        return errors;
    }

    /**
     * Ensure that the desired booking date and start time meets the following business rules:
     * - Bookings must be in the future: DateInPastError
     * - Bookings start on the hour or on the half hour: DesiredStartTimeError
     * - Bookings cannot be made within 2 hours of the appointment start time: TooSoonToAppointmentError
     * - Bookings can only be made for appointments that start and
     * end within the clinic hours: OutsideBusinessHoursError
     *
     * @return Optional List<BookingValidationError> a list of any errors found
     */
    private static Optional<List<BookingValidationError>> validateBookingStartDateTimeRules(
            LocalDateTime earliestDateTime,
            ClinicHours clinicHours,
            LocalDate bookingDate,
            LocalTime bookingStartTime,
            LocalTime bookingEndTime
    ) {
        List<BookingValidationError> errors = new ArrayList<>();
        // Booking date must be in the future
        if (bookingDate.isBefore(earliestDateTime.toLocalDate()))
            errors.add(new BookingValidationError.DateInPastError());
        // Booking date and time must be in the future
        if (bookingDate.isEqual(earliestDateTime.toLocalDate()) &&
                bookingStartTime.isBefore(earliestDateTime.toLocalTime()))
            errors.add(new BookingValidationError.TimeInPastError());
        // Bookings start on the hour or on the half hour
        if (bookingStartTime.getMinute() % Clinic.BOOKING_START_TIME_INTERVAL.toMinutes() != 0)
            errors.add(new BookingValidationError.DesiredStartTimeError());
        // Bookings cannot be made within 2 hours of the appointment start time
        if (!earliestDateTime.plusHours(2).isBefore(LocalDateTime.of(bookingDate, bookingStartTime)))
            errors.add(new BookingValidationError.TooSoonToAppointmentError());
        // Bookings can only be made for appointments that start within the clinic hours
        if (bookingStartTime.isBefore(clinicHours.getOpeningTime()) ||
                bookingStartTime.isAfter(clinicHours.getClosingTime()))
            errors.add(new BookingValidationError.OutsideBusinessHoursError());
        // Bookings can only be made for appointments that end within the clinic hours
        if (bookingEndTime.isAfter(clinicHours.getClosingTime()))
            errors.add(new BookingValidationError.OutsideBusinessHoursError());
        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

    public LocalTime getEndTime() {
        return this.startTime.plus(this.appointmentType.getDuration());
    }

    public boolean doesBookingOverlap(List<Booking> otherBookings) {
        // FIXME should null throw an illegal argument exception?
        if (otherBookings == null || otherBookings.isEmpty())
            return false;

        LocalTime myEndTime = this.startTime.plus(this.appointmentType.getDuration());

        // Do any other bookings start before my end time and end after my start time
        return otherBookings.stream().anyMatch(
                otherBooking ->
                        (this.date.isEqual(otherBooking.date) &&
                                doAppointmentTimesOverlap(
                                        this.startTime,
                                        myEndTime,
                                        otherBooking.startTime,
                                        otherBooking.getEndTime()))
        );
    }

    public static boolean doAppointmentTimesOverlapOtherBookings(
            LocalTime startTime,
            LocalTime endTime,
            List<Booking> otherBookings
    ) {
        // FIXME should null throw an illegal argument exception?
        if (otherBookings == null || otherBookings.isEmpty())
            return false;

        // Do any other bookings start before my end time and end after my start time
        return otherBookings.stream().anyMatch(
                otherBooking ->
                                doAppointmentTimesOverlap(
                                        startTime,
                                        endTime,
                                        otherBooking.startTime,
                                        otherBooking.getEndTime())
        );
    }

    public static boolean doAppointmentTimesOverlap(
            LocalTime firstStartTime,
            LocalTime firstEndTime,
            LocalTime secondStartTime,
            LocalTime secondEndTime) {
                return secondStartTime.isBefore(firstEndTime) && secondEndTime.isAfter(firstStartTime);
    }
}
