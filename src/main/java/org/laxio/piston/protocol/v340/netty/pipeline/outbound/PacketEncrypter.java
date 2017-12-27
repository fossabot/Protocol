package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.protocol.v340.netty.pipeline.encryption.PacketEncryption;

import javax.crypto.ShortBufferException;
import java.util.logging.Logger;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {

    private PacketEncryption encryption;

    public PacketEncrypter(PacketEncryption encryption) {
        this.encryption = encryption;
    }

    @Override
    protected void encode(ChannelHandlerContext context, ByteBuf input, ByteBuf output) throws ShortBufferException {
        this.encryption.encrypt(input, output);
    }

}
