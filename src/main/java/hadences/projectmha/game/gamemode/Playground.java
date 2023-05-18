package hadences.projectmha.game.gamemode;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.player.PlayerManager.playerdata;
import static hadences.projectmha.gui.events.GUIEventManager.sendTitleToAll;


public class Playground extends GamemodeManager implements Listener {
    private ProjectMHA ds = ProjectMHA.getPlugin(ProjectMHA.class);
    private boolean endGame;
    private String Winner;
    private ArrayList<Player> playerlist;

    private String Gamemode = "Playground";
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
            console.endPassives();
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

        for (Player p : playerlist) {
            playerdata.get(p.getUniqueId()).setSTAMINA(StartingStamina);
            p.setMaxHealth(StartingHealth);
            p.setHealth(StartingHealth);
            playerdata.get(p.getUniqueId()).setCAN_MOVE(false);
        }

    }

    public void teleportPlayersSpawnpoint() {
        for (Player p : playerlist) {
            p.teleport(console.getT1spawnpoint());
        }

    }

    @Override
    public String getGamemode() {
        return Gamemode;
    }

    @Override
    public void setGamemode(String gamemode) {
        Gamemode = gamemode;
    }

    public void checkForWinner() {
        //no winner must force end to stop this gamemode
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
                p.sendTitle(ChatColor.DARK_RED + "[" + ChatColor.RED + "You have been slain.." + ChatColor.DARK_RED + "]", "");
                p.setGameMode(GameMode.SPECTATOR);
                playerdata.get(p.getUniqueId()).setABILITY_USAGE(false);
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
                    p.sendTitle(ChatColor.DARK_RED + "[" + ChatColor.GREEN + "Respawned" + ChatColor.DARK_RED + "]", "");
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2, 2);
                }


            }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), 100);

        }
    }

    public void createTeams(Inventory board, Scoreboard Board) {
        resetTeams(Board.getTeams());
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

        objective.getScore(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-")).setScore(10);
        scores.add(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-"));

        objective.getScore("").setScore(9);
        scores.add("");

        String Gamemode = Chat.format("  &fGamemode: &dPlayground");
        objective.getScore(Gamemode).setScore(7);
        scores.add(Gamemode);

        if (console.getSeconds() < 10) {
            objective.getScore("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":0" + seconds).setScore(3);
            scores.add("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":0" + seconds);

        } else {
            objective.getScore("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":" + seconds).setScore(3);
            scores.add("  " + ChatColor.RED + "[" + ChatColor.YELLOW + "✦" + ChatColor.RED + "]" + ChatColor.WHITE + " Time : " + ChatColor.GREEN + minutes + ":" + seconds);
        }
        objective.getScore("   ").setScore(3);
        scores.add("   ");
        objective.getScore("    ").setScore(2);
        scores.add("    ");

    }
}
