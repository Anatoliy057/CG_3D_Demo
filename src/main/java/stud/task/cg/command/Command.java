package stud.task.cg.command;

public interface Command {

    void execute();

    void unExecute();

    static Command negative(Command command) {
        return new Command() {
            @Override
            public void execute() {
                command.unExecute();
            }

            @Override
            public void unExecute() {
                command.execute();
            }
        };
    }
}
