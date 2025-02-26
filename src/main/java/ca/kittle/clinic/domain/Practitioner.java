package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.validation.AppointmentValidationError;
import ca.kittle.clinic.domain.validation.BookingValidationError;
import io.jbock.util.Either;
import lombok.Getter;
import util.CustomValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Practitioner {

    private static final String FIRST_NAME_NULL_ERROR = "Practitioner first name cannot be null or blank";
    private static final String LAST_NAME_NULL_ERROR = "Practitioner last name cannot be null or blank";
    private static final String PHONE_NULL_ERROR = "Practitioner phone number cannot be null or blank";
    private static final String PHONE_INVALID_ERROR = "Practitioner phone number must be in the form ###-###-####";
    private static final String EMAIL_NULL_ERROR = "Practitioner email cannot be null or blank";
    private static final String EMAIL_INVALID_ERROR = "Practitioner email is invalid";

    private static final String TYPE_NULL_ERROR = "Appointment type cannot be null";
    private static final String DATE_TIME_NULL_ERROR = "Date and time for booking cannot be null";
    private static final String PATIENT_NULL_ERROR = "Patient cannot be null";
    private static final String CLINIC_NULL_ERROR = "Clinic cannot be null";

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;

    private final List<Appointment> appointments = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * This should be the preferred constructor used by the application
     *
     * @param firstName   The first name of the practitioner
     * @param lastName    The last name of the practitioner
     * @param phoneNumber The phone number of the practitioner
     * @param email       The email address of the practitioner
     */
    public Practitioner(String firstName, String lastName, String phoneNumber, String email) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException(FIRST_NAME_NULL_ERROR);
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException(LAST_NAME_NULL_ERROR);
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException(PHONE_NULL_ERROR);
        if (!CustomValidator.isValidPhoneNumber(phoneNumber))
            throw new IllegalArgumentException(PHONE_INVALID_ERROR);
        if (email == null || email.isBlank())
            throw new IllegalArgumentException(EMAIL_NULL_ERROR);
        if (!CustomValidator.isValidEmail(email))
            throw new IllegalArgumentException(EMAIL_INVALID_ERROR);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    
    /**
     * Retrieves a list of bookings for the practitioner on a specific date.
     *
     * @param forDate The date for which bookings should be retrieved.
     * @return A list of bookings that match the specified date.
     */
    public List<Booking> listBookings(LocalDate forDate) {
        return bookings.stream()
                .filter(b -> b.getDate().isEqual(forDate))
                .toList();
    }


    /**
     * Retrieves a list of available time slots for the practitioner on a specific date, based on the appointment type.
     * Ensures time slots do not overlap with existing bookings.
     *
     * @param forDate         The date to check for available time slots.
     * @param appointmentType The appointment type, which determines the duration of the appointment.
     * @return A list of available {@link LocalTime} slots on the specified date.
     */
    public List<LocalTime> availabileTimes(LocalDate forDate, Appointment.AppointmentType appointmentType) {
        List<LocalTime> times = new ArrayList<>();
        List<Booking> bookings = listBookings(forDate);
        // FIXME Need to use actual clinic hours here, not hardcoded values
        for (int hour = 9; hour < 17; hour++) {
            for (int minute = 0; minute < 60; minute += (int) Clinic.BOOKING_START_TIME_INTERVAL.toMinutes()) {
                LocalTime startTime = LocalTime.of(hour, minute);
                LocalTime endTime = startTime.plusMinutes(appointmentType.getDuration().toMinutes());
                if (!Booking.doAppointmentTimesOverlapOtherBookings(startTime, endTime, bookings))
                    times.add(startTime);
            }
        }
        return times;
    }

    /**
     * Cancels an existing booking for this practitioner.
     *
     * @param booking The booking to be canceled.
     * @return {@code true} if the booking was successfully removed, {@code false} if the booking was not found.
     */
    public boolean cancelBooking(Booking booking) {
        return bookings.remove(booking);
    }

    /**
     * Attempts to add a booking for a specific patient, clinic, appointment type, date, and start time.
     * Ensures the appointment type is valid and does not overlap an existing booking before proceeding.
     *
     * @param patient         The patient.
     * @param clinic          The clinic the appointment will be at.
     * @param appointmentType The type of appointment (e.g., STANDARD, CONSULTATION).
     * @param date            The specific date the booking is for.
     * @param startTime       The time slot for the booking.
     * @return Either<List<BookingValidationError>, Booking> either a list of validate errors or a Booking
     * @throws IllegalArgumentException if the appointment type is null.
     */
    public Either<List<BookingValidationError>, Booking> addBooking(
            Patient patient,
            Clinic clinic,
            Appointment.AppointmentType appointmentType,
            LocalDate date,
            LocalTime startTime) {

        if (patient == null)
            throw new IllegalArgumentException(PATIENT_NULL_ERROR);

        if (clinic == null)
            throw new IllegalArgumentException(CLINIC_NULL_ERROR);

        if (appointmentType == null)
            throw new IllegalArgumentException(TYPE_NULL_ERROR);

        if (date == null || startTime == null)
            throw new IllegalArgumentException(DATE_TIME_NULL_ERROR);

        Either<List<BookingValidationError>, Booking> result = Booking.createBooking(
                LocalDateTime.now(),
                clinic.getHours(),
                appointmentType,
                date,
                startTime,
                patient,
                this);
        if (result.isLeft())
            return result;

        Booking booking = result.getRight().isPresent() ? result.getRight().get() : null;
        if (booking == null)
            return Either.left(List.of(new BookingValidationError.CannotCreateBookingError()));

        List<Booking> existingBookings = listBookings(date);
        if (booking.doesBookingOverlap(existingBookings))
            return Either.left(List.of(new BookingValidationError.BookingOverlapsAnotherError()));
        bookings.add(booking);
        return Either.right(booking);
    }


    /**
     * Attempts to create an appointment for a specific booking.
     * Validates the booking details and handles errors if the appointment cannot be created.
     *
     * @param booking The booking that contains details for the appointment.
     * @return Either<List < AppointmentValidationError>, Appointment> - A list of validation errors if the appointment
     * cannot be created, or the successfully created Appointment instance.
     */
    public Either<List<AppointmentValidationError>, Appointment> createAppointment(Booking booking) {
        Either<List<AppointmentValidationError>, Appointment> result = Appointment.createAppointment(
                LocalDateTime.now(),
                booking.getAppointmentType(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getPatient(),
                this
        );
        if (result.isLeft())
            return result;

        Appointment appointment = result.getRight().isPresent() ? result.getRight().get() : null;
        if (appointment == null)
            return Either.left(List.of(new AppointmentValidationError.CannotCreateAppointmentError()));

        appointments.add(appointment);
        return Either.right(appointment);
    }

}