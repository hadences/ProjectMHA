package hadences.projectmha.game.quirk.Quirks.Engine;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static hadences.projectmha.player.PlayerManager.FixQuirkSchedulers;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class Engine extends QuirkCastManager {
    ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public Ability1 ability1 = new Ability1();
    public Ability2 ability2 = new Ability2();
    public Ultimate ultimate = new Ultimate();
    public RCAbility rcAbility = new RCAbility();

    private String ABILITY1_DISPLAY_NAME;
    private String ABILITY1_DESCRIPTION;
    private double ABILITY1_DAMAGE;
    private Integer ABILITY1_STAMINACOST;
    private Integer ABILITY1_COOLDOWN;

    private String ABILITY2_DISPLAY_NAME;
    private String ABILITY2_DESCRIPTION;
    private double ABILITY2_DAMAGE;
    private Integer ABILITY2_STAMINACOST;
    private Integer ABILITY2_COOLDOWN;

    private String ULT_DISPLAY_NAME;
    private String ULT_DESCRIPTION;
    private double ULT_DAMAGE;
    private Integer ULT_STAMINACOST;
    private Integer ULT_COOLDOWN;

    private String RCABILITY_DISPLAY_NAME;
    private String RCABILITY_DESCRIPTION;
    private double RCABILITY_DAMAGE;
    private Integer RCABILITY_STAMINACOST;
    private Integer RCABILITY_COOLDOWN;

    private String QuirkName = "Engine";

    private double OG_ABILITY1_DAMAGE;
    private double OG_ABILITY2_DAMAGE;
    private double OG_ULTIMATE_DAMAGE;
    private double OG_RCABILITY_DAMAGE;

    public double getOG_ABILITY1_DAMAGE() {
        return OG_ABILITY1_DAMAGE;
    }
    public double getOG_ABILITY2_DAMAGE() {
        return OG_ABILITY2_DAMAGE;
    }
    public double getOG_ULTIMATE_DAMAGE() {
        return OG_ULTIMATE_DAMAGE;
    }
    public double getOG_RCABILITY_DAMAGE() {
        return OG_RCABILITY_DAMAGE;
    }

    public Engine() {
        ABILITY1_DISPLAY_NAME = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Name");
        ABILITY1_DESCRIPTION = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Description");
        ABILITY1_DAMAGE =  2 * (double) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Damage");
        ABILITY1_STAMINACOST = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.StaminaCost");
        ABILITY1_COOLDOWN = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Cooldown");

        ABILITY2_DISPLAY_NAME = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability2.Name");
        ABILITY2_DESCRIPTION = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability2.Description");
        ABILITY2_DAMAGE = 2 * (double) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability2.Damage");
        ABILITY2_STAMINACOST = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability2.StaminaCost");
        ABILITY2_COOLDOWN = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability2.Cooldown");

        ULT_DISPLAY_NAME = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ultimate.Name");
        ULT_DESCRIPTION = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ultimate.Description");
        ULT_DAMAGE = 2 * (double) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ultimate.Damage");
        ULT_STAMINACOST = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ultimate.StaminaCost");
        ULT_COOLDOWN = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ultimate.Cooldown");

        RCABILITY_DISPLAY_NAME = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.RCAbility.Name");
        RCABILITY_DESCRIPTION = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.RCAbility.Description");
        RCABILITY_DAMAGE = 2 * (double) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.RCAbility.Damage");
        RCABILITY_STAMINACOST = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.RCAbility.StaminaCost");
        RCABILITY_COOLDOWN = (Integer) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.RCAbility.Cooldown");

        OG_ABILITY1_DAMAGE = ABILITY1_DAMAGE;
        OG_ABILITY2_DAMAGE = ABILITY2_DAMAGE;
        OG_ULTIMATE_DAMAGE = ULT_DAMAGE;
        OG_RCABILITY_DAMAGE = RCABILITY_DAMAGE;
    }

    public void initPassive(Player p){
        double passive_speed = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Engine.Passive.Speed");
        p.setWalkSpeed((float) passive_speed);
    }


    public boolean CastAbility1(Player p) {
        ability1.ability(p);
        return true;
    }

    public boolean CastAbility2(Player p) {
        mha.getServer().getPluginManager().registerEvents(ability2,ProjectMHA.getPlugin(ProjectMHA.class));
        ability2.ability(p,ability2);
        return true;
    }

    public boolean CastUltimate(Player p) {
        broadcastUltimate(p);
        mha.getServer().getPluginManager().registerEvents(ultimate,ProjectMHA.getPlugin(ProjectMHA.class));
        ultimate.ability(p,ultimate);
        return true;
    }

    public boolean CastRCAbility(Player p) {
        rcAbility.ability(p);
        return true;
    }

    public String getABILITY1_DISPLAY_NAME() {
        return ABILITY1_DISPLAY_NAME;
    }

    public void setABILITY1_DISPLAY_NAME(String ABILITY1_DISPLAY_NAME) {
        this.ABILITY1_DISPLAY_NAME = ABILITY1_DISPLAY_NAME;
    }

    public String getABILITY1_DESCRIPTION() {
        return ABILITY1_DESCRIPTION;
    }

    public void setABILITY1_DESCRIPTION(String ABILITY1_DESCRIPTION) {
        this.ABILITY1_DESCRIPTION = ABILITY1_DESCRIPTION;
    }

    public double getABILITY1_DAMAGE() {
        return ABILITY1_DAMAGE;
    }

    public void setABILITY1_DAMAGE(double ABILITY1_DAMAGE) {
        this.ABILITY1_DAMAGE = ABILITY1_DAMAGE;
    }

    public Integer getABILITY1_STAMINACOST() {
        return ABILITY1_STAMINACOST;
    }

    public void setABILITY1_STAMINACOST(Integer ABILITY1_STAMINACOST) {
        this.ABILITY1_STAMINACOST = ABILITY1_STAMINACOST;
    }

    public Integer getABILITY1_COOLDOWN() {
        return ABILITY1_COOLDOWN;
    }

    public void setABILITY1_COOLDOWN(Integer ABILITY1_COOLDOWN) {
        this.ABILITY1_COOLDOWN = ABILITY1_COOLDOWN;
    }

    public String getABILITY2_DISPLAY_NAME() {
        return ABILITY2_DISPLAY_NAME;
    }

    public void setABILITY2_DISPLAY_NAME(String ABILITY2_DISPLAY_NAME) {
        this.ABILITY2_DISPLAY_NAME = ABILITY2_DISPLAY_NAME;
    }

    public String getABILITY2_DESCRIPTION() {
        return ABILITY2_DESCRIPTION;
    }

    public void setABILITY2_DESCRIPTION(String ABILITY2_DESCRIPTION) {
        this.ABILITY2_DESCRIPTION = ABILITY2_DESCRIPTION;
    }

    public double getABILITY2_DAMAGE() {
        return ABILITY2_DAMAGE;
    }

    public void setABILITY2_DAMAGE(double ABILITY2_DAMAGE) {
        this.ABILITY2_DAMAGE = ABILITY2_DAMAGE;
    }

    public Integer getABILITY2_STAMINACOST() {
        return ABILITY2_STAMINACOST;
    }

    public void setABILITY2_STAMINACOST(Integer ABILITY2_STAMINACOST) {
        this.ABILITY2_STAMINACOST = ABILITY2_STAMINACOST;
    }

    public Integer getABILITY2_COOLDOWN() {
        return ABILITY2_COOLDOWN;
    }

    public void setABILITY2_COOLDOWN(Integer ABILITY2_COOLDOWN) {
        this.ABILITY2_COOLDOWN = ABILITY2_COOLDOWN;
    }

    public String getULT_DISPLAY_NAME() {
        return ULT_DISPLAY_NAME;
    }

    public void setULT_DISPLAY_NAME(String ULT_DISPLAY_NAME) {
        this.ULT_DISPLAY_NAME = ULT_DISPLAY_NAME;
    }

    public String getULT_DESCRIPTION() {
        return ULT_DESCRIPTION;
    }

    public void setULT_DESCRIPTION(String ULT_DESCRIPTION) {
        this.ULT_DESCRIPTION = ULT_DESCRIPTION;
    }

    public double getULT_DAMAGE() {
        return ULT_DAMAGE;
    }

    public void setULT_DAMAGE(double ULT_DAMAGE) {
        this.ULT_DAMAGE = ULT_DAMAGE;
    }

    public Integer getULT_STAMINACOST() {
        return ULT_STAMINACOST;
    }

    public void setULT_STAMINACOST(Integer ULT_STAMINACOST) {
        this.ULT_STAMINACOST = ULT_STAMINACOST;
    }

    public Integer getULT_COOLDOWN() {
        return ULT_COOLDOWN;
    }

    public void setULT_COOLDOWN(Integer ULT_COOLDOWN) {
        this.ULT_COOLDOWN = ULT_COOLDOWN;
    }

    public String getRCABILITY_DISPLAY_NAME() {
        return RCABILITY_DISPLAY_NAME;
    }

    public void setRCABILITY_DISPLAY_NAME(String RCABILITY_DISPLAY_NAME) {
        this.RCABILITY_DISPLAY_NAME = RCABILITY_DISPLAY_NAME;
    }

    public String getRCABILITY_DESCRIPTION() {
        return RCABILITY_DESCRIPTION;
    }

    public void setRCABILITY_DESCRIPTION(String RCABILITY_DESCRIPTION) {
        this.RCABILITY_DESCRIPTION = RCABILITY_DESCRIPTION;
    }

    public double getRCABILITY_DAMAGE() {
        return RCABILITY_DAMAGE;
    }

    public void setRCABILITY_DAMAGE(double RCABILITY_DAMAGE) {
        this.RCABILITY_DAMAGE = RCABILITY_DAMAGE;
    }

    public Integer getRCABILITY_STAMINACOST() {
        return RCABILITY_STAMINACOST;
    }

    public void setRCABILITY_STAMINACOST(Integer RCABILITY_STAMINACOST) {
        this.RCABILITY_STAMINACOST = RCABILITY_STAMINACOST;
    }

    public Integer getRCABILITY_COOLDOWN() {
        return RCABILITY_COOLDOWN;
    }

    public void setRCABILITY_COOLDOWN(Integer RCABILITY_COOLDOWN) {
        this.RCABILITY_COOLDOWN = RCABILITY_COOLDOWN;
    }

    public String getQuirkName() {
        return QuirkName;
    }

    public void setQuirkName(String quirkName) {
        QuirkName = quirkName;
    }
}

class Ability1{
    private Location location;
    private Vector vector;
    private RaycastUtils raycastUtils;
    public Damage damage;

    public void ability(Player p){
        location = raycastUtils.StartRaycast(p,3.5,1);
        damage(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE());
        visuals(p);
        playsound(p);
    }

    public void damage(Player p, double dmg){
        damage = new Damage();
        damage.damageList(p,(ArrayList<Entity>) location.getNearbyEntities(1,1,1),dmg * (1+p.getWalkSpeed()));
    }

    public void visuals(Player p){
        location = p.getEyeLocation();
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 1.6, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5f));
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.6 * Math.sin(theta), 0.5, 0.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5f));
        }
        vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0, 2, 0), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
        location.getWorld().spawnParticle(Particle.CLOUD,location.clone().add(vector),50,0,0,0,0.3);

    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,1);
    }
}

class Ability2 implements Listener {
    int AbilityTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Engine.Abilities.Ability2.AbilityTimer");
    public void ability(Player p, Ability2 ability2){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,2);
        p.setWalkSpeed(0.8f);
        BukkitTask EngineTask = new EngineScheduler(p,ProjectMHA.getPlugin(ProjectMHA.class),ability2).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),AbilityTimer*20);
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME(),EngineTask);
    }

    @EventHandler
    public void passive(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME().equalsIgnoreCase("Engine")) {
            if (p.getWalkSpeed() == 0.5f) {
                loc.getWorld().spawnParticle(Particle.CLOUD, loc, 5, 0.05, 0.05, 0.05, 0.05);
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 5, 0.05, 0.05, 0.05, 0.05);
                loc.getWorld().spawnParticle(Particle.FLAME, loc, 5, 0.05, 0.05, 0.05, 0.05);
            }

        }

    }
}

class Ultimate implements Listener {
    int AbilityTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Engine.Abilities.Ultimate.UltTimer");
    public void ability(Player p, Ultimate ultimate){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,2);
        p.setWalkSpeed(1f);
        BukkitTask EngineTask = new EngineScheduler(p,ProjectMHA.getPlugin(ProjectMHA.class),ultimate).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),AbilityTimer*20);
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME(),EngineTask);
    }

    @EventHandler
    public void passive(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME().equalsIgnoreCase("Engine")) {
            if (p.getWalkSpeed() == 1) {
                loc.getWorld().spawnParticle(Particle.CLOUD, loc, 5, 0.05, 0.05, 0.05, 0.05);
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 15, 0.15, 0.15, 0.15, 0.05);
                loc.getWorld().spawnParticle(Particle.FLAME, loc, 15, 0.05, 0.05, 0.05, 0.05);
            }

        }

    }
}

class RCAbility{
    int SpeedAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Engine.Abilities.RCAbility.SpeedAmplifier");
    public void ability(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10,SpeedAmplifier-1,true,true));
    }

}

class EngineScheduler extends BukkitRunnable {

    Player p;
    ProjectMHA plugin;
    Object obj;
    public EngineScheduler(Player e, ProjectMHA plugin, Object obj){
        this.p = e;
        this.plugin = plugin;
        this.obj = obj;
    }

    @Override
    public void run() {
        p.setWalkSpeed(0.2f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH,2,1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,40,3));
        HandlerList.unregisterAll((Listener) obj);
    }
}
