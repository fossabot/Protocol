package org.laxio.piston.protocol.v340.stream;

import org.laxio.piston.piston.data.Identifier;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamTools {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("(?<namespace>[0-9a-z_-]+):(?<name>[0-9a-z\\-\\.\\/\\_]+)");

    public static UUID readUUID(PistonInput input) throws IOException {
        return new UUID(input.readLong(), input.readLong());
    }

    public static void writeUUID(PistonOutput output, UUID data) throws IOException {
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
        do {
            byte temp = (byte)(data & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            data >>>= 7;
            if (data != 0) {
                temp |= 0b10000000;
            }

            output.writeByte(temp);
        } while (data != 0);
    }

    public static Identifier readIdentifier(PistonInput input) throws IOException {
        String iden = input.readString();

        Matcher matcher = IDENTIFIER_PATTERN.matcher(iden);
        while (matcher.matches()) {
            String namespace = matcher.group("namespace");
            String name = matcher.group("name");

            return new Identifier(namespace, name);
        }

        throw new IOException("No matches found for pattern");
    }

    public static void writeIdentifier(PistonOutput output, Identifier data) throws IOException {
        output.writeString(data.toString());
    }

}
