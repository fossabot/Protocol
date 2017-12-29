package org.laxio.piston.protocol.v340.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.metadata.type.FloatBlob;

import java.io.IOException;

public class FloatBlobFactory extends BlobFactory<FloatBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof FloatBlob;
    }

    @Override
    public FloatBlob read(PistonInput input, int index) throws IOException {
        return new FloatBlob(index, input.readFloat());
    }

    @Override
    public void write(PistonOutput output, FloatBlob blob) throws IOException {
        output.writeFloat(blob.getValue());
    }

}