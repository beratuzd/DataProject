import java.util.*;
import java.time.*;

public class HospitalSystem {
    private final long studentIdSalt;
    private final Random rng;

    private final Map<Integer, Patient> patientsById = new HashMap<>();
    private final Map<Integer, Doctor> doctorsById = new HashMap<>();
    private final BSTByName patientNameIndex = new BSTByName();
    private final TriageQueue triage = new TriageQueue();
    private final HospitalTree directory = new HospitalTree();
    private final UndoStack undo = new UndoStack();

    public HospitalSystem(long studentIdSalt) {
        this.studentIdSalt = studentIdSalt;
        this.rng = new Random(studentIdSalt);
        seedDemoData();
    }

    private void seedDemoData() {
        HospitalTree.HospitalNode cardiology = directory.root.addChild("Cardiology");
        HospitalTree.HospitalNode surgery = directory.root.addChild("Surgery");

        addDoctor(new Doctor(1001, "Dr. Ayşe", "Cardiology"), cardiology);
        addDoctor(new Doctor(1002, "Dr. Mehmet", "Surgery"), surgery);

        int count = (int)(studentIdSalt % 7) + 5;
        for (int i = 0; i < count; i++) {
            int pid = 2000 + i;
            String name = "Patient" + i;
            Patient p = new Patient(pid, name, "1990-01-0" + ((i % 9) + 1));
            addPatient(p);
            p.addVisit(new Visit(LocalDateTime.now().minusDays(rng.nextInt(30)), "General", "Initial check"));
        }
        Patient berat = new Patient(3000, "Berat Uzdil", "2005-08-11");
        addPatient(berat);
        berat.addVisit(new Visit(LocalDateTime.now(), "Genel", "Sistem başlangıcında eklendi"));
        Patient Yigit = new Patient(3001, "Yiğithan Erken", "2004-07-12");
        addPatient(Yigit);
        Yigit.addVisit(new Visit(LocalDateTime.now(), "Genel", "Sistem başlangıcında eklendi"));
        Patient mejder = new Patient(3002, "Mejder Avcı", "2005-12-07");
        addPatient(mejder);
        mejder.addVisit(new Visit(LocalDateTime.now(), "Genel", "Sistem başlangıcında eklendi"));
        Patient semi = new Patient(3003, "Semi Kazar", "2005-11-08");
        addPatient(semi);
        semi.addVisit(new Visit(LocalDateTime.now(), "Genel", "Sistem başlangıcında eklendi"));
        Patient ufuk = new Patient(3004, "Ufuk Akkuzu", "2005-9-07");
        addPatient(ufuk);
        ufuk.addVisit(new Visit(LocalDateTime.now(), "Genel", "Sistem başlangıcında eklendi"));
        Patient omer = new Patient(3005, "Ömer Hasan Özküçük", "2005-12-29");
        addPatient(omer);
        omer.addVisit(new Visit(LocalDateTime.now(), "Genel", "Sistem başlangıcında eklendi"));
    }

    private void addDoctor(Doctor d, HospitalTree.HospitalNode dept) {
        doctorsById.put(d.id, d);
        dept.doctorIds.add(d.id);
    }

    public void addPatient(Patient p) {
        patientsById.put(p.id, p);
        patientNameIndex.insert(normalizeName(p.name), p.id);
    }

    private String normalizeName(String n) {
        return n.trim().toLowerCase();
    }

    public void scheduleAppointment(int doctorId, int patientId, LocalDateTime time) {
        Doctor d = doctorsById.get(doctorId);
        Patient p = patientsById.get(patientId);
        if (d == null || p == null) throw new IllegalArgumentException("Invalid doctor or patient");
        d.enqueuePatient(patientId);
        undo.push(new Action() {
            public void undo() {
                removeFirstOccurrence(d, patientId);
            }
            public String label() {
                return "Schedule appointment for patient " + patientId;
            }
        }
        );
        p.addVisit(new Visit(time, d.department, "Scheduled appointment"));
    }

    private void removeFirstOccurrence(Doctor d, int patientId) {
        ArrayDeque<Integer> tmp = new ArrayDeque<>();
        boolean removed = false;
        Integer x;
        while ((x = d.nextPatient()) != null) {
            if (!removed && x == patientId) {
                removed = true; continue;
            }
            tmp.addLast(x);
        }
        tmp.forEach(d::enqueuePatient);
    }

    public void erCheckIn(int patientId, int severity) {
        CriticalPatient cp = new CriticalPatient(patientId, severity, LocalDateTime.now());
        triage.admit(cp);
        undo.push(new Action() {
            public void undo() {
                List<CriticalPatient> all = triage.snapshot();
                TriageQueue newQ = new TriageQueue();
                for (CriticalPatient c : all) {
                    if (!(c.patientId == patientId && c.severity == severity && c.arrival.equals(cp.arrival))) {
                        newQ.admit(c);
                    }
                }
                all.clear();
                for (CriticalPatient c : newQ.snapshot()) triage.admit(c);
            }
            public String label() {
                return "ER check-in patient " + patientId;
            }
        }
        );
    }

    public CriticalPatient erTreatNext() {
        CriticalPatient c = triage.treatNext();
        if (c != null) {
            undo.push(new Action() {
                public void undo() { triage.admit(c); }
                public String label() {
                    return "Treat ER patient " + c.patientId;
                }
            }
            );
            Patient p = patientsById.get(c.patientId);
            if (p != null) p.addVisit(new Visit(LocalDateTime.now(), "ER", "Severity " + c.severity + " treated"));
        }
        return c;
    }

    public Patient getPatientById(int id) {
        return patientsById.get(id);
    }
    public Doctor getDoctorById(int id) {
        return doctorsById.get(id);
    }
    public List<Integer> searchPatientsByName(String name) {

        return patientNameIndex.find(normalizeName(name));
    }
    public List<Integer> currentAdmittedPatients() {
        List<Integer> ids = new ArrayList<>();
        for (CriticalPatient c : triage.snapshot()) ids.add(c.patientId);
        return ids;
    }

    public boolean canUndo() {
        return undo.canUndo();
    }
    public void undoLast() {
        undo.undoLast();
    }

    public HospitalTree getDirectory() {
        return directory;
    }
}