package org.laxio.piston.protocol.v340.data.boss;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

public abstract class BarUpdateAction {

    private BossBar bar;

    public BarUpdateAction(BossBar bar) {
        this.bar = bar;
    }

    public BossBar getBar() {
        return bar;
    }

    public abstract void write(PistonOutput output);

}
