package org.laxio.piston.protocol.v340.data.metadata.type;

import org.laxio.piston.protocol.v340.data.metadata.PistonMetadataBlob;

public class VarIntBlob extends PistonMetadataBlob<Integer> {

    public VarIntBlob(int index, Integer value) {
        super(index, value);
    }

}
