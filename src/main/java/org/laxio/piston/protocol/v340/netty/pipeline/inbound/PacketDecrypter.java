package org.laxio.piston.protocol.v340.netty.pipeline.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.laxio.piston.protocol.v340.netty.pipeline.encryption.PacketEncryption;

import javax.crypto.ShortBufferException;
import java.util.List;
import java.util.logging.Logger;

public class PacketDecrypter extends ByteToMessageDecoder {

    private PacketEncryption encryption;

    public PacketDecrypter(PacketEncryption encryption) {
        this.encryption = encryption;
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf input, List<Object> list) throws ShortBufferException {
        Logger.getGlobal().info("Decrypting packet");

        list.add(encryption.decrypt(context, input));
    }

}
