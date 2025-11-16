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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {
    private final DataStore store = DataStore.getInstance();

    public void book(String pid, String did, String dateStr, String timeStr, boolean emergency) {
        Patient p = store.getPatient(pid);
        Doctor d = store.getDoctor(did);
        if (p == null) {
            System.out.println("Invalid patient ID.");
            return;
        }
        if (d == null) {
            System.out.println("Invalid doctor ID.");
            return;
        }

        LocalDate dt;
        LocalTime tm;
        try {
            dt = LocalDate.parse(dateStr);
            tm = LocalTime.parse(timeStr);
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid date/time format.");
            return;
        }
        LocalDateTime desired = LocalDateTime.of(dt, tm);

        // Validation: working hours (9-16) and not in past
        if (tm.isBefore(LocalTime.of(9,0)) || tm.isAfter(LocalTime.of(16,0))) {
            System.out.println("Requested time is outside working hours (09:00-16:00).");
            return;
        }
        if (desired.isBefore(LocalDateTime.now())) {
            System.out.println("Cannot book an appointment in the past.");
            return;
        }

        // daily limit check
        List<Appointment> doctorToday = store.getAppointments().stream()
                .filter(a -> a.getDoctor().getDoctorId().equals(did)
                        && a.getDateTime().toLocalDate().isEqual(dt)
                        && !a.isEmergency())
                .collect(Collectors.toList());

        if (doctorToday.size() >= d.getDailyLimit() && !emergency) {
            System.out.println("Doctor has reached max appointments for that day.");
            return;
        }

        // if emergency -> override logic
        if (emergency) {
            System.out.println("Emergency booking requested. Applying override logic...");
            // if requested slot is free -> book
            if (d.getSchedule().validateSlot(desired)) {
                createAndRegisterAppointment(p, d, desired, true);
                System.out.println("Emergency appointment booked at: " + desired);
                NotificationService.notifyBooking(p, d, desired, true);
                return;
            } else {
                // override: find earliest booked slot at or after desired and shift it forward
                LocalDateTime slotToOverride = d.getSchedule().findNextAvailableOnOrAfter(desired);
                if (slotToOverride == null) {
                    // no slot available at or after, try earliest overall (wrap)
                    System.out.println("No slots available to override; attempting to find next free slot...");
                    LocalDateTime fallback = d.getSchedule().findNextAvailableOnOrAfter(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(9,0)));
                    if (fallback == null) {
                        System.out.println("No slots available for emergency booking.");
                        return;
                    } else {
                        createAndRegisterAppointment(p, d, fallback, true);
                        System.out.println("Emergency appointment booked at fallback: " + fallback);
                        NotificationService.notifyBooking(p, d, fallback, true);
                        return;
                    }
                } else {
                    // shift the existing booking (if exists) forward by one hour if possible
                    Appointment existing = store.getAppointments().stream()
                            .filter(a -> a.getDoctor().getDoctorId().equals(did) && a.getDateTime().isEqual(slotToOverride))
                            .findFirst().orElse(null);

                    if (existing != null) {
                        LocalDateTime newSlot = d.getSchedule().findNextAvailableOnOrAfter(slotToOverride.plusHours(1));
                        if (newSlot != null) {
                            // move existing appointment to newSlot
                            existing.setDateTime(newSlot);
                            d.getSchedule().bookSlot(newSlot);
                            System.out.println("Shifted appointment " + existing.getAppointmentId()
                                    + " from " + slotToOverride + " to " + newSlot + " to accommodate emergency.");
                            // create emergency appointment at slotToOverride
                            createAndRegisterAppointment(p, d, slotToOverride, true);
                            NotificationService.notifyBooking(existing.getPatient(), d, newSlot, false);
                            NotificationService.notifyBooking(p, d, slotToOverride, true);
                            return;
                        } else {
                            // cannot shift
                            System.out.println("Unable to shift existing appointment to accommodate emergency.");
                            return;
                        }
                    } else {
                        // slot is not booked but not validated? Just book
                        createAndRegisterAppointment(p, d, slotToOverride, true);
                        NotificationService.notifyBooking(p, d, slotToOverride, true);
                        return;
                    }
                }
            }
        } else {
            // normal booking
            if (!d.getSchedule().validateSlot(desired)) {
                System.out.println("Requested slot is not available. Suggesting next available...");
                LocalDateTime next = d.getSchedule().findNextAvailableOnOrAfter(desired.plusMinutes(60));
                if (next == null) {
                    System.out.println("No next available slots found.");
                } else {
                    System.out.println("Next available: " + next);
                }
                return;
            } else {
                createAndRegisterAppointment(p, d, desired, false);
                System.out.println("Appointment booked at: " + desired);
                NotificationService.notifyBooking(p, d, desired, false);
            }
        }
    }

    private void createAndRegisterAppointment(Patient p, Doctor d, LocalDateTime slot, boolean emergency) {
        String id = IdGenerator.nextAppointmentId();
        Appointment appt = new Appointment(id, p, d, slot, emergency);
        store.addAppointment(appt);
        d.getSchedule().bookSlot(slot);
    }

    public void printDoctorScheduleDetailed(String did) {
        Doctor d = store.getDoctor(did);
        if (d == null) {
            System.out.println("Doctor not found.");
            return;
        }
        System.out.println("\nDoctor: " + d.getName() + " (" + d.getDoctorId() + ") - " + d.getDailyLimit() + " max/day");
        System.out.println("Available Slots:");
        d.getSchedule().getAvailableSlots().stream()
                .filter(s -> !d.getSchedule().getBookedSlots().contains(s))
                .forEach(s -> System.out.println("  - " + s));
        System.out.println("Booked Appointments:");
        store.getAppointments().stream()
                .filter(a -> a.getDoctor().getDoctorId().equals(did))
                .forEach(a -> System.out.println("  * " + a.briefString()));
    }

    public void cancel(String apptId) {
        Appointment a = store.getAppointment(apptId);
        if (a == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }
        // free slot
        a.cancel();
        a.getDoctor().getSchedule().freeSlot(a.getDateTime());
        System.out.println("Appointment " + apptId + " cancelled.");
        NotificationService.notifyCancellation(a.getPatient(), a.getDoctor(), a.getDateTime());
    }

    public void reschedule(String apptId, String newDate, String newTime) {
        Appointment a = store.getAppointment(apptId);
        if (a == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }
        try {
            LocalDateTime ndt = LocalDateTime.of(LocalDate.parse(newDate), LocalTime.parse(newTime));
            if (!a.getDoctor().getSchedule().validateSlot(ndt)) {
                System.out.println("Desired new slot is not available.");
                return;
            }
            // free old slot
            a.getDoctor().getSchedule().freeSlot(a.getDateTime());
            // book new slot
            a.setDateTime(ndt);
            a.getDoctor().getSchedule().bookSlot(ndt);
            System.out.println("Appointment " + apptId + " rescheduled to " + ndt);
            NotificationService.notifyReschedule(a.getPatient(), a.getDoctor(), ndt);
        } catch (Exception e) {
            System.out.println("Invalid date/time.");
        }
    }

    public void printAllAppointments() {
        System.out.println("\nAll Appointments:");
        for (Appointment a : store.getAppointments()) {
            System.out.println(" - " + a.briefString());
        }
    }
}