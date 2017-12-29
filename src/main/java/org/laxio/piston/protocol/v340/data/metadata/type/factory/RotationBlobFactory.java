package org.laxio.piston.protocol.v340.data.metadata.type.factory;

import org.laxio.piston.piston.entity.Rotation;
import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.data.metadata.type.RotationBlob;

import java.io.IOException;

public class RotationBlobFactory extends BlobFactory<RotationBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof RotationBlob;
    }

    @Override
    public RotationBlob read(PistonInput input, int index) throws IOException {
        return new RotationBlob(index, new Rotation(input.readFloat(), input.readFloat(), input.readFloat()));
    }

    @Override
    public void write(PistonOutput output, RotationBlob blob) throws IOException {
        output.writeFloat(blob.getValue().getX());
        output.writeFloat(blob.getValue().getY());
        output.writeFloat(blob.getValue().getZ());
    }

}