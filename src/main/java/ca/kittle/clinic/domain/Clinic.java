package ca.kittle.clinic.domain;

import lombok.Getter;
import util.CustomValidator;

import java.time.LocalTime;
import java.util.UUID;

@Getter
public class Clinic {

    // TODO hardcoding opening and closing times for MVP, this should be externalized
    private static final LocalTime OPENING_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(17, 0);

    private static final String ID_NULL_ERROR = "Clinic ID cannot be null or blank";
    private static final String NAME_NULL_ERROR = "Clinic name cannot be null or blank";
    private static final String PHONE_NULL_ERROR = "Clinic phone number cannot be null or blank";
    private static final String PHONE_INVALID_ERROR = "Clinic phone number cannot be in the form ###-###-####";
    private static final String EMAIL_NULL_ERROR = "Clinic email cannot be null or blank";
    private static final String EMAIL_INVALID_ERROR = "Clinic email is invalid";

    private final UUID id;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private static final LocalTime openingTime = OPENING_TIME;
    private static final LocalTime closingTime = CLOSING_TIME;

    /**
     * This should be the preferred constructor used by the application
     *
     * @param name        The name of the clinic
     * @param phoneNumber The phone number of the clinic
     * @param email       The email address of the clinic
     */
    public Clinic(String name, String phoneNumber, String email) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException(NAME_NULL_ERROR);
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException(PHONE_NULL_ERROR);
        if (!CustomValidator.isValidPhoneNumber(phoneNumber))
            throw new IllegalArgumentException(PHONE_INVALID_ERROR);
        if (email == null || email.isBlank())
            throw new IllegalArgumentException(EMAIL_NULL_ERROR);
        if (!CustomValidator.isValidEmail(email))
            throw new IllegalArgumentException(EMAIL_INVALID_ERROR);
        this.id = UUID.randomUUID();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * This constructor would typically be used by repository classes, creating a clinic from a datastore
     *
     * @param name        The name of the clinic
     * @param phoneNumber The phone number of the clinic
     * @param email       The email address of the clinic
     */
    public Clinic(UUID id, String name, String phoneNumber, String email) {
        if (id == null)
            throw new IllegalArgumentException(ID_NULL_ERROR);
        if (name == null || name.isBlank())
            throw new IllegalArgumentException(NAME_NULL_ERROR);
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException(PHONE_NULL_ERROR);
        if (!CustomValidator.isValidPhoneNumber(phoneNumber))
            throw new IllegalArgumentException(PHONE_INVALID_ERROR);
        if (email == null || email.isBlank())
            throw new IllegalArgumentException(EMAIL_NULL_ERROR);
        if (!CustomValidator.isValidEmail(email))
            throw new IllegalArgumentException(EMAIL_INVALID_ERROR);
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
