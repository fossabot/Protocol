package org.laxio.piston.protocol.v001;

import org.laxio.piston.piston.protocol.Protocol;

public class StickyProtocol implements Protocol {

    private static final String MC_PROTOCOL_VERSION; // MC Version (Specification)
    private static final String STICKY_PROTOCOL_VERSION; // Protocol Version (Implementation)

    public StickyProtocol() {
        // something
    }

    @Override
    public String getVersion() {
        return STICKY_PROTOCOL_VERSION;
    }

    @Override
    public String getMinecraftVersion() {
        return MC_PROTOCOL_VERSION;
    }

    static {
        MC_PROTOCOL_VERSION = StickyProtocol.class.getPackage().getSpecificationVersion();
        STICKY_PROTOCOL_VERSION = StickyProtocol.class.getPackage().getImplementationVersion();
    }

}
