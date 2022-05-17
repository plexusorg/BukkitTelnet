package me.totalfreedom.bukkittelnet;

import dev.plex.PermissionHandler;
import org.bukkit.World;

public class PermissionHandlerImpl implements PermissionHandler
{
    @Override
    public boolean hasPermission(String username, String permission)
    {
        return BukkitTelnet.getPlugin().permissions.has((World)null, username, permission);
    }
}
