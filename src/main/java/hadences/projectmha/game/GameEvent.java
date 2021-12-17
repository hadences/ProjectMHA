package hadences.projectmha.game;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.game.quirk.Cooldown;
import hadences.projectmha.item.GameItems;
import hadences.projectmha.player.Stamina;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

import static hadences.projectmha.game.quirk.Cooldown.*;
import static hadences.projectmha.player.PlayerManager.playerdata;


public class GameEvent implements Listener {

    // UniversalAbilities universalAbilities = new UniversalAbilities();

    //Blood effect on player when they take damage
    //Also cancels fall dmg if the damage is <= 2 hearts
    @EventHandler
    public void takeDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (playerdata.get(p.getUniqueId()).isIN_GAME() == false) {
                e.setCancelled(true);
                return;
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (e.getDamage() <= 4.0f) {
                    e.setCancelled(true);
                    return;
                }
                if(playerdata.get(p.getUniqueId()).isFALL_DAMAGE() == false){
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void RCAbility(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getItem() == null) return;
        if (!playerdata.get(p.getUniqueId()).isABILITY_USAGE()) return;
        if (playerdata.get(p.getUniqueId()).getQUIRK() == null) return;
        if (playerdata.get(p.getUniqueId()).isIN_LOBBY() == true) return;
        if (playerdata.get(p.getUniqueId()).isIN_LOBBY() == false && playerdata.get(p.getUniqueId()).isIN_GAME() == false)
            return;
        if (playerdata.get(p.getUniqueId()).isIN_GAME() == false || playerdata.get(p.getUniqueId()).isABILITY_USAGE() == false) {
            p.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "!" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You cannot use abilities!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
            e.setCancelled(true);
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() ==Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem().getItemMeta().getLocalizedName().equalsIgnoreCase("skill"))
                CastAbility(p, cooldowns4, 3);
            return;
        }
    }

    @EventHandler
    public void CastAbility(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();

        //checks if the player is in game
        if(playerdata.get(p.getUniqueId()).isIN_GAME())
        if(e.getNewSlot() >= 4 && e.getNewSlot() <= 7){
            e.setCancelled(true);
            p.getInventory().setHeldItemSlot(3);
            return;
        }

        if (p.getInventory().getItem(e.getNewSlot()) == null) return;
        if (playerdata.get(p.getUniqueId()).getQUIRK() == null) return;
        if (playerdata.get(p.getUniqueId()).isIN_LOBBY() == true) return;
        if (playerdata.get(p.getUniqueId()).isIN_LOBBY() == false && playerdata.get(p.getUniqueId()).isIN_GAME() == false)
            return;
        if (playerdata.get(p.getUniqueId()).isIN_GAME() == false || playerdata.get(p.getUniqueId()).isABILITY_USAGE() == false) {
            p.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "!" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You cannot use abilities!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1f);
            p.getInventory().setHeldItemSlot(3);
            e.setCancelled(true);
            return;
        }


        if (e.getNewSlot() == 0 || e.getNewSlot() == 1 || e.getNewSlot() == 2) {
            if (!e.getPlayer().getInventory().getItem(e.getNewSlot()).getItemMeta().getLocalizedName().equalsIgnoreCase("skill")) {
                p.getInventory().setHeldItemSlot(3);
                e.setCancelled(true);
                return;
            }

            if (e.getNewSlot() == 0)
                CastAbility(p, cooldowns, e.getNewSlot());
            if (e.getNewSlot() == 1)
                CastAbility(p, cooldowns2, e.getNewSlot());
            if (e.getNewSlot() == 2)
                CastAbility(p, cooldowns3, e.getNewSlot());

            p.getInventory().setHeldItemSlot(3);
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void itemSpawn(ItemSpawnEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void dropEvent(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(playerdata.get(p.getUniqueId()).isIN_GAME() || playerdata.get(p.getUniqueId()).isIN_LOBBY())
            e.setCancelled(true);
    }

    @EventHandler
    public void stopRegenHealth(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof  Player){
            if(playerdata.get(e.getEntity().getUniqueId()).isIN_GAME())
                e.setCancelled(true);
        }
    }

    //Cancels the firework dmg on players if the firework has the same team data as the player and the team is set to friendlyfire off.
    @EventHandler
    public void FireworkDmg(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)){
            if(e.getDamager() instanceof Firework)
                e.setCancelled(true);
            return;
        }
        Player p = (Player) e.getEntity();

        if(e.getDamager() instanceof Firework){
            Firework fw = (Firework) e.getDamager();
            e.setCancelled(true);
            if (fw.hasMetadata(playerdata.get(p.getUniqueId()).getTEAM()) && ProjectMHA.getPlugin(ProjectMHA.class).board.getScoreboard().getTeam(playerdata.get(p.getUniqueId()).getTEAM()).allowFriendlyFire() == false) {
                e.setCancelled(true);
            }

            try{
                if(fw.getCustomName().equalsIgnoreCase(p.getName()))
                    e.setCancelled(true);
            }catch (Exception exception){

            }
        }
    }

    public void CastAbility(Player p, HashMap<String, Long> cd, int slot) {
        if (!Cooldown.checkCD(p, cd)) return;
        if (!Stamina.checkStamina(p, slot)) return;
        if (!playerdata.get(p.getUniqueId()).isABILITY_USAGE()) return;
        if (useAbility(slot, p)) {
            cd.put(p.getName(), System.currentTimeMillis() + (getCooldown(p, slot) * 1000));
            playerdata.get(p.getUniqueId()).setSTAMINA(playerdata.get(p.getUniqueId()).getSTAMINA() - (int) getStamina(p, slot));
        }
    }

    public int getCooldown(Player p, int slot) {
        if (slot == 0){
            int timer = 0;
            try{
                timer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks."+playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME()+".Abilities.Ability1.AbilityTimer");
            }catch(Exception e){
            }
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_COOLDOWN() + timer;
        }
        else if (slot == 1)
        {
            int timer = 0;
            try{
                timer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks."+playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME()+".Abilities.Ability2.AbilityTimer");
            }catch(Exception e){
            }
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_COOLDOWN() + timer;
        }
        else if (slot == 2){
            int ultimer = 0;
            try{
                ultimer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks."+playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME()+".Abilities.Ultimate.UltTimer");
            }catch(Exception e){
            }
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_COOLDOWN() + ultimer;
        }else if (slot == 3)
        {
            int timer = 0;
            try{
                timer = (int) ProjectMHA.getPlugin(ProjectMHA.class).getConfig().get("Quirks."+playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME()+".Abilities.RCAbility.AbilityTimer");
            }catch(Exception e){
            }
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_COOLDOWN() + timer;
        }
        else return 0;

    }

    public double getStamina(Player p, int slot) {
        if (slot == 0)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_STAMINACOST();
        else if (slot == 1)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_STAMINACOST();
        else if (slot == 2)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_STAMINACOST();
        else if (slot == 3)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_STAMINACOST();
        return 0;
    }

    public boolean useAbility(int slot, Player p) {
        if (slot == 0)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().CastAbility1(p);
        if (slot == 1)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().CastAbility2(p);
        if (slot == 2)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().CastUltimate(p);
        if (slot == 3)
            return playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().CastRCAbility(p);

        return false;

    }

}
