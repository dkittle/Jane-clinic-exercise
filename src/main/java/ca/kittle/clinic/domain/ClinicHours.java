package ca.kittle.clinic.domain;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ClinicHours {

    private final LocalTime openingTime;
    private final LocalTime closingTime;

    public ClinicHours(LocalTime openingTime, LocalTime closingTime) {
        if (openingTime == null)
            throw new IllegalArgumentException("Opening time cannot be null ");
        if (closingTime == null)
            throw new IllegalArgumentException("Closing time cannot be null");
        if (!closingTime.isAfter(openingTime))
            throw new IllegalArgumentException("Closing time must be after opening time");
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
