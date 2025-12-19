import java.util.*;

public class Patient {
    public final int id;
    public final String name;
    public final String dob;
    private final VisitList visits = new VisitList();

    public Patient(int id, String name, String dob) {
        this.id = id; this.name = name; this.dob = dob;
    }

    public void addVisit(Visit v) { visits.append(v); }
    public Iterable<Visit> getHistory() { return visits; }
}