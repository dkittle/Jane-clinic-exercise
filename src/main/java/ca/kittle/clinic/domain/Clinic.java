package ca.kittle.clinic.domain;

import lombok.Getter;
import util.CustomValidator;

import java.time.Duration;
import java.time.LocalTime;

@Getter
public class Clinic {

    // TODO hardcoding opening and closing times for MVP, this should be externalized
    private static final LocalTime OPENING_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(17, 0);
    public static final Duration BOOKING_START_TIME_INTERVAL = Duration.ofMinutes(30);

    private static final String NAME_NULL_ERROR = "Clinic name cannot be null or blank";
    private static final String PHONE_NULL_ERROR = "Clinic phone number cannot be null or blank";
    private static final String PHONE_INVALID_ERROR = "Clinic phone number cannot be in the form ###-###-####";
    private static final String EMAIL_NULL_ERROR = "Clinic email cannot be null or blank";
    private static final String EMAIL_INVALID_ERROR = "Clinic email is invalid";
    private final ClinicHours hours = new ClinicHours(OPENING_TIME, CLOSING_TIME);
    private final String name;
    private final String phoneNumber;
    private final String email;

    /**
     * Create an instance of a Clinic
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
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
