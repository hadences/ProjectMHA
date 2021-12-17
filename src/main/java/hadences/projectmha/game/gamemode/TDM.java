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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.gui.events.GUIEventManager.sendTitleToAll;
import static hadences.projectmha.player.PlayerManager.playerdata;


public class TDM extends GamemodeManager implements Listener {
    private ProjectMHA ds = ProjectMHA.getPlugin(ProjectMHA.class);
    private boolean endGame;
    private String Winner;
    private ArrayList<Player> playerlist;
    private HashMap<Team,Integer> teamScores = new HashMap<>();
    private HashMap<UUID,Player> LastDamager = new HashMap<>();

    @EventHandler
    public void PlayerDamaged(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if(LastDamager.containsKey(player.getUniqueId())){
                LastDamager.replace(player.getUniqueId(),damager);
            }else{
                LastDamager.put(player.getUniqueId(),damager);
            }

        }
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
    }

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

    public void endgame() {
        //shows a title saying that the game ended
        sendTitleToAll(Chat.format("&c[&eGame Ended&c]"), printWinnerStatement());
        //set everyone to gamemode spectator and play sound
        for (Player p : playerlist) {
            p.setGameMode(GameMode.SPECTATOR);
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 1.0f);
        }
        //after 5 seconds teleport players back to spawn
        //reset all player values
        new BukkitRunnable() {
            @Override
            public void run() {
                end();
            }
        }.runTaskLater(ds, 5 * 20L);
    }

    public String printWinnerStatement() {
        if (Winner.isEmpty() || Winner.equalsIgnoreCase("")) {
            return Chat.format("&cForce End");
        } else {
            return Chat.format("&6" + Winner + " &fWins!");
        }
    }

    public void initialize(int StartingStamina, double StartingHealth, ArrayList<Player> playerlist) {
        this.playerlist = playerlist;
        endGame = false;
        Winner = "";

        teleportPlayersSpawnpoint();
        initializeTeamScore();

        for (Player p : playerlist) {
            playerdata.get(p.getUniqueId()).setSTAMINA(StartingStamina);
            p.setMaxHealth(StartingHealth);
            p.setHealth(StartingHealth);
            playerdata.get(p.getUniqueId()).setCAN_MOVE(false);
        }

    }

    public void incrementTeamScore(Team t){
        teamScores.replace(t,teamScores.get(t) + 4);
    }

    public void initializeTeamScore(){
        teamScores.put(ds.board.getScoreboard().getTeam("&cRed"),0 );
        teamScores.put(ds.board.getScoreboard().getTeam("&9Blue"),0 );
    }

    public void teleportPlayersSpawnpoint() {
        for (Player p : playerlist) {
            if (playerdata.get(p.getUniqueId()).getTEAM().equalsIgnoreCase("&cRed")){
                p.teleport(console.getT1spawnpoint());
            }else if (playerdata.get(p.getUniqueId()).getTEAM().equalsIgnoreCase("&9Blue")){
                p.teleport(console.getT2spawnpoint());
            }
        }

    }

    public void checkForWinner() {
        int maxScore = (int) ds.getConfig().get("Game.Gamemodes.Team-Deathmatch.Settings.MaxScore");
        Team Red = ds.board.getScoreboard().getTeam("&cRed");
        Team Blue = ds.board.getScoreboard().getTeam("&9Blue");

        if(console.getMinutes() == 0 && console.getSeconds() == 0){
            setWinner(getWinningTeam());
            setEndGame(true);
        }else if (teamScores.get(Red) == maxScore){
            setWinner(Chat.format("&cRed"));
            setEndGame(true);
        }else if (teamScores.get(Blue) == maxScore){
            setWinner(Chat.format("&9Blue"));
            setEndGame(true);
        }
    }

    public String getWinningTeam(){
        if(teamScores.get(ds.board.getScoreboard().getTeam("&cRed")) < teamScores.get(ds.board.getScoreboard().getTeam("&9Blue"))){
            return Chat.format("&9Blue");
        }
        return Chat.format("&cRed");
    }

    public HashMap<UUID, Player> getLastDamager() {
        return LastDamager;
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
                    Bukkit.broadcast(Component.text(Chat.format("" + ds.board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM()).getColor() + p.getName() + " &eeliminated by " + ds.board.getScoreboard().getTeam(playerdata.get(p.getKiller().getUniqueId()).getTEAM()).getColor() + p.getKiller().getName())));

                    incrementTeamScore(ds.board.getScoreboard().getTeam(playerdata.get(p.getKiller().getUniqueId()).getTEAM()));
                }else {
                    try {
                        //Death Message
                        Bukkit.broadcast(Component.text(Chat.format("" + ds.board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM()).getColor() + p.getName() + " &eeliminated by " + ds.board.getScoreboard().getTeam(playerdata.get(LastDamager.get(p.getUniqueId()).getUniqueId()).getTEAM()).getColor() + LastDamager.get(p.getUniqueId()).getName())));

                        incrementTeamScore(ds.board.getScoreboard().getTeam(playerdata.get(LastDamager.get(p.getUniqueId()).getUniqueId()).getTEAM()));
                    }catch (Exception exception){
                        Bukkit.broadcast(Component.text(Chat.format("" + ds.board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM()).getColor() + p.getName() + " &eeliminated&c!")));
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


        //Team Red
        Team Red = Board.registerNewTeam("&cRed");
        Red.setAllowFriendlyFire(true);
        Red.setCanSeeFriendlyInvisibles(false);
        Red.setPrefix(Chat.format("&cRed &f"));
        Red.setDisplayName(Chat.format("&cRed &f"));
        Red.color(NamedTextColor.RED);

        //Team Blue
        Team Blue = Board.registerNewTeam("&9Blue");
        Blue.setAllowFriendlyFire(false);
        Blue.setCanSeeFriendlyInvisibles(false);
        Blue.setPrefix(Chat.format("&9Blue &f"));
        Blue.setDisplayName(Chat.format("&9Blue &f"));
        Blue.color(NamedTextColor.BLUE);

        ItemStack RED = new ItemStack(Material.RED_WOOL);
        ItemStack BLUE = new ItemStack(Material.BLUE_WOOL);
        ItemStack Blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack ITEM_CLOSE = new ItemStack(Material.BARRIER);
        for (int j = 0; j < 27; j++) {
            board.setItem(j, Blank);
        }

        //Team Red
        ItemMeta meta = RED.getItemMeta();
        meta.displayName(Component.text(Chat.format("&cRed &f")));
        ArrayList<Component> List = new ArrayList<>();
        List.add(Component.text(Chat.format("&aClick to join team!")));
        meta.lore(List);
        meta.setLocalizedName("&cRed");
        RED.setItemMeta(meta);
        board.setItem(0, RED);

        //Team Blue
        meta = BLUE.getItemMeta();
        meta.displayName(Component.text(Chat.format("&9Blue &f")));
        List.clear();
        List.add(Component.text(Chat.format("&aClick to join team!")));
        meta.lore(List);
        meta.setLocalizedName("&9Blue");
        BLUE.setItemMeta(meta);
        board.setItem(1, BLUE);

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
        String Gamemode = Chat.format("  &fGamemode: &dTeam Deathmatch");
        String Score = Chat.format("    &c&l<< &eScore &c&l>>");
        String TeamRed = Chat.format("  &e>>&cTeam Red Score &7: &b" + teamScores.get(ds.board.getScoreboard().getTeam("&cRed")));
        String TeamBlue = Chat.format("  &e>>&9Team Blue Score &7: &b" + teamScores.get(ds.board.getScoreboard().getTeam("&9Blue")));

        objective.getScore(Gamemode).setScore(10);
        scores.add(Gamemode);

        objective.getScore("        ").setScore(9);
        scores.add("        ");

        objective.getScore(Score).setScore(8);
        scores.add(Score);

        objective.getScore(TeamRed).setScore(7);
        scores.add(TeamRed);

        objective.getScore(TeamBlue).setScore(6);
        scores.add(TeamBlue);

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
