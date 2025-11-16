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


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String appointmentId;
    private LocalDateTime dateTime;
    private String status;
    private Patient patient;
    private Doctor doctor;
    private String type;
    private boolean isEmergency;

    public Appointment(String appointmentId, Patient patient, Doctor doctor, LocalDateTime dateTime, boolean isEmergency) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = "Confirmed";
        this.isEmergency = isEmergency;
    }

    public String getAppointmentId() { return appointmentId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public boolean isEmergency() { return isEmergency; }
    public void setDateTime(LocalDateTime dt) { this.dateTime = dt; }
    public void cancel() { this.status = "Cancelled"; }
    public String briefString(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return appointmentId + " - " + patient.getMedicalId() + " - " + fmt.format(dateTime) + (isEmergency? " (EMERGENCY)":"");
    }
}
