import java.util.*;

public class HospitalTree {
    public static class HospitalNode {
        public final String name;
        public final List<HospitalNode> children = new ArrayList<>();
        public final List<Integer> doctorIds = new ArrayList<>();
        HospitalNode(String name) {
            this.name = name;
        }
        public HospitalNode addChild(String name) {
            HospitalNode c = new HospitalNode(name);
            children.add(c);
            return c;
        }
    }

    public final HospitalNode root = new HospitalNode("Hospital");

    public HospitalNode findDepartment(String name) {
        for (HospitalNode d : root.children) {
            if (d.name.equalsIgnoreCase(name)) return d;
        }
        return null;
    }
}