package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarAction;

import java.io.IOException;

public class BarUpdateFlagsAction extends BarAction {

    public BarUpdateFlagsAction(BossBar bar) {
        super(bar);
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        int flag = 0;
        if (bar.isDarken())
            flag = flag << 0x1;

        if (bar.isDragon())
            flag = flag << 0x2;

        output.writeByte((byte) flag);
    }

    @Override
    public String toString() {
        return "BarUpdateFlagsAction{" +
                "bar=" + bar +
                '}';
    }

}
