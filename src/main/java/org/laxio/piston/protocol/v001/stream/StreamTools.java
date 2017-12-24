package org.laxio.piston.protocol.v001.stream;

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
        int i = 0;
        int j = 0;
        while (true) {
            int k = input.read();
            if (k == -1)
                throw new IOException("End of stream");

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5)
                throw new IOException("VarInt too big");

            if ((k & 0x80) != 128)
                break;
        }

        return i;
    }

    public static void writeVarInt(PistonOutput output, int data) throws IOException {
        while (true) {
            if ((data & 0xFFFFFF80) == 0) {
                output.writeByte((byte) data);
                return;
            }

            output.writeByte((byte) (data & 0x7F | 0x80));
            data >>>= 7;
        }
    }

    public static long readVarLong(PistonInput input) throws IOException {
        long varInt = 0;
        for (int i = 0; i < 10; i++) {
            byte b = input.readByte();
            varInt |= ((long) (b & (i != 9 ? 0x7F : 0x01))) << (i * 7);

            if (i == 9 && (((b & 0x80) == 0x80) || ((b & 0x7E) != 0)))
                throw new IOException("VarLong too big");
            if ((b & 0x80) != 0x80)
                break;
        }

        return varInt;
    }

    public static void writeVarLong(PistonOutput output, long data) throws IOException {
        int length = 10;
        for (int i = 9; i >= 0; i--)
            if (((data >> (i * 7)) & (i != 9 ? 0x7F : 0x01)) == 0)
                length--;
        for (int i = 0; i < length; i++)
            output.write((int) ((i == length - 1 ? 0x00 : 0x80) | ((data >> (i * 7)) & (i != 9 ? 0x7F : 0x01))));
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
