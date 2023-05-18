package hadences.projectmha.game.quirk.Quirks.Hardening;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.utils.Damage;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.player.PlayerManager.FixQuirkSchedulers;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class Hardening extends QuirkCastManager {
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

    private String QuirkName = "Hardening";

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

    public Hardening() {
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
        mha.getServer().getPluginManager().registerEvents(ability1,ProjectMHA.getPlugin(ProjectMHA.class));
        ability1.ability(p,ability1);
        return true;
    }

    public boolean CastAbility2(Player p) {
        return ability2.ability(p);
    }

    public boolean CastUltimate(Player p) {
        broadcastUltimate(p);
        mha.getServer().getPluginManager().registerEvents(ultimate,ProjectMHA.getPlugin(ProjectMHA.class));
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

class Ability1 implements Listener {
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    private Player player;
    double Reduction_Percentage = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hardening.Abilities.Ability1.Harden_Damage_Reduction_Percentage");
    int AbilityTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hardening.Abilities.Ability1.AbilityTimer");
    public void ability(Player p, Ability1 ability1){
        player = p;
        setArmor(p);
        playsound(p);
        p.sendMessage(Component.text(Chat.format("&eUser gains &a" + Reduction_Percentage + "% &cdamage &ereduction&c!")));
        BukkitTask HardeningTask = new HardeningScheduler(p,ProjectMHA.getPlugin(ProjectMHA.class),ability1).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),AbilityTimer*20);
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME(),HardeningTask);
    }

    @EventHandler
    public void HardenerDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof LivingEntity)
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p != player) return;
            e.setDamage(e.getDamage() - (e.getDamage() * (Reduction_Percentage/100)));
            p.playSound((p).getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
        }
    }

    public void setArmor(Player p){
        helmet = new ItemStack(Material.LEATHER_HELMET);
        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) helmet.getItemMeta();
        setArmorColor(leatherArmorMeta, helmet, chestplate);

        leatherArmorMeta = (LeatherArmorMeta) leggings.getItemMeta();
        setArmorColor(leatherArmorMeta, leggings, boots);

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

    }

    private void setArmorColor(LeatherArmorMeta leatherArmorMeta, ItemStack helmet, ItemStack chestplate) {
        leatherArmorMeta.setColor(Color.fromBGR(139, 139, 139));
        leatherArmorMeta.displayName(Component.text(Chat.format("&7Harden")));
        helmet.setItemMeta(leatherArmorMeta);

        leatherArmorMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        leatherArmorMeta.setColor(Color.fromBGR(139, 139, 139));
        leatherArmorMeta.displayName(Component.text(Chat.format("&7Harden")));
        chestplate.setItemMeta(leatherArmorMeta);
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE,1,2);
    }
}

class Ability2{
    RayTrace rayTrace;
    int BlocksRadius = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hardening.Abilities.Ability2.BlockRadius");
    int SlowAmplifier = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hardening.Abilities.Ability2.SlowAmplifier");
    Damage damage = new Damage();
    public boolean ability(Player p){
        if(!p.getLocation().subtract(new Vector(0,0.5,0)).getBlock().isSolid()){p.sendMessage(Component.text(Chat.format("&eYou must be on the &cground&e!"))); return false;}

        rayTrace = new RayTrace(p.getLocation().toVector(),new Vector(p.getLocation().getDirection().getX(), 0, p.getLocation().getDirection().getZ()));
        ArrayList<Vector> positions = rayTrace.traverse(BlocksRadius,1);
        Location endpoint = positions.get(positions.size()-1).toLocation(p.getWorld());
        List<Entity> target = (List<Entity>) endpoint.getNearbyEntities(BlocksRadius,1,BlocksRadius);
        target = damage.cleanTargetList(p,target);
        visuals(p,endpoint);
        damage.damageList(p, (ArrayList<Entity>) target,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_DAMAGE());
        for(Entity e : target){
            if(e instanceof LivingEntity){
                e.setVelocity(new Vector(0,1,0).multiply(1.2));
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,15,SlowAmplifier-1));
            }
        }
        return true;
    }

    public void visuals(Player p,Location location){
        Vector pos;
        for(double theta = 0; theta < Math.PI*2; theta += Math.PI/16){
            pos = new Vector(2.5*Math.sin(theta),0, 2.5*Math.cos(theta));
            p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location.clone().add(pos),2,0,0,0,0.4);
        }
        p.getWorld().spawnParticle(Particle.FLASH, location.clone(),2,0.2,0.2,0.2,1.2);

        p.getWorld().playSound(location,Sound.ENTITY_GENERIC_EXPLODE,2,1);
    }
}

class Ultimate implements Listener{
    private Damage damage = new Damage();
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    private Player player;
    int UltTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Hardening.Abilities.Ultimate.UltTimer");
    public void ability(Player p, Ultimate ultimate){
        player = p;
        setArmor(p);
        playsound(p);
        p.sendMessage(Component.text(Chat.format("&eUser blocks all incoming &cdamage&c!")));
        BukkitTask HardeningTask = new HardeningScheduler(p,ProjectMHA.getPlugin(ProjectMHA.class),ultimate).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),UltTimer*20);
        FixQuirkSchedulers(p,playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME()+"Ultimate",HardeningTask);
    }

    public void setArmor(Player p){
        helmet = new ItemStack(Material.IRON_HELMET);
        chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        leggings = new ItemStack(Material.IRON_LEGGINGS);
        boots = new ItemStack(Material.IRON_BOOTS);

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE,1,1);
    }


    @EventHandler
    public void HardenerDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            //if(p != player) return;

            if(p == player){
                e.setCancelled(true);
                p.playSound((p).getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 2);
            }
            ArrayList<Entity> target = (ArrayList<Entity>) player.getNearbyEntities(2,2,2);
            target = (ArrayList<Entity>) damage.getTeamPlayers(p,target);

            if(target.contains(e.getEntity())) {
                if(e.getEntity() != player)
                p.sendMessage(Chat.format("&ePlayer " + ProjectMHA.getPlugin(ProjectMHA.class).board.getScoreboard().getTeam(playerdata.get(player.getUniqueId()).getTEAM()).getColor() + player.getName() + " &eblocked " + e.getDamage() + "&c!"));

                e.setCancelled(true);
                p.playSound((p).getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 2);
            }
        }
    }
}

class RCAbility{

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

class HardeningScheduler extends BukkitRunnable {

    Player p;
    ProjectMHA plugin;
    Object obj;

    public HardeningScheduler(Player e, ProjectMHA plugin, Object obj){
        this.p = e;
        this.plugin = plugin;
        this.obj = obj;
    }

    @Override
    public void run() {
        HandlerList.unregisterAll((Listener) obj);
        try {
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
        }catch (Exception exception){}
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK,1f ,1f);
    }
}
