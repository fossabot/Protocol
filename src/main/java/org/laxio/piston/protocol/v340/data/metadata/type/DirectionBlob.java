package org.laxio.piston.protocol.v340.data.metadata.type;

import org.laxio.piston.piston.entity.Direction;
import org.laxio.piston.protocol.v340.data.metadata.PistonMetadataBlob;

public class DirectionBlob extends PistonMetadataBlob<Direction> {

    public DirectionBlob(int index, Direction value) {
        super(index, value);
    }

}
