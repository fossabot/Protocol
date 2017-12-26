package org.laxio.piston.protocol.v340.netty.pipeline.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.laxio.piston.protocol.v340.netty.pipeline.PacketEncryption;

import javax.crypto.ShortBufferException;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {

    private PacketEncryption encryption;

    public PacketEncrypter(PacketEncryption encryption) {
        this.encryption = encryption;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf output) throws ShortBufferException {
        this.encryption.encrypt(input, output);
    }

}