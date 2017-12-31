package org.laxio.piston.protocol.v340.stream;

        /*-
         * #%L
         * Protocol
         * %%
         * Copyright (C) 2017 Laxio
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
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;

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
        StreamTools.writeUUID(this, data);
        return this;
    }

    @Override
    public PistonOutput writeUUID(UUID data, boolean dashes) throws IOException {
        StreamTools.writeUUID(this, data, dashes);
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

    @Override
    public PistonOutput writeLocation(Location data) throws IOException {
        StreamTools.writeLocation(this, data);
        return this;
    }

    @Override
    public PistonOutput writeLocation(Location data, boolean yawPitch) throws IOException {
        StreamTools.writeLocation(this, data, yawPitch);
        return this;
    }

    @Override
    public PistonOutput writeRotation(float data) throws IOException {
        StreamTools.writeRotation(this, data);
        return this;
    }

    @Override
    public PistonOutput writeVelocity(Velocity data) throws IOException {
        StreamTools.writeVelocity(this, data);
        return this;
    }

    @Override
    public PistonOutput writePosition(Location data) throws IOException {
        StreamTools.writePosition(this, data);
        return this;
    }

}
