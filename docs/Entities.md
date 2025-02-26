
```mermaid
---
title: Appointment class
---
classDiagram
    class Appointment{
        +AppointmentType type
        +Duration durationInMinutes
        +bool isConsultation
        +LocalDate date
        +LocalTime startTime
        +Practitioner practitioner
        +Patient patient
        +String notes
    }
```

`AppointmentType` is an enum of `Consultation`, `StandardAppointment`, and `CheckinAppointment`
`durationInMinutes` and `isConsultation` are read only properties delegated to AppointmentType.


```mermaid
---
title: Booking class
---
classDiagram
    class Booking{
        +AppointmentType appointmentType
        +LocalDate date
        +LocalTime startTime
        +Practitioner practitioner
        +Patient patient
    }
```

```mermaid
---
title: Clinic class
---
classDiagram
    class Clinic{
        +String name
        +ClinicHours clinicHours
        +String phoneNumber
        +String email
    }
```

```mermaid
---
title: Clinic Hours class
---
classDiagram
    class ClinicHours{
        +LocalTime openingTime
        +LocalTime closingTime
    }
```

```mermaid
---
title: Patient class
---
classDiagram
    class Patient{
        +String firstName
        +String lastName
        +String phoneNumber
        +String email
    }
```

```mermaid
---
title: Practitioner class
---
classDiagram
    class Practitioner{
        +String firstName
        +String lastName
        +String phoneNumber
        +String email
    }
```


```mermaid
---
title: Appointment (future ideas)
---
classDiagram
    Appointment <|-- Consultation
    Appointment <|-- StandardAppointment
    Appointment <|-- CheckinAppointment
    class Appointment{
        abstract Duration durationInMinutes
        abstract bool isConsultation
        +LocalDate date
        +LocalTime startTime
        +Practitioner practitioner
        +Patient patient
    }
    class Consultation{
        +Duration durationInMinutes = 90
        +bool isConsultation = true
        +Note symptoms
        +Note patientGoals
        +List<Assessment> practitionerAssessment
        +List<TreatmentRecommendation> treatmentRecommendations
    }
    class StandardAppointment{
        +Duration durationInMinutes = 60
        +bool isConsultation = false
        +Note status
        +List<Treatment> treatments
    }
    class CheckinAppointment{
        +Duration durationInMinutes = 30
        +bool isConsultation = false
        +Note status
        +List<Treatment> treatments
        +List<TreatmentRecommendation> treatmentRecommendations
    }
```
