package ca.kittle.clinic.domain.validation;

public sealed interface BookingValidationError {

    record CannotCreateBookingError() implements BookingValidationError {
    }

    record BookingOverlapsAnotherError() implements BookingValidationError {
    }

    record TypeNullError() implements BookingValidationError {
    }

    record DateNullError() implements BookingValidationError {
    }

    record ClinicHoursNullError() implements BookingValidationError {
    }

    record EarliestDateNullError() implements BookingValidationError {
    }

    record StartTimeNullError() implements BookingValidationError {
    }

    record DesiredStartTimeError() implements BookingValidationError {
    }

    record TooSoonToAppointmentError() implements BookingValidationError {
    }

    record PatientNullError() implements BookingValidationError {
    }

    record PractitionerNullError() implements BookingValidationError {
    }

    record DateInPastError() implements BookingValidationError {
    }

    record TimeInPastError() implements BookingValidationError {
    }

    record OutsideBusinessHoursError() implements BookingValidationError {
    }
}

