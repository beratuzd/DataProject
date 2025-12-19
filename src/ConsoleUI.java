import java.util.*;

public class ConsoleUI {
    private final HospitalSystem sys;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleUI(HospitalSystem sys) {
        this.sys = sys;
    }

    public void run() {
        System.out.println("Hospital System is running!");
        System.out.println("1) Search patient by ID");
        System.out.println("0) Exit");
        while (true) {
            System.out.print("> ");
            String choice = sc.nextLine();
            if ("0".equals(choice)) break;
            else if ("1".equals(choice)) {
                System.out.print("Patient ID: ");
                int id = Integer.parseInt(sc.nextLine());
                Patient p = sys.getPatientById(id);
                if (p == null) System.out.println("Patient not found.");
                else System.out.println("Patient: " + p.name);
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}