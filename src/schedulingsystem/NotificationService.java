/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingsystem;
import java.time.LocalDateTime;
/**
 *
 * @author HP
 */
public class NotificationService {
    public static void notifyBooking(Patient p, Doctor d, LocalDateTime time, boolean emergency) {
        System.out.println("Sending notification to patient " + p.getMedicalId() + " for " + time + (emergency? " [EMERGENCY]":""));
        System.out.println("Sending notification to doctor " + d.getDoctorId() + " for " + time + (emergency? " [EMERGENCY]":""));
    }

    public static void notifyCancellation(Patient p, Doctor d, LocalDateTime time) {
        System.out.println("Sending cancellation notifications to patient " + p.getMedicalId() + " and doctor " + d.getDoctorId() + " for " + time);
    }

    public static void notifyReschedule(Patient p, Doctor d, LocalDateTime time) {
        System.out.println("Sending reschedule notifications to patient " + p.getMedicalId() + " and doctor " + d.getDoctorId() + " for " + time);
    }
}
