package hadences.projectmha.game.gamemode;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.item.GameItems;
import hadences.projectmha.player.PlayerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.gui.events.GUIEventManager.sendTitleToAll;
import static hadences.projectmha.player.PlayerManager.playerdata;


public class FFA extends GamemodeManager implements Listener {
    private ProjectMHA ds = ProjectMHA.getPlugin(ProjectMHA.class);
    private boolean endGame;
    private String Winner;
    private ArrayList<Player> playerlist;
    private HashMap<Player, Integer> killcount = new HashMap<Player, Integer>();
    private HashMap<UUID,Player> LastDamager = new HashMap<>();

    @EventHandler
    public void PlayerDamaged(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
        Player player = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        if(LastDamager.containsKey(player.getUniqueId())){
            LastDamager.replace(player.getUniqueId(),damager);
            return;
        }else{
            LastDamager.put(player.getUniqueId(),damager);
            return;
        }

        }
    }

    public HashMap<UUID, Player> getLastDamager() {
        return LastDamager;
    }

    public void start() {
        PlayerManager pm;
        for (Player p : playerlist) {
            p.getInventory().setHeldItemSlot(3);
            pm = playerdata.get(p.getUniqueId());
            pm.setALIVE(true);
            pm.setIN_GAME(true);
            pm.setIN_LOBBY(false);
            pm.setABILITY_USAGE(true);
            pm.setCAN_MOVE(true);
        }
        console.setCDUltimate();

    }

    //resets the player data back to the regular game
    public void end() {
        resetTeams(ds.board.getScoreboard().getTeams());
        PlayerManager pm;
        for (Player p : playerlist) {
            pm = playerdata.get(p.getUniqueId());
            pm.setALIVE(false);
            pm.setIN_GAME(false);
            pm.setABILITY_USAGE(false);
            pm.setIN_LOBBY(false);
            pm.setIS_READY(false);
            p.setMaxHealth(20);
            p.setHealth(20);
            ds.resetInventory(p);
            p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
            p.setGameMode(GameMode.ADVENTURE);
            ds.board.showMainBoard();
        }
    }

    //end game logic
    public void endgame() {
        //shows a title saying that the game ended
        sendTitleToAll(Chat.format("&c[&eGame Ended&c]"), printWinnerStatement());
        //set everyone to gamemode spectator and play sound
        for (Player p : playerlist) {
            console.endPassives();
            p.setGameMode(GameMode.SPECTATOR);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 1.0f);
        }
        grantWins();
        //after 5 seconds teleport players back to spawn
        //reset all player values
        new BukkitRunnable() {
            @Override
            public void run() {
                end();
            }
        }.runTaskLater(ds, 5 * 20L);
    }

    public void grantWins(){
        for(Player p: playerlist){
            if(p.getName().contains(Winner)){
                playerdata.get(p.getUniqueId()).setWINS(playerdata.get(p.getUniqueId()).getWINS()+1);
            }
        }
    }

    public String printWinnerStatement() {
        if (Winner.isEmpty() || Winner.equalsIgnoreCase("")) {
            return Chat.format("&cForce End");
        } else {
            return Chat.format("&6" + Winner + " &fWins!");
        }
    }

    //Game starting logic
    public void initialize(int StartingStamina, double StartingHealth, ArrayList<Player> playerlist) {
        this.playerlist = playerlist;
        endGame = false;
        Winner = "";

        teleportPlayersSpawnpoint();
        initializeKillCount();

        for (Player p : playerlist) {
            playerdata.get(p.getUniqueId()).setSTAMINA(StartingStamina);
            p.setMaxHealth(StartingHealth);
            p.setHealth(StartingHealth);
            playerdata.get(p.getUniqueId()).setCAN_MOVE(false);
            p.setTotalExperience(1);
        }

    }

    public void showKillCount(){
        for(Player p: playerlist){
            p.setTotalExperience(killcount.get(p));
        }
    }

    public void teleportPlayersSpawnpoint() {
        ArrayList<Location> usedSpawnpoints = new ArrayList<>();
        Random random = new Random();
        for (Player p : playerlist) {
            Location spawnpoint = console.getSpawnpoints().get(random.nextInt(console.getSpawnpoints().size()));
            if(!usedSpawnpoints.contains(spawnpoint)){
                p.teleport(spawnpoint);
                usedSpawnpoints.add(spawnpoint);
            }
        }

    }

    //sets all players kill count scores to 0
    public void initializeKillCount(){
        for(Player p : playerlist){
            killcount.put(p,0);
        }
    }

    //increases the player's score by 1
    public void incrementPlayerKillCount(Player p){
        if(!killcount.isEmpty() && killcount.containsKey(p)){
            int oldValue = killcount.get(p);
            int kills = killcount.get(p)+1;
            killcount.replace(p,oldValue,kills);


        }
    }

    //returns a list of the three top players respectively
    public ArrayList<Player> getTop3Players(){
        ArrayList<Player> top3 = new ArrayList<>();
        ArrayList<Player> plist = (ArrayList<Player>) playerlist.clone();
        HashMap<Player, Integer> killcount_temp = (HashMap<Player, Integer>) killcount.clone();

            Player top_player;
            top_player = getTopPlayer(plist, killcount_temp);
            top3.add(top_player);
            plist.remove(top_player);
            killcount_temp.remove(top_player);

            if(!plist.isEmpty()) {
                top_player = getTopPlayer(plist, killcount_temp);
                top3.add(top_player);
                plist.remove(top_player);
                killcount_temp.remove(top_player);
            }else{
                top3.add(null);
            }
            if(!plist.isEmpty()) {
                top_player = getTopPlayer(plist, killcount_temp);
                top3.add(top_player);
                killcount_temp.remove(top_player);
            }else{
                top3.add(null);
            }

        return top3;
    }

    //converts the player into a string formatted score
    public String convertPlayerToScore(Player p){
        if(p == null)
            return "NONE";
        return killcount.get(p) + "";
    }

    //converts the player into a string formatted name
    public String convertPlayerToName(Player p){
        if(p == null)
            return "NONE";
        return p.getName();
    }


    //returns the top player from the given list
    public Player getTopPlayer(ArrayList<Player> plist,HashMap<Player,Integer> list){
        Player top_player = plist.get(0);
        for(Player p : list.keySet()){
            if(list.get(top_player) < list.get(p)){
                top_player = p;
            }
        }
        return top_player;
    }

    //Winner conditions - when time = 0 and check for highest score player or player gets Certain amount of kills
    public void checkForWinner() {
        int maxkills = (int) ds.getConfig().get("Game.Gamemodes.Free-For-All.Settings.KillCount");
        ArrayList<Player> top3 = getTop3Players();
        if(console.getMinutes() <= 0 && console.getSeconds() <= 0){
            setWinner(top3.get(0).getName());
            setEndGame(true);
        }else if(killcount.get(top3.get(0)) == maxkills){
            setWinner(top3.get(0).getName());
            setEndGame(true);
        }
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public Location getRandomSpawnpoint() {
        int random = new Random().nextInt(console.getSpawnpoints().size());
        return console.getSpawnpoints().get(random);
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity();
            if (playerdata.get(p.getUniqueId()).isIN_GAME()) {
                e.setCancelled(true);
                p.sendTitle(ChatColor.DARK_RED + "[" + ChatColor.RED + "ELIMINATED" + ChatColor.DARK_RED + "]", "");
                p.setGameMode(GameMode.SPECTATOR);
                playerdata.get(p.getUniqueId()).setABILITY_USAGE(false);
                if(p.getKiller() != null) {
                    //Death Message
                    Bukkit.broadcast(Component.text(Chat.format("&6" + p.getName() + " &eeliminated by &6" + p.getKiller().getName())));
                    //Increment killer points
                    incrementPlayerKillCount(e.getEntity().getKiller());
                    playerdata.get(p.getKiller().getUniqueId()).setSTAMINA(console.getStartingStamina());
                    showKillCount();
                    p.getKiller().playSound(p.getKiller().getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.5f,2f);
                }else {
                    try {
                        //Death Message
                        Bukkit.broadcast(Component.text(Chat.format("&6" + p.getName() + " &eeliminated by &6" + LastDamager.get(p.getUniqueId()).getName())));
                        //Increment killer points
                        incrementPlayerKillCount(LastDamager.get(p.getUniqueId()));
                        playerdata.get(LastDamager.get(p.getUniqueId()).getUniqueId()).setSTAMINA(console.getStartingStamina());
                        LastDamager.get(p.getUniqueId()).playSound(LastDamager.get(p.getUniqueId()).getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.5f,2f);

                        showKillCount();
                    }catch (Exception exception){
                        Bukkit.broadcast(Component.text(Chat.format("&6" + p.getName() + " &eeliminated&c!")));
                    }
                }
            } else {
                return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    p.getInventory().setHeldItemSlot(3);
                    p.setMaxHealth(console.getStartingHealth());
                    p.setHealth(console.getStartingHealth());
                    p.setFoodLevel(20);
                    p.setGameMode(GameMode.ADVENTURE);
                    p.teleport(getRandomSpawnpoint());
                    playerdata.get(p.getUniqueId()).setABILITY_USAGE(true);
                    playerdata.get(p.getUniqueId()).setSTAMINA(console.getStartingStamina());
                    p.sendTitle(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Respawned" + ChatColor.DARK_GREEN + "]", "");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);
                }


            }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), 100);

        }
    }

    public void createTeams(Inventory board, Scoreboard Board) {
        resetTeams(Board.getTeams());

        for(Player p : console.getPlayerList()){
            playerdata.get(p.getUniqueId()).setTEAM("NONE");
        }


        if(Board.getObjective("Kills") != null)
        Board.getObjective("Kills").unregister();
        Objective kills = Board.registerNewObjective("Kills",Criterias.TOTAL_KILLS);
        kills.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        //Team Player
        Team Player = Board.registerNewTeam("&7Player");
        Player.setAllowFriendlyFire(true);
        Player.setCanSeeFriendlyInvisibles(false);
        Player.setPrefix(Chat.format("&7<Player> &f"));
        Player.setDisplayName(Chat.format("&7<Player> &f"));
        Player.color(NamedTextColor.GRAY);

        ItemStack Solo = new ItemStack(Material.GRAY_WOOL);
        ItemStack Blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);
        for (int j = 0; j < 27; j++) {
            board.setItem(j, Blank);
        }

        ItemMeta meta = Solo.getItemMeta();
        meta.displayName(Component.text(Chat.format("&f<&7Solo&f>")));
        ArrayList<Component> List = new ArrayList<>();
        List.add(Component.text(Chat.format("&aClick to join team!")));
        meta.lore(List);
        meta.setLocalizedName("&7Player");
        Solo.setItemMeta(meta);
        board.setItem(0, Solo);

        //ITEM_CLOSE
        meta = ITEM_CLOSE.getItemMeta();
        meta.displayName(Component.text("[Close]").color(TextColor.color(255, 0, 0)));
        List.clear();
        List.add(Component.text(Chat.format("&f+ &cClick to close menu")));
        meta.lore(List);
        ITEM_CLOSE.setItemMeta(meta);

        board.setItem(26, ITEM_CLOSE);

    }



    public void updateGameBoard(Scoreboard scoreboard, ArrayList<String> scores){
        int minutes = console.getMinutes();
        int seconds = console.getSeconds();
        Objective objective = scoreboard.getObjective("GameBoard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-")).setScore(12);
        scores.add(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-"));

        objective.getScore("").setScore(11);
        scores.add("");
        //Top 3 Players
        ArrayList<Player> top3 = getTop3Players();

        Player First_Place = top3.get(0);
        Player Second_Place = top3.get(1);
        Player Third_Place = top3.get(2);

        /*
        ---------------------------- 11
                                     10
         << Score >>                  9
          1.) Name  ⚔ 3               8
          2.) Name  ⚔ 2               7
          3.) Name  ⚔ 1               6
                                      5
            Time                      4
         */
        String Gamemode = Chat.format("  &fGamemode: &dFree For All");
        String Score = Chat.format("    &c&l<< &eScore &c&l>>");
        String First = Chat.format("  &e1. &f" + convertPlayerToName(First_Place) + "  &7⚔ &e" + convertPlayerToScore(First_Place));
        String Second = Chat.format("  &e2. &f" + convertPlayerToName(Second_Place) + "  &7⚔ &e" + convertPlayerToScore(Second_Place));
        String Third = Chat.format("  &e3. &f" + convertPlayerToName(Third_Place) + "  &7⚔ &e" + convertPlayerToScore(Third_Place));

        objective.getScore(Gamemode).setScore(10);
        scores.add(Gamemode);

        objective.getScore("        ").setScore(9);
        scores.add("        ");

        objective.getScore(Score).setScore(8);
        scores.add(Score);

        objective.getScore(First).setScore(7);
        scores.add(First);

        objective.getScore(Second).setScore(6);
        scores.add(Second);

        objective.getScore(Third).setScore(5);
        scores.add(Third);

        objective.getScore(" ").setScore(4);
        scores.add(" ");

        if (console.getSeconds() < 10) {
            objective.getScore("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":0" + seconds).setScore(3);
            scores.add("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":0" + seconds);

        } else {
            objective.getScore("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":" + seconds).setScore(3);
            scores.add("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":" + seconds);
        }
        objective.getScore("   ").setScore(2);
        scores.add("   ");
    }
}
