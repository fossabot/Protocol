/*-
 * ========================LICENSE_START========================
 * Protocol
 * %%
 * Copyright (C) 2017 - 2018 Laxio
 * %%
 * This file is part of Piston, licensed under the MIT License (MIT).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ========================LICENSE_END========================
 */
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
