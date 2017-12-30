package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;

import java.io.IOException;

public class BarUpdateHealthAction extends BarAction {

    public BarUpdateHealthAction(BossBar bar) {
        super(bar);
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        output.writeFloat(bar.getHealth());
    }

    @Override
    public String toString() {
        return "BarUpdateHealthAction{" +
                "bar=" + bar +
                '}';
    }

}
