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
package org.laxio.piston.protocol.v340.packet.play.client;

import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;

public class NamedSoundEffectPacket extends ProtocolPacket {

    private String soundName;
    private int soundCategory;
    private Location location;
    private float volume;
    private float pitch;

    public NamedSoundEffectPacket() {
        // required empty constructor
    }

    public NamedSoundEffectPacket(String soundName, int soundCategory, Location location, float volume, float pitch) {
        this.soundName = soundName;
        this.soundCategory = soundCategory;
        this.location = location;
        this.volume = volume;
        this.pitch = pitch;
    }

    public String getSoundName() {
        return soundName;
    }

    public int getSoundCategory() {
        return soundCategory;
    }

    public Location getLocation() {
        return location;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public void write(PistonOutput output) throws IOException {
        output.writeString(soundName);
        output.writeVarInt(soundCategory);
        output.writeInt((int) (location.getX() * 32D));
        output.writeInt((int) (location.getY() * 32D));
        output.writeInt((int) (location.getZ() * 32D));
        output.writeFloat(volume);
        output.writeFloat(pitch);
    }

    @Override
    public String toString() {
        return "NamedSoundEffectPacket{" +
                "soundName='" + soundName + '\'' +
                ", soundCategory='" + soundCategory + '\'' +
                ", location=" + location +
                ", volume=" + volume +
                ", pitch=" + pitch +
                '}';
    }
}
