package org.laxio.piston.protocol.v340.data.metadata;

import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;

import java.io.IOException;

public abstract class BlobFactory<T extends MetadataBlob> {

    public abstract boolean matches(MetadataBlob blob);

    public abstract T read(PistonInput input, int index) throws IOException;

    public abstract void write(PistonOutput output, T blob) throws IOException;

}
