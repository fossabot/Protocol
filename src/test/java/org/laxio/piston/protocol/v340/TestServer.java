package org.laxio.piston.protocol.v340;

import me.hfox.aphelion.Aphelion;
import me.hfox.aphelion.CommandRegistration;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.piston.command.CommandSender;
import org.laxio.piston.piston.command.ConsoleCommandSender;
import org.laxio.piston.piston.event.ListenerManager;
import org.laxio.piston.piston.exception.PistonRuntimeException;
import org.laxio.piston.piston.logging.Logger;
import org.laxio.piston.piston.protocol.Protocol;
import org.laxio.piston.piston.session.MinecraftSessionService;
import org.laxio.piston.piston.translator.ProtocolTranslator;
import org.laxio.piston.piston.versioning.Version;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.util.List;

public class TestServer implements PistonServer {

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public Version getVersion() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public Version getMinecraftVersion() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public Protocol getProtocol() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public Protocol getProtocol(int version) {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public void addProtocol(Protocol protocol) {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public List<ProtocolTranslator> getTranslators() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public boolean addTranslator(ProtocolTranslator translator) {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public ListenerManager getManager() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public KeyPair getKeyPair() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public boolean isOnlineMode() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public MinecraftSessionService getSessionService() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public InetSocketAddress getBindAddress() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public Logger getLogger() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public ConsoleCommandSender getConsole() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public void runCommand(CommandSender sender, String command) {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public Aphelion<CommandSender> getAphelion() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public CommandRegistration<CommandSender> getCommandRegistration() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public void handle(Exception ex) {
        throw new PistonRuntimeException(ex);
    }

    @Override
    public boolean start() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public boolean isRunning() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Unavailable in test phase");
    }

}
