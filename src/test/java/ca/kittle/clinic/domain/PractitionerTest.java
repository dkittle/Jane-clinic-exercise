package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.fixtures.TestPractitioner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PractitionerTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PHONE_NUMBER = "123-456-7890";
    private static final String EMAIL = "john.doe@email.com";

    @Test
    @DisplayName("Should be able to create a practitioner with valid data")
    void shouldCreatePractitionerWithValidData() {
        Practitioner practitioner = new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL);

        assertEquals(FIRST_NAME, practitioner.getFirstName());
        assertEquals(LAST_NAME, practitioner.getLastName());
        assertEquals(PHONE_NUMBER, practitioner.getPhoneNumber());
        assertEquals(EMAIL, practitioner.getEmail());

        Practitioner cheria = TestPractitioner.CHERIA;
        assertEquals("Cheria", cheria.getFirstName());
        assertEquals("Lee", cheria.getLastName());
    }

    @Test
    @DisplayName("Should throw exception when first name is null")
    void shouldThrowExceptionWhenFirstNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(null, LAST_NAME, PHONE_NUMBER, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when first name is blank")
    void shouldThrowExceptionWhenFirstNameIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner("  ", LAST_NAME, PHONE_NUMBER, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when last name is null")
    void shouldThrowExceptionWhenLastNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, null, PHONE_NUMBER, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when last name is blank")
    void shouldThrowExceptionWhenLastNameIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, "  ", PHONE_NUMBER, EMAIL));
    }

    // TODO Candidate for parameterized test
    @Test
    @DisplayName("Should throw exception when phone number is null")
    void shouldThrowExceptionWhenPhoneNumberIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, null, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when phone number is blank")
    void shouldThrowExceptionWhenPhoneNumberIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, "  ", EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when phone number is invalid")
    void shouldThrowExceptionWhenPhoneNumberIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, "123-456-789", EMAIL));
    }

    // TODO Candidate for parameterized test
    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, null));
    }

    @Test
    @DisplayName("Should throw exception when email is blank")
    void shouldThrowExceptionWhenEmailIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, "  "));
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, "invalid-email"));
    }

}