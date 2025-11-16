Project Overview

SHAMS (Smart Healthcare Appointment & Management System) is a command-line Java application that simulates appointment booking, doctor schedule management, cancellations, rescheduling, and notification handling. It demonstrates object-oriented principles, design patterns (Factory & Observer), and a layered architecture for a healthcare system prototype.

System Requirements

Java JDK: 17 or higher

IDE (optional): NetBeans, Eclipse, IntelliJ IDEA, or any Java-compatible IDE

OS: Windows, macOS, or Linux with Java installed

Memory: Minimum 512 MB free

Project Structure
src/
 ├─ Main.java                   # Entry point
 ├─ User.java                   # Base class for Patient and Doctor
 ├─ Patient.java
 ├─ Doctor.java
 ├─ Schedule.java
 ├─ Appointment.java
 ├─ AppointmentService.java
 ├─ DataStore.java
 └─ NotificationService.java    # Mock notification system

Setup Instructions

Open your IDE (NetBeans recommended).

Create a new Java project.

Copy all .java files into the src folder.

Ensure all files are in the same package or remove package declarations.

Build the project to compile all files.

How to Run

Run Main.java.

You will see the menu:

=== SHAMS APPOINTMENT SYSTEM ===
1. Book Appointment
2. View Doctor Schedule (Detailed)
3. Cancel Appointment
4. Reschedule Appointment
5. View All Appointments (Admin)
6. Create Patient (Test)
7. Exit
Enter choice:
Sample Inputs
1. Book Appointment

Patient ID: P001

Doctor ID: D001

Emergency? yes or no

2. View Doctor Schedule

Doctor ID: D001

3. Cancel Appointment

Appointment ID: Copy the ID displayed when booking

4. Reschedule Appointment

Appointment ID

New date-time in format YYYY-MM-DDTHH:MM
e.g., 2025-11-16T10:00

5. View All Appointments

No input required, displays all appointments

6. Create Patient

Enter Patient details as prompted: ID, Name, Email, Phone, Age, Gender, History, Insurance

Notes

Initial seeded data:
Doctor: D001 – Dr. Smith, D002 – Dr. Allen
Patient: P001 – John Doe

All IDs are case-sensitive.

Menu redisplays automatically after each action.

Emergency appointments bypass normal scheduling rules.

Notifications are simulated and printed to the console.



Notes

Initial seeded data:
Doctor: D001 – Dr. Smith, D002 – Dr. Allen
Patient: P001 – John Doe

All IDs are case-sensitive.

Menu redisplays automatically after each action.

Emergency appointments bypass normal scheduling rules.

Notifications are simulated and printed to the console.
