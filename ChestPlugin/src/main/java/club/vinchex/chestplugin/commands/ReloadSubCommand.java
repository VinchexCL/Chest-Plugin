package club.vinchex.chestplugin.commands;

import club.vinchex.chestplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand implements CommandExecutor {

    private final Main plugin;

    public ReloadSubCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("chestplugin.reload")) {
            plugin.reloadConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', (getReloadSuccess())));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', (getNotHavePermission())));
        }
        return true;
    }
    private String getReloadSuccess() {
        StringBuilder sb = new StringBuilder();
        for (String line : plugin.getMessagesConfig().getStringList("reload-success")) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
    private String getNotHavePermission() {
        StringBuilder sb = new StringBuilder();
        for (String line : plugin.getMessagesConfig().getStringList("not-permission")) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}