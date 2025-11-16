/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingsystem;

/**
 *
 * @author HP
 */
import java.time.*;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String scheduleId;
    private Doctor doctor;
    private List<LocalDateTime> availableSlots = new ArrayList<>();
    private List<LocalDateTime> bookedSlots = new ArrayList<>();
    private int maxDailyLimit = 10;
    private LocalTime start = LocalTime.of(9,0);
    private LocalTime end = LocalTime.of(16,0);

    public Schedule(String id, Doctor doc){
        this.scheduleId = id;
        this.doctor = doc;
    }

    public void addAvailabilityForDays(int days) {
        LocalDate d = LocalDate.now().plusDays(1);
        for (int k=0;k<days;k++) {
            LocalDate day = d.plusDays(k);
            for (LocalTime t = start; t.isBefore(end.plusMinutes(1)); t = t.plusHours(1)) {
                availableSlots.add(LocalDateTime.of(day, t));
            }
        }
    }

    public boolean validateSlot(LocalDateTime t){
        return availableSlots.contains(t) && !bookedSlots.contains(t);
    }

    public void bookSlot(LocalDateTime t){
        if (availableSlots.contains(t) && !bookedSlots.contains(t)) {
            bookedSlots.add(t);
        }
    }

    public void freeSlot(LocalDateTime t){
        bookedSlots.remove(t);
        // ensure it's present in available list (it already should), nothing else necessary
    }

    public LocalDateTime findNextAvailableOnOrAfter(LocalDateTime t){
        return availableSlots.stream()
                .filter(s -> !bookedSlots.contains(s) && (s.isEqual(t) || s.isAfter(t)))
                .findFirst().orElse(null);
    }

    public List<LocalDateTime> getAvailableSlots() { return availableSlots; }
    public List<LocalDateTime> getBookedSlots() { return bookedSlots; }
}
