package hadences.projectmha.game.quirk.Quirks.FaJin;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
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

import static hadences.projectmha.game.quirk.Cooldown.cooldowns;
import static hadences.projectmha.game.quirk.Cooldown.cooldowns4;
import static hadences.projectmha.player.PlayerManager.FixQuirkSchedulers;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class FaJin extends QuirkCastManager {
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

    private Integer QUIRK_STORAGE;

    private String QuirkName = "FaJin";

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

    public FaJin() {
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

        QUIRK_STORAGE = 0;

        OG_ABILITY1_DAMAGE = ABILITY1_DAMAGE;
        OG_ABILITY2_DAMAGE = ABILITY2_DAMAGE;
        OG_ULTIMATE_DAMAGE = ULT_DAMAGE;
        OG_RCABILITY_DAMAGE = RCABILITY_DAMAGE;
    }


    public boolean CastAbility1(Player p) {
        ability1.ability(p);
        return true;
    }

    public boolean CastAbility2(Player p) {
        ability2.ability(p);
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

    @Override
    public Ultimate getUltimate() {
        return ultimate;
    }

    @Override
    public Integer getQUIRK_STORAGE() {
        return QUIRK_STORAGE;
    }

    @Override
    public void setQUIRK_STORAGE(Integer QUIRK_STORAGE) {
        this.QUIRK_STORAGE = QUIRK_STORAGE;
    }
}

// the ability 1 logicfor Fajin
class Ability1{

    int KineticCharge = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.FaJin.Abilities.Ability1.KineticCharge");
    Vector pos;
    Location location;
    Damage damage;

    public void ability(Player p){
        Location loc = p.getEyeLocation();

        //Add Kinetic Score
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setQUIRK_STORAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getQUIRK_STORAGE()+KineticCharge);

        double dmg = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE();
        //Punch
        location = RaycastUtils.StartRaycast(p,3.5,1);
        damage(p,dmg);
        Visuals(p);
        playsound(p);

        try {
            //Fajin Mode
            if (playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getUltimate().isInUltimate() && p == playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getUltimate().getPlayer()) {
                FaJinVisuals(p, loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,0.5f);
            }
        }catch (Exception e){

        }
    }

    public void damage(Player p, double dmg){
        damage = new Damage();
        damage.damageList(p,(ArrayList<Entity>) location.getNearbyEntities(1,1,1),dmg);
    }

    public void Visuals(Player p){
        Location location = p.getEyeLocation();
        Vector vector;
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 1.6, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5f));
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.6 * Math.sin(theta), 0.5, 0.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5f));
        }
        vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0, 2, 0), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
        location.getWorld().spawnParticle(Particle.CLOUD,location.clone().add(vector),50,0,0,0,0.3);

    }

    //particle effect
    public void FaJinVisuals(Player p, Location loc){
        loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,0.5f);
        //draw effect
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 12) {
            pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2.3 * Math.sin(theta), 5, 2.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(41, 225, 225 ), 1.2f));

            pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(2 * Math.sin(theta), 5, 2 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            loc.getWorld().spawnParticle(Particle.CLOUD,loc.clone().add(pos),10,0.02,0.02,0.02,0.2);

            pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(3.6 * Math.sin(theta), 9, 3.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(30, 205, 205  ), 1.2f));

            pos = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(3 * Math.sin(theta), 7, 3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            loc.getWorld().spawnParticle(Particle.CLOUD,loc.clone().add(pos),10,0.02,0.02,0.02,0.2);
        }
    }


    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,1);
    }
}


//second ability for the player
class Ability2 {

    int AbilityTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.FaJin.Abilities.Ability2.AbilityTimer");
    public void ability(Player p){
        cooldowns.replace(p.getName(), System.currentTimeMillis() + (0));
        cooldowns4.replace(p.getName(), System.currentTimeMillis() + (0));
        playerdata.get(p.getUniqueId()).setFALL_DAMAGE(false);
        playsound(p);
        p.sendMessage(Component.text(Chat.format("&eYou used &bKinetic Boost&c!")));
        BukkitTask FajinTask = new FaJinAbility2Scheduler(p,ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),AbilityTimer*20);
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME()+"Ability2",FajinTask);
    }

    public void visuals(Player p){
        p.getWorld().spawnParticle(Particle.CLOUD,p.getLocation(),10,0,0,0,1.2);
    }

    public void playsound(Player p){
        p.playSound(p.getLocation(),Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,2f,1f);
    }


}

class Ultimate extends hadences.projectmha.game.quirk.Ultimate implements Listener {

    private boolean inUltimate = false;
    private Player player;
    private int UltTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.FaJin.Abilities.Ultimate.UltTimer");

    public void ability(Player p, Ultimate ultimate){
        player = p;
        inUltimate = true;
        playSound(p);
        p.sendMessage(Component.text(Chat.format("&a+" + getMultiplier(p) + " &bSpeed &eand &cPower &efor &b" + UltTimer + " &eseconds!")));


        //Set Damage Values
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE() * getMultiplier(p));
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE() * getMultiplier(p));

        if(UltimateTimer.UltTimer.containsKey(p.getName()))
            UltimateTimer.UltTimer.replace(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );
        else
            UltimateTimer.UltTimer.put(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );

        BukkitTask FajinTask = new FajinScheduler(p,ProjectMHA.getPlugin(ProjectMHA.class),ultimate).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),UltTimer*20);
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME(),FajinTask);


    }

    public double getMultiplier(Player p){
        double multiplier = 1.5;

        multiplier = (multiplier * (1 + getDivisible(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getQUIRK_STORAGE())));

        if(multiplier > 6){
            multiplier = 6;
        }

        multiplier = roundAvoid(multiplier,2);

        if(inUltimate)
            return multiplier;

        return 1;
    }

    public double getDivisible(double num){
        if(num ==0){
            return 0;
        }

        return roundAvoid(num/100,2);
    }

    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void playSound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT,2 ,2);
    }

    @EventHandler
    public void Movement (PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location loc = p.getLocation();

        if(p != player) return;
        try {
            if (!playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME().equalsIgnoreCase("FaJin"))
                return;
            if (inUltimate && player == p) {
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone(), 1, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(41, 225, 225), 1.2f));
            }
        }catch (Exception exception){}
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean isInUltimate() {
        return inUltimate;
    }

    @Override
    public void setInUltimate(boolean inUltimate) {
        this.inUltimate = inUltimate;
    }
}

class RCAbility{
    int KineticCharge = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.FaJin.Abilities.RCAbility.KineticCharge");

    public void ability(Player p){
        Location loc = p.getLocation();
        Location EyeLoc = p.getEyeLocation();
        Vector Direction = EyeLoc.getDirection();

        //Add Kinetic Score
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setQUIRK_STORAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getQUIRK_STORAGE()+KineticCharge);

        //Particle
        loc.getWorld().spawnParticle(Particle.CLOUD,loc,10,0.5,0,0.5,0.2);
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,5,0.2,0.2,0.2,1.2);
        loc.getWorld().spawnParticle(Particle.FLASH,loc,5,0.2,0.2,0.2,1.2);

        //Sound
        loc.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT,2,2);

        //Dash Ability
        p.setVelocity(Direction.multiply(getMultiplier(p)));
        p.setFallDistance(0.0f);

    }

    public double getMultiplier(Player p){
        double multiplier = 1.5;

        multiplier = (multiplier * (1 + getDivisible(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getQUIRK_STORAGE())));

        if(multiplier > 6){
            multiplier = 6;
        }

        multiplier = roundAvoid(multiplier,2);

        if(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getUltimate().isInUltimate())
            return multiplier;

        return 1;
    }

    public double getDivisible(double num){
        if(num ==0){
            return 0;
        }

        return roundAvoid(num/100,2);
    }

    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}

class FajinScheduler extends BukkitRunnable {

    Player p;
    ProjectMHA plugin;
    Object obj;


    public FajinScheduler(Player e, ProjectMHA plugin, Object obj){
        this.p = e;
        this.plugin = plugin;
        this.obj = obj;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setQUIRK_STORAGE(0);
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getUltimate().setInUltimate(false);
        HandlerList.unregisterAll((Listener) obj);

        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_ABILITY1_DAMAGE());
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_ABILITY2_DAMAGE());

        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,2 ,2);
    }
}

class FaJinAbility2Scheduler extends BukkitRunnable{

    Player p;
    ProjectMHA plugin;
    public FaJinAbility2Scheduler(Player e, ProjectMHA plugin){
        this.p = e;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setFALL_DAMAGE(true);
    }
}
