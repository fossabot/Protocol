package org.laxio.piston.protocol.v340.data.metadata.type;

import org.laxio.piston.protocol.v340.data.metadata.PistonMetadataBlob;

public class ByteBlob extends PistonMetadataBlob<Byte> {

    public ByteBlob(int index, Byte value) {
        super(index, value);
    }

}
