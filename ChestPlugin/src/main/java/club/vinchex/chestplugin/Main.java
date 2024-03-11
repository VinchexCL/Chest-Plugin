package club.vinchex.chestplugin;

import club.vinchex.chestplugin.commands.ChestPluginCommand;
import club.vinchex.chestplugin.commands.FreechestCommand;
import club.vinchex.chestplugin.listeners.FreechestListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private FileConfiguration messagesConfig;
    private HashMap<UUID, Inventory> playerEnderChests = new HashMap<>();
    private File dataFile;
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        loadMessagesConfig();
        reloadConfig();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new FreechestListener(this), this);
        getCommand("freechest").setExecutor(new FreechestCommand(this));
        getCommand("reload").setExecutor(new ChestPluginCommand(this));
        getCommand("chestplugin").setExecutor(new ChestPluginCommand(this));
        getCommand("help").setExecutor(new ChestPluginCommand(this));

        dataFile = new File(getDataFolder(), "freechests.yml");
        if (!dataFile.exists()) {
            saveResource("freechests.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        getCommand("freechest").setExecutor(new FreechestCommand(this));

    }

    @Override
    public void onDisable() {
        savePlayerEnderChests();
    }

    private void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }


    public HashMap<UUID, Inventory> getPlayerEnderChests() {
        return playerEnderChests;
    }

    public void openEnderChest(Player player) {
        UUID uuid = player.getUniqueId();
        if (!playerEnderChests.containsKey(uuid)) {
            ConfigurationSection section = dataConfig.getConfigurationSection("freechests." + uuid.toString());
            String title = getConfig().getString("title", "Freechest");
            int size = getConfig().getInt("size", 9);
            Inventory inventory = Bukkit.createInventory(null, size, title);

            if (section != null) {
                for (String key : section.getKeys(false)) {
                    int slot = Integer.parseInt(key);
                    ItemStack item = section.getItemStack(key);
                    if (slot >= 0 && slot < size && item != null && item.getType() != Material.AIR) {
                        inventory.setItem(slot, item);
                    }
                }
            }

            playerEnderChests.put(uuid, inventory);
        }

        player.openInventory(playerEnderChests.get(uuid));
    }

    public void savePlayerEnderChests() {
        for (UUID uuid : playerEnderChests.keySet()) {
            ConfigurationSection section = dataConfig.createSection("freechests." + uuid.toString());
            Inventory inventory = playerEnderChests.get(uuid);

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null && item.getType() != Material.AIR) {
                    section.set(String.valueOf(i), item);
                }
            }
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            getLogger().warning(getErrorSaving() + e.getMessage());
        }
    }
    private String getErrorSaving() {
        StringBuilder sb = new StringBuilder();
        for (String line : getMessagesConfig().getStringList("error-saving")) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}