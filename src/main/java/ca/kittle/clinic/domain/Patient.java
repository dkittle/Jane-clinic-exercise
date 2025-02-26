package ca.kittle.clinic.domain;

import lombok.Getter;
import util.CustomValidator;

import java.util.UUID;

@Getter
public class Patient {

    private static final String ID_NULL_ERROR = "Patient ID cannot be null";
    private static final String FIRST_NAME_NULL_ERROR = "Patient first name cannot be null or blank";
    private static final String LAST_NAME_NULL_ERROR = "Patient last name cannot be null or blank";
    private static final String PHONE_NULL_ERROR = "Patient phone number cannot be null or blank";
    private static final String PHONE_INVALID_ERROR = "Patient phone number must be in the form ###-###-####";
    private static final String EMAIL_NULL_ERROR = "Patient email cannot be null or blank";
    private static final String EMAIL_INVALID_ERROR = "Patient email is invalid";

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;

    /**
     * This should be the preferred constructor used by the application
     *
     * @param firstName   The first name of the patient
     * @param lastName    The last name of the patient
     * @param phoneNumber The phone number of the patient
     * @param email       The email address of the patient
     */
    public Patient(String firstName, String lastName, String phoneNumber, String email) {
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
     * This constructor would typically be used by repository classes, creating a patient from a datastore
     *
     * @param id          The UUID of the patient
     * @param firstName   The first name of the patient
     * @param lastName    The last name of the patient
     * @param phoneNumber The phone number of the patient
     * @param email       The email address of the patient
     */
    public Patient(UUID id, String firstName, String lastName, String phoneNumber, String email) {
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
}