package ca.kittle.clinic.domain;

import ca.kittle.clinic.domain.fixtures.TestClinic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ca.kittle.clinic.domain.fixtures.TestClinic.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClinicTest {


    @Test
    @DisplayName("Should be able to create a clinic with valid data")
    void shouldCreateClinicWithValidData() {
        Clinic clinic = TestClinic.TEST_CLINIC;
        assertEquals(CLINIC_NAME, clinic.getName());
        assertEquals(PHONE_NUMBER, clinic.getPhoneNumber());
        assertEquals(EMAIL, clinic.getEmail());
    }


    @Test
    @DisplayName("Should throw exception when clinic name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clinic(null, PHONE_NUMBER, EMAIL)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic phone number is null")
    void shouldThrowExceptionWhenPhoneNumberIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clinic(CLINIC_NAME, null, EMAIL)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic phone number is invalid")
    void shouldThrowExceptionWhenPhoneNumberIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clinic(CLINIC_NAME, "4165551212", EMAIL)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(CLINIC_NAME, PHONE_NUMBER, null)
        );
    }

    @Test
    @DisplayName("Should throw exception when clinic email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                new Clinic(CLINIC_NAME, PHONE_NUMBER, "clinic-email.com")
        );
    }
}