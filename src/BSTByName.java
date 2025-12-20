import java.util.*;

public class BSTByName {
    static class Node {
        String key;
        List<Integer> patientIds = new ArrayList<>();
        Node left, right;
        Node(String key, int id) {
            this.key = key; this.patientIds.add(id);
        }
    }
    private Node root;

    private int cmp(String a, String b) {
        return a.compareTo(b);
    }

    public void insert(String nameKey, int patientId) {
        root = insertRec(root, nameKey, patientId);
    }

    private Node insertRec(Node n, String k, int id) {
        if (n == null) return new Node(k, id);
        int c = cmp(k, n.key);
        if (c < 0) n.left = insertRec(n.left, k, id);
        else if (c > 0) n.right = insertRec(n.right, k, id);
        else n.patientIds.add(id);
        return n;
    }

    public List<Integer> find(String nameKey) {
        Node n = root;
        while (n != null) {
            int c = cmp(nameKey, n.key);
            if (c == 0) return new ArrayList<>(n.patientIds);
            n = (c < 0) ? n.left : n.right;
        }
        return Collections.emptyList();
    }
}