/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hospitalmanagementsystem;

/**
 *
 * @author hp
 */
import java.util.*;

class InvalidPatientException extends Exception {
    public InvalidPatientException(String message) {
        super(message);
    }
}

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InvalidDoctorException extends Exception {
    public InvalidDoctorException(String message) {
        super(message);
    }
}

class AppointmentUnavailableException extends Exception {
    public AppointmentUnavailableException(String message) {
        super(message);
    }
}

class Patient {
    int id;
    String name;
    int age;
    String doctor;
    boolean appointmentBooked;

    Patient(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.appointmentBooked = false;
    }
}

public class HospitalManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Patient> patients = new ArrayList<>();

    static String[] doctors = {
        "Dr. Kumar",
        "Dr. Priya",
        "Dr. Arun"
    };

    static int patientId = 1;

    // Register patient
    static void registerPatient() throws InvalidPatientException, InvalidAgeException {

        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            throw new InvalidPatientException("Patient name cannot be empty.");
        }

        System.out.print("Enter patient age: ");
        int age = sc.nextInt();
        sc.nextLine();

        if (age <= 0 || age > 120) {
            throw new InvalidAgeException("Invalid age. Age must be between 1 and 120.");
        }

        Patient p = new Patient(patientId++, name, age);
        patients.add(p);

        System.out.println("Patient registered successfully!");
        System.out.println("Patient ID: " + p.id);
    }

    // Book appointment
    static void bookAppointment()
            throws InvalidPatientException,
                   InvalidDoctorException,
                   AppointmentUnavailableException {

        if (patients.isEmpty()) {
            throw new InvalidPatientException("No patients registered.");
        }

        System.out.print("Enter patient ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Patient patient = null;

        for (Patient p : patients) {
            if (p.id == id) {
                patient = p;
                break;
            }
        }

        if (patient == null) {
            throw new InvalidPatientException("Invalid patient ID.");
        }

        System.out.println("\nAvailable Doctors:");

        for (int i = 0; i < doctors.length; i++) {
            System.out.println((i + 1) + ". " + doctors[i]);
        }

        System.out.print("Select doctor: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > doctors.length) {
            throw new InvalidDoctorException("Invalid doctor selection.");
        }

        if (patient.appointmentBooked) {
            throw new AppointmentUnavailableException(
                    "Patient already has an appointment."
            );
        }

        patient.doctor = doctors[choice - 1];
        patient.appointmentBooked = true;

        System.out.println("Appointment booked successfully!");
        System.out.println("Doctor: " + patient.doctor);
    }

    // Cancel appointment
    static void cancelAppointment() throws InvalidPatientException {

        System.out.print("Enter patient ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Patient patient = null;

        for (Patient p : patients) {
            if (p.id == id) {
                patient = p;
                break;
            }
        }

        if (patient == null) {
            throw new InvalidPatientException("Invalid patient ID.");
        }

        if (!patient.appointmentBooked) {
            System.out.println("No appointment found for this patient.");
            return;
        }

        patient.appointmentBooked = false;
        patient.doctor = null;

        System.out.println("Appointment cancelled successfully.");
    }

    // Display patients
    static void displayPatients() {

        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        System.out.println("\n----- Patient Details -----");

        for (Patient p : patients) {

            System.out.println("Patient ID: " + p.id);
            System.out.println("Name: " + p.name);
            System.out.println("Age: " + p.age);

            if (p.appointmentBooked) {
                System.out.println("Doctor: " + p.doctor);
                System.out.println("Appointment: Booked");
            } else {
                System.out.println("Appointment: Not Booked");
            }

            System.out.println("---------------------------");
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Book Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Display Patients");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            try {

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        registerPatient();
                        break;

                    case 2:
                        bookAppointment();
                        break;

                    case 3:
                        cancelAppointment();
                        break;

                    case 4:
                        displayPatients();
                        break;

                    case 5:
                        System.out.println("Thank you!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid menu choice.");

                }

            } catch (InvalidPatientException |
                     InvalidAgeException |
                     InvalidDoctorException |
                     AppointmentUnavailableException e) {

                System.out.println("Exception: " + e.getMessage());

            } catch (InputMismatchException e) {

                System.out.println("Exception: Please enter a valid number.");
                sc.nextLine();
            }
        }
    }
}