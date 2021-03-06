/*-
 * ========================LICENSE_START========================
 * Protocol
 * %%
 * Copyright (C) 2017 - 2018 Laxio
 * %%
 * This file is part of Piston, licensed under the MIT License (MIT).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ========================LICENSE_END========================
 */
package org.laxio.piston.protocol.v340.stream;

import io.netty.buffer.ByteBuf;
import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.entity.Velocity;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;

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
    public int readableBytes() {
        return buf.readableBytes();
    }

    @Override
    public int read() {
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
    public byte[] readBytes() throws IOException {
        return readBytes(readVarInt());
    }

    @Override
    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return bytes;
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
    public int readUnsignedByte() {
        return buf.readUnsignedByte();
    }

    @Override
    public double readDouble() {
        return buf.readDouble();
    }

    @Override
    public void writeDouble(double data) {
        buf.writeDouble(data);
    }

    @Override
    public float readFloat() {
        return buf.readFloat();
    }

    @Override
    public void writeFloat(float data) {
        buf.writeFloat(data);
    }

    @Override
    public short readShort() {
        return buf.readShort();
    }

    @Override
    public void writeShort(int data) {
        buf.writeShort(data);
    }

    @Override
    public int readInt() {
        return buf.readInt();
    }

    @Override
    public void writeInt(int data) {
        buf.writeInt(data);
    }

    @Override
    public boolean readBoolean() {
        return buf.readBoolean();
    }

    @Override
    public void writeBoolean(boolean data) {
        buf.writeBoolean(data);
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
    public UUID readUUID(boolean dashes) throws IOException {
        return StreamTools.readUUID(this, dashes);
    }

    @Override
    public PistonOutput writeUUID(UUID data, boolean dashes) throws IOException {
        StreamTools.writeUUID(this, data, dashes);
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

    @Override
    public Location readLocation() throws IOException {
        return StreamTools.readLocation(this);
    }

    @Override
    public PistonOutput writeLocation(Location data) throws IOException {
        return StreamTools.writeLocation(this, data);
    }

    @Override
    public Location readLocation(boolean yawPitch) throws IOException {
        return StreamTools.readLocation(this, yawPitch);
    }

    @Override
    public PistonOutput writeLocation(Location data, boolean yawPitch) throws IOException {
        return StreamTools.writeLocation(this, data, yawPitch);
    }

    @Override
    public float readRotation() throws IOException {
        return StreamTools.readRotation(this);
    }

    @Override
    public PistonOutput writeRotation(float data) throws IOException {
        return StreamTools.writeRotation(this, data);
    }

    @Override
    public Velocity readVelocity() throws IOException {
        return StreamTools.readVelocity(this);
    }

    @Override
    public PistonOutput writeVelocity(Velocity data) throws IOException {
        return StreamTools.writeVelocity(this, data);
    }

    @Override
    public Location readPosition() throws IOException {
        return StreamTools.readPosition(this);
    }

    @Override
    public PistonOutput writePosition(Location data) throws IOException {
        return StreamTools.writePosition(this, data);
    }

}
