package hadences.projectmha.player;

import hadences.projectmha.game.quirk.Quirk;
import hadences.projectmha.ProjectMHA;
import hadences.projectmha.gui.GUIMenuManager;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.net.ssl.SSLEngineResult;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public static HashMap<UUID, PlayerManager> playerdata = new HashMap<>();

    //Player Info
    private String PLAYER_NAME;
    private int WINS;
    private java.util.UUID UUID;
    //In Game information
    private Quirk QUIRK;
    private String TEAM;
    private HashMap<String, BukkitTask> RUNNABLES = new HashMap<>();


    //GUI information
    private GUIMenuManager guiMenuManager;

    //Game Variables
    private boolean DEV_MODE;
    private boolean IN_GAME;
    private boolean ALIVE;
    private boolean IS_READY;
    private boolean IN_LOBBY;
    private boolean ABILITY_USAGE;
    private int STAMINA;
    private boolean CAN_MOVE;
    private boolean CAN_JUMP;
    private boolean FALL_DAMAGE;

    //Quirk Variables


    public PlayerManager(Player player, int WINS) {
        this.PLAYER_NAME = player.getName();
        this.WINS = WINS;
        this.UUID = player.getUniqueId();
        this.QUIRK = Quirk.getQuirk("None");
        this.TEAM = "NONE";

        //Gui Information
        this.guiMenuManager = new GUIMenuManager();
        guiMenuManager.createInventories();

        //Game Variables
        this.DEV_MODE = false;
        this.IN_GAME = false;
        this.ALIVE = true;
        this.IS_READY = false;
        this.IN_LOBBY = false;
        this.ABILITY_USAGE = false;
        this.STAMINA = 0;
        this.CAN_MOVE = true;
        this.CAN_JUMP = true;
        this.FALL_DAMAGE = true;
    }

    public static void FixQuirkSchedulers(Player p, String key,BukkitTask sched){
        HashMap<String,BukkitTask> runnables = playerdata.get(p.getUniqueId()).getRUNNABLES();
        if(runnables.containsKey(key)){
            runnables.get(key).cancel();
            runnables.remove(key);
        }
        runnables.put(key,sched);
    }

    public boolean isFALL_DAMAGE() {
        return FALL_DAMAGE;
    }

    public void setFALL_DAMAGE(boolean FALL_DAMAGE) {
        this.FALL_DAMAGE = FALL_DAMAGE;
    }

    public boolean isCAN_JUMP() {
        return CAN_JUMP;
    }

    public void setCAN_JUMP(boolean CAN_JUMP) {
        this.CAN_JUMP = CAN_JUMP;
    }

    public HashMap<String, BukkitTask> getRUNNABLES() {
        return RUNNABLES;
    }

    public void setRUNNABLES(HashMap<String, BukkitTask> RUNNABLES) {
        this.RUNNABLES = RUNNABLES;
    }

    public String getPLAYER_NAME() {
        return PLAYER_NAME;
    }

    public void setPLAYER_NAME(String PLAYER_NAME) {
        this.PLAYER_NAME = PLAYER_NAME;
    }

    public int getWINS() {
        return WINS;
    }

    public void setWINS(int WINS) {
        this.WINS = WINS;
    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public Quirk getQUIRK() {
        return QUIRK;
    }

    public void setQUIRK(Quirk QUIRK) {
        this.QUIRK = QUIRK;
    }

    public String getTEAM() {
        return TEAM;
    }

    public void setTEAM(String TEAM) {
        this.TEAM = TEAM;
    }

    public boolean isDEV_MODE() {
        return DEV_MODE;
    }

    public void setDEV_MODE(boolean DEV_MODE) {
        this.DEV_MODE = DEV_MODE;
    }

    public boolean isIN_GAME() {
        return IN_GAME;
    }

    public void setIN_GAME(boolean IN_GAME) {
        this.IN_GAME = IN_GAME;
    }

    public boolean isALIVE() {
        return ALIVE;
    }

    public void setALIVE(boolean ALIVE) {
        this.ALIVE = ALIVE;
    }

    public boolean isIS_READY() {
        return IS_READY;
    }

    public void setIS_READY(boolean IS_READY) {
        this.IS_READY = IS_READY;
    }

    public boolean isIN_LOBBY() {
        return IN_LOBBY;
    }

    public void setIN_LOBBY(boolean IN_LOBBY) {
        this.IN_LOBBY = IN_LOBBY;
    }

    public boolean isABILITY_USAGE() {
        return ABILITY_USAGE;
    }

    public void setABILITY_USAGE(boolean ABILITY_USAGE) {
        this.ABILITY_USAGE = ABILITY_USAGE;
    }

    public int getSTAMINA() {
        return STAMINA;
    }

    public void setSTAMINA(int STAMINA) {
        this.STAMINA = STAMINA;
    }

    public boolean isCAN_MOVE() {
        return CAN_MOVE;
    }

    public void setCAN_MOVE(boolean CAN_MOVE) {
        this.CAN_MOVE = CAN_MOVE;
    }

    public GUIMenuManager getGuiMenuManager() {
        return guiMenuManager;
    }

    public void setGuiMenuManager(GUIMenuManager guiMenuManager) {
        this.guiMenuManager = guiMenuManager;
    }
}
