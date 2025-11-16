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
public class Doctor extends User {
    private String doctorId;
    private String speciality;
    private int yearsExperience;
    private double rating;
    private Schedule schedule;
    private int dailyLimit;

    public Doctor(String doctorId, String name, String email, String phone,
                  String speciality, int yearsExperience, int dailyLimit) {
        this.doctorId = doctorId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.speciality = speciality;
        this.yearsExperience = yearsExperience;
        this.schedule = new Schedule(doctorId, this);
        this.dailyLimit = dailyLimit;
    }

    public String getDoctorId(){ return doctorId; }
    public Schedule getSchedule(){ return schedule; }
    public int getDailyLimit(){ return dailyLimit; }
    public String getName(){ return name; }
}
