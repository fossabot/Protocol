package org.laxio.piston.protocol.v340.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.metadata.type.PositionBlob;

import java.io.IOException;

public class PositionBlobFactory extends BlobFactory<PositionBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof PositionBlob;
    }

    @Override
    public PositionBlob read(PistonInput input, int index) throws IOException {
        return new PositionBlob(index, input.readPosition());
    }

    @Override
    public void write(PistonOutput output, PositionBlob blob) throws IOException {
        output.writePosition(blob.getValue());
    }

}