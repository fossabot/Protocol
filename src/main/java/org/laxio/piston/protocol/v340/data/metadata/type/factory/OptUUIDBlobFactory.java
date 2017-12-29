package org.laxio.piston.protocol.v340.data.metadata.type.factory;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.data.metadata.type.OptUUIDBlob;

import java.io.IOException;
import java.util.UUID;

public class OptUUIDBlobFactory extends BlobFactory<OptUUIDBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof OptUUIDBlob;
    }

    @Override
    public OptUUIDBlob read(PistonInput input, int index) throws IOException {
        UUID uuid = (input.readBoolean() ? input.readUUID() : null);
        return new OptUUIDBlob(index, uuid);
    }

    @Override
    public void write(PistonOutput output, OptUUIDBlob blob) throws IOException {
        output.writeBoolean(blob.getValue() != null);
        if (blob.getValue() != null) {
            output.writeUUID(blob.getValue());
        }
    }

}