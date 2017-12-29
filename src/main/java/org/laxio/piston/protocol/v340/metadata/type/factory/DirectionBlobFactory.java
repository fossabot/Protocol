package org.laxio.piston.protocol.v340.metadata.type.factory;

import org.laxio.piston.piston.entity.Direction;
import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.metadata.type.DirectionBlob;

import java.io.IOException;

public class DirectionBlobFactory extends BlobFactory<DirectionBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof DirectionBlob;
    }

    @Override
    public DirectionBlob read(PistonInput input, int index) throws IOException {
        return new DirectionBlob(index, Direction.getById(input.readVarInt()));
    }

    @Override
    public void write(PistonOutput output, DirectionBlob blob) throws IOException {
        output.writeVarInt(blob.getValue().getId());
    }

}