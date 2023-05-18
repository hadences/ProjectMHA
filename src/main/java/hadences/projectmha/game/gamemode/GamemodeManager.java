package hadences.projectmha.game.gamemode;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

import static hadences.projectmha.player.PlayerManager.playerdata;


public class GamemodeManager implements Listener {
    public static ArrayList<Listener> gamemodeListeners = new ArrayList<>();
    private ProjectMHA ds = ProjectMHA.getPlugin(ProjectMHA.class);
    private boolean endGame;
    private String Winner;
    private ArrayList<Player> playerlist;

    private String Gamemode = "NONE";

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

    public void playintromusic(){

    }

    public HashMap<UUID, Player> getLastDamager() {
        return LastDamager;
    }

    public void initialize(int StartingStamina, double StartingHealth, ArrayList<Player> playerlist) {


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
            ds.resetInventory(p);
            p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
            p.setGameMode(GameMode.ADVENTURE);
            ds.board.showMainBoard();
        }
    }

    public void endgame() {

    }

    public String printWinnerStatement() {
        if (Winner.isEmpty() || Winner.equalsIgnoreCase("")) {
            return Chat.format("&cForce Ended");
        } else {
            return Chat.format("&6" + Winner + " &fWins!");
        }
    }


    public void checkForWinner() {

    }

    public String getGamemode() {
        return Gamemode;
    }

    public void setGamemode(String gamemode) {
        Gamemode = gamemode;
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

    public void createTeams(Inventory board, Scoreboard scoreboard) {

    }

    public void updateGameBoard(Scoreboard scoreboard, ArrayList<String> scores){

    }

    public void resetTeams(Set<Team> teams){
        for(Team t : teams){
            t.unregister();
        }
    }
}
