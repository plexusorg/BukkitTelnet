package me.totalfreedom.bukkittelnet;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import me.totalfreedom.bukkittelnet.api.TelnetRequestDataTagsEvent;
import me.totalfreedom.bukkittelnet.api.TelnetRequestUsageEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.json.JSONArray;
import org.json.JSONObject;

public class PlayerEventListener implements Listener
{
    private final BukkitTelnet plugin;

    public PlayerEventListener(BukkitTelnet plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        triggerPlayerListUpdates();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        triggerPlayerListUpdates();
    }


    private static BukkitTask updateTask = null;

    public void triggerPlayerListUpdates()
    {
        if (updateTask != null)
        {
            updateTask.cancel();
        }

        updateTask = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                final SocketListener socketListener = plugin.telnet.getSocketListener();
                if (socketListener != null)
                {
                    final TelnetRequestDataTagsEvent event = new TelnetRequestDataTagsEvent();
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    socketListener.triggerPlayerListUpdates(generatePlayerList(event.getDataTags()));
                }
            }
        }.runTaskLater(plugin, 20L * 2L);
    }

    @SuppressWarnings("unchecked")
    private static String generatePlayerList(final Map<Player, Map<String, Object>> dataTags)
    {
        final JSONArray players = new JSONArray();

        for (Map.Entry<Player, Map<String, Object>> playerMapEntry : dataTags.entrySet())
        {
            final HashMap<String, String> info = new HashMap<>();

            final Player player = playerMapEntry.getKey();
            final Map<String, Object> playerTags = playerMapEntry.getValue();

            info.put("name", player.getName());
            info.put("ip", player.getAddress().getAddress().getHostAddress());
            info.put("displayName", StringUtils.trimToEmpty(player.getDisplayName()));
            info.put("uuid", player.getUniqueId().toString());

            for (Map.Entry<String, Object> playerTagsEntry : playerTags.entrySet())
            {
                final Object value = playerTagsEntry.getValue();
                info.put(playerTagsEntry.getKey(), value != null ? value.toString() : "null");
            }

            players.put(info);
        }

        final JSONObject response = new JSONObject();
        response.put("players", players);
        return response.toString();
    }

    private static BukkitTask usageUpdateTask = null;

    // Just putting this stuff here
    public void triggerUsageUpdates()
    {
        if (usageUpdateTask != null)
        {
            return;
        }

        usageUpdateTask = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                final SocketListener socketListener = plugin.telnet.getSocketListener();
                if (socketListener != null)
                {
                    final TelnetRequestUsageEvent event = new TelnetRequestUsageEvent();
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    socketListener.triggerDataUsageUpdates(generateUsageStats());
                }
            }
        }.runTaskTimer(plugin, 100L, 100L); // every 5 seconds
    }


    @SuppressWarnings("unchecked")
    private static String generateUsageStats()
    {
        final JSONObject info = new JSONObject();

        String cpuUsage = null;
        String ramUsage = null;
        String tps = null;

        tps = String.valueOf(new DecimalFormat("#.##").format(Bukkit.getServer().getTPS()[0]));
        info.put("tps", tps);
        return info.toString();
    }
}
