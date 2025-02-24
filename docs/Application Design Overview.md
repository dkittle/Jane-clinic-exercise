# Jane Client Scheduling Assignment ~ Design Overview 

## Domain Model

### Entities
**Appointment**  
Represents a patient's meeting with a practitioner.

**Booking**  
Time and date for an appointment between a specific patient and practitioner.

**Clinic**  
The practice where the practitioner works and where appointments are held.

**Patient**  
The person seeking care.

**Practitioner**  
The person providing care.

## MVP Scenarios

### Patient
View available appointment times
Book an appointment

### Practitioner
View scheduled appointments for the current day

## Business Rules

### Clinic

- open from 9am until 5pm

### Appointments

- 3 types:
    - 90 minute consultation,
    - 60 minute standard appointment, and
    - 30 minute check-in
- Start on the hour or half hour
- Cannot overlap

### Bookings

- Can only be made for appointments that start and end within the clinic hours
- Cannot be made within 2 hours of the appointment start time

