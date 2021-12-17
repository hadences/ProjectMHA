package hadences.projectmha.game.quirk.Quirks.Decay;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.object.MHABlock;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
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

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.game.GameManager.MHABlocks;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class Decay extends QuirkCastManager{
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

    private String QuirkName = "Decay";

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

    public Decay() {
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
        if(ability2.ability(p,ability2)){
            mha.getServer().getPluginManager().registerEvents(ability2,ProjectMHA.getPlugin(ProjectMHA.class));
            return true;
        }
        return false;
    }

    public boolean CastUltimate(Player p) {
        if(ultimate.ability(p,ultimate)) {
            mha.getServer().getPluginManager().registerEvents(ultimate,ProjectMHA.getPlugin(ProjectMHA.class));
            broadcastUltimate(p);
            return true;
        }
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
    Location endpoint;
    Damage damage = new Damage();
    int DecayTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ability1.DecayTimer");
    int DecayAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ability1.DecayAmplifier");


    public void ability(Player p){
        endpoint = RaycastUtils.StartRaycast(p,4,0.5);
        visuals(p);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT,0.5f,0.1f);
        ArrayList<Entity> target = (ArrayList<Entity>) damage.cleanTargetList(p, (List<Entity>) endpoint.getNearbyEntities(1,1,1));
        for(Entity e: target){
            if(e instanceof LivingEntity){
                playSound(e.getLocation());
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER,DecayTimer*20, DecayAmplifier));
                return;
            }
        }
    }

    public void visuals(Player p){
        Vector pos;
        Location loc = p.getEyeLocation();
        loc.subtract(new Vector(0,0.4,0));
        for(double theta =0; theta <= Math.PI; theta += Math.PI/12) {
            pos = new Vector(0, Math.sin(theta) * 1.8, Math.cos(theta) * 1.8);
            pos = VectorUtils.rotateVector(pos, 270, 90);
            pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 35, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(99, 117, 111), 0.7f));
            loc.getWorld().spawnParticle(Particle.ASH, loc.clone().add(pos), 10, 0.12, 0.12, 0.12, 0.01);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 20, 0.12, 0.1, 0.12, 0.2, new Particle.DustOptions(Color.fromRGB(  87, 91, 90), 0.7f));
        }
    }

    public void playSound(Location loc){
        loc.getWorld().playSound(loc, Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1,0.5f);
    }

}

class Ability2 implements Listener {
    private Player player;
    int travel_distance = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ability2.DecayRange");
    int decay_block_seconds = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ability2.DecayBlockTimer");

    int DecayTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ability2.DecayTimer");
    int DecayAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ability2.DecayAmplifier");


    @EventHandler
    public void decayPlayer(EntityMoveEvent e){
        if(e.hasExplicitlyChangedBlock()){
            if(e.getEntity() instanceof LivingEntity){
                if(e.getEntity().getLocation().subtract(new Vector(0,1,0)).getBlock().getType() == Material.DEAD_BRAIN_CORAL_BLOCK && MHABlocks.containsKey(e.getEntity().getLocation().subtract(new Vector(0,1,0)).getBlock().getLocation())) {
                    (e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER,DecayTimer*20, DecayAmplifier));
                }
            }
        }
    }

    @EventHandler
    public void decayPlayer(PlayerMoveEvent e){
        if(e.hasExplicitlyChangedBlock()){
            if(e.getPlayer() != player)
                if (e.getPlayer().getLocation().subtract(new Vector(0, 1, 0)).getBlock().getType() == Material.DEAD_BRAIN_CORAL_BLOCK && MHABlocks.containsKey(e.getPlayer().getLocation().subtract(new Vector(0, 1, 0)).getBlock().getLocation())) {
                    (e.getPlayer()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, DecayTimer * 20, DecayAmplifier));
                }

        }
    }


    public boolean ability(Player p,Ability2 ability2){
        player = p;
        if(!p.getLocation().subtract(new Vector(0,0.5,0)).getBlock().isSolid()){p.sendMessage(Component.text(Chat.format("&eYou must be on the &cground&e!"))); return false;}
        Location loc = p.getLocation();


        Vector dir = loc.getDirection();
        loc.getWorld().playSound(loc.clone().add(new Vector(0, 0.4, 0)), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 0.5f);
        RayTrace rayTrace = new RayTrace(new Vector(loc.getX(), loc.getY() - 1, loc.getZ()), new Vector(dir.getX(), 0, dir.getZ()));
        RayTrace rayTrace1 = new RayTrace(new Vector(loc.getX(), loc.getY() - 1, loc.getZ()), VectorUtils.rotateVector(new Vector(dir.getX(), 0, dir.getZ()), 262, 0));
        RayTrace rayTrace2 = new RayTrace(new Vector(loc.getX(), loc.getY() - 1, loc.getZ()), VectorUtils.rotateVector(new Vector(dir.getX(), 0, dir.getZ()), 278, 0));
        ArrayList<Vector> positions = rayTrace.traverse(travel_distance, 1);
        ArrayList<Vector> positions1 = rayTrace1.traverse(travel_distance-2, 1);
        ArrayList<Vector> positions2 = rayTrace2.traverse(travel_distance-2, 1);

        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(i >= positions.size()-1){
                    this.cancel();
                }

                if(i < positions.size()-1-2){
                    if(positions1.get(i).toLocation(p.getWorld()).getBlock().isSolid())
                        new MHABlock(positions1.get(i).toLocation(p.getWorld()).getBlock(),Material.DEAD_BRAIN_CORAL_BLOCK,decay_block_seconds);
                    if(positions2.get(i).toLocation(p.getWorld()).getBlock().isSolid())
                        new MHABlock(positions2.get(i).toLocation(p.getWorld()).getBlock(),Material.DEAD_BRAIN_CORAL_BLOCK,decay_block_seconds);
                }
                if(positions.get(i).toLocation(p.getWorld()).getBlock().isSolid())
                    new MHABlock(positions.get(i).toLocation(p.getWorld()).getBlock(),Material.DEAD_BRAIN_CORAL_BLOCK,decay_block_seconds);
                positions.get(i).toLocation(p.getWorld()).getWorld().playSound(positions.get(i).toLocation(p.getWorld()).clone().add(new Vector(0, 0.4, 0)), Sound.BLOCK_FIRE_EXTINGUISH, 0.2f, 1f);
                positions.get(i).toLocation(p.getWorld()).getWorld().spawnParticle(Particle.ASH,positions.get(i).toLocation(p.getWorld()), 10, 0.12, 0.12, 0.12, 0.01);

                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);

        new BukkitRunnable(){
            @Override
            public void run() {
                HandlerList.unregisterAll(ability2);

            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),decay_block_seconds*20);


        return true;
    }

}

class Ultimate implements Listener{
    private Player player;
    int DecayTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ultimate.DecayTimer");
    int DecayAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ultimate.DecayAmplifier");
    double DecayRadius = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ultimate.DecayRadius");
    int decay_block_seconds = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.Ultimate.DecayBlockTimer");

    @EventHandler
    public void decayPlayer(EntityMoveEvent e){
        if(e.hasExplicitlyChangedBlock()){
            if(e.getEntity() instanceof LivingEntity) {
                if (e.getEntity().getLocation().subtract(new Vector(0, 1, 0)).getBlock().getType() == Material.DEAD_BRAIN_CORAL_BLOCK && MHABlocks.containsKey(e.getEntity().getLocation().subtract(new Vector(0, 1, 0)).getBlock().getLocation())) {
                    (e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, DecayTimer * 20, DecayAmplifier));
                }
            }
        }
    }

    @EventHandler
    public void decayPlayer(PlayerMoveEvent e){
        if(e.hasExplicitlyChangedBlock()){
            if(e.getPlayer() != player)
                if (e.getPlayer().getLocation().subtract(new Vector(0, 1, 0)).getBlock().getType() == Material.DEAD_BRAIN_CORAL_BLOCK && MHABlocks.containsKey(e.getPlayer().getLocation().subtract(new Vector(0, 1, 0)).getBlock().getLocation())) {
                    (e.getPlayer()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, DecayTimer * 20, DecayAmplifier));
                }

        }
    }

    public boolean ability(Player p, Ultimate ultimate){
        player = p;
        if(!p.getLocation().subtract(new Vector(0,0.5,0)).getBlock().isSolid()){p.sendMessage(Component.text(Chat.format("&eYou must be on the &cground&e!"))); return false;}
        Location location = p.getLocation();
        location.subtract(new Vector(0,1,0));
        location.getWorld().playSound(location.clone().add(new Vector(0, 0.4, 0)), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.4f, 1f);

        new BukkitRunnable(){
            double r = 0;
            Vector pos;
            @Override
            public void run() {
                if(r >= DecayRadius) this.cancel();

                for(double t = 0; t < Math.PI*2; t += Math.PI/82){
                    pos = new Vector(r*Math.sin(t),0,r*Math.cos(t));

                    if(location.clone().add(pos).getBlock().isSolid()) {
                        new MHABlock(DecayCheckTopBlock(location.clone().add(pos)).getBlock(), Material.DEAD_BRAIN_CORAL_BLOCK, decay_block_seconds);
                    }
                }

                r += 0.5;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);


        new BukkitRunnable(){
            @Override
            public void run() {
                HandlerList.unregisterAll(ultimate);

            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),decay_block_seconds*20);


        return true;
    }

    public Location DecayCheckTopBlock(Location location){
        if(location.clone().add(new Vector(0,1,0)).getBlock().isSolid() && location.clone().add(new Vector(0,1,0)).getBlock().isCollidable() && location.clone().add(new Vector(0,1,0)).getBlock().getType() != Material.BARRIER){
            new MHABlock((location).getBlock(), Material.DEAD_BRAIN_CORAL_BLOCK, decay_block_seconds);
            DecayCheckTopBlock(location.add(new Vector(0,1,0)));
        }
        return location;
    }

    public void playSound(Player p){
    }
}

class RCAbility{
    int AbilityTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.RCAbility.AbilityTimer");
    int MobilityAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Decay.Abilities.RCAbility.MobilityAmplifier");

    public void ability(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,AbilityTimer*20, MobilityAmplifier-1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,AbilityTimer*20, MobilityAmplifier-1));

        playsound(p);
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_VEX_CHARGE,1,2);

    }

}
