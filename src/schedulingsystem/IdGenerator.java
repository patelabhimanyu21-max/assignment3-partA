/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingsystem;

public class IdGenerator {
    private static int counter = 100;

    public static synchronized String nextAppointmentId() {
        counter++;
        return "A" + counter;
    }
}