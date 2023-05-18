package hadences.projectmha.game.quirk.Quirks.Permeation;

import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.Quirk;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.ProjectMHA;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static hadences.projectmha.player.PlayerManager.playerdata;

public class Permeation extends QuirkCastManager implements Cloneable {
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

    private String QuirkName = "Permeation";

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

    public Permeation() {
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
        mha.getServer().getPluginManager().registerEvents(ultimate,ProjectMHA.getPlugin(ProjectMHA.class));

        ultimate.ability(p, ultimate);
        return true;
    }

    public boolean CastRCAbility(Player p) {
        return rcAbility.ability(p);
    }

    @Override
    @NotNull
    public Quirk clone() {
        try {
            return (Quirk) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
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
        damage.damageList(p,(ArrayList<Entity>) location.getNearbyEntities(1,1,1),dmg);
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

class Ability2{
    private Location location;
    private Vector vector;
    private RaycastUtils raycastUtils;
    public Damage damage;

    public void ability(Player p){
        location = raycastUtils.StartRaycast(p,3.5,1);
        damage(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());
        visuals(p);
        playsound(p);
    }

    public void damage(Player p, double dmg){
        damage = new Damage();
        damage.damageList(p,(ArrayList<Entity>) location.getNearbyEntities(1,1,1),dmg);
        damage.stun(p,(ArrayList<Entity>) location.getNearbyEntities(1,1,1), ((Integer) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Permeation.Abilities.Ability2.StunTimer")));
    }

    public void visuals(Player p){
        location = p.getEyeLocation();
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 25) {
            vector = VectorUtils.rotateVector(VectorUtils.rotateVector(new Vector(0.3 * Math.sin(theta), 1.6, 0.3 * Math.cos(theta)), -90, 90), p.getLocation().getYaw(), p.getLocation().getPitch());
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(vector), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(255, 228, 35), 0.5f));
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

class Ultimate implements Listener {
    
    private boolean inUltimate = false;
    private Player player;
    
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        ArrayList<EntityDamageEvent.DamageCause> PhaseCauses = new ArrayList<>();
        PhaseCauses.add(EntityDamageEvent.DamageCause.CONTACT);
        PhaseCauses.add(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        PhaseCauses.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
        PhaseCauses.add(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);
        PhaseCauses.add(EntityDamageEvent.DamageCause.PROJECTILE);

        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (player != p) return;
            try {
                if (playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME().equalsIgnoreCase("Permeation") && inUltimate && p == player) {
                    e.setCancelled(true);
                    if (!PhaseCauses.contains(e.getCause())) return;
                    playSound(p);

                    p.setGameMode(GameMode.SPECTATOR);
                    p.setVelocity(new Vector(0, 0.1, 0));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            playSound(p);
                            p.setGameMode(GameMode.ADVENTURE);
                        }
                    }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), 3);
                }
            }catch (Exception exception){}
        }
    }

    public void ability(Player p, Ultimate ultimate){
        this.player = p;

        double damage_ability_one = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE();
        double damage_ability_two = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE();
        int UltTimer_ticks = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Permeation.Abilities.Ultimate.UltTimer") * 20;
        double Ult_Buff = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Permeation.Abilities.Ultimate.UltBuff") * 2;
        //send message to player
        p.sendMessage(Chat.format("&eYou are now immune for &b" + UltTimer_ticks/20 + " &eseconds and your abilities have been buffed! &a+" + Ult_Buff/2));

        if(UltimateTimer.UltTimer.containsKey(p.getName()))
            UltimateTimer.UltTimer.replace(p.getName(),System.currentTimeMillis() + ((UltTimer_ticks/20) * 1000) );
        else
            UltimateTimer.UltTimer.put(p.getName(),System.currentTimeMillis() + ((UltTimer_ticks/20) * 1000) );


        inUltimate = true;
        //change damage values
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_DAMAGE(damage_ability_one + Ult_Buff);
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_DAMAGE(damage_ability_two + Ult_Buff);
        new BukkitRunnable(){
            @Override
            public void run() {
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_ABILITY1_DAMAGE());
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_ABILITY2_DAMAGE());
                HandlerList.unregisterAll((Listener) ultimate);
                inUltimate = false;
            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), UltTimer_ticks);
    }

    public void playSound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 2.0f,2.0f);
    }
}

class RCAbility{

    public boolean ability(Player p){
        return StartPhase(p);
    }

    public boolean StartPhase(Player p){
        //if(!isOnGround(p)) return false;
        if(!isPhasingBlock(p)) {
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f,2.0f);
            p.sendMessage(ChatColor.RED + "You are not facing a block!");
            return false;
        }
        playSound(p);
        p.setGameMode(GameMode.SPECTATOR);
        p.setVelocity(p.getEyeLocation().getDirection().normalize().multiply(0.95));
        new BukkitRunnable(){
            Location phaseLoc;
            int time =0;
            boolean unphase = false;
            @Override
            public void run() {
                if(time == 6){
                    phaseLoc = p.getEyeLocation();
                }
                if(time >= 6 && (int) p.getEyeLocation().getY() != (int) phaseLoc.getY()){
                    unphase = true;
                }
                if ((time >= 5 && !p.getLocation().getBlock().isSolid())) {
                    p.setInvulnerable(true);
                    p.setGameMode(GameMode.ADVENTURE);
                    playSound(p);
                    p.setInvulnerable(false);
                    this.cancel();
                }
                if(time >= 20 && p.getGameMode() == GameMode.ADVENTURE){
                    playSound(p);
                    this.cancel();
                }
                if(time >= 20 || unphase){
                    UnPhase(p);
                }
                time++;
            }

        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
        return true;
    }

    public void playSound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.0f,2.0f);
    }

    public void UnPhase(Player p){
        if (p.getEyeLocation().getBlock().isSolid() || p.getLocation().getBlock().isSolid()) {
            p.setVelocity(new Vector(0, 1, 0).normalize().multiply(0.75));
        }
    }

    public boolean isPhasingBlock(Player p){
        Location endpoint = RaycastUtils.StartRaycast(p,3,0.2);
        if(endpoint.getBlock().isSolid() && endpoint.getBlock().getType() != Material.BARRIER){
            return true;
        }
        return false;
    }
}
