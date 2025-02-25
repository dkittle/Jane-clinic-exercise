package ca.kittle.clinic.domain.fixtures;

import ca.kittle.clinic.domain.Patient;

import java.util.List;
import java.util.UUID;

public final class TestPatients {
    // Predefined UUIDs for test patients to ensure consistency in tests
    private static final UUID P001_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID P002_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final UUID P003_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    private static final UUID P004_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    private static final UUID P005_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");
    private static final UUID P006_ID = UUID.fromString("66666666-6666-6666-6666-666666666666");
    private static final UUID P007_ID = UUID.fromString("77777777-7777-7777-7777-777777777777");
    private static final UUID P008_ID = UUID.fromString("88888888-8888-8888-8888-888888888888");
    private static final UUID P009_ID = UUID.fromString("99999999-9999-9999-9999-999999999999");
    private static final List<Patient> ALL_PATIENTS = List.of(
            new Patient(P001_ID, "Hin-Fan", "Rose", "416-555-3421", "hinfan.rose@email.com"),
            new Patient(P002_ID, "Sangah", "Lily", "647-555-8932", "sangah.lily@email.com"),
            new Patient(P003_ID, "Ankita", "Daisy", "437-555-2314", "ankita.daisy@email.com"),
            new Patient(P004_ID, "Oscar", "Tulip", "416-555-7845", "oscar.tulip@email.com"),
            new Patient(P005_ID, "Sofia", "Iris", "647-555-1298", "sofia.iris@email.com"),
            new Patient(P006_ID, "Matt", "Dahlia", "437-555-9076", "matt.dahlia@email.com"),
            new Patient(P007_ID, "Paul", "Jasmine", "416-555-4567", "paul.jasmine@email.com"),
            new Patient(P008_ID, "Vikas", "Lotus", "647-555-3412", "vikas.lotus@email.com"),
            new Patient(P009_ID, "Sarbari", "Marigold", "437-555-8901", "sarbari.marigold@email.com")
    );

    private TestPatients() {
        // Utility class should not be instantiated
    }

    /**
     * Returns an unmodifiable list of all test patients.
     *
     * @return List of Patient instances
     */
    public static List<Patient> getAllPatients() {
        return ALL_PATIENTS;
    }
}
