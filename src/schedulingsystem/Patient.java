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


public class Patient extends User {
    private String medicalId;
    private int age;
    private String gender;
    private String history;
    private String insurance;

    public Patient(String medicalId, String name, String email, String phone,
                   int age, String gender, String history, String insurance) {
        this.medicalId = medicalId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.history = history;
        this.insurance = insurance;
    }

    public String getMedicalId() { return medicalId; }
    public String getName() { return name; }
}
