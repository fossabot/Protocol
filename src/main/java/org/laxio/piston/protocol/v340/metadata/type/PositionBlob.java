package org.laxio.piston.protocol.v340.metadata.type;

import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.metadata.PistonMetadataBlob;

public class PositionBlob extends PistonMetadataBlob<Location> {

    public PositionBlob(int index, Location value) {
        super(index, value);
    }

}
