package hadences.projectmha.game.quirk.Quirks.Explosion;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.player.PlayerManager.playerdata;

public class Explosion extends QuirkCastManager {
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

    private String QuirkName = "Explosion";

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

    public Explosion() {
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
    Location loc;
    double hitbox = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Explosion.Abilities.Ability1.Hitbox");
    Damage damage = new Damage();

    public void ability(Player p){
        loc = p.getEyeLocation().add(p.getEyeLocation().getDirection().normalize().multiply(2));

        Location endpoint = RaycastUtils.StartRaycast(p,5,hitbox);
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(hitbox,hitbox,hitbox);
        target = damage.cleanTargetList(p,target);
        damage(p, playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE(), (ArrayList<Entity>) target);
        visuals(p,loc);
        playsound(p);
        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(-0.5));

    }

    public void damage(Player p, double dmg, ArrayList<Entity> target){
        damage.damageList(p, target,dmg);

    }

    public void visuals(Player p,Location loc){
        drawExplosion(loc,0.5);

    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,1);
    }

    public void drawExplosion(Location loc, double offset){
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,3,offset,offset,offset,0.5);
        loc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,loc,25,offset,offset,offset,0.05);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 25, offset+0.7,offset+0.7,offset+0.7, 0, new Particle.DustOptions(Color.fromRGB(255, 136, 49 ), 3f));
        loc.getWorld().spawnParticle(Particle.LAVA,loc,5,offset,offset,offset,0.03);
    }
}

class Ability2{
    Vector pos;
    Location loc;
    Damage damage = new Damage();
    int blind_timer = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Explosion.Abilities.Ability2.BlindTimer");

    public void ability(Player p){
        loc = p.getLocation();
        loc.add(p.getEyeLocation().getDirection().normalize().multiply(3));

        blind(p);
        playsound(p);
        damage(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());

        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(-0.8));

    }

    public void blind(Player p){
        List<Entity> blindtarget = (List<Entity>) loc.getNearbyEntities(2.5,2.5,2.5);
        blindtarget = damage.cleanTargetList(p,blindtarget);
        for(Entity e : blindtarget){
            if(e instanceof LivingEntity){
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,blind_timer,10,true,false));
            }
        }
        blindtarget.clear();
        double radius;
        for(double height = 0; height < 3; height++){
            for(double theta = 0; theta < 2*Math.PI; theta += Math.PI/2){
                if(height == 0 || height ==2){
                    radius = 0.5;
                }else{
                    radius = 1.5;
                }
                pos = new Vector(radius * Math.sin(theta),height, radius * Math.cos(theta));
                drawExplosion(loc.clone().add(pos),0.8);
            }
        }
    }

    public void drawExplosion(Location loc, double offset){
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,3,offset,offset,offset,0.5);
        loc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,loc,25,offset,offset,offset,0.05);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 25, offset+0.7,offset+0.7,offset+0.7, 0, new Particle.DustOptions(Color.fromRGB(255, 136, 49 ), 3f));
        loc.getWorld().spawnParticle(Particle.LAVA,loc,5,offset,offset,offset,0.03);
    }

    public void damage(Player p, double dmg){
        damage.damageList(p, (ArrayList<Entity>) loc.getNearbyEntities(2,2,2), dmg);

    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,2);
    }
}

class Ultimate {

    int blast_length = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Explosion.Abilities.Ultimate.BlastLength");

    RayTrace rayTrace;
    public void ability(Player p){
        rayTrace = new RayTrace(p.getEyeLocation().toVector(),p.getEyeLocation().getDirection());
        Damage damage = new Damage();
        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(-0.8));

        new BukkitRunnable(){

            ArrayList<Vector> vectors = rayTrace.traverse(blast_length,1.5);
            ArrayList<Entity> list = new ArrayList<>();
            int i = 0;
            double r = 0;
            @Override
            public void run() {
                if(i >= vectors.size()-1){
                    this.cancel();
                }

                Location location = vectors.get(i).toLocation(p.getWorld());
                drawExplosion(location,r);
                playSound(location);
                list.addAll(location.getNearbyEntities(r,r,r));
                list = damage.removeDuplicates(list);
                damage.damageList(p,list,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_DAMAGE());

                r+=0.2;
                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);




    }

    public void drawExplosion(Location loc, double offset){
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,3,offset,offset,offset,0.5);
        loc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,loc,25,offset,offset,offset,0.05);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 25, offset+0.7,offset+0.7,offset+0.7, 0, new Particle.DustOptions(Color.fromRGB(255, 136, 49 ), 3f));
        loc.getWorld().spawnParticle(Particle.LAVA,loc,5,offset,offset,offset,0.03);
    }

    public void playSound(Location location){
        location.getWorld().playSound(location,Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,1);
    }
}

class RCAbility{

    public void ability(Player p){
        Location Eyeloc = p.getEyeLocation();
        Vector direction = Eyeloc.getDirection();
        p.setVelocity(direction.normalize().multiply(0.8));
        Location explosionlocation = p.getEyeLocation().subtract(p.getEyeLocation().getDirection().normalize());
        p.playSound(explosionlocation,Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2,1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20,180,false,false));
        drawExplosion(explosionlocation,0.05);
    }

    public void drawExplosion(Location loc,double offset){
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,loc,3,offset,offset,offset,0.5);
        loc.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,loc,25,offset,offset,offset,0.05);
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 25, 1.2,1.2,1.2, 0, new Particle.DustOptions(Color.fromRGB(255, 136, 49 ), 3f));
        loc.getWorld().spawnParticle(Particle.LAVA,loc,5,offset,offset,offset,0.03);
    }

}
