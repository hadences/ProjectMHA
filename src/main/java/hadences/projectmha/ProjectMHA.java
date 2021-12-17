package hadences.projectmha;

import hadences.projectmha.commands.summondummy.Dummy;
import hadences.projectmha.events.MainEvent;
import hadences.projectmha.game.GameEvent;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.commands.CommandManager;
import hadences.projectmha.commands.ToggleDev;
import hadences.projectmha.gui.events.GUIEventManager;
import hadences.projectmha.scoreboard.BoardManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.config.ArenaConfig.loadArenas;
import static hadences.projectmha.config.ArenaConfig.updateArenaConfig;
import static hadences.projectmha.config.PlayerConfig.updatePlayerConfig;
import static hadences.projectmha.config.QuirkConfig.loadQuirks;

public final class ProjectMHA extends JavaPlugin {

    public BoardManager board = new BoardManager();

    public ItemStack MenuItem = new ItemStack(Material.NETHER_STAR);


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MainEvent(), this);
        getServer().getPluginManager().registerEvents(new GUIEventManager(), this);
        getServer().getPluginManager().registerEvents(new GameEvent(), this);

        //Dummy Event
        getServer().getPluginManager().registerEvents(new Dummy(), this);

        //initialize ds command
        getCommand("mha").setExecutor(new CommandManager());
        getCommand("dev").setExecutor(new ToggleDev());
        //create Menu Item
        createMenuItem();


        //loads the config
        loadConfig();
        //create scoreboard
        board.createScoreboard();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        updateConfig();
        HandlerList.unregisterAll(this);
    }

    public void updateConfig() {
        updateArenaConfig();
        updatePlayerConfig();
        //updatePlayerConfig();
        saveConfig();
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);

        //Populate the arenalist through config.
        loadArenas();
        loadQuirks();
    }

    //Load Menu Item
    public void createMenuItem() {
        List<Component> lore = new ArrayList<>();
        ItemMeta meta = MenuItem.getItemMeta();
        meta.setLocalizedName("menu");
        meta.displayName(Component.text(Chat.format("&0&l[&6&lMENU&0&l]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &cClick to open menu")));
        meta.lore(lore);
        MenuItem.setItemMeta(meta);
    }

    public void resetInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(8, MenuItem);
    }

}
