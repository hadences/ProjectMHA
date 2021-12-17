package hadences.projectmha.game.quirk.Quirks.Tape;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.game.quirk.UltimateTimer;
import hadences.projectmha.object.MHABlock;
import hadences.projectmha.utils.RayTrace;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static hadences.projectmha.player.PlayerManager.playerdata;

public class Tape extends QuirkCastManager {
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

    private String QuirkName = "Tape";

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

    public Tape() {
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
        mha.getServer().getPluginManager().registerEvents(ultimate,ProjectMHA.getPlugin(ProjectMHA.class));

        ultimate.ability(p,ultimate);
        return true;
    }

    public boolean CastRCAbility(Player p) {
        return rcAbility.ability(p);
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

    int tape_timer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ability1.TapeTimer");

    public void ability(Player p){

        Location loc = p.getEyeLocation();
        Vector dir = loc.getDirection();
        RayTrace rayTrace = new RayTrace(loc.clone().add(loc.getDirection()).toVector(), new Vector(dir.getX(), dir.getY(), dir.getZ()));
        ArrayList<Vector> positions = rayTrace.traverse(12, 1);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT,2,2);
        for (Vector v : positions) {
            Location position = v.toLocation(p.getWorld());
            Block block = p.getWorld().getBlockAt(position);
            if (block.getType() == Material.AIR) {
                new MHABlock(block,Material.COBWEB,tape_timer);
                position.getWorld().spawnParticle(Particle.WHITE_ASH,position,5,0.5,0.5,0.5,0.1);

            }
        }
    }

}

class Ability2{

    int TapeRange = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ability2.TapeRange");
    int TapeTimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ability2.TapeTimer");
    double Tape_Radius = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ability2.Tape_Radius");
    RayTrace rayTrace;

    public boolean ability(Player p){
        rayTrace = new RayTrace(p.getEyeLocation().toVector(),p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(TapeRange,1);
        try {
            Location endpoint = getEndpoint(p, positions);
            showindicator(endpoint);

            new BukkitRunnable(){
                @Override
                public void run() {
                    createWeb(endpoint);
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
            pos = new Vector(Tape_Radius*Math.sin(t),0.5,Tape_Radius*Math.cos(t));
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

    public void createWeb(Location location){
        location.add(new Vector(0,1,0));
        for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
            double radius = Tape_Radius*Math.sin(i);
            double y = Tape_Radius*Math.cos(i);
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                location.add(x, y, z);
                if(!location.getBlock().isSolid()) {
                    new MHABlock(location.getBlock(), Material.COBWEB, TapeTimer);
                    location.getWorld().spawnParticle(Particle.WHITE_ASH,location,5,0.5,0.5,0.5,0.1);
                }
                location.subtract(x, y, z);
            }
        }

    }


    public void playsound(Player p,Location location){
        p.getWorld().playSound(location, Sound.ENTITY_SPIDER_AMBIENT,2,2);

    }
}

class Ultimate implements Listener{
    private int UltTimer_ticks = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ultimate.UltTimer") * 20;
    private boolean inUltimate = false;
    private Player player;

    private int OG_Ability1CD;
    private int OG_Ability2CD;
    private int OG_RCAbilityCD;

    private int Ability1CD;
    private int Ability2CD;
    private int RCAbilityCD;

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(p != player) return;
        if(!inUltimate) return;
        p.getWorld().spawnParticle(Particle.WHITE_ASH,p.getLocation(),10,0.5,0.5,0.5,0.1);
    }


    public void ability(Player p,Ultimate ultimate){
        player = p;
        inUltimate = true;

        OG_Ability1CD = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_COOLDOWN();
        OG_Ability2CD = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_COOLDOWN();
        OG_RCAbilityCD = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_COOLDOWN();

        Ability1CD = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ultimate.Ability1CD");
        Ability2CD = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ultimate.Ability2CD");
        RCAbilityCD = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.Ultimate.RCAbilityCD");

        //Set player cd lower
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_COOLDOWN(Ability1CD);
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_COOLDOWN(Ability2CD);
        playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setRCABILITY_COOLDOWN(RCAbilityCD);


        p.sendMessage(Component.text(Chat.format("&eYou can now shoot &fTape &afaster&e!")));

        if(UltimateTimer.UltTimer.containsKey(p.getName()))
            UltimateTimer.UltTimer.replace(p.getName(),System.currentTimeMillis() + ((UltTimer_ticks/20) * 1000) );
        else
            UltimateTimer.UltTimer.put(p.getName(),System.currentTimeMillis() + ((UltTimer_ticks/20) * 1000) );

        new BukkitRunnable(){
            @Override
            public void run() {
                HandlerList.unregisterAll((Listener) ultimate);
                inUltimate = false;

                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY1_COOLDOWN(OG_Ability1CD);
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setABILITY2_COOLDOWN(OG_Ability2CD);
                playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().setRCABILITY_COOLDOWN(OG_RCAbilityCD);

            }
        }.runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), UltTimer_ticks);
    }

    public void playSound(Player p){
    }
}

class RCAbility {

    int length = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.RCAbility.Tape_Length");
    double hitbox = (double) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks.Tape.Abilities.RCAbility.Tape_Hitbox");

    private Location loc;
    private Location endpoint;

    public boolean ability(Player p) {
        loc = p.getLocation();
        endpoint = RaycastUtils.StartRaycast(p,length,hitbox);
        return pulllogic(p,endpoint);
    }

    public boolean pulllogic(Player p, Location endpoint) {
        double length_from_origin = RaycastUtils.calculateDistance(loc,endpoint);

        if (endpoint.getBlock().isSolid() && endpoint.getBlock().getType() != Material.BARRIER) {
            playsound(p);
            TapeAbilityBlock(endpoint,p);
            visuals(p,length_from_origin,0,0);
        }else{
            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_LAND,1,1);
            return false;
        }
        return true;
    }

    public void visuals(Player p, double length, float yaw, float pitch){
        Vector pos;
        loc = p.getEyeLocation();
        double y = -0.1;
        for(double t = 0; t <= length; t += 0.1){
            pos = VectorUtils.rotateVector(new Vector(t,y,0),loc.getYaw()+yaw,loc.getPitch()+pitch);
            //White Particle
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(pos), 10, 0.08, 0.08, 0.08, 0,new Particle.DustOptions(Color.fromRGB(255,255,255), 0.5F));
        }
    }

    public void playsound(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0f,2.0f);
    }

    public void TapeAbilityBlock(Location block, Player p){
        new BukkitRunnable(){
            Location playerLoc = p.getLocation();
            Vector v;
            int time = 0;
            @Override
            public void run() {
                if(block.distance(playerLoc) < 4 || time > 80){
                    p.setFallDistance(0.0f);
                    this.cancel();
                }
                playerLoc = p.getLocation();
                v = block.toVector().subtract(playerLoc.toVector());
                p.setVelocity(v.normalize().multiply(1));
                time++;
            }
        }.runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
    }

}