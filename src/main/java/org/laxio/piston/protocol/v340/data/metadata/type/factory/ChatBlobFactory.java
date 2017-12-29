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