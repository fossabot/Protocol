package org.laxio.piston.protocol.v340.metadata.type;

import org.laxio.piston.protocol.v340.metadata.PistonMetadataBlob;

public class StringBlob extends PistonMetadataBlob<String> {

    public StringBlob(int index, String value) {
        super(index, value);
    }

}
