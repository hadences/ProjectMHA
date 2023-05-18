package hadences.projectmha.game.quirk.Quirks.Heal;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.object.MHABlock;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.game.quirk.Cooldown.cooldowns;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class Heal extends QuirkCastManager {
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

    private String QuirkName = "Heal";

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

    public Heal() {
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
        return ability1.ability(p);
    }

    public boolean CastAbility2(Player p) {
        return ability2.ability(p);
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
    private Location location;
    private Vector vector;
    private int RegenerationTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.Ability1.RegenerationTimer");
    private int RegenerationAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.Ability1.RegenerationAmplifier");
    private Damage damage = new Damage();
    public boolean ability(Player p){
        location = RaycastUtils.StartRaycast(p,3.5,1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT,0.5f,1);
        return damage(p);
    }

    public boolean damage(Player p){
        damage = new Damage();

        List<Entity> target = (List<Entity>) location.getNearbyEntities(1,1,1);
        target = damage.getTeamPlayers(p,target);

        if(!target.isEmpty()) {
            for (Entity e : target) {
                if (e instanceof LivingEntity && e instanceof Player) {
                    ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,RegenerationTimer*20,RegenerationAmplifier-1));
                    visuals(p);
                    playsound(p);
                    p.sendMessage(Chat.format("&eYou &ahealed &ean ally&c!"));
                    return true;
                }
            }
        }
        return false;
    }


    public void visuals(Player p){
        location = p.getEyeLocation();
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 0.8, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(3, 219, 1), 0.5f));

        }
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,0.5f,1);
    }
}

class Ability2{

    private int HealRange = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.Ability2.HealRange");
    private double Heal_Radius = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.Ability2.Heal_Radius");
    private RayTrace rayTrace;
    private Vector vector;
    private Damage damage = new Damage();

    public boolean ability(Player p){
        rayTrace = new RayTrace(p.getEyeLocation().toVector(),p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(HealRange,1);
        try {
            Location endpoint = getEndpoint(p, positions);
            showindicator(endpoint);

            new BukkitRunnable(){
                @Override
                public void run() {
                    AbsorbLogic(p,endpoint);
                    playsound(p,endpoint);
                }
            }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),20);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    public void showindicator(Location location){
        Vector pos;
        for(double t = 0; t < 2*Math.PI; t += Math.PI/32){
            pos = new Vector(Heal_Radius*Math.sin(t),0.5,Heal_Radius*Math.cos(t));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 10, 0.05, 0.05, 0.05, 0,new Particle.DustOptions(Color.fromRGB(255, 0, 0 ), 1.2F));
        }
    }

    public Location getEndpoint(Player p, ArrayList<Vector> positions){
        for(Vector v : positions){
            if(v.toLocation(p.getWorld()).getBlock().isSolid())
                return v.toLocation(p.getWorld());
        }
        return null;
    }

    public void AbsorbLogic(Player p,Location location){
        location.add(new Vector(0,0.5,0));
        for(double t = 0; t <= Math.PI*2; t+=Math.PI/50){
            vector = new Vector(Heal_Radius*Math.sin(t), 0, Heal_Radius*Math.cos(t));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(3, 219, 1), 1.5f));
        }

        ArrayList<Entity> target =  (ArrayList<Entity>) damage.cleanTargetList(p, (List<Entity>) location.getNearbyEntities(Heal_Radius,Heal_Radius,Heal_Radius));
        damage.damageList(p,target,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());
        for(Entity e: target){
            if(e instanceof Player){
                healUser(p, playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());
                absorbanimation(p,location);
                break;
            }
        }
    }

    public void absorbanimation(Player p, Location location){
        rayTrace = new RayTrace(location.toVector(),p.getEyeLocation().toVector().subtract(location.toVector()).normalize());
        ArrayList<Vector> positions = rayTrace.traverse(p.getEyeLocation().distance(location), 0.2);
        for(Vector v : positions){
            p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,v.toLocation(p.getWorld()),1,0,0,0,0);
        }
    }

    public void healUser(Player p, double damage){
        if((p.getHealth() + damage) >= p.getMaxHealth())
            p.setHealth(p.getMaxHealth());
        else
            p.setHealth(p.getHealth() + damage);
    }


    public void playsound(Player p,Location location){
        p.getWorld().playSound(location, Sound.BLOCK_SCULK_SENSOR_BREAK,2f,1);
    }
}

class Ultimate {
    private int UltTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.Ultimate.UltTimerBuff");
    private double UltRadius = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.Ultimate.UltRadius");
    private Vector pos;
    private Location location;
    private Damage damage = new Damage();
    public void ability(Player p){
        location = p.getLocation();
        playSound(p);
        new BukkitRunnable(){
            int radius = 0;
            @Override
            public void run() {
                for(double t = 0; t < 2*Math.PI; t += Math.PI/32){
                    pos = new Vector(radius*Math.cos(t),0,radius*Math.sin(t));
                    p.getWorld().spawnParticle(Particle.TOTEM,location.clone().add(pos),1,0,0,0,0);
                }

                if(radius >= UltRadius) this.cancel();

                radius++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);

        ArrayList<Entity> target = (ArrayList<Entity>) location.getNearbyEntities(UltRadius,UltRadius,UltRadius);
        target = (ArrayList<Entity>) damage.getTeamPlayers(p,target);
        for(Entity e: target){
            if(e instanceof Player){
                e.sendMessage(Chat.format("&eYou gained &aRegrowth&c!"));
                ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,UltTimer*20,5-1));
            }
        }
    }

    public void playSound(Player p){
        p.getWorld().playSound(location, Sound.ITEM_TOTEM_USE,1.5f,1);

    }
}

class RCAbility{

    int SpeedAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Heal.Abilities.RCAbility.SpeedAmplifier");
    public void ability(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10,SpeedAmplifier-1,true,true));
    }

}
