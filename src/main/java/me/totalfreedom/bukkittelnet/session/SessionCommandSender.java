package me.totalfreedom.bukkittelnet.session;

//import dev.plex.SimulatedPlayer;

import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import me.totalfreedom.bukkittelnet.BukkitTelnet;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class SessionCommandSender implements CommandSender
{
    private final ClientSession session;
    private boolean opped = true;

    public SessionCommandSender(ClientSession session)
    {
        this.session = session;
    }

    @Override
    public void sendMessage(String message)
    {
        session.writeRawLine(message);
    }

    @Override
    public void sendMessage(String[] messages)
    {
        for (String message : messages)
        {
            sendMessage(message);
        }
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message)
    {
        this.sendMessage(message);
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages)
    {
        this.sendMessage(messages);
    }

    @Override
    public String getName()
    {
        return this.session.getUserName();
    }

    @Override
    public @NotNull Spigot spigot()
    {
        return new Spigot();
    }

    @Override
    public @NotNull Component name()
    {
        return Component.text(this.getSession().getUserName());
    }

    @Override
    public Server getServer()
    {
        return Bukkit.getServer();
    }

    @Override
    public boolean isPermissionSet(String name)
    {
        return true;
    }

    @Override
    public boolean isPermissionSet(Permission perm)
    {
        return true;
    }

    @Override
    public boolean hasPermission(String name)
    {
        return BukkitTelnet.getPlugin().handler.hasPermission(this.getSession().getUserName(), name);
    }

    @Override
    public boolean hasPermission(Permission perm)
    {
        return hasPermission(perm.getName());
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value)
    {
        return null;
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin)
    {
        return null;
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks)
    {
        return null;
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks)
    {
        return null;
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment)
    {

    }

    @Override
    public void recalculatePermissions()
    {

    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions()
    {
        return null;
    }

    @Override
    public boolean isOp()
    {
        return opped;
    }

    @Override
    public void setOp(boolean value)
    {
        this.opped = false;
    }
}
