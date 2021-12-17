package hadences.projectmha.scoreboard;

import hadences.projectmha.chat.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.player.PlayerManager.playerdata;


public class BoardManager {
    private Scoreboard scoreboard;
    ArrayList<String> scores = new ArrayList<>();


    //Main Board
    private int OnlinePlayers;

    //Lobby Board
    private boolean PlayersReady;
    private boolean GamemodeSelected;

    public void createScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        //Health Scoreboard

        Objective Health = scoreboard.registerNewObjective(Chat.format("&cHealth ❤"), Criterias.HEALTH);
        Health.setDisplaySlot(DisplaySlot.BELOW_NAME);

        //PlayerData Board
        Objective MainBoard = scoreboard.registerNewObjective("MainBoard", "dummy");
        MainBoard.setDisplayName("  " + Chat.getConsoleName() + "     ");
        MainBoard.setDisplaySlot(DisplaySlot.SIDEBAR);

        //GameLobby Board
        Objective LobbyBoard = scoreboard.registerNewObjective("LobbyBoard", "dummy");
        LobbyBoard.setDisplayName(Chat.getConsoleName());

        //inGame Board
        Objective GameBoard = scoreboard.registerNewObjective("GameBoard", "dummy");
        GameBoard.setDisplayName(Chat.getConsoleName());
    }

    public void showMainBoard() {
        resetScores();
        updateMainBoard();
        scoreboard.getObjective("MainBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void showLobbyBoard() {
        resetScores();
        updateLobbyBoard();
        scoreboard.getObjective("LobbyBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void showGameBoard() {
        resetScores();
        updateGameBoard();
        scoreboard.getObjective("GameBoard").setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateMainBoard() {
        OnlinePlayers = Bukkit.getOnlinePlayers().size();

        Objective objective = scoreboard.getObjective("MainBoard");
        objective.getScore(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-")).setScore(10);
        scores.add(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-"));

        objective.getScore("").setScore(9);

        objective.getScore(Chat.format("  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "♟" + ChatColor.WHITE + "]" + ChatColor.GRAY + " Online Players : " + ChatColor.GREEN + OnlinePlayers)).setScore(8);
        scores.add(Chat.format("  " + ChatColor.WHITE + "[" + ChatColor.GREEN + "♟" + ChatColor.WHITE + "]" + ChatColor.GRAY + " Online Players : " + ChatColor.GREEN + OnlinePlayers));

        objective.getScore(" ").setScore(7);

        objective.getScore(Chat.format(" " + ChatColor.WHITE + "" + ChatColor.BOLD + "-" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-")).setScore(2);
        scores.add(Chat.format(" " + ChatColor.WHITE + "" + ChatColor.BOLD + "-" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-"));
        objective.getScore("   ").setScore(1);

    }

    public void updateLobbyBoard() {
        PlayersReady = allPlayersReady();
        GamemodeSelected = GamemodeSelected();
        Objective objective = scoreboard.getObjective("LobbyBoard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(Chat.format(ChatColor.WHITE + "" + ChatColor.BOLD + " -" + ChatColor.RED + "" + ChatColor.BOLD + "------------------" + ChatColor.WHITE + "" + ChatColor.BOLD + "-")).setScore(10);

        objective.getScore("").setScore(9);
        scores.add("");

        scores.add("  " + ChatColor.WHITE + "[" + ChatColor.BLUE + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " GM Selected : " + ChatColor.WHITE + GamemodeSelected);
        objective.getScore("  " + ChatColor.WHITE + "[" + ChatColor.BLUE + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " GM Selected : " + ChatColor.WHITE + GamemodeSelected).setScore(8);

        objective.getScore(" ").setScore(7);
        scores.add(" ");
        objective.getScore("  " + ChatColor.WHITE + "[" + ChatColor.RED + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.WHITE + console.getPlayerList().size()).setScore(4);
        scores.add("  " + ChatColor.WHITE + "[" + ChatColor.RED + "♦" + ChatColor.WHITE + "]" + ChatColor.WHITE + " Players : " + ChatColor.WHITE + console.getPlayerList().size());

        objective.getScore("   ").setScore(3);
        scores.add("   ");
        objective.getScore("  " + ChatColor.WHITE + " All Players Ready : " + ChatColor.GOLD + PlayersReady).setScore(2);
        scores.add("  " + ChatColor.WHITE + " All Players Ready : " + ChatColor.GOLD + PlayersReady);

        objective.getScore("    ").setScore(1);

    }

    public void updateGameBoard() {
        console.getGamemodeManager().updateGameBoard(scoreboard,scores);
    }

    public boolean GamemodeSelected() {
        if (console.getGamemodeManager() == null)
            return false;
        return true;
    }

    public boolean allPlayersReady() {
        for (Player p : console.getPlayerList()) {
            if (!playerdata.get(p.getUniqueId()).isIS_READY()) {
                return false;
            }
        }
        return true;
    }

    public void resetScores() {
        for (String score : scores) {
            scoreboard.resetScores(score);
        }
        scores.clear();
    }



    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
