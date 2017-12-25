package org.laxio.piston.protocol.v340.packet;

/**
 * Represents the stages of the protocol
 */
public enum ProtocolState {

    HANDSHAKE(0),
    STATUS(1),
    LOGIN(2),
    PLAY(3);

    private int id;

    ProtocolState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProtocolState getById(int id) {
        for (ProtocolState state : values()) {
            if (state.getId() == id) {
                return state;
            }
        }

        return null;
    }

}
