package dev._2lstudios.hamsterapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.hamsterapi.HamsterAPI;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayer;
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayerManager;
import dev._2lstudios.hamsterapi.utils.PacketInjector;

public class PlayerJoinListener implements Listener {
    private final Plugin plugin;

    public PlayerJoinListener(final Plugin plugin) {
        this.plugin = plugin;
    }

    private void createPlayer(final Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        try {
            final HamsterAPI hamsterAPI = HamsterAPI.getInstance();
            final HamsterPlayerManager hamsterPlayerManager = hamsterAPI.getHamsterPlayerManager();

            hamsterPlayerManager.remove(player);

            final PacketInjector packetInjector = hamsterAPI.getPacketInjector();
            final HamsterPlayer hamsterPlayer = hamsterAPI.getHamsterPlayerManager().get(player);

            packetInjector.uninject(hamsterPlayer);
            hamsterAPI.getPacketInjector().inject(hamsterPlayer);
        } catch (final Exception exception) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> createPlayer(player), 250L);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        createPlayer(event.getPlayer());
    }
}