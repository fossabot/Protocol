package org.laxio.piston.protocol.v340.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.piston.world.Location;
import org.laxio.piston.protocol.v340.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.metadata.type.OptPositionBlob;

import java.io.IOException;

public class OptPositionBlobFactory extends BlobFactory<OptPositionBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof OptPositionBlob;
    }

    @Override
    public OptPositionBlob read(PistonInput input, int index) throws IOException {
        Location pos = (input.readBoolean() ? input.readPosition() : null);
        return new OptPositionBlob(index, pos);
    }

    @Override
    public void write(PistonOutput output, OptPositionBlob blob) throws IOException {
        output.writeBoolean(blob.getValue() != null);
        if (blob.getValue() != null) {
            output.writePosition(blob.getValue());
        }
    }

}