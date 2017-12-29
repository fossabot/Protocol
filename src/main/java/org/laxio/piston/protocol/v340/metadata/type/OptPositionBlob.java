package org.laxio.piston.protocol.v340.metadata.type;

import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.metadata.PistonMetadataBlob;

public class OptPositionBlob extends PistonMetadataBlob<Location> {

    public OptPositionBlob(int index, Location value) {
        super(index, value);
    }

}
