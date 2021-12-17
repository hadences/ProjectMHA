package hadences.projectmha.game.quirk.Quirks.Bloodcurdle;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import jdk.vm.ci.code.site.Mark;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static hadences.projectmha.player.PlayerManager.playerdata;
import static hadences.projectmha.utils.Damage.removeDuplicates;

public class Bloodcurdle extends QuirkCastManager {
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

    private String QuirkName = "Bloodcurdle";

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

    private HashMap<UUID,ArrayList<Entity>> MarkList = new HashMap<>();

    public Bloodcurdle() {
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

    public void checkHashMap(Player p){
        if(!MarkList.containsKey(p.getUniqueId())){
            MarkList.put(p.getUniqueId(),new ArrayList<Entity>());
        }
        return;
    }

    public boolean CastAbility1(Player p) {
        checkHashMap(p);
        ability1.ability(p, MarkList);
        return true;
    }

    public boolean CastAbility2(Player p) {
        checkHashMap(p);
        return ability2.ability(p, MarkList);
    }

    public boolean CastUltimate(Player p) {
        checkHashMap(p);
        broadcastUltimate(p);
        ultimate.ability(p, MarkList);
        return true;
    }

    public boolean CastRCAbility(Player p) {
        checkHashMap(p);
        rcAbility.ability(p,MarkList);
        return true;
    }

    public HashMap<UUID, ArrayList<Entity>> getMarkList() {
        return MarkList;
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
    private int MarkPercentage = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Bloodcurdle.Abilities.Ability1.Mark_Chance_Percentage");
    private Location endpoint;
    private Damage damage = new Damage();

    public void ability(Player p, HashMap<UUID,ArrayList<Entity>> MarkList){
        endpoint = RaycastUtils.StartRaycast(p,4,0.5);
        visuals(p);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP,0.5f,1f);
        ArrayList<Entity> target = (ArrayList<Entity>) damage.cleanTargetList(p, (List<Entity>) endpoint.getNearbyEntities(1,1,1));
        damage.damageList(p,target,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE());
        MarkChance(p,target,MarkList);
    }

    public void MarkChance(Player p, ArrayList<Entity> targets, HashMap<UUID,ArrayList<Entity>> MarkList){
        for(Entity e: targets){
            if(MarkList.get(p.getUniqueId()).contains(e)) continue;
            int random = (int) (Math.random()*100);
            if(random < MarkPercentage){
                MarkList.get(p.getUniqueId()).add(e);
                p.sendMessage(Component.text(Chat.format("&eYou &cmarked &a" + e.getName() + "&c!")));
                p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,2);
            }
        }

        MarkList.replace(p.getUniqueId(),removeDuplicates(MarkList.get(p.getUniqueId())));
    }

    public void visuals(Player p){
        Vector pos;
        Location loc = p.getEyeLocation();
        loc.subtract(new Vector(0,0.4,0));
        for(double theta =0; theta <= Math.PI; theta += Math.PI/12) {
            pos = new Vector(0, Math.sin(theta) * 1.8, Math.cos(theta) * 1.8);
            pos = VectorUtils.rotateVector(pos, 270, 90);
            pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 35, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(192, 192, 192), 0.7f));
            loc.getWorld().spawnParticle(Particle.SWEEP_ATTACK, loc.clone().add(pos), 1, 0.12, 0.12, 0.12, 0.1);
        }
    }

}

class Ability2{
    Damage damage = new Damage();
    private int StunChancePossibilities = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Bloodcurdle.Abilities.Bloodcurdle.StunTimerPossibility");
    public boolean ability(Player p, HashMap<UUID,ArrayList<Entity>> MarkList){
        if(MarkList.get(p.getUniqueId()).isEmpty()){
            p.sendMessage(Chat.format("&eNo &cMarked &ePlayers&c!"));
            return false;
        }
        new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                if( i > MarkList.get(p.getUniqueId()).size()-1) {
                    MarkList.get(p.getUniqueId()).clear();
                    this.cancel();
                }
                try {
                    Entity e = MarkList.get(p.getUniqueId()).get(i);
                    int stunTimer = (int) (Math.random() * StunChancePossibilities + 1);
                    p.sendMessage(Chat.format("&eParalyzed &c" + e.getName() + " &efor &a" + stunTimer + " &eseconds&c!"));
                    damage.stun(p, e, stunTimer, false);
                    e.sendMessage(Chat.format("&eParalyzed for &a" + stunTimer + " &eseconds&c!"));
                    p.playSound(e.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 2f, 2f);
                }catch (Exception exception){}
                i++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,5);
        return true;
    }

}

class Ultimate {

    public void ability(Player p, HashMap<UUID,ArrayList<Entity>> MarkList){
        
    }

    public void playSound(Player p){
    }
}

class RCAbility{
    private ArrayList<Entity> targets = new ArrayList<>();
    private Damage damage = new Damage();
    private Location teleport;
    private int BlockRange = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Bloodcurdle.Abilities.RCAbility.BlockRange");
    private int MarkPercentage = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Bloodcurdle.Abilities.RCAbility.Mark_Chance_Percentage");

    public void ability(Player p, HashMap<UUID,ArrayList<Entity>> MarkList) {
        Location peyeloc = p.getEyeLocation();
        teleport = RayTrace(p, peyeloc);
        playsound(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(teleport);
                //p.setVelocity(new Vector(0,0,0));
                p.setFallDistance(0.0f);
            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), 1);
        particleEffect(p);
        targets = removeDuplicates(targets);
        damage.damageList(p,targets,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_DAMAGE());
        MarkChance(p,targets,MarkList);
        targets.clear();
    }

    public void MarkChance(Player p, ArrayList<Entity> targets, HashMap<UUID,ArrayList<Entity>> MarkList){
        for(Entity e: targets){
            if(MarkList.get(p.getUniqueId()).contains(e)) continue;
            int random = (int) (Math.random()*100);
            if(random < MarkPercentage){
                MarkList.get(p.getUniqueId()).add(e);
                p.sendMessage(Component.text(Chat.format("&eYou &cmarked &a" + e.getName() + "&c!")));
                p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,2);
            }
        }

        MarkList.replace(p.getUniqueId(),removeDuplicates(MarkList.get(p.getUniqueId())));
    }

    public Location RayTrace(Player p, Location loc) {
        RayTrace rayTrace = new RayTrace(loc.toVector(), loc.getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(BlockRange, 1);

        for (int i = 0; i < positions.size(); i++) {
            Location position = positions.get(i).toLocation(p.getWorld());
            Block block = p.getWorld().getBlockAt(position);

            List<Entity> target = (List<Entity>) position.getNearbyEntities(1, 1, 1);
            target = damage.cleanTargetList(p, target);
            if (target.size() >= 1) {
                for (Entity e : target) {
                    if (!targets.contains(e))
                        targets.add(e);
                }
            }

            if (block != null && block.getType() != Material.AIR) {
                position.setYaw(loc.getYaw());
                position.setPitch(loc.getPitch());
                return position;
            }
            if (i == positions.size() - 3) {
                position.setYaw(loc.getYaw());
                position.setPitch(loc.getPitch());
                return position;
            }
        }
        return null;
    }


    public void playsound(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 0.8f);
    }

    public void particleEffect(Player p) {
        Location eyeloc = p.getEyeLocation().subtract(0, 0.4, 0);
        float yaw = eyeloc.getYaw();
        float pitch = eyeloc.getPitch() + 90;
        double x = 0;
        double z = 0;
        double t = 0;
        int u = 0;
        Vector pos;
        for (double y = 0; y < teleport.distance(eyeloc); y += 0.02) {
            x = 0.4 * Math.cos(t);
            z = 0.4 * Math.sin(t);

            //white particles
            pos = new Vector(x, y, z);
            pos = VectorUtils.rotateVector(pos, yaw, pitch);
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 1, 0, 0, 0, 0.1, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 0.8f));

            pos = new Vector(0, 0.4 * Math.cos(t) + y, 0.2 * Math.cos(t) + 0.1);
            pos = VectorUtils.rotateVector(pos, yaw, pitch);
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 5, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(208, 46, 46), 0.4f));

            pos = new Vector(0, 0.4 * Math.cos(t) + y, 0.2 * Math.cos(t));
            pos = VectorUtils.rotateVector(pos, yaw, pitch);
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 5, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(228, 64, 64), 0.4f));

            pos = new Vector(0, 0.4 * Math.cos(t) + y, 0.2 * Math.cos(t) - 0.1);
            pos = VectorUtils.rotateVector(pos, yaw, pitch);
            p.getWorld().spawnParticle(Particle.REDSTONE, eyeloc.clone().add(pos), 5, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(251, 46, 46), 0.4f));

            u += 1;
            t += Math.PI / 32;
        }
    }

}
