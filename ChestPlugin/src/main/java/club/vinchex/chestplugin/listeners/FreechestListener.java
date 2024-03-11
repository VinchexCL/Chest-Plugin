package club.vinchex.chestplugin.listeners;

import club.vinchex.chestplugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class FreechestListener implements Listener {

    private final Main plugin;

    public FreechestListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (plugin.getPlayerEnderChests().containsKey(player.getUniqueId()) && event.getClickedInventory() == player.getEnderChest()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (plugin.getPlayerEnderChests().containsKey(player.getUniqueId())) {
            plugin.getPlayerEnderChests().put(player.getUniqueId(), event.getInventory());
            plugin.savePlayerEnderChests();
        }
    }
}