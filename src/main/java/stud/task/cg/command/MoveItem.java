package stud.task.cg.command;

import stud.task.cg.math.Vector4;
import stud.task.cg.model.Move;

public class MoveItem implements Command {

    private Vector4 add;
    private Vector4 before;
    private Move item;

    public MoveItem(Move item, Vector4 add) {
        this.add = add;
        this.item = item;
        before = add.negative();
    }

    @Override
    public void execute() {
        item.setPosition(item.getPosition().add(add));
    }

    @Override
    public void unExecute() {
        item.setPosition(item.getPosition().add(before));
    }
}
