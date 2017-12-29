package org.laxio.piston.protocol.v340.metadata.type;

import org.laxio.piston.protocol.v340.metadata.PistonMetadataBlob;

public class BooleanBlob extends PistonMetadataBlob<Boolean> {

    public BooleanBlob(int index, boolean value) {
        super(index, value);
    }

}
