package me.totalfreedom.bukkittelnet.api;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TelnetPreLoginEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    @Getter
    @Setter
    private String name;
    @Getter
    private final String ip;
    @Setter
    private boolean bypassPassword;

    public TelnetPreLoginEvent(String ip, String name, boolean bypassPassword)
    {
        super(!Bukkit.getServer().isPrimaryThread());
        this.ip = ip;
        this.name = name;
        this.bypassPassword = bypassPassword;
    }

    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }


    @Override
    public void setCancelled(boolean cancel)
    {
        cancelled = cancel;
    }

    public boolean canBypassPassword()
    {
        return bypassPassword;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
