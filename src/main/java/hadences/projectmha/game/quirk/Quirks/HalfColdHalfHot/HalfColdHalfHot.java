package hadences.projectmha.game.quirk.Quirks.HalfColdHalfHot;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.object.MHABlock;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static hadences.projectmha.player.PlayerManager.playerdata;

public class HalfColdHalfHot extends QuirkCastManager {
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

    private String QuirkName = "HalfCold-HalfHot";

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

    public HalfColdHalfHot() {
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
        if(ultimate.ability(p)){
            broadcastUltimate(p);
            return true;
        }
        p.sendMessage(Component.text(Chat.format("&eThere is no &bice &eat your current direction&c!")));
        return false;
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

    public void ability(Player p){
        ice_wall(p);
    }

    public void damage(Player p, Location loc, double dmg){

        int freeze_sec = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Abilities.Ability1.FreezeTimer");

        Damage damage = new Damage();
        damage.damageList(p, (ArrayList<Entity>) loc.getNearbyEntities(0.5,0.5,0.5),dmg);
        damage.stun(p, (ArrayList<Entity>) loc.getNearbyEntities(0.5,0.5,0.5), freeze_sec);

    }

    public void ice_wall(Player p){

        Ice ice = new Ice();

        new BukkitRunnable(){
            Location origin = p.getLocation();
            Location endpoint = p.getLocation().add(p.getLocation().getDirection().normalize());
            Vector direction = endpoint.toVector().subtract(origin.toVector());

            Location start = origin.clone();

            int CustomModelData = 1;
            boolean small = true;

            int i = 0;

            @Override
            public void run() {

                if(i >= 30){
                    this.cancel();
                }

                ice_playsound(start);
                ice_visuals(start,CustomModelData);
                damage(p,start,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE());
                origin = start.clone();
                origin.setY(0);
                endpoint = origin.clone().add(p.getLocation().getDirection().normalize());
                endpoint.setY(0);
                direction = endpoint.toVector().subtract(origin.toVector()).normalize();
                direction.setY(0);
                start = start.add(direction.divide(new Vector(2,2,2)));

                if(i%6 == 0 && i > 0){
                    CustomModelData++;
                }
                if(CustomModelData >= 6)
                    CustomModelData = 6;
                if(CustomModelData >= 3){
                    small = false;
                }

                if(i%2==0)
                    ice.SpawnIce_Wall(start,start.getYaw(), start.getPitch(),CustomModelData,small);



                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

    public void ice_visuals(Location location, double amp){
        amp = amp / 10;
        location.getWorld().spawnParticle(Particle.SNOWFLAKE,location,30,0.2+ amp,0.3+ amp,0.2+ amp,0.1);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.5+ amp, 0+ amp, 0.5+ amp, 0.02, new Particle.DustOptions(Color.fromRGB(184, 253, 255 ), 1.2f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.5+ amp, 0+ amp, 0.5+ amp, 0.02, new Particle.DustOptions(Color.fromRGB(220, 254, 255 ), 1.2f));
    }

    public void ice_playsound(Location loc){
        loc.getWorld().playSound(loc,Sound.BLOCK_GLASS_BREAK,.5F,2);
    }

}

class Ability2{

    public void ability(Player p){
        new BukkitRunnable(){
            int times = 0;
            @Override
            public void run() {
                if(times >= 3)
                    this.cancel();

                playsound(p);

                fireBlast(p);

                times++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,7);
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_BLAZE_SHOOT,1, 1F);

    }

    public void fireBlast(Player p){

        int burn_seconds = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Abilities.Ability2.BurnTimer");
        int flame_range = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Abilities.Ability2.FlameRange");
        RayTrace rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(flame_range, 1);
        Damage damage = new Damage();

        new BukkitRunnable(){
            int i =0;
            @Override
            public void run() {
                if(i >= positions.size()-1)
                    this.cancel();

                Location position = positions.get(i).toLocation(p.getWorld());
                flame_visuals(position);
                damage.damageList(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());
                damage.burn(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),burn_seconds);

                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);

    }

    public void flame_visuals(Location location){
        location.getWorld().spawnParticle(Particle.FLAME, location, 5, 0.02, 0.02, 0.02, 0.05);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 48, 48 ), 1f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 128, 48 ), 1f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(249, 198, 52 ), 1f));
    }
}

class Ultimate {

    public boolean ability(Player p){
        return test_ice(p);
    }

    public boolean test_ice(Player p){
        Location endpoint = RaycastUtils.StartRaycast(p,10,1);
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(1,1,1);

        if(contains_ice(target)){
            start_flame(p, getIceObjects(target));
            return true;
        }
        return false;
    }

    public List<Entity> getIceObjects(List<Entity> target){
        Location location = target.get(0).getLocation();
        for(Entity e: target){
            try {
                if (e.getCustomName().contains("ice")) {
                    location = e.getLocation();
                    break;
                }
            }catch (Exception exception){

            }
        }

        List<Entity> ice_objects = (List<Entity>) location.getNearbyEntities(10,10,10);
        List<Entity> toRemove = new ArrayList<>();
        for(Entity e: ice_objects){
            try {
                if(e.getCustomName().contains("ice")){
                   continue;
                }else{
                    toRemove.add(e);
                }
            }catch (Exception exception){
                toRemove.add(e);
            }
        }
        ice_objects.removeAll(toRemove);
        return ice_objects;

    }

    public void start_flame(Player p, List<Entity> target){
        //FLame visuals
        Location loc = p.getLocation().add(0,1,0);
        //loc.add(loc.getDirection().multiply(1.2));
        Vector pos;

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
        for(int i = 0; i < 1; i++) {
            for (double theta = 0; theta <= Math.PI; theta += Math.PI / 14) {
                pos = new Vector(Math.sin(theta) * 1.5, 0, Math.cos(theta) * 1.5);
                pos = VectorUtils.rotateVector(pos, 270, 0);
                pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
                if(i == 0) {
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(252, 48, 48 ), 0.8f));
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(252, 128, 48 ), 0.8f));
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(249, 198, 52 ), 0.8f));
                    loc.getWorld().spawnParticle(Particle.SMALL_FLAME, loc.clone().add(pos), 5, 0.2, 0, 0.2, 0.1);
                    loc.getWorld().spawnParticle(Particle.FLAME, loc.clone().add(pos), 5, 0.1, 0, 0.1, 0.1);
                }
            }
        }


        new BukkitRunnable(){
            Damage damage = new Damage();
            int i = 0;
            @Override
            public void run() {
                if(i >= target.size()-1){
                    this.cancel();
                }

                spawnBigBang(p,target.get(i).getLocation().clone().add(new Vector(0,0.5,0)));
                target.get(i).getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE,target.get(i).getLocation().clone().add(new Vector(0,0.5,0)),3,1.5,1.5,1.5,0.05);
                damage.damageList(p, (ArrayList<Entity>) target.get(i).getLocation().getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_DAMAGE());
                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,1);
    }


    public void spawnBigBang(Player p,Location loc){
        spawnFirework(p,loc.clone());
        //spawnFirework(p,loc.clone().add(new Vector(0,0.2,0)));
        //spawnFirework(p,loc.clone().add(new Vector(0.2,0,0)));
        //spawnFirework(p,loc.clone().subtract(new Vector(0,0.2,-0.2)));
    }

    public void spawnFirework(Player p, Location loc){
        FireworkEffect effect;
        Firework fw;
        FireworkMeta fm;

        fw = loc.getWorld().spawn(loc.clone(), Firework.class);
        effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.fromRGB(252, 48, 48 )).withColor(Color.fromRGB(252, 128, 48)).withColor(Color.fromRGB(249, 198, 52)).with(FireworkEffect.Type.BURST).build();
        fm = fw.getFireworkMeta();
        fw.setCustomName(p.getName());
        fw.setSilent(true);
        fm.clearEffects();
        fm.addEffect(effect);

        Field f;
        try {
            f = fm.getClass().getDeclaredField("power");
            f.setAccessible(true);
            f.set(fm, -2);
        } catch (Exception exception) {

        }
        fw.setMetadata(playerdata.get(p.getUniqueId()).getTEAM(), new FixedMetadataValue(ProjectMHA.getPlugin(ProjectMHA.class), true));
        fw.setFireworkMeta(fm);
    }

    public boolean contains_ice(List<Entity> target){
        for(Entity e : target){
            try{
                if(e.getCustomName().contains("ice"))
                    return true;
            }catch(Exception exception){
            }
        }
        return false;
    }

    public void playSound(Player p){
    }
}

class RCAbility{

    public void ability(Player p){
        movelogic(p);
    }

    public void movelogic(Player p){
        Ice ice = new Ice();
        int ticks = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Abilities.RCAbility.AbilityTimer");
        new BukkitRunnable(){
            int tick = 0;
            @Override
            public void run() {
                if(p.getLocation().subtract(0,0.2,0).getBlock().isSolid()) {
                    p.setVelocity(new Vector(p.getLocation().getDirection().getX(), 0, p.getLocation().getDirection().getZ()).multiply(0.6));
                    ice_visuals(p);
                    ice_playsound(p);
                    if(tick % 3 == 0)
                    ice.SpawnIce_0(p, p.getLocation().getYaw(), p.getLocation().getPitch());
                }else{

                    flame_playsound(p);
                    flame_flight(p);
                    flame_visuals(p);
                }

                if(tick >= ticks){
                    this.cancel();
                }
                tick++;

            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

    public void flame_flight(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,20,255,false,false));
    }

    public void flame_visuals(Player p){

        Location location = p.getEyeLocation().subtract(0,0.45,0);
        location.getWorld().spawnParticle(Particle.FLAME, location, 5, 0.02, 0.02, 0.02, 0.1);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 48, 48 ), 0.6f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(252, 128, 48 ), 0.6f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(249, 198, 52 ), 0.6f));

    }

    public void flame_playsound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_FIRE_AMBIENT,2F,2);
    }

    public void ice_visuals(Player p){
        Location location = p.getLocation();
        location.getWorld().spawnParticle(Particle.SNOWFLAKE,location,30,0.2,0.3,0.2,0.1);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.5, 0, 0.5, 0.02, new Particle.DustOptions(Color.fromRGB(184, 253, 255 ), 1.2f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.5, 0, 0.5, 0.02, new Particle.DustOptions(Color.fromRGB(220, 254, 255 ), 1.2f));
    }

    public void ice_playsound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.BLOCK_GLASS_BREAK,.5F,2);
    }


}

class Ice{


    public void SpawnIce_0(Player p, float yaw, float pitch){
        int ticks = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Object.Ice_0.Timer");

        ItemStack ICE_0_MODEL = new ItemStack(Material.WHITE_DYE);
        ItemMeta meta = ICE_0_MODEL.getItemMeta();
        meta.setCustomModelData(1);
        ICE_0_MODEL.setItemMeta(meta);

        ArmorStand ice = p.getWorld().spawn(p.getLocation(),ArmorStand.class);

        ice.setCustomNameVisible(false);
        ice.setCustomName("object-ice");
        ice.setBasePlate(false);
        ice.setInvulnerable(false);
        ice.setAI(false);
        ice.setGravity(true);
        ice.setSilent(true);
        ice.setSmall(true);
        ice.setInvisible(true);
        ice.setRotation(yaw,pitch);
        ice.setHelmet(ICE_0_MODEL);

        startTimer(ice,ticks);
    }

    public void SpawnIce_Spike(Location location, float yaw, float pitch){
        int ticks = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Object.Ice_Spike.Timer");

        ItemStack ICE_0_MODEL = new ItemStack(Material.WHITE_DYE);
        ItemMeta meta = ICE_0_MODEL.getItemMeta();
        meta.setCustomModelData(7);
        ICE_0_MODEL.setItemMeta(meta);

        ArmorStand ice = location.getWorld().spawn(location,ArmorStand.class);

        ice.setCustomNameVisible(false);
        ice.setCustomName("object-ice");

        ice.setBasePlate(false);
        ice.setInvulnerable(false);
        ice.setAI(false);
        ice.setGravity(true);
        ice.setSilent(true);
        ice.setSmall(true);
        ice.setInvisible(false);
        ice.setRotation(yaw,pitch);
        ice.setHelmet(ICE_0_MODEL);

        //Spawn Particle
        location.getWorld().spawnParticle(Particle.SNOWFLAKE,location,30,0.2,0.6,0.2,0.1);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.2, 0.6, 0.2, 0.02, new Particle.DustOptions(Color.fromRGB(184, 253, 255 ), 1.2f));
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 10, 0.2, 0.6, 0.2, 0.02, new Particle.DustOptions(Color.fromRGB(220, 254, 255 ), 1.2f));


        startTimer(ice,ticks);
    }


    public void SpawnIce_Wall(Location location, float yaw, float pitch, int CustomModelData, boolean small){
        int ticks = 20 * (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.HalfCold-HalfHot.Object.Ice_Wall.Timer");

        ItemStack ICE_WALL_MODEL = new ItemStack(Material.WHITE_DYE);
        ItemMeta meta = ICE_WALL_MODEL.getItemMeta();
        meta.setCustomModelData(CustomModelData);
        ICE_WALL_MODEL.setItemMeta(meta);

        ArmorStand ice = location.getWorld().spawn(location.clone().add(new Vector(0,1,0)),ArmorStand.class);

        ice.setCustomNameVisible(false);
        ice.setCustomName("object-ice");

        ice.setBasePlate(false);
        ice.setInvulnerable(false);
        ice.setAI(false);
        ice.setGravity(true);
        ice.setSilent(true);
        ice.setSmall(small);
        ice.setInvisible(true);
        ice.setRotation(yaw,pitch);
        ice.setHelmet(ICE_WALL_MODEL);

        startTimer(ice,ticks);

    }

    public void startTimer(ArmorStand armorStand, int ticks){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!armorStand.isDead())
                armorStand.getWorld().playSound(armorStand.getLocation(),Sound.BLOCK_GLASS_BREAK,0.2F,1);
                armorStand.remove();
            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),ticks);
    }



}
