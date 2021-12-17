package hadences.projectmha.game;

import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.gamemode.GamemodeManager;
import hadences.projectmha.ProjectMHA;
import hadences.projectmha.arena.Arena;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.object.MHABlock;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import static hadences.projectmha.game.quirk.Cooldown.*;
import static hadences.projectmha.game.gamemode.GamemodeManager.gamemodeListeners;
import static hadences.projectmha.player.PlayerManager.playerdata;
import static hadences.projectmha.arena.Arena.arenalist;
import static org.bukkit.Bukkit.getServer;

public class GameManager {

    public static GameManager console;
    private ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public static HashMap<Location, MHABlock> MHABlocks = new HashMap<>();
    public static HashMap<Location, BukkitTask> BlockTimers = new HashMap<>();

    //Lobby Variables
    private int PlayersNeeded;
    private ArrayList<Player> PlayerList;
    private boolean inLobby;

    //Game Variables
    private String mapID;
    private int StartingStamina;
    private double StartingHealth;
    private Arena Arena;
    private GamemodeManager gamemodeManager;
    private boolean inGame;
    private ArrayList<Location> Spawnpoints;
    private Location t1spawnpoint;
    private Location t2spawnpoint;
    private int minutes;
    private int Seconds;

    public GameManager(String mapID) {
        this.mapID = mapID;
        //this.StartingHealth = (double) mha.getConfig().get("Game.Settings.StartingHealth");
        //this.StartingStamina = (int) mha.getConfig().get("Game.Settings.StartingStamina");
        this.PlayersNeeded = 1;
        this.PlayerList = new ArrayList<>();
        this.Arena = arenalist.get(mapID);
        this.inLobby = false;
        this.inGame = false;
        this.Spawnpoints = arenalist.get(mapID).getSpawnpoints();
        this.t1spawnpoint = arenalist.get(mapID).getT1spawn();
        this.t2spawnpoint = arenalist.get(mapID).getT2spawn();
        //this.minutes = (int) mha.getConfig().get("Game.Settings.Minutes");
        //this.Seconds = (int) mha.getConfig().get("Game.Settings.Seconds");
        gamemodeManager = null;
    }

    public void updateGamemodeSettings(String Gamemode){
        this.StartingHealth = (double) mha.getConfig().get("Game.Gamemodes."+Gamemode+".Settings.StartingHealth");
        this.StartingStamina = (int) mha.getConfig().get("Game.Gamemodes."+Gamemode+".Settings.StartingStamina");
        this.minutes = (int) mha.getConfig().get("Game.Gamemodes."+Gamemode+".Settings.Minutes");
        this.Seconds = (int) mha.getConfig().get("Game.Gamemodes."+Gamemode+".Settings.Seconds");
    }

    //Call beginning methomha of the game
    public void GameInitialize() {
        LobbyInitialize();
    }

    public void LobbyInitialize() {
        for (Player p : PlayerList) {
            p.teleport(Arena.getLobbyspawn());
            playerdata.get(p.getUniqueId()).setIN_LOBBY(true);
            playerdata.get(p.getUniqueId()).setABILITY_USAGE(false);
            inLobby = true;
        }
    }

    public void initiate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                loop();
                if (!inGame && inLobby) { //Players are in Lobby
                    //Lobby Methods
                    //check if all players ready up
                    mha.board.showLobbyBoard();
                    if (checkAllPlayersReady() && getPlayerList().size() >= getPlayersNeeded() && gamemodeManager != null) {
                        startgame();
                    }
                    //update teams
                    //updateTeams(board);
                    //update lobby scoreboard
                    //updateLobbyScoreboard(plugin.board);
                } else if (inGame && !inLobby) {
                    updateTimer("-");
                    mha.board.showGameBoard();
                    gamemodeManager.checkForWinner();
                    //check if end game
                    if (gamemodeManager.isEndGame()) {
                        gamemodeManager.endgame();
                        unregisterEvents();
                        this.cancel();
                    }


                }
            }
        }.runTaskTimer(mha, 0, 20L);


    }

    public void registerGamemodeEvents() {
        getServer().getPluginManager().registerEvents((Listener) gamemodeManager, ProjectMHA.getPlugin(ProjectMHA.class));
        gamemodeListeners.add((Listener) gamemodeManager);
    }

    public void unregisterEvents() {
        for (Object l : gamemodeListeners) {
            HandlerList.unregisterAll((Listener) l);
        }
        gamemodeListeners.clear();
    }

    public static boolean checkPlayersInGame() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (playerdata.get(p.getUniqueId()).isIN_GAME() || playerdata.get(p.getUniqueId()).isIN_LOBBY()) {
                return true;
            }
        }
        return false;
    }

    public void addPlayersToGameList(Collection<? extends Player> playersList) {
        for (Player p : playersList) {
            PlayerList.add(p);
        }
    }

    public boolean checkAllPlayersReady() {
        for (Player p : PlayerList) {
            if (!playerdata.get(p.getUniqueId()).isIS_READY())
                return false;
        }
        return true;
    }

    public void startgame() {
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time == 6) {
                    gamemodeManager.initialize(StartingStamina, StartingHealth, PlayerList);
                    start_after();
                    registerGamemodeEvents();
                    this.cancel();
                }
                if (time == 0) {
                    inLobby = false;
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + "5");
                }
                if (time == 1) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + "4");
                }
                if (time == 2) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + "3");
                }
                if (time == 3) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + "2");
                }
                if (time == 4) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + "1");
                }
                if (time == 5) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.RED + "Starting!");
                }
                playsoundtoAll(Sound.BLOCK_DISPENSER_DISPENSE, 2.0f);

                time++;
            }
        }.runTaskTimer(mha, 0, 20l);
    }

    public void start_after() {
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time == 1) {
                    sendTitleToAll("3");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                }
                if (time == 2) {
                    sendTitleToAll("2");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                }
                if (time == 3) {
                    sendTitleToAll("1");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                }
                if (time == 4) {
                    sendTitleToAll("START");
                    playsoundtoAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                    setCDUltimate();
                    setPassives();
                    gamemodeManager.start();
                    inGame = true;
                    this.cancel();
                }
                time++;
            }
        }.runTaskTimer(mha, 0, 20l);
    }

    public void setPassives(){
        for(Player p : getPlayerList()){
            playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().initPassive(p);
        }
    }

    public void setCDUltimate(){
        for(Player p : getPlayerList()){
            cooldowns3.put(p.getName(), System.currentTimeMillis() + (playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_COOLDOWN() * 1000));
        }
    }

    public static void createGame(String Arena) {
        console = new GameManager(Arena);
        console.addPlayersToGameList(Bukkit.getOnlinePlayers());
        console.GameInitialize();
        console.initiate();
    }


    public void sendTitleToAll(String string) {
        for (Player p : getPlayerList()) {
            p.sendTitle(ChatColor.RED + "<" + ChatColor.YELLOW + string + ChatColor.RED + ">", "");
        }

    }

    public void playsoundtoAll(Sound sound, float pitch) {
        for (Player p : getPlayerList()) {
            p.playSound(p.getLocation(), sound, 0.5F, pitch);
        }
    }

    public void updateTimer(String mode) {
        if (mode.equalsIgnoreCase("+")) {
            Seconds = Seconds + 1;
            if (Seconds == 60) {
                minutes = minutes + 1;
                Seconds = 0;
            }
        } else if (mode.equalsIgnoreCase("-")) {
            if (Seconds <= 0 && minutes >= 1) {
                minutes--;
                Seconds = 59;
            } else if (Seconds <= 0 && minutes == 0) {
                Seconds = 0;
            }
            Seconds--;
        }
    }

    public void loop() {
        //gets each player and sets each player's food level to 20 if its less than 20
        // During GAME this shows the information that the player needs during fights.
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getFoodLevel() < 20) {
                p.setFoodLevel(20);
            }

            if (playerdata.get(p.getUniqueId()).isIN_GAME() && !playerdata.get(p.getUniqueId()).isIN_LOBBY()) {
                try {
                    if (playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getQUIRK_STORAGE() == null) {
                        p.sendActionBar(org.bukkit.ChatColor.RED + "<<< "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "1" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "2" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns2) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "3" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns3) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "RC" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns4) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "♦" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.YELLOW + playerdata.get(p.getUniqueId()).getSTAMINA() + " "
                                + org.bukkit.ChatColor.RED + " >>>");
                    } else {
                        p.sendActionBar(org.bukkit.ChatColor.RED + "<<< "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "1" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "2" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns2) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "3" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns3) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "RC" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.RED + checkCD(p, cooldowns4) + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.GOLD + "♦" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.YELLOW + playerdata.get(p.getUniqueId()).getSTAMINA() + " "
                                + org.bukkit.ChatColor.WHITE + "[" + org.bukkit.ChatColor.AQUA + "♦" + org.bukkit.ChatColor.WHITE + "] : " + org.bukkit.ChatColor.DARK_AQUA + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getQUIRK_STORAGE() + " "
                                + org.bukkit.ChatColor.RED + " >>>");
                    }
                }catch (Exception e){

                }
                if(UltimateTimer.UltTimer.containsKey(p.getName())){
                    if (UltimateTimer.UltTimer.get(p.getName()) > System.currentTimeMillis()) {
                        long timeleft = (UltimateTimer.UltTimer.get(p.getName()) - System.currentTimeMillis()) / 1000;
                        p.sendTitle(" ", Chat.format("&d<&b" + timeleft+"&d>"),0,20,0);
                    }
                }
            }
        }
    }

    public String checkCD(Player p, HashMap<String, Long> cd) {
        String r = "";
        if (cd.get(p.getName()) > System.currentTimeMillis()) {
            long timeleft = (cd.get(p.getName()) - System.currentTimeMillis()) / 1000;
            return r + timeleft;
        }
        return org.bukkit.ChatColor.GREEN + "R";
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return Seconds;
    }

    public void setSeconds(int Seconds) {
        this.Seconds = Seconds;
    }

    public void setStartingHealth(double startingHealth) {
        StartingHealth = startingHealth;
    }

    public ArrayList<Location> getSpawnpoints() {
        return Spawnpoints;
    }

    public void setSpawnpoints(ArrayList<Location> spawnpoints) {
        Spawnpoints = spawnpoints;
    }

    public Location getT1spawnpoint() {
        return t1spawnpoint;
    }

    public void setT1spawnpoint(Location t1spawnpoint) {
        this.t1spawnpoint = t1spawnpoint;
    }

    public Location getT2spawnpoint() {
        return t2spawnpoint;
    }

    public void setT2spawnpoint(Location t2spawnpoint) {
        this.t2spawnpoint = t2spawnpoint;
    }

    public int getPlayersNeeded() {
        return PlayersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
        PlayersNeeded = playersNeeded;
    }

    public ArrayList<Player> getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        PlayerList = playerList;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    public String getMapID() {
        return mapID;
    }

    public void setMapID(String mapID) {
        this.mapID = mapID;
    }

    public int getStartingStamina() {
        return StartingStamina;
    }

    public void setStartingStamina(int startingStamina) {
        StartingStamina = startingStamina;
    }

    public Double getStartingHealth() {
        return StartingHealth;
    }

    public void setStartingHealth(Double startingHealth) {
        StartingHealth = startingHealth;
    }

    public Arena getArena() {
        return Arena;
    }

    public void setArena(Arena arena) {
        Arena = arena;
    }

    public GamemodeManager getGamemodeManager() {
        return gamemodeManager;
    }

    public void setGamemodeManager(GamemodeManager gamemodeManager) {
        this.gamemodeManager = gamemodeManager;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
