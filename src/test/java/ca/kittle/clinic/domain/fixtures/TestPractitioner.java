package ca.kittle.clinic.domain.fixtures;

import ca.kittle.clinic.domain.Practitioner;

public class TestPractitioner {
    public static final Practitioner CHERIA =
            new Practitioner(
                    "Cheria",
                    "Lee",
                    "416-555-1111",
                    "cheria.lee@email.com");

    private TestPractitioner() {
        // Utility class should not be instantiated
    }

}
