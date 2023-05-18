package hadences.projectmha.game.quirk.Quirks.HellFlame;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.player.PlayerManager.playerdata;
import static hadences.projectmha.utils.Damage.removeDuplicates;

public class HellFlame extends QuirkCastManager {
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

    private String QuirkName = "Hellflame";

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

    public HellFlame() {
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
        return ability2.ability(p);
    }

    public boolean CastUltimate(Player p) {
        broadcastUltimate(p);
        ProjectMHA.getPlugin(ProjectMHA.class).getServer().getPluginManager().registerEvents(ultimate,ProjectMHA.getPlugin(ProjectMHA.class));
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

    private int BurnTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hellflame.Abilities.Ability1.BurnTimer");
    private Location location;
    private Damage damage = new Damage();
    private RayTrace rayTrace;
    private Location initial;
    private Vector vector;

    public void ability(Player p){
        //Raycast the location and deals dmg at the entities.
        location = RaycastUtils.StartRaycast(p,8,0.1);

        //Create a vector going towards the target location and set its velocity
        Vector direction = location.toVector().subtract(p.getLocation().toVector()).normalize();
        initial = p.getLocation().add(0,0.5,0);
        //set Velocity
        p.setVelocity(direction.clone().multiply(1.3));

        //set the fall distance back to 0
        p.setFallDistance(0.0f);

        //sound logic
        playsound(p);
        new BukkitRunnable(){
            @Override
            public void run() {
                //particle visual effect
                visual(p,direction.clone(), location.clone(),initial);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,1);

            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),8);


        //damage logic
        damage.damageList(p, (ArrayList<Entity>) damage.cleanTargetList(p, (List<Entity>) location.getNearbyEntities(1,1,1)),
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE());
        damage.burn(p, (ArrayList<Entity>) damage.cleanTargetList(p, (List<Entity>) location.getNearbyEntities(1,1,1)),
                BurnTimer);
    }

    public void visual(Player p, Vector direciton, Location endpoint, Location initial){
        rayTrace = new RayTrace(initial.toVector(),direciton);
        ArrayList<Vector> positions = rayTrace.traverse(endpoint.distance(initial),0.2);
        for(Vector position : positions){
            location.getWorld().spawnParticle(Particle.FLAME,position.toLocation(p.getWorld()),2, 0.02, 0.02, 0.02, 0.05);
            location.getWorld().spawnParticle(Particle.REDSTONE, position.toLocation(p.getWorld()), 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 48, 48 ), 1f));
            location.getWorld().spawnParticle(Particle.REDSTONE, position.toLocation(p.getWorld()), 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 128, 48 ), 1f));
            location.getWorld().spawnParticle(Particle.REDSTONE, position.toLocation(p.getWorld()), 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(249, 198, 52 ), 1f));

        }
        location = p.getEyeLocation();
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 1.6, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255, 38, 38), 0.5f));
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.6 * Math.sin(theta), 0.5, 0.6 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255, 159, 29), 0.5f));
        }
        vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0, 2, 0), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
        location.getWorld().spawnParticle(Particle.CLOUD,location.clone().add(vector),50,0,0,0,0.3);

    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT,1,1);
    }
}

class Ability2{
    private int BurnTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hellflame.Abilities.Ability2.BurnTimer");

    private Location location;
    private Damage damage = new Damage();

    public boolean ability(Player p){
        if(!p.getLocation().subtract(new Vector(0,0.5,0)).getBlock().isSolid()){p.sendMessage(Component.text(Chat.format("&eYou must be on the &cground&e!"))); return false;}

        location = p.getLocation();
        //indicator on the ground for 10 ticks
        indicator(p);
        //after 10 ticks use ability
        new BukkitRunnable(){
            @Override
            public void run() {
                abilitylogic(p);
            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),5);
        //knocks all players up in the air

        //damage logic
        return true;
    }

    //Shows an indicator
    public void indicator(Player p){
        Location location1 = location.clone();
        Vector vector;
        for(double distance = 0.2; distance <= 6; distance += 0.2) {
            vector = location1.getDirection();
            vector.normalize();
            vector.setY(0);
            vector.divide(new Vector(2,2,2));
            location1.add(vector);
            for (double t = -distance; t <= distance; t += 0.2) {
                vector = new Vector(0, 0, t);
                vector = VectorUtils.rotateVector(vector, location.getYaw(), 0);
                if(t == -distance || (t+0.1) >= distance){
                    location1.getWorld().spawnParticle(Particle.REDSTONE,location1.clone().add(vector), 1, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.5f));
                }
            }
        }
    }

    //Ability logic
    public void abilitylogic(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK,1f,1f);
        ArrayList<Entity> target = new ArrayList<>();
        Vector vector;
        int i = 0;
        for(double distance = 0.2; distance <= 6; distance += 0.2) {
            vector = location.getDirection();
            vector.normalize();
            vector.setY(0);
            vector.divide(new Vector(2,2,2));
            location.add(vector);

            ArrayList<Entity> temp = (ArrayList<Entity>) damage.cleanTargetList(p, (List<Entity>) location.getNearbyEntities(distance,distance,distance));
            target.addAll(temp);
            target = removeDuplicates(target);

            for (double t = -distance; t <= distance; t += 0.2) {
                vector = new Vector(0, 0, t);
                vector = VectorUtils.rotateVector(vector, location.getYaw(), 0);
                location.getWorld().spawnParticle(Particle.FLAME, location.clone().add(vector), 1, 0.02, 0.02, 0.02, 0.02);
                if(i % 4 == 0)
                    location.getWorld().spawnParticle(Particle.LAVA, location.clone().add(vector), 1, 0.001, 0.001, 0.001, 0.02);
                i++;
                if(t == -distance || (t+0.1) >= distance){
                    location.getWorld().spawnParticle(Particle.REDSTONE,location.clone().add(vector), 1, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(254, 148, 65  ), 1.5f));
                }
            }
        }

        for(Entity e : target){
            if(e instanceof LivingEntity){
                e.setVelocity(new Vector(0,1,0).multiply(1.2));
            }
        }

        damage.burn(p,target,BurnTimer);
        damage.damageList(p,target,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());



    }




}

class Ultimate implements Listener {
    private Player player;
    private boolean inUltimate = false;
    private int UltTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hellflame.Abilities.Ultimate.UltTimer");
    private int BurnTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hellflame.Abilities.Ultimate.BurnTimer");
    private int BeamLength = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hellflame.Abilities.Ultimate.BeamLength");
    private RayTrace rayTrace;
    private Damage damage = new Damage();
    @EventHandler
    public void changePosition(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(p != player) return;
        if(e.hasExplicitlyChangedPosition() && inUltimate) e.setCancelled(true);
    }

    public void ability(Player p, Ultimate ultimate){
        player = p;
        //Make the player float if on the air // and unable to change position
        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,UltTimer*20,255));
        //Toggle Ultimate
        inUltimate = true;
        //Show Ult Timer
        if(UltimateTimer.UltTimer.containsKey(p.getName()))
            UltimateTimer.UltTimer.replace(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );
        else
            UltimateTimer.UltTimer.put(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );
        //Ult shoots for a certain amount of timer
        new BukkitRunnable(){
            int tick = 0;
            @Override
            public void run() {
                if(tick >= UltTimer*20){
                    inUltimate = false;
                    HandlerList.unregisterAll(ultimate);
                    this.cancel();
                }
                //Show particle effect every 10 ticks
                if(tick % 10 == 0){
                    flame_visuals(p);
                    beamparticle(p);
                }

                playSound(p);

                damage(p);
                tick++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
        //During the timer every 10 or 5 ticks particle effect comes out

        //Every tick theres a damage/ability logic

    }

    public void damage(Player p){
        rayTrace = new RayTrace(p.getEyeLocation().toVector(),p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(BeamLength,0.5);
        ArrayList<Entity> target = new ArrayList<>();

        for(Vector v : positions){
            ArrayList<Entity> temp = (ArrayList<Entity>) v.toLocation(p.getWorld()).getNearbyEntities(1,1,1);
            temp = (ArrayList<Entity>) damage.cleanTargetList(p,temp);
            target.addAll(temp);
        }

        target = removeDuplicates(target);
        damage.damageList(p,target,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_DAMAGE());
        damage.burn(p,target,BurnTimer);
    }

    public void beamparticle(Player p){
        Location eyeloc = p.getEyeLocation().subtract(0, 0.6, 0);
        float yaw = eyeloc.getYaw();
        float pitch = eyeloc.getPitch() + 90;
        double x = 0;
        double z = 0;
        double t = 0;
        Vector pos;
        for (double y = 0; y < BeamLength; y += 0.1) {
            x = 2 * Math.cos(t);
            z = 2 * Math.sin(t);
            //Circular Particle Effect
            pos = new Vector(x, y, z);
            pos = VectorUtils.rotateVector(pos, yaw, pitch);
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 1, 0, 0, 0, 0.1, new Particle.DustOptions(Color.fromRGB(255, 146, 0 ), 0.8f));
            p.getWorld().spawnParticle(Particle.FLAME, eyeloc.clone().add(pos), 1, 0, 0, 0, 0.02);

            //Main Beam
            pos = new Vector(0, y, 0);
            pos = VectorUtils.rotateVector(pos, yaw, pitch);
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 1, 0.5, 0.5, 0.5, 5, new Particle.DustOptions(Color.fromRGB(255, 96, 0 ), 1.2f));
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 1, 0.5, 0.5, 0.5, 5, new Particle.DustOptions(Color.fromRGB(255, 174, 25 ), 1.2f));

            t += Math.PI / 45;
        }

    }


    public void flame_visuals(Player p){

        Location location = p.getEyeLocation().subtract(0,0.45,0);
        location.getWorld().spawnParticle(Particle.FLAME, location, 25, 0.1, 0.1, 0.1, 0.1);
    }

    public void playSound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_FIRE_AMBIENT,2f,2f);
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BLAZE_SHOOT,0.5f,2f);

    }
}

class RCAbility{

    public void ability(Player p){
        movelogic(p);
    }

    public void movelogic(Player p){
        int ticks = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hellflame.Abilities.RCAbility.AbilityTimer");
        new BukkitRunnable(){
            int tick = 0;
            @Override
            public void run() {
                    flame_playsound(p);
                    flame_flight(p);
                    flame_visuals(p);

                if(tick >= ticks){
                    this.cancel();
                }
                tick++;

            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

    public void flame_flight(Player p){
        Location Eyeloc = p.getEyeLocation();
        Vector direction = Eyeloc.getDirection();
        p.setVelocity(direction.normalize().multiply(0.8));

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20,255,false,false));
    }

    public void flame_visuals(Player p){

        Location location = p.getEyeLocation().subtract(0,0.45,0);
        location.getWorld().spawnParticle(Particle.FLAME, location, 15, 0.02, 0.02, 0.02, 0.1);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 48, 48 ), 0.6f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 128, 48 ), 0.6f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(249, 198, 52 ), 0.6f));

    }

    public void flame_playsound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_FIRE_AMBIENT,2F,2);
    }

}
