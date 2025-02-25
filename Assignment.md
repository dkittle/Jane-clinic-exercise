# Jane Physiotherapy Clinic Scheduling Exercise

## Overview

Goal of this exercise is to evaluate your coding style, code design skills, code efficiency, use of data structures and clarity of your tests

- Ability to write code to solve an abstract problem.
- Clear, consistent and idiomatic coding style.
- Use of appropriate design patterns, data structures and algorithms.
- Efficiency of the solution.
- Approach to automated testing.
- PR write up
  - Documentation / README.
  - Code changes in commits and commit messages.

## Assignment Requirements

- [ ] Include a set of unit tests
- [ ] Submitted in a Git repository
    - [ ] Clear documentation on how to run the tests
    - [ ] Assumptions that you’ve made
    - [ ] Missing details that would complete the solution

## Product Requirements

Design and implement the core components of an on-line scheduling application for a physiotherapy clinic.

- [ ] The clinic is open from 9am until 5pm.
- [ ] The clinic offers three types of appointments:
  - [x] a 90 minutes initial consultation, 
  - [x] standard 60 minute appointments and,
  - [x] 30 minute check-ins.
- [ ] Appointments do not overlap. There can only be one booked appointment at any time.
- [ ] Appointments start on:
  - [ ] the hour or,
  - [ ] half-hour.
- [ ] Bookings can only be made for appointments that start and end within the clinic hours.
- [ ] Bookings cannot be made within 2 hours of the appointment start time.

## Use Cases

### Patient

- [ ] Provide the patient with a list of available appointment times. Inputs are the appointment type and a date, either today or in the future. The 2 hour booking deadline applies for today’s appointments.
- [ ] Allow the patient to book an appointment.

### Practitioner

- [ ] List scheduled appointments for the current day.

