package hadences.projectmha.game.quirk.Quirks.Blackwhip;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.player.PlayerManager.playerdata;


public class Blackwhip extends QuirkCastManager {
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

    private String QuirkName = "Blackwhip";

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

    public Blackwhip() {
        ABILITY1_DISPLAY_NAME = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Name");
        ABILITY1_DESCRIPTION = (String) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Description");
        ABILITY1_DAMAGE = 2 * (double) mha.getConfig().get("Quirks." + QuirkName + ".Abilities.Ability1.Damage");
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
        ultimate.ability(p);
        return true;
    }


    public boolean CastRCAbility(Player p) {
        return rcAbility.ability(p);
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

class Ability1 {

    int length = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ability1.Blackwhip_Length");
    double hitbox = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ability1.Blackwhip_Hitbox");

    private Location loc;
    private Location endpoint;

    public void ability(Player p) {
        loc = p.getLocation();
        endpoint = RaycastUtils.StartRaycast(p,length,hitbox);
        playsound(p);
        double length_from_origin = RaycastUtils.calculateDistance(loc,endpoint);
        visuals(p,length_from_origin,0,0);
        pulllogic(p,endpoint);
    }

    public void pulllogic(Player p, Location endpoint) {
        Damage damage = new Damage();
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox);
        target = damage.cleanTargetList(p,target);
        for(Entity e : target){
            if(e.getUniqueId() != p.getUniqueId()){
                BlackwhipAbilityEntity(e,p);
            }
        }
    }

    public void visuals(Player p, double length, float yaw, float pitch){

        Vector pos = new Vector(0,0,0);
        Vector pos_left = new Vector(0,0,0);
        Vector pos_right = new Vector(0,0,0);
        loc = p.getEyeLocation();

        double y = -0.35;
        for(double t = 0; t <= length; t += 0.1){
            if(t >= 0 && t < 2){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.5),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.7),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 2 && t < 3.5){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.4),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 3.5 && t < 6){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>6 && t < 9){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>9){
                pos = VectorUtils.rotateVector(new Vector(t,y,-0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,+0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }
            //Black Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(42, 42, 42), 0.5F));
            //Dark_Aqua Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_left), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_right), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
        }
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_BITE, 1.0f,2.0f);
    }

    public void BlackwhipAbilityEntity(Entity e, Player p){
        new BukkitRunnable(){
            Location entityLoc = e.getLocation();
            Location playerLoc = p.getLocation();
            Vector v;
            int time = 0;
            @Override
            public void run() {
                if(playerLoc.distance(entityLoc) < 7 || time > 80){
                    this.cancel();
                }
                entityLoc = e.getLocation();
                playerLoc = p.getLocation();
                v = playerLoc.toVector().subtract(entityLoc.toVector());
                e.setVelocity(v.normalize().multiply(1.5));
                time++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

}

class Ability2 {
    int length = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ability2.Blackwhip_Length");
    double hitbox = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ability2.Blackwhip_Hitbox");


    private Location loc;
    private Location endpoint;

    public void ability(Player p) {
        loc = p.getLocation();
        endpoint = RaycastUtils.StartRaycast(p,length,hitbox);
        playsound(p);
        double length_from_origin = RaycastUtils.calculateDistance(loc,endpoint);
        visuals(p,length_from_origin,0,0);
        visuals(p,length_from_origin,30,0);
        visuals(p,length_from_origin,-30,0);
        damage(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());

    }

    public void damage(Player p, double dmg) {
        Location endpoint1 = RaycastUtils.StartRaycast(p,length,hitbox);
        Location endpoint2 = RaycastUtils.StartRaycast(p,length,hitbox,-120,0);
        Location endpoint3 = RaycastUtils.StartRaycast(p,length,hitbox,-60,0);

        Damage damage = new Damage();
        damage.damageList(p, (ArrayList<Entity>) endpoint1.getNearbyEntities(hitbox,hitbox,hitbox),dmg);
        damage.damageList(p, (ArrayList<Entity>) endpoint2.getNearbyEntities(hitbox,hitbox,hitbox),dmg);
        damage.damageList(p, (ArrayList<Entity>) endpoint3.getNearbyEntities(hitbox,hitbox,hitbox),dmg);

    }

    public void visuals(Player p, double length, float yaw, float pitch){

        Vector pos = new Vector(0,0,0);
        Vector pos_left = new Vector(0,0,0);
        Vector pos_right = new Vector(0,0,0);
        loc = p.getEyeLocation();

        double y = -0.35;
        for(double t = 0; t <= length; t += 0.1){
            if(t >= 0 && t < 2){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.5),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.7),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 2 && t < 3.5){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.4),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 3.5 && t < 6){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>6 && t < 9){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>9){
                pos = VectorUtils.rotateVector(new Vector(t,y,-0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,+0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }
            //Black Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(42, 42, 42), 0.5F));
            //Dark_Aqua Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_left), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_right), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
        }
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_BITE, 1.0f,2.0f);
    }

}

class Ultimate {

    double ult_radius = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ultimate.UltRadius");
    int ult_timer = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ultimate.SlowTimer");
    int ult_slow = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ultimate.UltSlow");
    private List<Entity> target;
    Location location;
    Vector pos;

    public void ability(Player p) {
        //for one second show indicator
        indicator(p);
        //ability logic
        new BukkitRunnable(){
            @Override
            public void run() {
                visuals(p);
                ultimate(p);
            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),20);
    }

    private void indicator(Player p){
        location = p.getLocation();
        for(double t = 0; t < 2*Math.PI; t += Math.PI/32){
            pos = new Vector(ult_radius*Math.sin(t),0.5,ult_radius*Math.cos(t));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(255, 0, 0 ), 1.2F));
        }
    }

    private void ultimate(Player p){
        target = (List<Entity>) location.getNearbyEntities(ult_radius,5,ult_radius);
        Damage damage = new Damage();
        target = damage.cleanTargetList(p,target);
        for (Entity e : target){
            if(e instanceof LivingEntity) {
                e.setVelocity(location.toVector().subtract(e.getLocation().toVector()).multiply(0.4));
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,ult_timer,ult_slow));
            }
        }
    }

    private void damage(double dmg){
        for (Entity e : target){
            if(e instanceof LivingEntity){
                ((LivingEntity) e).damage(dmg);
            }
        }
    }

    private void visuals(Player p){
        new BukkitRunnable(){
            int i = 0;
            int t = 0;
            double radius = ult_radius;
            @Override
            public void run() {
                if(t == 3) {
                    t = 0;
                    i++;
                    playsound(p);
                    for (double t = 0; t < 2 * Math.PI; t += Math.PI / 32) {
                        pos = new Vector(radius * Math.sin(t), 0.5, radius * Math.cos(t));

                        //Black Particle
                        location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 10, 0.05, 0.05, 0.05, 0, new Particle.DustOptions(Color.fromRGB(42, 42, 42), 1.2F));
                        //Dark Aqua Particle
                        location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos).add(new Vector(0, 0.5, 0)), 10, 0.05, 0.05, 0.05, 0, new Particle.DustOptions(Color.fromRGB(2, 101, 87), 1.2F));
                        location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos).subtract(new Vector(0, 0.5, 0)), 10, 0.05, 0.05, 0.05, 0, new Particle.DustOptions(Color.fromRGB(2, 101, 87), 1.2F));

                    }
                    radius = radius/2;
                }
                if(i > 2){
                    for (Entity e : target){
                        if(e instanceof LivingEntity) {
                            e.teleport(location);
                            damage(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_DAMAGE());
                        }
                    }
                    this.cancel();
                }
            t++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);


    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_BITE, 1.0f,1.0f);
    }

}

class RCAbility {

    int length = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ability1.Blackwhip_Length");
    double hitbox = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Blackwhip.Abilities.Ability1.Blackwhip_Hitbox");

    private Location loc;
    private Location endpoint;

    public boolean ability(Player p) {
        loc = p.getLocation();
        endpoint = RaycastUtils.StartRaycast(p,length,hitbox);
        return pulllogic(p,endpoint);
    }

    public boolean pulllogic(Player p, Location endpoint) {
        double length_from_origin = RaycastUtils.calculateDistance(loc,endpoint);

        if (endpoint.getBlock().isSolid() && endpoint.getBlock().getType() != Material.BARRIER) {
            playsound(p);
            BlackwhipAbilityBlock(endpoint,p);
            visuals(p,length_from_origin,0,0);
        }else{
            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,1,1);
            return false;
        }
        return true;
    }

    public void visuals(Player p, double length, float yaw, float pitch){

        Vector pos = new Vector(0,0,0);
        Vector pos_left = new Vector(0,0,0);
        Vector pos_right = new Vector(0,0,0);
        loc = p.getEyeLocation();

        double y = -0.35;
        for(double t = 0; t <= length; t += 0.1){
            if(t >= 0 && t < 2){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.5),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.7),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.25*t)-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 2 && t < 3.5){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.4),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-.2*t)+0.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t > 3.5 && t < 6){
                pos = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -1.2),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(0.2*t) -0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>6 && t < 9){
                pos = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.8),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,(-0.1*t) + 0.6),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }else if(t>9){
                pos = VectorUtils.rotateVector(new Vector(t,y,-0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_left = VectorUtils.rotateVector(new Vector(t,y,-0.3),loc.getYaw()+yaw,loc.getPitch()+pitch);
                pos_right = VectorUtils.rotateVector(new Vector(t,y,+0.1),loc.getYaw()+yaw,loc.getPitch()+pitch);
            }
            //Black Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(42, 42, 42), 0.5F));
            //Dark_Aqua Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_left), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos_right), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(2, 101, 87 ), 0.5F));
        }
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_BITE, 1.0f,2.0f);
    }

    public void BlackwhipAbilityBlock(Location block, Player p){
        new BukkitRunnable(){
            Location playerLoc = p.getLocation();
            Vector v;
            int time = 0;
            @Override
            public void run() {
                if(block.distance(playerLoc) < 4 || time > 80){
                    p.setFallDistance(0.0f);
                    this.cancel();
                }
                playerLoc = p.getLocation();
                v = block.toVector().subtract(playerLoc.toVector());
                p.setVelocity(v.normalize().multiply(1));
                time++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

}
