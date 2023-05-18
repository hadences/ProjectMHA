package hadences.projectmha.game.quirk;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.Quirks.Blackwhip.Blackwhip;
import hadences.projectmha.game.quirk.Quirks.Bloodcurdle.Bloodcurdle;
import hadences.projectmha.game.quirk.Quirks.Cremation.Cremation;
import hadences.projectmha.game.quirk.Quirks.Decay.Decay;
import hadences.projectmha.game.quirk.Quirks.Engine.Engine;
import hadences.projectmha.game.quirk.Quirks.Erasure.Erasure;
import hadences.projectmha.game.quirk.Quirks.Explosion.Explosion;
import hadences.projectmha.game.quirk.Quirks.FaJin.FaJin;
import hadences.projectmha.game.quirk.Quirks.HalfColdHalfHot.HalfColdHalfHot;
import hadences.projectmha.game.quirk.Quirks.Hardening.Hardening;
import hadences.projectmha.game.quirk.Quirks.Heal.Heal;
import hadences.projectmha.game.quirk.Quirks.HellFlame.HellFlame;
import hadences.projectmha.game.quirk.Quirks.Overhaul.Overhaul;
import hadences.projectmha.game.quirk.Quirks.Permeation.Permeation;
import hadences.projectmha.game.quirk.Quirks.Rewind.Rewind;
import hadences.projectmha.game.quirk.Quirks.Rifle.Rifle;
import hadences.projectmha.game.quirk.Quirks.Tape.Tape;
import hadences.projectmha.game.quirk.Quirks.ZeroGravity.ZeroGravity;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.player.PlayerManager.playerdata;
import static org.bukkit.ChatColor.RED;

public class Quirk implements Cloneable {

    public static ArrayList<Quirk> quirklist = new ArrayList<>();

    private String QUIRK_NAME;
    private String DISPLAY_NAME;
    private String CLASSIFICATION;
    private String ROLE;
    private QuirkCastManager QUIRKCASTMANAGER;

    public Quirk(String quirk_name, String display_name, String classification, String role) {
        QUIRK_NAME = quirk_name;
        DISPLAY_NAME = display_name;
        CLASSIFICATION = classification;
        ROLE = role;
        QUIRKCASTMANAGER = null;
        setQUIRKCASTMANAGER(QUIRK_NAME);
    }

    public Quirk(Quirk quirk){
        QUIRK_NAME = quirk.getQUIRK_NAME();
        DISPLAY_NAME = quirk.getDISPLAY_NAME();
        CLASSIFICATION = quirk.getCLASSIFICATION();
        ROLE = quirk.getROLE();
        QUIRKCASTMANAGER = null;
        setQUIRKCASTMANAGER(QUIRK_NAME);
    }

    public void setQUIRKCASTMANAGER(String QUIRK_NAME) {
        if(QUIRK_NAME.equalsIgnoreCase("Permeation")) this.QUIRKCASTMANAGER = new Permeation();
        else if(QUIRK_NAME.equalsIgnoreCase("Blackwhip")) this.QUIRKCASTMANAGER = new Blackwhip();
        else if(QUIRK_NAME.equalsIgnoreCase("Erasure")) this.QUIRKCASTMANAGER = new Erasure();
        else if(QUIRK_NAME.equalsIgnoreCase("Zero-Gravity")) this.QUIRKCASTMANAGER = new ZeroGravity();
        else if(QUIRK_NAME.equalsIgnoreCase("HalfCold-HalfHot")) this.QUIRKCASTMANAGER = new HalfColdHalfHot();
        else if(QUIRK_NAME.equalsIgnoreCase("Explosion")) this.QUIRKCASTMANAGER = new Explosion();
        else if(QUIRK_NAME.equalsIgnoreCase("Tape")) this.QUIRKCASTMANAGER = new Tape();
        else if(QUIRK_NAME.equalsIgnoreCase("Decay")) this.QUIRKCASTMANAGER = new Decay();
        else if(QUIRK_NAME.equalsIgnoreCase("Engine")) this.QUIRKCASTMANAGER = new Engine();
        else if(QUIRK_NAME.equalsIgnoreCase("FaJin")) this.QUIRKCASTMANAGER = new FaJin();
        else if(QUIRK_NAME.equalsIgnoreCase("Hardening")) this.QUIRKCASTMANAGER = new Hardening();
        else if(QUIRK_NAME.equalsIgnoreCase("Cremation")) this.QUIRKCASTMANAGER = new Cremation();
        else if(QUIRK_NAME.equalsIgnoreCase("Rewind")) this.QUIRKCASTMANAGER = new Rewind();
        else if(QUIRK_NAME.equalsIgnoreCase("Bloodcurdle")) this.QUIRKCASTMANAGER = new Bloodcurdle();
        else if(QUIRK_NAME.equalsIgnoreCase("Rifle")) this.QUIRKCASTMANAGER = new Rifle();
        //else if(QUIRK_NAME.equalsIgnoreCase("Overhaul")) this.QUIRKCASTMANAGER = new Overhaul();
        else if(QUIRK_NAME.equalsIgnoreCase("Hellflame")) this.QUIRKCASTMANAGER = new HellFlame();
        else if(QUIRK_NAME.equalsIgnoreCase("Heal")) this.QUIRKCASTMANAGER = new Heal();


    }

    public QuirkCastManager getQUIRKCASTMANAGER() {
        return QUIRKCASTMANAGER;
    }

    public static Quirk getQuirk(String quirk) {

        for (int i = 0; i < quirklist.size(); i++) {
            if (quirklist.get(i).getQUIRK_NAME().equalsIgnoreCase(quirk))
                return new Quirk(quirklist.get(i));
        }
        return null;
    }



    public String getQUIRK_NAME() {
        return QUIRK_NAME;
    }

    public void setQUIRK_NAME(String QUIRK_NAME) {
        this.QUIRK_NAME = QUIRK_NAME;
    }

    public String getDISPLAY_NAME() {
        return DISPLAY_NAME;
    }

    public void setDISPLAY_NAME(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;
    }

    public String getCLASSIFICATION() {
        return CLASSIFICATION;
    }

    public void setCLASSIFICATION(String CLASSIFICATION) {
        this.CLASSIFICATION = CLASSIFICATION;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }


    public static boolean selectQuirk(String quirk) {
        for (Quirk q : quirklist) {
            if(q.getQUIRK_NAME().equalsIgnoreCase(quirk))
                return true;
        }
        return false;
    }

    public static boolean QuirkTaken(Player p, String quirk){
        if(ProjectMHA.getPlugin(ProjectMHA.class).board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM()).allowFriendlyFire() == false){
            for(Player e: console.getPlayerList()){
                if(playerdata.get(p.getUniqueId()).getTEAM().equalsIgnoreCase(playerdata.get(e.getUniqueId()).getTEAM())){
                    try {
                        if (playerdata.get(e.getUniqueId()).getQUIRK().getQUIRK_NAME().equalsIgnoreCase(quirk)) {
                            return true;
                        }
                    }catch (Exception exception){continue;}
                }
            }
        }
        return false;
    }

    public static void sendTitle_QuirkTaken(Player p){ p.sendTitle(ChatColor.WHITE + "[" + RED + "Quirk Taken" + ChatColor.WHITE + "]",ChatColor.GOLD + "Someone chose this quirk!"); }


}
