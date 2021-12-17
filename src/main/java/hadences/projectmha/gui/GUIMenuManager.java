package hadences.projectmha.gui;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.arena.Arena.arenalist;
import static hadences.projectmha.config.ArenaConfig.getMaps;
import static org.bukkit.Bukkit.getServer;

public class GUIMenuManager {

    ProjectMHA ds = JavaPlugin.getPlugin(ProjectMHA.class);

    //Character Related
    private Inventory CharacterMenu;
    private Inventory QuirkMenu;
    private Inventory TeamMenu;
    private Inventory SlotMenu;

    //Game Related
    private Inventory MainMenu;
    private Inventory MapMenu;
    private Inventory GamemodeMenu;

    //Playground Gamemode Inventory
    private Inventory PlaygroundMenu;

    public void createInventories() {
        updateCharacterMenu();
        updateQuirkMenu();
        updateSlotMenu();
        updateMainMenu();
        updateGamemodeMenu();
        updatePlaygroundMenu();
    }

    public void updateGamemodeMenu() {
        GamemodeMenu = Bukkit.createInventory(null, 27, Chat.getConsoleName() + "");
        List<Component> lore = new ArrayList<>();
        ItemMeta meta;

        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);
        ItemStack BLACK_GLASS_PANE = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack GAMEMODE;

        meta = BLACK_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        BLACK_GLASS_PANE.setItemMeta(meta);

        for (int i = 0; i < 27; i++) {
            GamemodeMenu.setItem(i, BLACK_GLASS_PANE);
        }

        int j = 0;
        try {
            if (!ds.getConfig().getConfigurationSection("Game.Gamemodes").getKeys(false).isEmpty()) {
                for (String key : ds.getConfig().getConfigurationSection("Game.Gamemodes").getKeys(false)) {
                    GAMEMODE = new ItemStack(Material.getMaterial((String) ds.getConfig().get("Game.Gamemodes." + key + ".Item")));
                    meta = GAMEMODE.getItemMeta();
                    meta.displayName(Component.text(Chat.format(ds.getConfig().get("Game.Gamemodes." + key + ".DisplayName").toString())));
                    lore.clear();
                    lore.add(Component.text(Chat.format("&f+ &aClick to select gamemode!")));
                    meta.lore(lore);
                    meta.setLocalizedName(key);
                    GAMEMODE.setItemMeta(meta);
                    GamemodeMenu.setItem(j, GAMEMODE);
                    j++;
                }
            }
        } catch (Exception e) {
            getServer().broadcastMessage("Gamemode GUI");
        }

        //ITEM_CLOSE
        meta = ITEM_CLOSE.getItemMeta();
        meta.displayName(Component.text("[Close]").color(TextColor.color(255, 0, 0)));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &cClick to close menu")));
        meta.lore(lore);
        ITEM_CLOSE.setItemMeta(meta);

        GamemodeMenu.setItem(26, ITEM_CLOSE);
    }

    public void updateMapMenu() {
        MapMenu = Bukkit.createInventory(null, 54, Chat.getConsoleName() + " Maps");
        ItemMeta meta;
        ItemStack item_close = new ItemStack(Material.BARRIER);
        ItemStack item_map = new ItemStack(Material.MAP);
        ItemStack BLACK_GLASS_PANE = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        meta = BLACK_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        BLACK_GLASS_PANE.setItemMeta(meta);
        List<String> lore = new ArrayList<>();

        for (int i = 0; i < 54; i++) {
            MapMenu.setItem(i, BLACK_GLASS_PANE);
        }

        //item_close data
        meta = item_close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "[Close Menu]");
        lore.clear();
        lore.add(ChatColor.WHITE + "* " + ChatColor.RED + "Click to Exit!");
        meta.setLore(lore);
        item_close.setItemMeta(meta);
        MapMenu.setItem(53, item_close);

        if (!arenalist.isEmpty())
            for (int i = 0; i < getMaps().size(); i++) {
                //item_map data
                meta = item_map.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE + getMaps().get(i).getArenaname());
                lore.clear();
                lore.add(ChatColor.WHITE + "* " + ChatColor.GREEN + "Click to Start!");
                meta.setLore(lore);
                item_map.setItemMeta(meta);
                MapMenu.setItem(i, item_map);
            }
    }

    public void updateMainMenu() {
        MainMenu = Bukkit.createInventory(null, 27, Chat.getConsoleName());
        List<Component> lore = new ArrayList<>();
        ItemMeta meta;

        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);
        ItemStack START = new ItemStack(Material.BEACON);
        ItemStack BLACK_GLASS_PANE = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        meta = BLACK_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        BLACK_GLASS_PANE.setItemMeta(meta);

        //ITEM_CLOSE
        meta = ITEM_CLOSE.getItemMeta();
        meta.displayName(Component.text("[Close]").color(TextColor.color(255, 0, 0)));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &cClick to close menu")));
        meta.lore(lore);
        ITEM_CLOSE.setItemMeta(meta);

        //START
        meta = START.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&aStart Game&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to Start Game!")));
        meta.lore(lore);
        meta.setLocalizedName("start");
        START.setItemMeta(meta);

        for (int i = 0; i < 27; i++) {
            MainMenu.setItem(i, BLACK_GLASS_PANE);
        }

        MainMenu.setItem(26, ITEM_CLOSE);
        MainMenu.setItem(13, START);
    }

    public void updateCharacterMenu() {
        CharacterMenu = Bukkit.createInventory(null, 27, Chat.getConsoleName() + ChatColor.BLACK + "");
        List<Component> lore = new ArrayList<>();
        ItemMeta meta;

        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);
        ItemStack SELECT_BREATHING = new ItemStack(Material.DRAGON_BREATH);
        ItemStack TEAM_SELECTION = new ItemStack(Material.NAME_TAG);
        ItemStack READY = new ItemStack(Material.EMERALD);
        ItemStack GAMEMODE = new ItemStack(Material.FIREWORK_ROCKET);

        ItemStack BLACK_GLASS_PANE = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack WHITE_GLASS_PANE = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack RED_GLASS_PANE = new ItemStack(Material.RED_STAINED_GLASS_PANE);

        meta = WHITE_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        WHITE_GLASS_PANE.setItemMeta(meta);

        meta = BLACK_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        BLACK_GLASS_PANE.setItemMeta(meta);

        meta = RED_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        RED_GLASS_PANE.setItemMeta(meta);

        //ITEM_CLOSE
        meta = ITEM_CLOSE.getItemMeta();
        meta.displayName(Component.text("[Close]").color(TextColor.color(255, 0, 0)));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &cClick to close menu")));
        meta.lore(lore);
        ITEM_CLOSE.setItemMeta(meta);

        //SELECT_BREATHING
        meta = SELECT_BREATHING.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&fQuirk Selection&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to select your Quirk")));
        meta.lore(lore);
        SELECT_BREATHING.setItemMeta(meta);

        //Team Selection
        meta = TEAM_SELECTION.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&aTeam Selection&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to select a team!")));
        meta.lore(lore);
        TEAM_SELECTION.setItemMeta(meta);

        //READY
        meta = READY.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&aReady Up&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to Lock in!")));
        meta.lore(lore);
        meta.setLocalizedName("ready");
        READY.setItemMeta(meta);

        //Gamemode
        meta = GAMEMODE.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&6Select Gamemode&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to select gamemode!")));
        meta.lore(lore);
        meta.setLocalizedName("gamemode");
        GAMEMODE.setItemMeta(meta);

        //Glass Pane Initializer
        for (int i = 0; i < 27; i++) {
            if (i < 9 || i == 9 || i == 17 || (i >= 18 && i != 26)) {
                CharacterMenu.setItem(i, BLACK_GLASS_PANE);
            } else if ((i >= 10 && i < 12) || (i >= 15 && i < 17))
                CharacterMenu.setItem(i, RED_GLASS_PANE);
        }

        CharacterMenu.setItem(26, ITEM_CLOSE);
        CharacterMenu.setItem(14, SELECT_BREATHING);
        CharacterMenu.setItem(12, TEAM_SELECTION);
        CharacterMenu.setItem(13, READY);
        CharacterMenu.setItem(4, GAMEMODE);


    }

    public void updateTeamMenu() {
        TeamMenu = Bukkit.createInventory(null, 27, Chat.getConsoleName() + ChatColor.BLACK + "");
        console.getGamemodeManager().createTeams(TeamMenu, ds.board.getScoreboard());
    }

    public void updateQuirkMenu() {
        QuirkMenu = Bukkit.createInventory(null, 27, ChatColor.BLACK + "Select your Quirk!");
        List<Component> lore = new ArrayList<>();
        ItemMeta meta;

        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);
        ItemStack QUIRK;
        ItemStack BLACK_GLASS_PANE = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        meta = BLACK_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        BLACK_GLASS_PANE.setItemMeta(meta);

        //ITEM_CLOSE
        meta = ITEM_CLOSE.getItemMeta();
        meta.displayName(Component.text("[Close]").color(TextColor.color(255, 0, 0)));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &cClick to close menu")));
        meta.lore(lore);
        ITEM_CLOSE.setItemMeta(meta);

        QuirkMenu.setItem(26, ITEM_CLOSE);

        //Fill the inventory with config breathings
        int i = 0;
        try {
            if (!ds.getConfig().getConfigurationSection("Quirks").getKeys(false).isEmpty()) {
                for (String key : ds.getConfig().getConfigurationSection("Quirks").getKeys(false)) {
                    //Item
                    QUIRK = new ItemStack(Material.getMaterial((String) ds.getConfig().get("Quirks." + key + ".Item")));
                    meta = QUIRK.getItemMeta();
                    meta.displayName(Component.text(Chat.format("&f&l[&a&lQuirk&f&l] " +ds.getConfig().get("Quirks." + key + ".DisplayName") + "")));
                    lore.clear();
                    lore.add(Component.text(Chat.format("&7Classification: " + ds.getConfig().get("Quirks." + key + ".Classification"))));
                    lore.add(Component.text(Chat.format("&7Role: " + ds.getConfig().get("Quirks." + key + ".Role"))));
                    lore.add(Component.text(Chat.format("&7Type: " + ds.getConfig().get("Quirks." + key + ".Type"))));

                    meta.lore(lore);
                    meta.setLocalizedName(key);
                    QUIRK.setItemMeta(meta);

                    QuirkMenu.setItem(i, QUIRK);

                    i++;
                }
            }
        } catch (Exception e) {
            getServer().broadcastMessage("Breathing Exception");
        }

        for (int j = i; j < 26; j++) {
            QuirkMenu.setItem(j, BLACK_GLASS_PANE);
        }

    }

    public void updateSlotMenu() {
        SlotMenu = Bukkit.createInventory(null, 27, Chat.getConsoleName() + ChatColor.BLACK + "");
        List<Component> lore = new ArrayList<>();
        ItemMeta meta;

        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);

        ItemStack BLACK_GLASS_PANE = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack LIME_GLASS_PANE = new ItemStack(Material.LIME_STAINED_GLASS_PANE);

        meta = BLACK_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        BLACK_GLASS_PANE.setItemMeta(meta);

        meta = LIME_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(" "));
        LIME_GLASS_PANE.setItemMeta(meta);

        for (int i = 0; i < 27; i++) {
            SlotMenu.setItem(i, BLACK_GLASS_PANE);
        }

        //ITEM_CLOSE
        meta = ITEM_CLOSE.getItemMeta();
        meta.displayName(Component.text("[Close]").color(TextColor.color(255, 0, 0)));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &cClick to close menu")));
        meta.lore(lore);
        ITEM_CLOSE.setItemMeta(meta);
        SlotMenu.setItem(26, ITEM_CLOSE);

        // /Slot 1
        meta = LIME_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&aSlot 1&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to add ability to slot 1!")));
        meta.lore(lore);
        meta.setLocalizedName("one");
        LIME_GLASS_PANE.setItemMeta(meta);
        SlotMenu.setItem(10, LIME_GLASS_PANE);

        // /Slot 2
        meta = LIME_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&aSlot 2&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to add ability to slot 2!")));
        meta.lore(lore);
        meta.setLocalizedName("two");
        LIME_GLASS_PANE.setItemMeta(meta);
        SlotMenu.setItem(13, LIME_GLASS_PANE);

        // /Slot 3
        meta = LIME_GLASS_PANE.getItemMeta();
        meta.displayName(Component.text(Chat.format("&8[&aSlot 3&8]")));
        lore.clear();
        lore.add(Component.text(Chat.format("&f+ &aClick to add ability to slot 3!")));
        meta.lore(lore);
        meta.setLocalizedName("three");
        LIME_GLASS_PANE.setItemMeta(meta);
        SlotMenu.setItem(16, LIME_GLASS_PANE);


    }

    public void updatePlaygroundMenu(){
        PlaygroundMenu = Bukkit.createInventory(null, 27, Chat.format("&cPlayground &dMenu"));

        ItemStack DUMMY = new ItemStack(Material.ARMOR_STAND);
        ItemStack HEAL = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemStack QUIRK = new ItemStack(Material.DRAGON_BREATH);
        ItemStack CLOSE = new ItemStack(Material.BARRIER);

        ItemStack BLACK = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack YELLOW = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);

        //Sets the whole inventory to black
        for(int i = 0; i < 27; i++){
            PlaygroundMenu.setItem(i,BLACK);
        }

        //Sets the middle section of the inventory to yellow
        for(int i = 10; i < 17; i++){
            PlaygroundMenu.setItem(i,YELLOW);
        }


        //Meta for the DUMMY item
        ItemMeta meta = DUMMY.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();
        meta.displayName(Component.text(Chat.format("&f[&eSummon Dummy&F]")));
        lore.add(Component.text(Chat.format("&a+ &eClick to summon a dummy at your current &alocation")));
        meta.lore(lore);
        meta.setLocalizedName("summon_dummy");
        DUMMY.setItemMeta(meta);

        lore.clear();

        //Meta for the HEAL item
        meta = HEAL.getItemMeta();
        meta.displayName(Component.text(Chat.format("&f[&aHeal &e+ &6Stamina&f]")));
        lore.add(Component.text(Chat.format("&a+ &eClick to reset your &chealth &eand &6stamina&e!")));
        meta.lore(lore);
        meta.setLocalizedName("heal_stamina");
        HEAL.setItemMeta(meta);

        lore.clear();

        //Meta for the QUIRK item
        meta = QUIRK.getItemMeta();
        meta.displayName(Component.text(Chat.format("&f[&6Quirk Selection&f]")));
        lore.add(Component.text(Chat.format("&a+ &eClick to select your &aQuirk&e!")));
        meta.lore(lore);
        meta.setLocalizedName("quirk");
        QUIRK.setItemMeta(meta);

        lore.clear();

        //Meta for the CLOSE item
        meta = CLOSE.getItemMeta();
        meta.displayName(Component.text(Chat.format("&f[&cClose&f]")));
        lore.add(Component.text(Chat.format("&a+ &eClick to close!")));
        meta.lore(lore);
        meta.setLocalizedName("close");
        CLOSE.setItemMeta(meta);


        //Creates the menu if the items
        PlaygroundMenu.setItem(13,HEAL);
        PlaygroundMenu.setItem(12, DUMMY);
        PlaygroundMenu.setItem(14,QUIRK);
        PlaygroundMenu.setItem(PlaygroundMenu.getSize()-1, CLOSE);


    }

    public Inventory getPlaygroundMenu() {
        return PlaygroundMenu;
    }

    public Inventory getGamemodeMenu() {
        return GamemodeMenu;
    }

    public Inventory getMainMenu() {
        return MainMenu;
    }

    public Inventory getMapMenu() {
        return MapMenu;
    }

    public Inventory getCharacterMenu() {
        return CharacterMenu;
    }

    public Inventory getQuirkMenu() {
        return QuirkMenu;
    }

    public Inventory getTeamMenu() {
        return TeamMenu;
    }

    public Inventory getSlotMenu() {
        return SlotMenu;
    }
}
