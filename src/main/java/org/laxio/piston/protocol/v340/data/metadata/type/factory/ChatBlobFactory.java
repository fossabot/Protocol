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

import org.json.JSONObject;
import org.laxio.piston.piston.chat.MessageBuilder;
import org.laxio.piston.piston.chat.MessageComponent;
import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.protocol.stream.PistonInput;
import org.laxio.piston.piston.protocol.stream.PistonOutput;
import org.laxio.piston.protocol.v340.data.metadata.BlobFactory;
import org.laxio.piston.protocol.v340.data.metadata.type.ChatBlob;

import java.io.IOException;

public class ChatBlobFactory extends BlobFactory<ChatBlob> {

    @Override
    public boolean matches(MetadataBlob blob) {
        return blob instanceof ChatBlob;
    }

    @Override
    public ChatBlob read(PistonInput input, int index) throws IOException {
        JSONObject json = new JSONObject(input.readString());
        MessageComponent component = MessageBuilder.builder().json(json).build();
        return new ChatBlob(index, component);
    }

    @Override
    public void write(PistonOutput output, ChatBlob blob) throws IOException {
        output.writeString(blob.getValue().toJSON().toString());
    }

}
