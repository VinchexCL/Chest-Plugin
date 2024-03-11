package club.vinchex.chestplugin.commands;

import club.vinchex.chestplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpSubCommand implements CommandExecutor {

    private final Main plugin;

    public HelpSubCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("chestplugin.help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', getHelpMessage()));
            return false;
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', getNotHavePermission()));
        }
        return false;
    }
    private String getHelpMessage() {
        StringBuilder sb = new StringBuilder();
        for (String line : plugin.getMessagesConfig().getStringList("help-message")) {
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
