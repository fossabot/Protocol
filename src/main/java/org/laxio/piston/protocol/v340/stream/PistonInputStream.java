package org.laxio.piston.protocol.v340.stream;

        /*-
         * #%L
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
         * #L%
         */

import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.entity.Velocity;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.world.Location;

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
    public byte[] readBytes() throws IOException {
        return readBytes(readVarInt());
    }

    @Override
    public byte[] readBytes(int length) throws IOException {
        if (length < 0)
            throw new IOException("Invalid array length");
        byte[] data = new byte[length];
        readFully(data);
        return data;
    }


    @Override
    public UUID readUUID() throws IOException {
        return StreamTools.readUUID(this);
    }

    @Override
    public UUID readUUID(boolean dashes) throws IOException {
        return StreamTools.readUUID(this, dashes);
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

    @Override
    public Location readLocation() throws IOException {
        return StreamTools.readLocation(this);
    }

    @Override
    public Location readLocation(boolean yawPitch) throws IOException {
        return StreamTools.readLocation(this, yawPitch);
    }

    @Override
    public float readRotation() throws IOException {
        return StreamTools.readRotation(this);
    }

    @Override
    public Velocity readVelocity() throws IOException {
        return StreamTools.readVelocity(this);
    }

    @Override
    public Location readPosition() throws IOException {
        return StreamTools.readPosition(this);
    }

}
