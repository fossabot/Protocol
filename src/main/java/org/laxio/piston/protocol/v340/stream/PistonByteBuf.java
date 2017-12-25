package org.laxio.piston.protocol.v340.stream;

import io.netty.buffer.ByteBuf;
import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

import java.io.IOException;
import java.util.UUID;

/**
 * Used to read Protocol data from a ByteBuf
 */
public class PistonByteBuf implements PistonInput, PistonOutput {

    private ByteBuf buf;

    public PistonByteBuf(ByteBuf buf) {
        this.buf = buf;
    }

    public ByteBuf getBuf() {
        return buf;
    }

    @Override
    public int read() throws IOException {
        return readByte();
    }

    @Override
    public void write(int data) {
        buf.writeByte(data);
    }

    @Override
    public byte readByte() {
        return buf.readByte();
    }

    @Override
    public void writeByte(byte data) {
        buf.writeByte(data);
    }

    @Override
    public void write(byte[] data) {
        buf.writeBytes(data);
    }

    @Override
    public long readLong() {
        return buf.readLong();
    }

    @Override
    public void writeLong(long data) {
        buf.writeLong(data);
    }

    @Override
    public int readUnsignedShort() {
        return buf.readUnsignedShort();
    }

    @Override
    public UUID readUUID() throws IOException {
        return StreamTools.readUUID(this);
    }

    @Override
    public PistonOutput writeUUID(UUID data) throws IOException {
        StreamTools.writeUUID(this, data);
        return this;
    }

    @Override
    public String readString() throws IOException {
        return StreamTools.readString(this);
    }

    @Override
    public PistonOutput writeString(String data) throws IOException {
        StreamTools.writeString(this, data);
        return this;
    }

    @Override
    public int readVarInt() throws IOException {
        return StreamTools.readVarInt(this);
    }

    @Override
    public PistonOutput writeVarInt(int data) throws IOException {
        StreamTools.writeVarInt(this, data);
        return this;
    }

    @Override
    public long readVarLong() throws IOException {
        return StreamTools.readVarLong(this);
    }

    @Override
    public PistonOutput writeVarLong(long data) throws IOException {
        StreamTools.writeVarLong(this, data);
        return this;
    }

    @Override
    public Identifier readIdentifier() throws IOException {
        return StreamTools.readIdentifier(this);
    }

    @Override
    public PistonOutput writeIdentifier(Identifier data) throws IOException {
        StreamTools.writeIdentifier(this, data);
        return this;
    }

}
