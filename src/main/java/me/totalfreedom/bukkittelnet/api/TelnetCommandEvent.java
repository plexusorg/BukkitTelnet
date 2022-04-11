package me.totalfreedom.bukkittelnet.api;

import me.totalfreedom.bukkittelnet.session.SessionCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TelnetCommandEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private SessionCommandSender sender;
    private String command;

    public TelnetCommandEvent(SessionCommandSender sender, String command)
    {
        super(!Bukkit.getServer().isPrimaryThread());
        this.cancelled = false;
        this.sender = sender;
        this.command = command;
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

    public SessionCommandSender getSender()
    {
        return sender;
    }

    public void setSender(SessionCommandSender sender)
    {
        this.sender = sender;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        if (command == null)
        {
            command = "";
        }

        this.command = command;
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
