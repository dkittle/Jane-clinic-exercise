package ca.kittle.clinic.domain;

import lombok.Builder;
import lombok.Getter;
import util.CustomValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Practitioner {

    private static final String ID_NULL_ERROR = "Practitioner ID cannot be null";
    private static final String FIRST_NAME_NULL_ERROR = "Practitioner first name cannot be null or blank";
    private static final String LAST_NAME_NULL_ERROR = "Practitioner last name cannot be null or blank";
    private static final String PHONE_NULL_ERROR = "Practitioner phone number cannot be null or blank";
    private static final String PHONE_INVALID_ERROR = "Practitioner phone number must be in the form ###-###-####";
    private static final String EMAIL_NULL_ERROR = "Practitioner email cannot be null or blank";
    private static final String EMAIL_INVALID_ERROR = "Practitioner email is invalid";
    private static final String BOOKING_NULL_ERROR = "Booking cannot be null";

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;

    private final List<Booking> bookings = new ArrayList<>();

    /**
     * This should be the preferred constructor used by the application
     *
     * @param firstName   The first name of the practitioner
     * @param lastName    The last name of the practitioner
     * @param phoneNumber The phone number of the practitioner; must be in the form ###-###-####
     * @param email       The email address of the practitioner; must be in the simple form xxx@yyy.com
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
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * This constructor would typically be used by repository classes, creating a practitioner from a datastore
     *
     * @param id          The UUID of the practitioner
     * @param firstName   The first name of the practitioner
     * @param lastName    The last name of the practitioner
     * @param phoneNumber The phone number of the practitioner; must be in the form ###-###-####
     * @param email       The email address of the practitioner; must be in the simple form xxx@yyy.com
     */
    public Practitioner(UUID id, String firstName, String lastName, String phoneNumber, String email) {
        if (id == null)
            throw new IllegalArgumentException(ID_NULL_ERROR);
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
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public List<Booking> listBookings(LocalDate forDate) {
        return bookings.stream()
                .filter(b -> b.getDate().isEqual(forDate))
                .toList();
    }

//    public boolean addBooking(Booking booking) {
//        if (booking == null)
//            throw new IllegalArgumentException(BOOKING_NULL_ERROR);
//
//        // TODO check clinic hours here
//
//    }

    // Where should this business rule live??
//    private boolean validateBookingStartTime(Booking booking) {
//
//    }
}