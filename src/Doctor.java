import java.util.ArrayDeque;

public class Doctor {
    public final int id;
    public final String name;
    public final String department;
    private final ArrayDeque<Integer> waitingQueue = new ArrayDeque<>();

    public Doctor(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public void enqueuePatient(int patientId) {
        waitingQueue.addLast(patientId);
    }
    public Integer nextPatient() {
        return waitingQueue.pollFirst();
    }
    public int queueSize() {
        return waitingQueue.size();
    }
}