package org.laxio.piston.protocol.v340.data.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.data.metadata.type.SlotBlob;

public class SlotBlobFactory extends BlobFactory<SlotBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof SlotBlob;
    }

    @Override
    public SlotBlob read(PistonInput input, int index) {
        // TODO: write implementation for slots
        throw new UnsupportedOperationException("Slot Blobs have not yet been implemented");
    }

    @Override
    public void write(PistonOutput output, SlotBlob blob) {
        // TODO: write implementation for slots
        throw new UnsupportedOperationException("Slot Blobs have not yet been implemented");
    }

}