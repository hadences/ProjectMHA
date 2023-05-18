package hadences.projectmha.game.quirk.Quirks.Cremation;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.object.MHABlock;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static hadences.projectmha.player.PlayerManager.playerdata;

public class Cremation extends QuirkCastManager {
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

    private String QuirkName = "Cremation";

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

    public Cremation() {
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

    @Override
    public Ultimate getUltimate() {
        return ultimate;
    }

    public void setUltimate(Ultimate ultimate) {
        this.ultimate = ultimate;
    }
}

class Ability1{
    Location loc;
    RayTrace rayTrace;
    Damage damage = new Damage();
    public void ability(Player p){
        Location location = p.getLocation().add(0,0.5,0);
        location.add(location.getDirection().multiply(2));

        FireworkEffect effect;
        Firework fw;
        FireworkMeta fm;
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);

        loc = location.clone();
        for(int i = 0; i < 14; i++) {
            if (14 % 2 == 0){
                loc.add(loc.getDirection());
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc.clone(), 15, 0.5, 0.5, 0.5, 0.5);

                damage.damageList(p, (ArrayList<Entity>) loc.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE());

                fw = p.getWorld().spawn(loc.clone(), Firework.class);
                effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.fromRGB(0, 197, 181 )).withColor(Color.fromRGB(28, 214, 223)).withColor(Color.fromRGB(76, 76, 76)).with(FireworkEffect.Type.BURST).build();
                fm = fw.getFireworkMeta();
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

        }

        if(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getUltimate().isInUltimate())
            ultimateAbility(p);
    }

    public void ultimateAbility(Player p) {

        FireworkEffect effect;
        Firework fw;
        FireworkMeta fm;

        for (int t = 0; t < 2; t++) {
            Location location = p.getLocation().add(0, 0.5, 0);
            if(t ==0)
                location.setDirection(VectorUtils.rotateVector(location.getDirection(),  -30, location.getPitch()));
            else
                location.setDirection(VectorUtils.rotateVector(location.getDirection(), -150, location.getPitch()));

            location.add(location.getDirection().multiply(2));

            loc = location.clone();

            for (int i = 0; i < 14; i++) {
                if (14 % 2 == 0) {
                    loc.add(loc.getDirection());
                    loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc.clone(), 15, 0.5, 0.5, 0.5, 0.5);

                    damage.damageList(p, (ArrayList<Entity>) loc.getNearbyEntities(1.5, 1.5, 1.5), playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE());

                    fw = p.getWorld().spawn(loc.clone(), Firework.class);
                    effect = FireworkEffect.builder().trail(false).flicker(false).withColor(Color.fromRGB(0, 197, 181)).withColor(Color.fromRGB(28, 214, 223)).withColor(Color.fromRGB(76, 76, 76)).with(FireworkEffect.Type.BURST).build();
                    fm = fw.getFireworkMeta();
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

            }
        }
    }
}

class Ability2{
    Location loc;
    Damage damage = new Damage();

    int BurnTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Cremation.Abilities.Ability2.BurnTimer");

    public void ability(Player p){
        loc = p.getLocation().add(0,1,0);
        Vector pos;

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 2, 1);
        for(int i = 0; i < 6; i++) {
            for (double theta = 0; theta <= Math.PI; theta += Math.PI / 14) {
                pos = new Vector(Math.sin(theta) * 3, 0, Math.cos(theta) * 3);
                pos = VectorUtils.rotateVector(pos, 270, 0);
                pos = VectorUtils.rotateVector(pos, loc.getYaw(), loc.getPitch());
                if(i == 0) {loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(12, 189, 255 ), 0.8f));
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 15, 0.1, 0.1, 0.1, 0.2, new Particle.DustOptions(Color.fromRGB(14, 142, 243 ), 0.8f));
                }
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc.clone().add(pos), 5, 0.2, 0, 0.2, 0.1);
                loc.getWorld().spawnParticle(Particle.SOUL, loc.clone().add(pos), 5, 0.1, 0, 0.1, 0.1);

                damage.burn(p, (ArrayList<Entity>) loc.clone().add(pos).getNearbyEntities(1,1,1), BurnTimer);
                damage.damageList(p, (ArrayList<Entity>) loc.clone().add(pos).getNearbyEntities(1,1,1), playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());
                if(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getUltimate().isInUltimate()){
                    new MHABlock(p,loc.clone().add(pos).getBlock(),Material.FIRE,BurnTimer);
                }




            }
            loc.add(loc.getDirection());
        }
    }
}

class Ultimate extends hadences.projectmha.game.quirk.Ultimate {
    private boolean inUltimate = false;
    int UltTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Cremation.Abilities.Ultimate.UltTimer");
    double BuffDamage = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Cremation.Abilities.Ultimate.BuffDamage");
    public void ability(Player p){
        inUltimate = true;

        if(UltimateTimer.UltTimer.containsKey(p.getName()))
            UltimateTimer.UltTimer.replace(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );
        else
            UltimateTimer.UltTimer.put(p.getName(),System.currentTimeMillis() + ((UltTimer) * 1000) );

        //Original dmg
        Double Ability1Dmg = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_DAMAGE();
        Double Ability2Dmg = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE();
        Double RCAbilityDmg = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_DAMAGE();

        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_DAMAGE(Ability1Dmg + (2*BuffDamage));
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_DAMAGE(Ability2Dmg + (2*BuffDamage));
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setRCABILITY_DAMAGE(RCAbilityDmg + (2*BuffDamage));

        p.getWorld().playSound(p.getLocation(),Sound.ENTITY_WITHER_AMBIENT,2f,1f);
        visuals(p);

        p.sendMessage(Component.text(Chat.format("&e+ &b&lDevil's &8&lDance &eamplifies all &askills &eand increases &cpower &eby &c"+ BuffDamage + "!")));

        new BukkitRunnable(){
            @Override
            public void run() {
                inUltimate = false;
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_ABILITY1_DAMAGE());
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_ABILITY2_DAMAGE());
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setRCABILITY_DAMAGE(playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getOG_RCABILITY_DAMAGE());
            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),UltTimer*20);
    }

    public void visuals(Player p){
        Vector pos;
        Location location = p.getLocation();

        for(double t = 0; t < 2*Math.PI; t += Math.PI/64){
            pos = new Vector(2*Math.sin(t),0,2*Math.cos(t));
            location.getWorld().spawnParticle(Particle.SOUL,location.clone().add(pos),0,0,1,0,0.1);
            location.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,location.clone().add(pos),0,0,1,0,0.1);

        }

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
    RayTrace rayTrace;
    Damage damage = new Damage();
    public void ability(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_FIRE_AMBIENT,2,2);
        rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(6, 1);
        for(int i = 0; i < positions.size(); i++){
            Location position = positions.get(i).toLocation(p.getWorld());
            //position.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, position, 1, 0, 0, 0, 0.1);
            position.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, position, 2, 0.1, 0.1, 0.1, 0.05);
            position.getWorld().spawnParticle(Particle.SOUL, position, 1, 0.05, 0.05, 0.05, 0.1);
            damage.damageList(p, (ArrayList<Entity>) position.getNearbyEntities(1,1,1),playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_DAMAGE());
        }

    }

}
