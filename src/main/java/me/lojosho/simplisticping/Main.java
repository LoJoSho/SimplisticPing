package me.lojosho.simplisticping;

import me.lojosho.simplisticping.command.CommandPing;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginCommand("ping").setExecutor(new CommandPing(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
