package hadences.projectmha.game.quirk.Quirks.Rewind;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.game.quirk.Cooldown.*;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class Rewind extends QuirkCastManager {
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

    private String QuirkName = "Rewind";

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

    public Rewind() {
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

    public void initPassive(Player p) {

    }

    public boolean CastAbility1(Player p) {
        ability1.ability(p,ability1);
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

class Ability1 implements Listener {
    int CastRange = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.Ability1.CastRange");

    private int StaminaLossInterval = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.Ability1.StaminaLossInterval");
    private int StaminaLoss = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.Ability1.StaminLossAmount");

    private boolean toggle = false;
    private Ability1 ability1;
    Damage damage = new Damage();

    private Player player;

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        if(p != player) return;

        toggle = false;
        HandlerList.unregisterAll(ability1);

    }

    public void ability(Player p, Ability1 ability1){
        this.ability1 = ability1;
        player = p;
        p.playSound(p.getLocation(),Sound.ITEM_TRIDENT_RETURN,1f,1f);
        if(toggle == false){
            toggle = true;
            ProjectMHA.getPlugin(ProjectMHA.class).getServer().getPluginManager().registerEvents(this,ProjectMHA.getPlugin(ProjectMHA.class));
            p.sendMessage(Component.text(Chat.format("&eRewind &fAura &e: &e[&aActivated&e]")));
            loop(p);
        }else{
            toggle = false;
            HandlerList.unregisterAll(ability1);
            p.sendMessage(Component.text(Chat.format("&eRewind &fAura &e: &e[&cDeactivated&e]")));
        }
    }

    public void loop(Player p){
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if(playerdata.get(p.getUniqueId()).getSTAMINA() <= 0 || toggle == false) {

                    this.cancel();
                }
                if(i >= StaminaLossInterval){
                    i = 0;
                    playerdata.get(p.getUniqueId()).setSTAMINA(playerdata.get(p.getUniqueId()).getSTAMINA() - StaminaLoss);
                }

                    visuals(p);
                List<Entity> target = (List<Entity>) p.getLocation().getNearbyEntities(CastRange,CastRange,CastRange);
                target = damage.cleanTargetList(p,target);
                for(Entity e: target){
                    if(e instanceof LivingEntity){
                        ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,25,50));
                        ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,25,50));
                        ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,25,50));

                    }
                }


                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

    public void visuals(Player p){
        Vector pos;
        Location location = p.getLocation();
        for(double t = 0; t < Math.PI*2; t += Math.PI/42){
            pos = new Vector(CastRange* Math.sin(t), 0.5, CastRange*Math.cos(t));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 5, 0.1, 0.1, 0.1, 0.5, new Particle.DustOptions(Color.fromRGB(254, 254, 45 ), 0.3f));
        }
    }


}

class Ability2{
    private Location location;
    private Vector pos;
    private int AbilityRadius = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.Ability2.AbilityRadius");
    private Damage damage = new Damage();
    public void ability(Player p){
        location = p.getLocation();
        playsound(p);
        visuals(p);
        ArrayList<Entity> target = (ArrayList<Entity>) location.getNearbyEntities(AbilityRadius,AbilityRadius,AbilityRadius);
        target = (ArrayList<Entity>) damage.cleanTargetList(p,target);

        for(Entity e : target){
            if(e instanceof LivingEntity){
                e.setVelocity(((LivingEntity) e).getEyeLocation().toVector().subtract(location.toVector()).multiply(0.8));
            }
        }
    }

    public void visuals(Player p){
        location = p.getLocation();
        for(double theta = 0; theta < Math.PI*2; theta += Math.PI/64){
            pos = new Vector(Math.sin(theta)*AbilityRadius , 0 , Math.cos(theta)*AbilityRadius);
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 2.2f));
        }
        location.getWorld().spawnParticle(Particle.FLASH,location,35,1,1,1,0.5);

    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_LIGHTNING_BOLT_IMPACT,2, 0.5F);

    }
}

class Ultimate {
    private int UltTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.Ultimate.UltTimer");
    private double UltRadius = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.Ultimate.UltRadius");
    private Location location;
    private Damage damage = new Damage();
    private Vector pos;
    public void ability(Player p){
        location = p.getLocation();

        if(UltimateTimer.UltTimer.containsKey(p.getName()))
            UltimateTimer.UltTimer.replace(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );
        else
            UltimateTimer.UltTimer.put(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );

        p.sendMessage(Component.text(Chat.format("&eAll &aallies &ein your ultimate range will have no &bcooldown&c!")));
        new BukkitRunnable(){
            int tick = 0;
            @Override
            public void run() {
                if( tick >= UltTimer*20) this.cancel();

                ArrayList<Entity> target = (ArrayList<Entity>) location.getNearbyEntities(UltRadius, UltRadius, UltRadius);
                target = (ArrayList<Entity>) damage.getTeamPlayers(p,target);
                for(Entity e: target){
                    if(e instanceof Player){
                        cooldowns.replace(e.getName(),System.currentTimeMillis() + (0*1000));
                        cooldowns2.replace(e.getName(),System.currentTimeMillis() + (0*1000));
                        cooldowns4.replace(e.getName(),System.currentTimeMillis() + (0*1000));
                    }
                }
                if(tick%20 == 0)
                for(double y = 0; y <= 6; y += 1){
                    for(double t = 0; t < Math.PI*2; t += Math.PI/42){
                        pos = new Vector(UltRadius*Math.cos(t),y,UltRadius*Math.sin(t));
                        location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 5, 0.02, 0.02, 0.02, 0.02, new Particle.DustOptions(Color.fromRGB(245, 238, 78), 1.2f));
                    }
                }


                tick++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);


    }

    public void playSound(Player p){
    }
}

class RCAbility{
    int CastRange = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Rewind.Abilities.RCAbility.CastRange");
    Damage damage = new Damage();
    public void ability(Player p){
        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_VEX_AMBIENT,0.5f,1f);
        visuals(p);
        List<Entity> target = (List<Entity>) p.getLocation().getNearbyEntities(CastRange,CastRange,CastRange);
        target = damage.getTeamPlayers(p,target);
        for(Entity e: target){
            if(e instanceof Player){
                cooldowns.replace(e.getName(),System.currentTimeMillis() + ((((cooldowns.get(e.getName()) - System.currentTimeMillis())/1000)-1)*1000) );
                cooldowns2.replace(e.getName(),System.currentTimeMillis() + ((((cooldowns2.get(e.getName()) - System.currentTimeMillis())/1000)-1)*1000) );
                cooldowns4.replace(e.getName(),System.currentTimeMillis() + ((((cooldowns4.get(e.getName()) - System.currentTimeMillis())/1000)-1)*1000) );

                visual_line(p, (Player) e);
            }
        }
    }

    public void visual_line(Player p, Player target){
        RayTrace rayTrace = new RayTrace((p.getLocation().add(new Vector(0,0.5,0))).toVector(),target.getLocation().toVector().subtract(p.getLocation().toVector()));
        ArrayList<Vector> positions = rayTrace.traverse(CastRange/2,0.1);

        for(Vector v : positions){
            if(!damage.getTeamPlayers(p, (List<Entity>) v.toLocation(p.getWorld()).getNearbyEntities(0.1,0.1,0.1)).isEmpty())
                break;
            p.getWorld().spawnParticle(Particle.REDSTONE, v.toLocation(p.getWorld()), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(254, 254, 45 ), 0.8f));
        }


    }

    public void visuals(Player p){
        Vector pos;
        Location location = p.getLocation();
        for(double t = 0; t < Math.PI*2; t += Math.PI/42){
            pos = new Vector(CastRange* Math.sin(t), 0.5, CastRange*Math.cos(t));
            location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(pos), 5, 0.12, 0.12, 0.12, 0.5, new Particle.DustOptions(Color.fromRGB(254, 254, 45 ), 0.8f));
        }
    }


}

