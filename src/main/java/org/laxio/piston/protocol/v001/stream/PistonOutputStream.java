package org.laxio.piston.protocol.v001.stream;

import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Used to write Protocol data to an OutputStream
 */
public class PistonOutputStream extends DataOutputStream implements PistonOutput {

    public PistonOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void writeByte(byte data) throws IOException {
        super.writeByte(data);
    }

    @Override
    public PistonOutput writeUUID(UUID data) throws IOException {
        writeLong(data.getMostSignificantBits());
        writeLong(data.getLeastSignificantBits());
        return this;
    }

    @Override
    public PistonOutput writeString(String data) throws IOException {
        StreamTools.writeString(this, data);
        return this;
    }

    @Override
    public PistonOutput writeVarInt(int data) throws IOException {
        StreamTools.writeVarInt(this, data);
        return this;
    }

    @Override
    public PistonOutput writeVarLong(long data) throws IOException {
        StreamTools.writeVarLong(this, data);
        return this;
    }

    @Override
    public PistonOutput writeIdentifier(Identifier data) throws IOException {
        StreamTools.writeIdentifier(this, data);
        return this;
    }

}
