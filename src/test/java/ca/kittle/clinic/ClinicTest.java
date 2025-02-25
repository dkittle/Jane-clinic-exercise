package ca.kittle.clinic;

import ca.kittle.clinic.domain.Clinic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClinicTest {

    private static final String CLINIC_NAME = "Test Clinic";
    private static final String PHONE_NUMBER = "123-456-7890";
    private static final String EMAIL = "testclinic@email.com";

    @Test
    @DisplayName("Should be able to create a clinic with valid data")
    void shouldCreateClinicWithValidData() {
        Clinic clinic =
                new Clinic(CLINIC_NAME, PHONE_NUMBER, EMAIL);
        assertEquals(CLINIC_NAME, clinic.getName());
        assertEquals(PHONE_NUMBER, clinic.getPhoneNumber());
        assertEquals(EMAIL, clinic.getEmail());
    }

    @Test
    @DisplayName("Should be able to create a clinic with valid data including ID")
    void shouldCreateClinicWithValidDataIncId() {
        UUID id = UUID.randomUUID();
        Clinic clinic =
                new Clinic(id, CLINIC_NAME, PHONE_NUMBER, EMAIL);
        assertEquals(id, clinic.getId());
        assertEquals(CLINIC_NAME, clinic.getName());
        assertEquals(PHONE_NUMBER, clinic.getPhoneNumber());
        assertEquals(EMAIL, clinic.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when clinic name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(null, PHONE_NUMBER, EMAIL)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(id, null, PHONE_NUMBER, EMAIL)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic phone number is null")
    void shouldThrowExceptionWhenPhoneNumberIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(CLINIC_NAME, null, EMAIL)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(id, CLINIC_NAME, null, EMAIL)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic phone number is invalid")
    void shouldThrowExceptionWhenPhoneNumberIsInvalid() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(CLINIC_NAME, "4165551212", EMAIL)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(id, CLINIC_NAME, "4165551212", EMAIL)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(CLINIC_NAME, PHONE_NUMBER, null)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(id, CLINIC_NAME, PHONE_NUMBER, null)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        UUID id = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(CLINIC_NAME, PHONE_NUMBER, "clinicemail.com")
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(id, CLINIC_NAME, PHONE_NUMBER, "clinicemail.com")
        );
    }
}
