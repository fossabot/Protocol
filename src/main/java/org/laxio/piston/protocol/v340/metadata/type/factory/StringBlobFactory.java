package org.laxio.piston.protocol.v340.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.metadata.type.StringBlob;

import java.io.IOException;

public class StringBlobFactory extends BlobFactory<StringBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof StringBlob;
    }

    @Override
    public StringBlob read(PistonInput input, int index) throws IOException {
        return new StringBlob(index, input.readString());
    }

    @Override
    public void write(PistonOutput output, StringBlob blob) throws IOException {
        output.writeString(blob.getValue());
    }

}