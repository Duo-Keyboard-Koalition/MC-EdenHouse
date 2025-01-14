package org.duoKeyboardKoalition.edenHouseSpigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.duoKeyboardKoalition.edenHouseSpigot.scanners.MultiblockScanner;
import org.duoKeyboardKoalition.edenHouseSpigot.Listener.VillagerListener;
import org.duoKeyboardKoalition.edenHouseSpigot.scanners.VillagerJobScanner;

public final class EdenHouseSpigot extends JavaPlugin {
    private MultiblockScanner multiblockScanner;
    private VillagerJobScanner villagerScanner;

    @Override
    public void onEnable() {
        // Initialize scanners
        multiblockScanner = new MultiblockScanner(this);
        villagerScanner = new VillagerJobScanner(this);

        // Register event listeners
        getServer().getPluginManager().registerEvents(multiblockScanner, this);
        getServer().getPluginManager().registerEvents(villagerScanner, this);

        // Schedule initial scans after server is fully started
        Bukkit.getScheduler().runTaskLater(this, () -> {
            multiblockScanner.scanLoadedChunks();
            villagerScanner.scanAllVillagers();
        }, 20L * 5); // 5 second delay

        getLogger().info("EdenHouseSpigot has been enabled!");
    }

    @Override
    public void onDisable() {
        // Perform final scans and save data
        if (multiblockScanner != null) {
            multiblockScanner.scanLoadedChunks();
        }
        if (villagerScanner != null) {
            villagerScanner.scanAllVillagers();
        }

        getLogger().info("EdenHouseSpigot has been disabled.");
    }

    // Getters for the scanners if needed
    public MultiblockScanner getMultiblockScanner() {
        return multiblockScanner;
    }

    public VillagerJobScanner getVillagerScanner() {
        return villagerScanner;
    }
}