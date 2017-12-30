package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.ServerDifficulty;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class ServerDifficultyPacket extends ProtocolPacket {

    private ServerDifficulty difficulty;

    public ServerDifficultyPacket() {
        // required empty constructor
    }

    public ServerDifficultyPacket(ServerDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ServerDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeByte(difficulty.getId());
    }

    @Override
    public String toString() {
        return "ServerDifficultyPacket{" +
                "difficulty=" + difficulty +
                '}';
    }

}
