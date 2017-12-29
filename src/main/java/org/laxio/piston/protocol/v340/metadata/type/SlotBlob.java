package org.laxio.piston.protocol.v340.metadata.type;

import org.laxio.piston.piston.item.Slot;
import org.laxio.piston.protocol.v340.metadata.PistonMetadataBlob;

public class SlotBlob extends PistonMetadataBlob<Slot> {

    public SlotBlob(int index, Slot value) {
        super(index, value);
    }

}
