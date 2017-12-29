package org.laxio.piston.protocol.v340.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.metadata.type.VarIntBlob;

import java.io.IOException;

public class VarIntBlobFactory extends BlobFactory<VarIntBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof VarIntBlob;
    }

    @Override
    public VarIntBlob read(PistonInput input, int index) throws IOException {
        return new VarIntBlob(index, input.readVarInt());
    }

    @Override
    public void write(PistonOutput output, VarIntBlob blob) throws IOException {
        output.writeVarInt(blob.getValue());
    }

}