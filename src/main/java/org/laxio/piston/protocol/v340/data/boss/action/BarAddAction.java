package org.laxio.piston.protocol.v340.data.boss.action;

import org.laxio.piston.piston.boss.BossBar;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.boss.BarUpdateAction;

public class BarAddAction extends BarUpdateAction {

    public BarAddAction(BossBar bar) {
        super(bar);
    }

    @Override
    public void write(PistonOutput output) {

    }

}
