package org.laxio.piston.protocol.v340.metadata.type;

import org.laxio.piston.piston.entity.Rotation;
import org.laxio.piston.protocol.v340.metadata.PistonMetadataBlob;

public class RotationBlob extends PistonMetadataBlob<Rotation> {

    public RotationBlob(int index, Rotation value) {
        super(index, value);
    }

}
