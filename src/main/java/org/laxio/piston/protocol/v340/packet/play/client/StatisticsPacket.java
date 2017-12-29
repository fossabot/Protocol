package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.entity.player.Statistic;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.List;

public class StatisticsPacket extends ProtocolPacket {

    private List<Statistic> statistics;

    public StatisticsPacket() {
        // required empty packet
    }

    public StatisticsPacket(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeVarInt(statistics.size());
        for (Statistic statistic : statistics) {
            output.writeString(statistic.getName());
            output.writeVarInt(statistic.getValue());
        }
    }

    @Override
    public String toString() {
        return "StatisticsPacket{" +
                "statistics=" + statistics +
                '}';
    }

}
