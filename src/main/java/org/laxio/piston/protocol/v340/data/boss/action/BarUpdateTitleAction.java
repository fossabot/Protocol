package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;

import java.io.IOException;

public class BarUpdateTitleAction extends BarAction {

    public BarUpdateTitleAction(BossBar bar) {
        super(bar);
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        output.writeString(bar.getTitle().toJSON().toString());
    }

    @Override
    public String toString() {
        return "BarUpdateTitleAction{" +
                "bar=" + bar +
                '}';
    }

}
