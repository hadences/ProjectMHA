package hadences.projectmha.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class RaycastUtils {
    static RayTrace rayTrace;
    public static Location StartRaycast(Player player, double length, double hitbox) {
        Damage damage = new Damage();

        rayTrace = new RayTrace(player.getEyeLocation().toVector(), player.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(length, 0.01);

        for (int i = 0; i < positions.size(); i++) {
            Location position = positions.get(i).toLocation(player.getWorld());

            Block block = player.getWorld().getBlockAt(position);

            List<Entity> target = (List<Entity>) position.getNearbyEntities(hitbox, hitbox, hitbox);
            target = damage.cleanTargetList(player,target);
            if (target.size() >= 1) {
                return position;
            }

            if (block != null && block.isSolid() && block.getType() != Material.COBWEB) {
                return position;
            }
         if(i == positions.size()-1){
             return position;
         }
        }
        rayTrace = null;
        return null;
    }

    public static Location StartRaycast(Player player, double length, double hitbox, float yaw, float pitch) {
        Damage damage = new Damage();

        rayTrace = new RayTrace(player.getEyeLocation().toVector(), VectorUtils.rotateVector(player.getEyeLocation().getDirection(),yaw, pitch));
        ArrayList<Vector> positions = rayTrace.traverse(length, 0.01);

        for (int i = 0; i < positions.size(); i++) {
            Location position = positions.get(i).toLocation(player.getWorld());

            Block block = player.getWorld().getBlockAt(position);

            List<Entity> target = (List<Entity>) position.getNearbyEntities(hitbox, hitbox, hitbox);
            target = damage.cleanTargetList(player,target);
            if (target.size() >= 1) {
                return position;
            }

            if (block != null && block.getType() != Material.AIR) {
                return position;
            }
            if(i == positions.size()-1){
                return position;
            }
        }
        rayTrace = null;
        return null;
    }

    public static Location StartRayCastBlock(Player p,double length,double accuracy){
        rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
        ArrayList<Vector> positions = rayTrace.traverse(length, accuracy);
        for (int i = 0; i < positions.size(); i++) {
            Location position = positions.get(i).toLocation(p.getWorld());
            Block block = p.getWorld().getBlockAt(position);
            if (block != null && block.getType() != Material.AIR) {
                return position;
            }
            if(i == positions.size()-1){
                return position;
            }
        }
        return null;
    }



    public static double calculateDistance(Location loc1, Location loc2){

        return loc1.distance(loc2);

    }

}
