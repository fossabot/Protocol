package org.laxio.piston.protocol.v340.stream;

import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.entity.Velocity;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamTools {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("(?<namespace>[0-9a-z_-]+):(?<name>[0-9a-z\\-\\.\\/\\_]+)");

    public static UUID readUUID(PistonInput input) throws IOException {
        return readUUID(input, false);
    }

    public static void writeUUID(PistonOutput output, UUID data) throws IOException {
        writeUUID(output, data, true);
    }

    public static UUID readUUID(PistonInput input, boolean dashes) throws IOException {
        if (dashes) {
            return UUID.fromString(input.readString());
        }

        return new UUID(input.readLong(), input.readLong());
    }

    public static void writeUUID(PistonOutput output, UUID data, boolean dashes) throws IOException {
        if (dashes) {
            writeString(output, data.toString());
            return;
        }

        output.writeLong(data.getMostSignificantBits());
        output.writeLong(data.getLeastSignificantBits());
    }

    public static String readString(PistonInput input) throws IOException {
        int length = input.readVarInt();
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = (byte) input.read();
        }

        return new String(data, UTF8);
    }

    public static void writeString(PistonOutput output, String data) throws IOException {
        output.writeVarInt(data.length());
        output.write(data.getBytes("UTF-8"));
    }

    public static int readVarInt(PistonInput input) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = input.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5)
                throw new IOException("VarInt is too big");
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeVarInt(PistonOutput output, int data) throws IOException {
        writeVar(output, data);
    }

    public static long readVarLong(PistonInput input) throws IOException {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = input.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10)
                throw new IOException("VarLong is too big");
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void writeVarLong(PistonOutput output, long data) throws IOException {
        writeVar(output, data);
    }

    public static void writeVar(PistonOutput output, long data) throws IOException {
        long val = data;
        do {
            byte temp = (byte) (val & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            val >>>= 7;
            if (val != 0) {
                temp |= 0b10000000;
            }

            output.writeByte(temp);
        } while (val != 0);
    }

    public static Identifier readIdentifier(PistonInput input) throws IOException {
        String iden = input.readString();

        Matcher matcher = IDENTIFIER_PATTERN.matcher(iden);
        if (matcher.matches()) {
            String namespace = matcher.group("namespace");
            String name = matcher.group("name");

            return new Identifier(namespace, name);
        }

        throw new IOException("No matches found for pattern");
    }

    public static void writeIdentifier(PistonOutput output, Identifier data) throws IOException {
        output.writeString(data.toString());
    }

    public static Location readLocation(PistonInput input) throws IOException {
        return readLocation(input, false);
    }

    public static PistonOutput writeLocation(PistonOutput output, Location data) throws IOException {
        return writeLocation(output, data, false);
    }

    public static Location readLocation(PistonInput input, boolean yawPitch) throws IOException {
        double x = input.readDouble();
        double y = input.readDouble();
        double z = input.readDouble();

        if (yawPitch) {
            float yaw = readRotation(input);
            float pitch = readRotation(input);
            return new Location(x, y, z, yaw, pitch);
        }

        return new Location(x, y, z);
    }

    public static PistonOutput writeLocation(PistonOutput output, Location data, boolean yawPitch) throws IOException {
        output.writeDouble(data.getX());
        output.writeDouble(data.getY());
        output.writeDouble(data.getZ());

        if (yawPitch) {
            writeRotation(output, data.getYaw());
            writeRotation(output, data.getPitch());
        }

        return output;
    }

    public static float readRotation(PistonInput input) throws IOException {
        return (float) (input.readByte() / 360) * 256F;
    }

    public static PistonOutput writeRotation(PistonOutput output, float value) throws IOException {
        int i = (int) (value * 256D) / 360;
        output.write(i);
        return output;
    }

    public static Velocity readVelocity(PistonInput input) throws IOException {
        short x = input.readShort();
        short y = input.readShort();
        short z = input.readShort();

        return new Velocity(x, y, z);
    }

    public static PistonOutput writeVelocity(PistonOutput output, Velocity value) throws IOException {
        output.writeShort(value.getX());
        output.writeShort(value.getY());
        output.writeShort(value.getZ());
        return output;
    }

    public static Location readPosition(PistonInput input) throws IOException {
        long val = input.readLong();
        int x = (int) val >> 38;
        int y = (int) (val >> 26) & 0xFFF;
        int z = (int) val << 38 >> 38;

        if (x >= (2 ^ 25)) {
            x -= 2 ^ 26;
        }

        if (y >= (2 ^ 11)) {
            y -= 2 ^ 12;
        }

        if (z >= (2 ^ 25)) {
            z -= 2 ^ 26;
        }

        return new Location(x, y, z);
    }

    public static PistonOutput writePosition(PistonOutput output, Location value) throws IOException {
        int x = value.getBlockX();
        int y = value.getBlockY();
        int z = value.getBlockZ();
        long data = ((x & 0x3FFFFFF) << 38) | ((y & 0xFFF) << 26) | (z & 0x3FFFFFF);
        output.writeLong(data);
        return output;
    }

}
