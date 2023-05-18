package hadences.projectmha;

import hadences.projectmha.nms.entityglow.GlowManager;
import hadences.projectmha.utils.RaycastUtils;
import hadences.projectmha.utils.VectorUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.nms.entityglow.GlowManager.glowManagerHashMap;

public class debug implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        if(args.length == 1 && args[0].equalsIgnoreCase("glow")){
            List<Player> viewers = new ArrayList<>();
            viewers.add((Player) sender);
            GlowManager glowManager = new GlowManager((Player) sender,viewers,10,"glow");
            glowManager.update();
            sender.sendMessage("glowing");
        }
        else if(args.length == 1 && args[0].equalsIgnoreCase("stop")){
            List<Player> viewers = new ArrayList<>();
            viewers.add((Player) sender);
            glowManagerHashMap.get(((Player) sender).getUniqueId()).removeViewers(viewers);
            glowManagerHashMap.get(((Player) sender).getUniqueId()).update();
            sender.sendMessage("stop");
        }else if(args.length == 1 && args[0].equalsIgnoreCase("a")){
            particle((Player) sender);
        }



        return false;
    }

    public void particle(Player p){
        Location eyeloc = p.getEyeLocation().subtract(0, 0.6, 0);
        float yaw = eyeloc.getYaw();
        float pitch = eyeloc.getPitch() + 90;
        double x = 0;
        double z = 0;
        double t = 0;
        Vector pos;
        for (double y = 0; y < 12; y += 0.02) {
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
}
