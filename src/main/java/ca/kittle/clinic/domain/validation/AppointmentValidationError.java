package ca.kittle.clinic.domain.validation;

public sealed interface AppointmentValidationError {

    record TypeNullError() implements AppointmentValidationError {
    }

    record CannotCreateAppointmentError() implements AppointmentValidationError {
    }

    record DateNullError() implements AppointmentValidationError {
    }

    record EarliestDateNullError() implements AppointmentValidationError {
    }

    record StartTimeNullError() implements AppointmentValidationError {
    }

    record PatientNullError() implements AppointmentValidationError {
    }

    record PractitionerNullError() implements AppointmentValidationError {
    }
}

