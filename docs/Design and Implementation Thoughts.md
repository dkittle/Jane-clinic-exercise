# Design/Impl Questions and Thoughts

## Assumptions

- I don't need to associate the practitioner with the clinic as there is only one practitioner per clinic to begin with and we will only support one clinic for the mvp.
- All clinics, patients, and practitioners will use North American phone numbers in the form ###-###-####.
- Should really use a Validator from hibernate validations but it can be done later.
- 

## Domain Modelling

**Should patient and practitioner be a part of both Appointment and Booking classes?**  
It seems, in an absolute minimal MVP, we should likely only have patient and practitioner associated with the Booking, and not necessarily with the Appointment.

Yet my wife gave up her appointment with a practitioner so that I could have urgently needed care at a clinic IRL (a clinic that uses Jane App) so I really want to include them in both class definitions. I realize that this could be construed as building for "future requirements", though :smile:

**Should any class have clinic?**  
Hmm, for a first release, there is only one clinic so technically we don't need to associate it with practitioners (and, by association, to bookings and appointments).

I'll treat it as a nice to have (and call this building for the *near* future). Hmm, should a practitioner work at more than one clinic? I know some RMTs do IRL... More reason to defer it for now, as it's likely a domain question.

**Appointment Type in Booking seems to couple Booking to Appointment**  
This bothers me; I'd love to debate it with someone. I'll leave the coupling for now, but it bothers me.


## Domain Questions

**When should an appointment be created?**  
It seems like the appointment represents the meeting between the patient and practitioner and likely should only be created when the patient arrives at the clinic. Creating it at the time of the booking  creates some small technical headaches (necessitating "cascading" updates between bookings and appointments if a booking is cancelled, for example). I think creating them at the same time is unnecessary coupling.

**Is a Consultation REQUIRED before a patient can book a standard or check-in appointment**  
I'm assuming this the case, and I'm accommodating for it in my solution with a "isConsultation" flag in the Appointment class via the Appointment Type enumeration.

**Should a standard appointment having been attended be REQUIRED before booking a check-in?**  
I'm assuming no. Perhaps a patient had a consultation but didn't follow up with treatment and now seeks a check-in on their condition.


## Next Steps

- Implement a timer to "hold" a booking date/time while the patient completes the booking process.

- Have a status on the Booking class. Could be:  
PENDING (from when date/time is selected to when booking is confirmed by patient)  
CONFIRMED (confirmed by patient but Appointment is still in the future)  
CANCELLED (cancelled by patient online or by contacting clinic)  
NO_SHOW (patient didn't show up for appointment)  
COMPLETED (appointment happened)  
RESCHEDULED (is this needed?)

