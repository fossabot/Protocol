package org.laxio.piston.protocol.v340.util;

import org.laxio.piston.piston.exception.protocol.packet.PacketNotFoundException;
import org.laxio.piston.piston.exception.protocol.packet.UnsupportedPacketException;
import org.laxio.piston.piston.protocol.Packet;
import org.laxio.piston.piston.protocol.PacketDirection;
import org.laxio.piston.piston.protocol.ProtocolState;
import org.laxio.piston.piston.util.map.ReverseHashMap;
import org.laxio.piston.piston.util.map.ReverseMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ProtocolMap {

    private final ReverseMap<Class<? extends Packet>, Integer> packetMap;
    private final Map<ProtocolState, Map<PacketDirection, Integer>> ids;
    private final Map<ProtocolState, Map<PacketDirection, ReverseMap<Integer, Class<? extends Packet>>>> packets;

    public ProtocolMap() {
        this.packetMap = new ReverseHashMap<>();
        this.ids = new HashMap<>();
        this.packets = new HashMap<>();
        init();
    }

    public Map<ProtocolState, Map<PacketDirection, ReverseMap<Integer, Class<? extends Packet>>>> getPackets() {
        return packets;
    }

    public int getId(Packet packet) throws PacketNotFoundException {
        Class<? extends Packet> cls = packet.getClass();
        Integer result = packetMap.get(cls);
        if (result == null) {
            throw new PacketNotFoundException("Could not find registered Packet id for " + cls.getName());
        }

        return result;
    }

    /**
     * Returns the packet class referring to the arguments supplied
     *
     * @param state     The state that the protocol is in
     * @param direction The direction of the packet
     * @param id        The id number of the packet
     *
     * @return The class of the packet
     */
    public Class<? extends Packet> getPacket(ProtocolState state, PacketDirection direction, int id) {
        return this.packets.get(state).get(direction).get(id);
    }

    /**
     * Constructs a packet based on the arguments supplied
     *
     * @param state     The state that the protocol is in
     * @param direction The direction of the packet
     * @param id        The id number of the packet
     *
     * @return A constructed Packet based on the supplied arguments
     *
     * @throws PacketNotFoundException    If no Packet is found matching the criteria
     * @throws UnsupportedPacketException If the Packet cannot be constructed via reflection
     */
    public Packet construct(ProtocolState state, PacketDirection direction, int id) throws PacketNotFoundException, UnsupportedPacketException {
        Class<? extends Packet> cls = getPacket(state, direction, id);
        if (cls == null) {
            throw new PacketNotFoundException(state.name() + ":" + direction.name() + "#" + id);
        }

        try {
            Constructor<? extends Packet> constructor = cls.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedPacketException(cls.getName() + " is not configured correctly", e);
        }
    }

    /**
     * Adds the supplied Packet class to the map of ids for the supplied state and direction.
     * Uses the next available packet id as the id for the added packet
     *
     * @param state     The protocol state which the packet belongs to
     * @param direction The direction of travel which the packet will take
     * @param cls       The class of the packet to add
     *
     * @throws UnsupportedPacketException If the Packet cannot be constructed via reflection
     */
    public void add(ProtocolState state, PacketDirection direction, Class<? extends Packet> cls) throws UnsupportedPacketException {
        int id = this.ids.get(state).get(direction);
        add(state, direction, id, cls);

        id += 1;
        this.ids.get(state).put(direction, id);
    }

    /**
     * Adds the supplied Packet class to the map of ids for the supplied state and direction
     *
     * @param state     The protocol state which the packet belongs to
     * @param direction The direction of travel which the packet will take
     * @param id        The id number of the packet
     * @param cls       The class of the packet to add
     *
     * @throws UnsupportedPacketException If the Packet cannot be constructed via reflection
     */
    public void add(ProtocolState state, PacketDirection direction, int id, Class<? extends Packet> cls) throws UnsupportedPacketException {
        // test the packet and check that it is supported
        try {
            Constructor<? extends Packet> constructor = cls.getConstructor();
            constructor.newInstance(); // disregard
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedPacketException(cls.getName() + " is not configured correctly", e);
        }

        ReverseMap<Integer, Class<? extends Packet>> map = this.packets.get(state).get(direction);
        map.put(id, cls);
        this.packetMap.put(cls, id);
    }

    /**
     * Fills out the map with necessary objects
     */
    private void init() {
        for (ProtocolState state : ProtocolState.values()) {
            Map<PacketDirection, ReverseMap<Integer, Class<? extends Packet>>> stateMap = new HashMap<>();
            Map<PacketDirection, Integer> idMap = new HashMap<>();
            for (PacketDirection direction : PacketDirection.values()) {
                ReverseMap<Integer, Class<? extends Packet>> reverse = new ReverseHashMap<>();
                stateMap.put(direction, reverse);
                idMap.put(direction, 0x00);
            }

            this.packets.put(state, stateMap);
            this.ids.put(state, idMap);
        }
    }

}
