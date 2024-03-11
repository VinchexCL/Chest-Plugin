package club.vinchex.chestplugin.commands;

import club.vinchex.chestplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreechestCommand implements CommandExecutor {

    private final Main plugin;

    public FreechestCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', getJustForPlayer()));
            return true;
        }

        Player player = (Player) sender;
        plugin.openEnderChest(player);
        return true;
    }
    private String getJustForPlayer() {
        StringBuilder sb = new StringBuilder();
        for (String line : plugin.getMessagesConfig().getStringList("not-console")) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}