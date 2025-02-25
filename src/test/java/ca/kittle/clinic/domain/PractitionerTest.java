package ca.kittle.clinic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
    }

    @Test
    @DisplayName("Should be able to create a practitioner with valid data and specific ID")
    void shouldCreatePractitionerWithValidDataAndId() {
        UUID id = UUID.randomUUID();
        Practitioner practitioner = new Practitioner(id, FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL);

        assertEquals(id, practitioner.getId());
        assertEquals(FIRST_NAME, practitioner.getFirstName());
        assertEquals(LAST_NAME, practitioner.getLastName());
        assertEquals(PHONE_NUMBER, practitioner.getPhoneNumber());
        assertEquals(EMAIL, practitioner.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when first name is null")
    void shouldThrowExceptionWhenFirstNameIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(null, LAST_NAME, PHONE_NUMBER, EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, null, LAST_NAME, PHONE_NUMBER, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when first name is blank")
    void shouldThrowExceptionWhenFirstNameIsBlank() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner("  ", LAST_NAME, PHONE_NUMBER, EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, "  ", LAST_NAME, PHONE_NUMBER, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when last name is null")
    void shouldThrowExceptionWhenLastNameIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, null, PHONE_NUMBER, EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, null, PHONE_NUMBER, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when last name is blank")
    void shouldThrowExceptionWhenLastNameIsBlank() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, "  ", PHONE_NUMBER, EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, "  ", PHONE_NUMBER, EMAIL));
    }

    // TODO Candidate for parameterized test
    @Test
    @DisplayName("Should throw exception when phone number is null")
    void shouldThrowExceptionWhenPhoneNumberIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, null, EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, LAST_NAME, null, EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when phone number is blank")
    void shouldThrowExceptionWhenPhoneNumberIsBlank() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, "  ", EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, LAST_NAME, "  ", EMAIL));
    }

    @Test
    @DisplayName("Should throw exception when phone number is invalid")
    void shouldThrowExceptionWhenPhoneNumberIsInvalid() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, "123-456-789", EMAIL));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, LAST_NAME, "123-456-789", EMAIL));
    }

    // TODO Candidate for parameterized test
    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, null));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, LAST_NAME, PHONE_NUMBER, null));
    }

    @Test
    @DisplayName("Should throw exception when email is blank")
    void shouldThrowExceptionWhenEmailIsBlank() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, "  "));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, LAST_NAME, PHONE_NUMBER, "  "));
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(FIRST_NAME, LAST_NAME, PHONE_NUMBER, "invalid-email"));
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(id, FIRST_NAME, LAST_NAME, PHONE_NUMBER, "invalid-email"));
    }

    @Test
    @DisplayName("Should throw exception when ID is null in repository constructor")
    void shouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Practitioner(null, FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL));
    }

}