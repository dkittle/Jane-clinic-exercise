package ca.kittle.clinic.domain.fixtures;

import ca.kittle.clinic.domain.Clinic;

public class TestClinic {
    private static final String CLINIC_NAME = "Test Clinic";
    private static final String PHONE_NUMBER = "123-456-7890";
    private static final String EMAIL = "testclinic@email.com";

    public static final Clinic TEST_CLINIC = new Clinic(CLINIC_NAME, PHONE_NUMBER, EMAIL);

}
