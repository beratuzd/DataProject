import java.util.*;

public class TriageQueue {
    private final PriorityQueue<CriticalPatient> pq;

    public TriageQueue() {
        pq = new PriorityQueue<>((a, b) ->  {
            int cmp = Integer.compare(b.severity, a.severity);
            if (cmp != 0) return cmp;
            return a.arrival.compareTo(b.arrival);
        });
    }

    public void admit(CriticalPatient c) {
        pq.add(c);
    }
    public CriticalPatient treatNext() {
        return pq.poll();
    }
    public CriticalPatient peek() {
        return pq.peek();
    }
    public int size() {
        return pq.size();
    }
    public List<CriticalPatient> snapshot() {
        return new ArrayList<>(pq);
    }
}