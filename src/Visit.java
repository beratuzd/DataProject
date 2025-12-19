import java.time.*;

public class Visit {
    public final LocalDateTime time;
    public final String department;
    public final String notes;

    public Visit(LocalDateTime time, String department, String notes) {
        this.time = time; this.department = department; this.notes = notes;
    }
}