package hadences.projectmha.gui.events;

import hadences.projectmha.commands.summondummy.Dummy;
import hadences.projectmha.game.gamemode.FFA;
import hadences.projectmha.game.gamemode.TDM;
import hadences.projectmha.game.quirk.Quirk;
import hadences.projectmha.game.gamemode.Playground;
import hadences.projectmha.player.PlayerManager;
import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.item.GameItems;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static hadences.projectmha.game.GameManager.*;
import static hadences.projectmha.game.quirk.Quirk.*;
import static hadences.projectmha.player.PlayerManager.playerdata;
import static hadences.projectmha.config.ArenaConfig.getMaps;


public class GUIEventManager implements Listener {
    private ProjectMHA ds = ProjectMHA.getPlugin(ProjectMHA.class);
    private GameItems gameItems = new GameItems();
    @EventHandler
    public void GamemodeMenu(InventoryClickEvent e) {
        if (e.getCurrentItem() == null)
            return;
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory() != playerdata.get(p.getUniqueId()).getGuiMenuManager().getGamemodeMenu())
            return; //checks if the inventory is Inventory GamemodeMenu
        e.setCancelled(true);

        int slot = e.getSlot();
        PlayerManager pm = playerdata.get(p.getUniqueId());
        String gm = e.getCurrentItem().getItemMeta().getLocalizedName();

        //Close Menu
        if (slot == 26) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
            return;
        } else if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE)
            return;

        //Gamemode logic
        else if (gm.equalsIgnoreCase("Playground")) {
            console.setGamemodeManager(new Playground());
            console.updateGamemodeSettings(gm);
            updateAllTeamMenu();
        }
        else if (gm.equalsIgnoreCase("Free-For-All")) {
            console.setGamemodeManager(new FFA());
            console.updateGamemodeSettings(gm);
            updateAllTeamMenu();
        }else if (gm.equalsIgnoreCase("Team-Deathmatch")) {
            console.setGamemodeManager(new TDM());
            console.updateGamemodeSettings(gm);
            updateAllTeamMenu();
        }


            sendTitleToAll(Chat.format("" + e.getCurrentItem().getItemMeta().getDisplayName()), ChatColor.WHITE + "Gamemode Selected");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.2f, 2.0f);
            p.closeInventory();


    }

    public void updateAllTeamMenu(){
        for(Player p : console.getPlayerList()){
            playerdata.get(p.getUniqueId()).getGuiMenuManager().updateTeamMenu();
        }
    }

    @EventHandler
    public void PlaygroundMenuEvent(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory() != playerdata.get(p.getUniqueId()).getGuiMenuManager().getPlaygroundMenu())
            return;
        e.setCancelled(true);

        PlayerManager pm = playerdata.get(p.getUniqueId());

        if(!e.getCurrentItem().getItemMeta().hasLocalizedName()){
            p.closeInventory();
            return;
        }

        String local_name = e.getCurrentItem().getItemMeta().getLocalizedName();

        if(local_name.equalsIgnoreCase("close")){
            p.closeInventory();
        }
        else if(local_name.equalsIgnoreCase("quirk")){
            p.closeInventory();
            p.openInventory(pm.getGuiMenuManager().getQuirkMenu());
        }
        else if(local_name.equalsIgnoreCase("summon_dummy")){
            p.closeInventory();
            Dummy dummy = new Dummy();
            dummy.spawn(p);
            p.sendMessage(Component.text(Chat.format("&eSummoned dummy.")));
        }
        else if(local_name.equalsIgnoreCase("heal_stamina")){
            p.closeInventory();
            p.setHealth(p.getMaxHealth());
            pm.setSTAMINA((Integer) ds.getConfig().get("Game.Gamemodes.Playground.Settings.StartingStamina"));
            p.sendMessage(Component.text(Chat.format("&6Stamina &eand &cHealth &ereset!")));
        }
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        p.getInventory().setHeldItemSlot(3);
    }


    @EventHandler
    public void CharacterMenuEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory() != playerdata.get(p.getUniqueId()).getGuiMenuManager().getCharacterMenu())
            return; //checks if the inventory is Inventory CharacterMenu
        e.setCancelled(true);

        int slot = e.getSlot();
        PlayerManager pm = playerdata.get(p.getUniqueId());

        //Close Menu
        if (slot == 26) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        }
        //Select Quirk
        else if (slot == 14) {
            if (playerdata.get(p.getUniqueId()).getTEAM().equalsIgnoreCase("NONE")) {
                p.sendMessage(Component.text(Chat.getConsoleName() + Chat.format(" &aSelect your team!")));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 2.0F);
                p.closeInventory();
                return;
            }

            p.closeInventory();
            p.openInventory(pm.getGuiMenuManager().getQuirkMenu());
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        }
        //Select Team
        else if (slot == 12) {
            if (console.getGamemodeManager() == null) {
                p.sendMessage(Component.text(Chat.getConsoleName() + Chat.format(" &aSelect a gamemode first!")));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 2.0F);
                p.closeInventory();
                return;
            }
            p.closeInventory();
            p.openInventory(pm.getGuiMenuManager().getTeamMenu());
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

        }
        //Ready up
        else if (slot == 13) {
            p.closeInventory();
            if (canReady(p) && console.getGamemodeManager() != null) {
                Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " is ready!");
                playerdata.get(p.getUniqueId()).setIS_READY(true);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
            } else if (console.getGamemodeManager() == null) {
                p.sendMessage(Component.text(Chat.getConsoleName() + Chat.format(" &aGamemode not selected!")));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 2.0F);
            } else if (playerdata.get(p.getUniqueId()).getTEAM().equalsIgnoreCase("NONE")) {
                p.sendMessage(Component.text(Chat.getConsoleName() + Chat.format(" &aSelect your team!")));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 2.0F);
            } else {
                p.sendMessage(Component.text(Chat.getConsoleName() + Chat.format(" &aSelect your quirk!")));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 2.0F);
            }

        } else if (e.getCurrentItem().getItemMeta().getLocalizedName().equalsIgnoreCase("gamemode")) {
            if(!PlayerReady()) {
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                p.closeInventory();
                p.openInventory(playerdata.get(p.getUniqueId()).getGuiMenuManager().getGamemodeMenu());
            }else{
                p.closeInventory();
                p.sendMessage(Component.text(Chat.format("&eA player has locked in! Gamemodes can &cnot &ebe changed after a player has locked in.")));
            }

        }

    }

    public boolean PlayerReady(){
        for(Player p : console.getPlayerList()){
            if(playerdata.get(p.getUniqueId()).isIS_READY()){
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void QuirkMenuEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null)
            return;
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory() != playerdata.get(p.getUniqueId()).getGuiMenuManager().getQuirkMenu())
            return; //checks if the inventory is Inventory CharacterMenu
        e.setCancelled(true);

        int slot = e.getSlot();
        PlayerManager pm = playerdata.get(p.getUniqueId());
        String quirk = e.getCurrentItem().getItemMeta().getLocalizedName();

        //Close Menu
        if (slot == 26) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        } else if (selectQuirk(quirk)) {
            if(QuirkTaken(p,quirk)){
                p.closeInventory();
                sendTitle_QuirkTaken(p);
                return;
            }

            playerdata.get(p.getUniqueId()).setQUIRK(Quirk.getQuirk(quirk));
            p.sendTitle(playerdata.get(p.getUniqueId()).getQUIRK().getDISPLAY_NAME(),Chat.format("&aQuirk &fSelected"));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
            gameItems.givePlayerGameItems(p);
            gameItems.givePlayerQuirkInfo(p,3);
            p.closeInventory();
        }

    }

    //
    @EventHandler
    public void TeamMenuEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null)
            return;
        Player p = (Player) e.getWhoClicked();
        PlayerManager pm = playerdata.get(p.getUniqueId());
        if (e.getInventory() != pm.getGuiMenuManager().getTeamMenu())
            return; //checks if the inventory is Inventory Team Menu
        e.setCancelled(true);
        int slot = e.getSlot();

        //Close Menu
        if (slot == 26) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        } else if (e.getCurrentItem().getItemMeta().hasLocalizedName()) {
            joinTeam(e.getCurrentItem().getItemMeta().getLocalizedName(), p);
            p.closeInventory();
            p.sendMessage(Chat.format("&eJoined Team " + e.getCurrentItem().getItemMeta().getLocalizedName()));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
            p.sendMessage(Chat.format("&eYour quirk has been reset to &aQuirkless!"));
            playerdata.get(p.getUniqueId()).setQUIRK(Quirk.getQuirk("None"));
        }
    }


    @EventHandler
    public void MainMenuEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null)
            return;
        Player p = (Player) e.getWhoClicked();
        PlayerManager pm = playerdata.get(p.getUniqueId());
        if (e.getInventory() != pm.getGuiMenuManager().getMainMenu())
            return; //checks if the inventory is Inventory SlotMenu

        if(e.getCurrentItem().getItemMeta().getLocalizedName() == null)
            return;

        e.setCancelled(true);
        //Close Menu
        if (e.getSlot() == 26) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        } else if (e.getCurrentItem().getItemMeta().getLocalizedName().equalsIgnoreCase("start")) {
            p.closeInventory();
            playerdata.get(p.getUniqueId()).getGuiMenuManager().updateMapMenu();
            p.openInventory(playerdata.get(p.getUniqueId()).getGuiMenuManager().getMapMenu());
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
        }

    }

    @EventHandler
    public void MapMenuEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        PlayerManager pm = playerdata.get(p.getUniqueId());
        if (e.getInventory() != pm.getGuiMenuManager().getMapMenu())
            return; //checks if the inventory is Inventory SlotMenu
        e.setCancelled(true);

        //Play Menu{
        if (e.getSlot() != 53) {
            if (checkPlayersInGame()) {
                p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " Game is in Progress!");
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5F, 1.0F);
                return;
            }
            //Button_Play
            if(e.getSlot() > getMaps().size()){
                return;
            }
            String MapID = getMaps().get(e.getSlot()).getArenaname();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
            //p.sendMessage("Map Joined : " + MapID);
            p.sendMessage(Chat.getConsoleName() + ChatColor.WHITE + " Game Created for Map : " + ChatColor.GREEN + MapID);
            createGame(MapID);
            p.closeInventory();
        } else if (e.getSlot() == 53) {
            //Button_Close
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
            p.closeInventory();
        }
        //}


    }

    //check if the player can ready up
    public boolean canReady(Player p) {
        PlayerManager pm = playerdata.get(p.getUniqueId());
        if (pm.getQUIRK() != null && !pm.getTEAM().equalsIgnoreCase("NONE"))
            return true;
        return false;
    }

    //sends a title to all existing players
    public static void sendTitleToAll(String title, String subtitle) {
        for (Player p : console.getPlayerList()) {
            p.sendTitle(title, subtitle);
        }
    }

    public void joinTeam(String team, Player p) {
        ds.board.getScoreboard().getTeam(team).addPlayer(p);
        playerdata.get(p.getUniqueId()).setTEAM(team);
    }
}
