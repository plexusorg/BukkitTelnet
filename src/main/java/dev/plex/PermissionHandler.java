package dev.plex;

public interface PermissionHandler
{
    boolean hasPermission(String username, String permission);
}
