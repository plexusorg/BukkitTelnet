package dev.plex;

import java.util.UUID;

public interface PermissionHandler
{

    boolean hasPermission(String username, String permission);

}
