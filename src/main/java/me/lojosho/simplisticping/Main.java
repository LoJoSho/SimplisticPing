package me.lojosho.simplisticping;

import me.lojosho.simplisticping.command.CommandPing;
import me.lojosho.simplisticping.command.CommandPingTab;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginCommand("ping").setExecutor(new CommandPing(this));
        getServer().getPluginCommand("ping").setTabCompleter(new CommandPingTab());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
