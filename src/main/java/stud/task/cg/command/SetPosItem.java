package stud.task.cg.command;

import stud.task.cg.math.Vector4;
import stud.task.cg.model.Move;

public class SetPosItem implements Command {

    private Vector4 to;
    private Vector4 before;
    private Move item;

    public SetPosItem( Move item, Vector4 to) {
        this.to = to;
        this.item = item;

        before = item.getPosition();
    }

    @Override
    public void execute() {
        item.setPosition(to);
    }

    @Override
    public void unExecute() {
        item.setPosition(before);
    }
}
