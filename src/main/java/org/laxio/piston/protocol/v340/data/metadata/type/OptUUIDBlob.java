package org.laxio.piston.protocol.v340.data.metadata.type;

import org.laxio.piston.protocol.v340.data.metadata.PistonMetadataBlob;

import java.util.UUID;

public class OptUUIDBlob extends PistonMetadataBlob<UUID> {

    public OptUUIDBlob(int index, UUID value) {
        super(index, value);
    }

}