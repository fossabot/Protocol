package org.laxio.piston.protocol.v340.data.metadata.type;

import org.laxio.piston.piston.chat.MessageComponent;
import org.laxio.piston.protocol.v340.data.metadata.PistonMetadataBlob;

public class ChatBlob extends PistonMetadataBlob<MessageComponent> {

    public ChatBlob(int index, MessageComponent value) {
        super(index, value);
    }

}