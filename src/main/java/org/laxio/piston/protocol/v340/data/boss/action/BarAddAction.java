package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;

import java.io.IOException;

public class BarAddAction extends BarAction {

    public BarAddAction(BossBar bar) {
        super(bar);
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        output.writeString(bar.getTitle().toJSON().toString());
        output.writeFloat(bar.getHealth());
        output.writeVarInt(bar.getColor().getId());
        output.writeVarInt(bar.getDivision().getId());

        int flag = 0;
        if (bar.isDarken())
            flag = flag << 0x1;

        if (bar.isDragon())
            flag = flag << 0x2;

        output.writeByte((byte) flag);
    }

    @Override
    public String toString() {
        return "BarAddAction{" +
                "bar=" + bar +
                '}';
    }

}
