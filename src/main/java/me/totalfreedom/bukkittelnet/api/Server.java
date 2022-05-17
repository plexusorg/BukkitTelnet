package me.totalfreedom.bukkittelnet.api;

import java.util.List;
import me.totalfreedom.bukkittelnet.SocketListener;
import me.totalfreedom.bukkittelnet.TelnetConfigLoader.TelnetConfig;
import me.totalfreedom.bukkittelnet.session.ClientSession;

public interface Server
{

    void startServer();

    void stopServer();

    @Deprecated
    SocketListener getSocketListener();

    TelnetConfig getConfig();

    List<ClientSession> getSessions();

}
