package org.laxio.piston.protocol.v340.stream;

import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.protocol.stream.PistonInput;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Used to read Protocol data from an InputStream
 */
public class PistonInputStream extends DataInputStream implements PistonInput {

    public PistonInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int readableBytes() {
        throw new UnsupportedOperationException("Streams don't yet have this feature");
    }

    @Override
    public UUID readUUID() throws IOException {
        return StreamTools.readUUID(this);
    }

    @Override
    public String readString() throws IOException {
        return StreamTools.readString(this);
    }

    @Override
    public int readVarInt() throws IOException {
        return StreamTools.readVarInt(this);
    }

    @Override
    public long readVarLong() throws IOException {
        return StreamTools.readVarLong(this);
    }

    @Override
    public Identifier readIdentifier() throws IOException {
        return StreamTools.readIdentifier(this);
    }

}
