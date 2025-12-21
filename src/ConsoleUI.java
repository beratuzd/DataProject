import java.util.*;
import java.time.*;

public class ConsoleUI {
    private final HospitalSystem sys;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleUI(HospitalSystem sys) {
        this.sys = sys;
    }

    public void run() {
        System.out.println("Hospital System is running!");
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Search patient by ID");
            System.out.println("2) Add new patient");
            System.out.println("3) Schedule appointment");
            System.out.println("4) ER check-in");
            System.out.println("5) Treat next ER patient");
            System.out.println("6) Undo last action");
            System.out.println("0) Exit");

            System.out.print("> ");
            String choice = sc.nextLine();

            if ("0".equals(choice)) break;
            else if ("1".equals(choice)) {
                System.out.print("Patient ID: ");
                int id = Integer.parseInt(sc.nextLine());
                Patient p = sys.getPatientById(id);
                if (p == null) System.out.println("Patient not found.");
                else {
                    System.out.println("Patient: " + p.name + " (ID: " + p.id + ")");
                    for (Visit v : p.getHistory()) {
                        System.out.println(" - " + v.time + " [" + v.department + "]: " + v.notes);
                    }
                }
            }
            else if ("2".equals(choice)) {
                System.out.print("New Patient ID: ");
                int id = Integer.parseInt(sc.nextLine());
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("DOB (YYYY-MM-DD): ");
                String dob = sc.nextLine();
                Patient p = new Patient(id, name, dob);
                sys.addPatient(p);
                System.out.println("Patient added: " + name);
            }
            else if ("3".equals(choice)) {
                System.out.print("Doctor ID: ");
                int did = Integer.parseInt(sc.nextLine());
                System.out.print("Patient ID: ");
                int pid = Integer.parseInt(sc.nextLine());
                sys.scheduleAppointment(did, pid, LocalDateTime.now().plusDays(1));
                System.out.println("Appointment scheduled.");
            }
            else if ("4".equals(choice)) {
                System.out.print("Patient ID: ");
                int pid = Integer.parseInt(sc.nextLine());
                System.out.print("Severity (1-10): ");
                int sev = Integer.parseInt(sc.nextLine());
                sys.erCheckIn(pid, sev);
                System.out.println("Patient checked into ER.");
            }
            else if ("5".equals(choice)) {
                CriticalPatient c = sys.erTreatNext();
                if (c == null) System.out.println("No ER patients.");
                else System.out.println("Treated patient: " + c.patientId);
            }
            else if ("6".equals(choice)) {
                if (sys.canUndo()) {
                    sys.undoLast();
                    System.out.println("Last action undone.");
                } else {
                    System.out.println("Nothing to undo.");
                }
            }
            else {
                System.out.println("Invalid choice.");
            }
        }
    }
}