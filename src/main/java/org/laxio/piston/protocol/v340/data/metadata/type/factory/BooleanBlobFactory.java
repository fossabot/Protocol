package org.laxio.piston.protocol.v340.data.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.data.metadata.type.BooleanBlob;

import java.io.IOException;

public class BooleanBlobFactory extends BlobFactory<BooleanBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof BooleanBlob;
    }

    @Override
    public BooleanBlob read(PistonInput input, int index) throws IOException {
        return new BooleanBlob(index, input.readBoolean());
    }

    @Override
    public void write(PistonOutput output, BooleanBlob blob) throws IOException {
        output.writeBoolean(blob.getValue());
    }

}