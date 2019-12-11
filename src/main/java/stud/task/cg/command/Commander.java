package stud.task.cg.command;

import java.util.Stack;

public class Commander {

    private Stack<Command> past;
    private Stack<Command> future;

    public Commander() {
        past = new Stack<>();
        future = new Stack<>();
    }

    public void execute(Command command) {
        command.execute();
        past.push(command);
        future.clear();
    }

    public void prev() {
        if (past.isEmpty()) return;
        Command command = past.pop();
        command.unExecute();
        future.add(command);
    }

    public void future() {
        if (future.isEmpty()) return;
        Command command = future.pop();
        command.execute();
        past.push(command);
    }
}
