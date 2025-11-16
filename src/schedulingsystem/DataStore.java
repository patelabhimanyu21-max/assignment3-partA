/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingsystem;

import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final DataStore instance = new DataStore();
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    private DataStore(){}

    public static DataStore getInstance() {
        return instance;
    }

    public void addDoctor(Doctor d){ doctors.add(d); }
    public void addPatient(Patient p){ patients.add(p); }
    public void addAppointment(Appointment a){ appointments.add(a); }

    public Doctor getDoctor(String id){
        return doctors.stream().filter(d -> d.getDoctorId().equals(id)).findFirst().orElse(null);
    }

    public Patient getPatient(String id){
        return patients.stream().filter(p -> p.getMedicalId().equals(id)).findFirst().orElse(null);
    }

    public Appointment getAppointment(String id){
        return appointments.stream().filter(a -> a.getAppointmentId().equals(id)).findFirst().orElse(null);
    }

    public List<Appointment> getAppointments() { return appointments; }
}
