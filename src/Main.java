import java.util.*;

public class Main {
    private static final long STUDENT_ID = 230316043L;
    Random rand = new Random(STUDENT_ID);
    public static void main(String[] args) {
        HospitalSystem sys = new HospitalSystem(STUDENT_ID);
        ConsoleUI ui = new ConsoleUI(sys);
        ui.run();
    }
}