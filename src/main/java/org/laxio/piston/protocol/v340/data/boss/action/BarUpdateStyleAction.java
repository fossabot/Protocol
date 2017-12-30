package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;

import java.io.IOException;

public class BarUpdateStyleAction extends BarAction {

    public BarUpdateStyleAction(BossBar bar) {
        super(bar);
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        output.writeVarInt(bar.getColor().getId());
        output.writeVarInt(bar.getDivision().getId());
    }

    @Override
    public String toString() {
        return "BarUpdateStyleAction{" +
                "bar=" + bar +
                '}';
    }

}
