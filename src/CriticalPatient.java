import java.time.*;

public class CriticalPatient {
    public final int patientId;
    public final int severity;
    public final LocalDateTime arrival;

    public CriticalPatient(int patientId, int severity, LocalDateTime arrival) {
        this.patientId = patientId;
        this.severity = severity;
        this.arrival = arrival;
    }
}