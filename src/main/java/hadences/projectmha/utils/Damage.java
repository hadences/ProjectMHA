package hadences.projectmha.utils;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

import static hadences.projectmha.player.PlayerManager.FixQuirkSchedulers;
import static hadences.projectmha.player.PlayerManager.playerdata;

public class Damage {
    ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public void damageList(Player p, ArrayList<Entity> entities, double hearts){
        entities = (ArrayList<Entity>) cleanTargetList(p,entities);
        for(Entity e : entities){
            if(e instanceof Player){
                ((LivingEntity) e).damage(hearts,p);

            }else{
                try{
                    ((LivingEntity) e).damage(hearts,p);
                }catch (Exception exception){
                }
            }
        }
    }

    public void disarm(Player p, Entity e, int seconds){

            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ITEM_BREAK,1.0f,1.0f);

            if(e instanceof Player){
                ((Player) e).sendTitle(Chat.format("&6[&4!&6] &DISARMED"),Chat.format("&cUnable to use your quirk!"));
                playerdata.get(e.getUniqueId()).setABILITY_USAGE(false);
                BukkitTask Disarm = new Disarm((Player) e,ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),seconds*20);
                FixQuirkSchedulers((Player) e,"CC_DISARM",Disarm);
            }


    }

    public void stun(Player p, Entity e, int seconds, boolean playsound){
            if(playsound)
            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1.0f,2.0f);

            if(e instanceof Player){
                ((Player) e).sendTitle(Chat.format("&6[&4!&6] &eIMMOBILIZED"),Chat.format("&cUnable to move!"));
                playerdata.get(e.getUniqueId()).setCAN_JUMP(false);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,999*20,100));
                BukkitTask Stun = new Stun((Player) e,ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),seconds*20);
                FixQuirkSchedulers((Player) e,"CC_STUN",Stun);

        }else {
                try {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, seconds * 20, 100));
                } catch (Exception exception) {

                }
            }

    }

    public void immobilize(Player p, Entity e, int seconds){
            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1.0f,2.0f);

            if(e instanceof Player){

                ((Player) e).sendTitle(Chat.format("&6[&4!&6] &eIMMOBILIZED"),Chat.format("&cUnable to use abilities and move!"));
                playerdata.get(e.getUniqueId()).setCAN_JUMP(false);
                playerdata.get(p.getUniqueId()).setABILITY_USAGE(false);

                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,999*20,100));
                BukkitTask Immobilize = new Immobilize((Player) e,ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),seconds*20);
                FixQuirkSchedulers((Player) e,"CC_IMMOBILIZE",Immobilize);
        }else {
                try {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, seconds * 20, 100));
                } catch (Exception exception) {

                }
            }

    }

    public void burn(Player p, ArrayList<Entity> entities, int seconds){
        entities = (ArrayList<Entity>) cleanTargetList(p,entities);

        for(Entity e : entities){
            if(e.getCustomName() != null && e.getCustomName().contains("object"))
                continue;
            e.getWorld().playSound(e.getLocation(), Sound.BLOCK_FIRE_AMBIENT,1.0f,1.0f);

            if(e instanceof LivingEntity){
                (e).setFireTicks(seconds*20);
            }
        }
    }

    public void disarm(Player p, ArrayList<Entity> entities, int seconds){
        entities = (ArrayList<Entity>) cleanTargetList(p,entities);

        for(Entity e : entities){
            if(e.getCustomName() != null && e.getCustomName().contains("object"))
                continue;
            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1.0f,2.0f);

            if(e instanceof Player){
                ((Player) e).sendTitle(Chat.format("&6[&4!&6] &DISARMED"),Chat.format("&cUnable to use your quirk!"));
                playerdata.get(e.getUniqueId()).setABILITY_USAGE(false);
                BukkitTask Disarm = new Disarm((Player) e,ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),seconds*20);
                FixQuirkSchedulers((Player) e,"CC_DISARM",Disarm);
            }
        }

    }

    public void stun(Player p, ArrayList<Entity> entities, int seconds){
        entities = (ArrayList<Entity>) cleanTargetList(p,entities);

        for(Entity e : entities){
            if(e.getCustomName() != null && e.getCustomName().contains("object"))
                continue;
            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1.0f,2.0f);

            if(e instanceof Player){
                ((Player) e).sendTitle(Chat.format("&6[&4!&6] &eIMMOBILIZED"),Chat.format("&cUnable to move!"));
                playerdata.get(e.getUniqueId()).setCAN_JUMP(false);
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999 * 20, 100));

                BukkitTask Stun = new Stun((Player) e,ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),seconds*20);
                FixQuirkSchedulers((Player) e,"CC_STUN",Stun);
            }else {
                try {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, seconds * 20, 100));
                } catch (Exception exception) {

                }
            }
        }

    }

    public void immobilize(Player p, ArrayList<Entity> entities, int seconds){
        entities = (ArrayList<Entity>) cleanTargetList(p,entities);

        for(Entity e : entities) {
            if(e.getCustomName() != null && e.getCustomName().contains("object"))
                continue;
            e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 2.0f);

            if (e instanceof Player) {
                ((Player) e).sendTitle(Chat.format("&6[&4!&6] &eIMMOBILIZED"), Chat.format("&cUnable to use abilities and move!"));
                playerdata.get(e.getUniqueId()).setCAN_JUMP(false);
                playerdata.get(p.getUniqueId()).setABILITY_USAGE(false);
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999 * 20, 100));

                BukkitTask Immobilize = new Immobilize((Player) e, ProjectMHA.getPlugin(ProjectMHA.class)).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class), seconds * 20);
                FixQuirkSchedulers((Player) e, "CC_IMMOBILIZE", Immobilize);
            } else {
                try {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, seconds * 20, 100));
                } catch (Exception exception) {

                }
        }
        }

    }


    public List<Entity> cleanTargetList(Player p, List<Entity> target){
        List<Entity> toRemove = new ArrayList<>();
        if(target.contains(p)) toRemove.add(p);
        Team team = mha.board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM());
        //if friendly fire is off then remove teammates from the list

            if(!target.isEmpty())
                for(Entity e : target){

                    if(e instanceof Player){
                        if(((Player) e).getGameMode() == GameMode.SPECTATOR || ((Player) e).getGameMode() == GameMode.CREATIVE){
                            toRemove.add(e);
                        }else if(!team.allowFriendlyFire() && playerdata.get(p.getUniqueId()).getTEAM() == playerdata.get(e.getUniqueId()).getTEAM())
                            toRemove.add(e);
                    }
                }

        target.removeAll(toRemove);
        toRemove.clear();
        target = removeDuplicates(target);
        return target;
    }

    public List<Entity> getTeamPlayers(Player p, List<Entity> target){
        List<Entity> toRemove = new ArrayList<>();
        if(target.contains(p)) toRemove.add(p);
        Team team = mha.board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM());
        //if friendly fire is off then remove teammates from the list

        if(!target.isEmpty())
            for(Entity e : target){
                if(e instanceof Player){
                    if(((Player) e).getGameMode() == GameMode.SPECTATOR || ((Player) e).getGameMode() == GameMode.CREATIVE){
                        toRemove.add(e);
                    }else if(!team.allowFriendlyFire() && playerdata.get(p.getUniqueId()).getTEAM() != playerdata.get(e.getUniqueId()).getTEAM())
                        toRemove.add(e);
                }else{
                    toRemove.add(e);
                }
            }

        target.removeAll(toRemove);
        toRemove.clear();
        target = removeDuplicates(target);
        return target;
    }

    public static <T> ArrayList<T> removeDuplicates(List<T> list)
    {
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

}

class Immobilize extends BukkitRunnable{

    Player p;
    ProjectMHA plugin;
    public Immobilize(Player p, ProjectMHA plugin){
        this.p = p;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setCAN_JUMP(true);
        playerdata.get(p.getUniqueId()).setABILITY_USAGE(true);
        p.removePotionEffect(PotionEffectType.SLOW);
    }
}

class Stun extends BukkitRunnable{

    Player p;
    ProjectMHA plugin;
    public Stun(Player p, ProjectMHA plugin){
        this.p = p;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setCAN_JUMP(true);
        p.removePotionEffect(PotionEffectType.SLOW);
    }
}

class Disarm extends BukkitRunnable{

    Player p;
    ProjectMHA plugin;
    public Disarm(Player p, ProjectMHA plugin){
        this.p = p;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        playerdata.get(p.getUniqueId()).setABILITY_USAGE(true);
    }
}
