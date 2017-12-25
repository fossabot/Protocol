package org.laxio.piston.protocol.v340;

import org.laxio.piston.piston.protocol.Protocol;

public class StickyProtocolV340 implements Protocol {

    private static final String MC_PROTOCOL_VERSION; // MC Version (Specification)
    private static final String STICKY_PROTOCOL_VERSION; // Protocol Version (Implementation)

    public StickyProtocolV340() {
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
        MC_PROTOCOL_VERSION = StickyProtocolV340.class.getPackage().getSpecificationVersion();
        STICKY_PROTOCOL_VERSION = StickyProtocolV340.class.getPackage().getImplementationVersion();
    }

}
