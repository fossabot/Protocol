package org.laxio.piston.protocol.v340.data.metadata.type;

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

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.exception.entity.metadata.EndOfMetadataException;
import org.laxio.piston.piston.exception.entity.metadata.UnknownMetadataBlobException;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.data.metadata.type.factory.*;

import java.io.IOException;

public enum BlobType {

    BYTE(0x00, new ByteBlobFactory()),
    VARINT(0x01, new VarIntBlobFactory()),
    FLOAT(0x02, new FloatBlobFactory()),
    STRING(0x03, new StringBlobFactory()),
    CHAT(0x04, new ChatBlobFactory()),
    SLOT(0x05, new SlotBlobFactory()),
    BOOLEAN(0x06, new BooleanBlobFactory()),
    ROTATION(0x07, new RotationBlobFactory()),
    POSITION(0x08, new PositionBlobFactory()),
    OPT_POSITION(0x09, new OptPositionBlobFactory()),
    DIRECTION(0x10, new DirectionBlobFactory()),
    OPT_UUID(0x11, new OptUUIDBlobFactory());

    private int type;
    private BlobFactory factory;

    BlobType(int type, BlobFactory factory) {
        this.type = type;
        this.factory = factory;
    }

    public int getType() {
        return type;
    }

    public BlobFactory getFactory() {
        return factory;
    }

    public static MetadataBlob read(PistonInput input) throws IOException, EndOfMetadataException, UnknownMetadataBlobException {
        int index = input.readUnsignedByte();
        if (index == 0xFF) {
            throw new EndOfMetadataException();
        }

        int type = input.readByte();
        for (BlobType blob : values()) {
            if (blob.type == type) {
                return blob.factory.read(input, index);
            }
        }

        throw new UnknownMetadataBlobException("No such blob type for " + type, type);
    }

    @SuppressWarnings("unchecked")
    public static void write(PistonOutput output, MetadataBlob blob) throws UnknownMetadataBlobException, IOException {
        for (BlobType type : values()) {
            if (type.factory.matches(blob)) {
                type.factory.write(output, blob);
                return;
            }
        }

        throw new UnknownMetadataBlobException("Unable to find factory matching " + blob.getClass().getSimpleName(), -1);
    }

}
