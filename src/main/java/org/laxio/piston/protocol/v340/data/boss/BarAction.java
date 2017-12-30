package org.laxio.piston.protocol.v340.data.boss;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

import java.io.IOException;

public abstract class BarAction {

    protected BossBar bar;

    public BarAction(BossBar bar) {
        this.bar = bar;
    }

    public BossBar getBar() {
        return bar;
    }

    public abstract int getId();

    public abstract void write(PistonOutput output) throws IOException;

    @Override
    public String toString() {
        return "BarAction{" +
                "bar=" + bar +
                '}';
    }

}
