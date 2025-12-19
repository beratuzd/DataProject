import java.util.Iterator;

public class VisitList implements Iterable<Visit> {
    private static class Node {
        Visit v; Node next;
        Node(Visit v) {
            this.v = v;
        }
    }
    private Node head, tail;
    private int size = 0;

    public void append(Visit v) {
        Node n = new Node(v);
        if (head == null) head = tail = n;
        else {
            tail.next = n; tail = n;
        }
        size++;
    }

    public int size() { return size; }

    @Override
    public Iterator<Visit> iterator() {
        return new Iterator<Visit>() {
            Node cur = head;
            public boolean hasNext() {
                return cur != null;
            }
            public Visit next() {
                Visit v = cur.v; cur = cur.next; return v;
            }
        };
    }
}