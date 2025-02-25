package ca.kittle.clinic.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.CustomValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CustomValidatorTest {
    private static final String PHONE_NUMBER = "416-555-1111";

    @Test
    @DisplayName("Test valid North American phone number")
    void testValidPhoneNumber() {
        assert CustomValidator.isValidPhoneNumber(PHONE_NUMBER);
    }

    @Test
    @DisplayName("Test phone number with no hyphens")
    void testNoHyphensPhoneNumber() {
        assertFalse(CustomValidator.isValidPhoneNumber("4165551111"));
    }

    // TODO Candidate for property based testing
    @Test
    @DisplayName("Test phone number with mispositioned hyphens")
    void testWrongPositionedHyphensPhoneNumber() {
        assertFalse(CustomValidator.isValidPhoneNumber("41-65551-111"));
        assertFalse(CustomValidator.isValidPhoneNumber("416-5551111-"));
        assertFalse(CustomValidator.isValidPhoneNumber("-416555-1111"));
        assertFalse(CustomValidator.isValidPhoneNumber("-4165551111-"));
    }


    // TODO Candidate for property based testing
    @Test
    @DisplayName("Test phone number with letters")
    void testPhoneNumberWithLetters() {
        // b in place of 6
        assertFalse(CustomValidator.isValidPhoneNumber("41b-555-1111"));
        // S in place of a 5
        assertFalse(CustomValidator.isValidPhoneNumber("416-S55-1111"));
        // I in place of a 1
        assertFalse(CustomValidator.isValidPhoneNumber("416-555-I111"));
        // l (lower case L) in place of a 1
        assertFalse(CustomValidator.isValidPhoneNumber("416-555-111l"));
    }

    @Test
    @DisplayName("Test valid email")
    void testValidEmail() {
        assert CustomValidator.isValidEmail("clinic@email.com");
        assert CustomValidator.isValidEmail("clinic@email.a");
        assert CustomValidator.isValidEmail("clinic@e.a");
    }

    @Test
    @DisplayName("Test phone number with no @ sign")
    void testEmailWithoutAt() {
        assertFalse(CustomValidator.isValidEmail("clinicATemail.com"));
        assertFalse(CustomValidator.isValidEmail("clinic[at]email.com"));
        assertFalse(CustomValidator.isValidEmail("clinic [ at ] email.com"));
    }
}
