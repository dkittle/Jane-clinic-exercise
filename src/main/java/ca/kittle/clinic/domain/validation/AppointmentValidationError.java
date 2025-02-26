package ca.kittle.clinic.domain.validation;

public sealed interface AppointmentValidationError {

    record TypeNullError() implements AppointmentValidationError {
    }

    record DateNullError() implements AppointmentValidationError {
    }

    record ClinicHoursNullError() implements AppointmentValidationError {
    }

    record EarliestDateNullError() implements AppointmentValidationError {
    }

    record StartTimeNullError() implements AppointmentValidationError {
    }

    record DesiredStartTimeError() implements AppointmentValidationError {
    }

    record TooSoonToAppointmentError() implements AppointmentValidationError {
    }

    record PatientNullError() implements AppointmentValidationError {
    }

    record PractitionerNullError() implements AppointmentValidationError {
    }

    record DateInPastError() implements AppointmentValidationError {
    }

    record TimeInPastError() implements AppointmentValidationError {
    }

    record OutsideBusinessHoursError() implements AppointmentValidationError {
    }
}
