package me.totalfreedom.bukkittelnet;

import dev.plex.PermissionHandler;
import lombok.Getter;
import me.totalfreedom.bukkittelnet.api.Server;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitTelnet extends JavaPlugin
{
    @Getter
    private static BukkitTelnet plugin;
    public TelnetConfigLoader config;
    public TelnetServer telnet;
    public TelnetLogAppender appender;
    public PlayerEventListener listener;
    public Permission permissions;

    public PermissionHandler handler;
    @Override
    public void onLoad()
    {
        plugin = this;
        config = new TelnetConfigLoader(plugin);
        telnet = new TelnetServer(plugin, config.getConfig());
        appender = new TelnetLogAppender();
        listener = new PlayerEventListener(plugin);
        handler = new PermissionHandlerImpl();

        TelnetLogger.setPluginLogger(plugin.getLogger());
        TelnetLogger.setServerLogger(Bukkit.getLogger());
        System.setProperty("log4j2.formatMsgNoLookups", "true");
    }

    @Override
    public void onEnable()
    {
        setupPermissions();
        config.load();

        appender.attach();

        telnet.startServer();

        getServer().getPluginManager().registerEvents(listener, plugin);

        getServer().getServicesManager().register(Server.class, telnet, this, ServicePriority.Normal);

        TelnetLogger.info(plugin.getName() + " v" + plugin.getDescription().getVersion() + " enabled");
    }

    @Override
    public void onDisable()
    {
        HandlerList.unregisterAll(plugin);

        appender.deattach();
        appender.removeAllSesssions();

        telnet.stopServer();

        TelnetLogger.info(plugin.getName() + " disabled");
    }

    private boolean setupPermissions() {
        if (!getServer().getPluginManager().isPluginEnabled("Vault"))
        {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permissions = rsp.getProvider();
        return permissions != null;
    }

}
