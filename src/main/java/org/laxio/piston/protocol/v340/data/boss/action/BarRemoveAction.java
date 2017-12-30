package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;

public class BarRemoveAction extends BarAction {

    public BarRemoveAction(BossBar bar) {
        super(bar);
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void write(PistonOutput output) {
        // nothing happens here
    }

    @Override
    public String toString() {
        return "BarRemoveAction{" +
                "bar=" + bar +
                '}';
    }

}
