
package schedulingsystem;
import java.util.*;
import java.util.Scanner;




/**
 *
 * @author HP
 */
public class SchedulingSystem {


    private static final Scanner sc = new Scanner(System.in);
    private static final AppointmentService apptService = new AppointmentService();
    private static final DataStore store = DataStore.getInstance();

    public static void main(String[] args) {
        seedData();
        System.out.println("========================================");
        System.out.println("       SHAMS APPOINTMENT SYSTEM");
        System.out.println("========================================");

        int choice = -1;
        while (choice != 7) {
            printMenu();
            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 : bookAppointmentFlow();
                             break;
                    case 2 : detailedScheduleFlow();
                             break;
                    case 3 : cancelAppointmentFlow();
                             break;
                    case 4 : rescheduleFlow();
                             break;
                    case 5 : viewAllAppointmentsFlow();
                             break;
                    case 6 : createPatientFlow();
                             break;
                    case 7 : System.out.println("Exiting SHAMS... Goodbye!");
                             break;
                    default : System.out.println("Invalid option.");
                             break;
                }
            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n 1. Book Appointment");
        System.out.println("2. View Doctor Schedule (Detailed)");
        System.out.println("3. Cancel Appointment");
        System.out.println("4. Reschedule Appointment");
        System.out.println("5. View All Appointments (Admin)");
        System.out.println("6. Create Patient (Test)");
        System.out.println("7. Exit");
    }

    private static void seedData() {
        // Seed doctor with schedule for tomorrow
        Doctor d1 = new Doctor("D001", "Dr. Smith", "smith@hospital.com", "1234567890",
                "Cardiology", 12, 10);
        d1.getSchedule().addAvailabilityForDays(3); // add slots for next 3 days
        store.addDoctor(d1);

        Doctor d2 = new Doctor("D002", "Dr. Allen", "allen@hospital.com", "1112223333",
                "Dermatology", 6, 8);
        d2.getSchedule().addAvailabilityForDays(3);
        store.addDoctor(d2);

        Patient p1 = new Patient("P001", "John Doe", "john@test.com", "987654321",
                34, "Male", "None", "INS-442");
        store.addPatient(p1);

        Patient p2 = new Patient("P002", "Mary Jane", "mary@test.com", "555444333",
                28, "Female", "Asthma", "INS-991");
        store.addPatient(p2);
    }

    private static void bookAppointmentFlow() {
        try {
            
            System.out.print("Enter Patient ID: ");
            String pid = sc.nextLine().trim();
            System.out.print("Enter Doctor ID: ");
            String did = sc.nextLine().trim();

            System.out.print("Enter desired date (YYYY-MM-DD): ");
            String dateStr = sc.nextLine().trim();
            System.out.print("Enter desired time (HH:MM) 24h: ");
            String timeStr = sc.nextLine().trim();

            System.out.print("Emergency? (yes/no): ");
            boolean emergency = sc.nextLine().trim().equalsIgnoreCase("yes");

            apptService.book(pid, did, dateStr, timeStr, emergency);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private static void detailedScheduleFlow() {
        System.out.print("Enter Doctor ID: ");
        String did = sc.nextLine().trim();
        apptService.printDoctorScheduleDetailed(did);
    }

    private static void cancelAppointmentFlow() {
        System.out.print("Enter Appointment ID to cancel: ");
        String aid = sc.nextLine().trim();
        apptService.cancel(aid);
    }

    private static void rescheduleFlow() {
        System.out.print("Enter Appointment ID to reschedule: ");
        String aid = sc.nextLine().trim();
        System.out.print("Enter new date (YYYY-MM-DD): ");
        String newDate = sc.nextLine().trim();
        System.out.print("Enter new time (HH:MM): ");
        String newTime = sc.nextLine().trim();
        apptService.reschedule(aid, newDate, newTime);
    }

    private static void viewAllAppointmentsFlow() {
        apptService.printAllAppointments();
    }

    private static void createPatientFlow() {
        System.out.print("Enter new Patient ID: ");
        String pid = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Gender: ");
        String gender = sc.nextLine().trim();

        Patient p = new Patient(pid, name, email, phone, age, gender, "", "");
        DataStore.getInstance().addPatient(p);
        System.out.println("Patient created: " + pid);
    }


}

    
