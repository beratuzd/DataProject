import java.util.ArrayDeque;

public class UndoStack {
    private final ArrayDeque<Action> stack = new ArrayDeque<>();
    public void push(Action a) {
        stack.push(a);
    }
    public boolean canUndo() {
        return !stack.isEmpty();
    }
    public void undoLast() {
        if (!canUndo()) throw new IllegalStateException("Undo stack is empty");
        stack.pop().undo();
    }
}