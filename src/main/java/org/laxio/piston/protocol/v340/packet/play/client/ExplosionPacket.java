package org.laxio.piston.protocol.v340.packet.play.client;

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

import org.laxio.piston.piston.list.LockableLinkedList;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.packet.ProtocolPacket;

import java.io.IOException;
import java.util.List;

public class ExplosionPacket extends ProtocolPacket {

    private Location location;
    private float radius;
    private List<Location> records;
    private Location playerMotion;

    public ExplosionPacket() {
        // required empty constructor
    }

    public ExplosionPacket(Location location, float radius, List<Location> records, Location playerMotion) {
        this.location = location;
        this.radius = radius;
        this.records = LockableLinkedList.createLocked(records);
        this.playerMotion = playerMotion;
    }

    public Location getLocation() {
        return location;
    }

    public float getRadius() {
        return radius;
    }

    public List<Location> getRecords() {
        return records;
    }

    public Location getPlayerMotion() {
        return playerMotion;
    }

    @Override
    public void onWrite(PistonOutput output) throws IOException {
        output.writeLocation(location, false);
        output.writeFloat(radius);
        output.writeInt(records.size());
        for (Location record : records) {
            byte x = (byte) (record.getBlockX() - location.getBlockX());
            byte y = (byte) (record.getBlockX() - location.getBlockX());
            byte z = (byte) (record.getBlockX() - location.getBlockX());
            output.writeBytes(new byte[]{x, y, z});
        }

        output.writeLocation(playerMotion, false);
    }

    @Override
    public String toString() {
        return "ExplosionPacket{" +
                "location=" + location +
                ", radius=" + radius +
                ", records=" + records +
                ", playerMotion=" + playerMotion +
                '}';
    }

}
