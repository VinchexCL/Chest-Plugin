package club.vinchex.chestplugin.commands;

import club.vinchex.chestplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChestPluginCommand implements CommandExecutor {

    private final Main plugin;

    public ChestPluginCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                ReloadSubCommand reloadSubCommand = new ReloadSubCommand(plugin);
                return reloadSubCommand.onCommand(sender, command, label, args);

            } else if (args[0].equalsIgnoreCase("help")) {
                HelpSubCommand helpSubCommand = new HelpSubCommand(plugin);
                return helpSubCommand.onCommand(sender, command, label, args);
            }
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m-----------------------------------------------"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l ChestPlugin"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a Version: &71.0"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a Author: &7Vinchex"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a Help: &7/chestplugin help"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m-----------------------------------------------"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m"));
            return true;
        }
        return false;
    }
}
