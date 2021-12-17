package hadences.projectmha.commands.summondummy;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;

public class Dummy implements Listener {

    ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public void spawn(Player p){
        //Armor
        ItemStack playerhead = new ItemStack(Material.GLASS);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta headmeta = playerhead.getItemMeta();
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setColor(Color.fromBGR(255, 255, 255));
        headmeta.setCustomModelData(1);
        playerhead.setItemMeta(headmeta);
        chestplate.setItemMeta(meta);

        //Summon dummy logic
        ArmorStand dummy = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        dummy.setArms(true);
        dummy.setLeftArmPose(new EulerAngle(0,0,80));
        dummy.setRightArmPose(new EulerAngle(0,0,-80));
        dummy.setVisible(true);
        dummy.getEquipment().setHelmet(playerhead);
        dummy.getEquipment().setChestplate(chestplate);
        dummy.setMaxHealth( 2 * (Double) mha.getConfig().get("Game.Entity.Dummy.Health"));
        dummy.setHealth(2 *(Double) mha.getConfig().get("Game.Entity.Dummy.Health"));
        dummy.setCustomName(Chat.format("&eDamage Dealt: &a" + 0.0 +" &7|"+" &c❤ " + dummy.getHealth()/2 + " &7/ &c" + dummy.getMaxHealth()/2 + ""));
        dummy.setBasePlate(true);
        dummy.setAI(false);
        dummy.addScoreboardTag("dummy");
        dummy.setCustomNameVisible(true);
    }

    @EventHandler
    public void DummyDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) e.getEntity();
            if(!armorStand.getScoreboardTags().contains("dummy")) return;

            double damage = e.getDamage();

            double newHealth = armorStand.getHealth() - damage;
            if(newHealth <= 0){
                armorStand.setHealth(0);
            }else
                armorStand.setHealth(newHealth);

            e.setCancelled(true);
            armorStand.setCustomName(Chat.format("&eDamage Dealt: &a" + damage/2 +" &7|"+" &c❤ " +armorStand.getHealth()/2 + " &7/ &c" + armorStand.getMaxHealth()/2));

        }
    }

    @EventHandler
    public void interactArmorStand(PlayerArmorStandManipulateEvent e){
        //Player p = e.getPlayer();
        if(e.getRightClicked().getScoreboardTags().contains("dummy"))
            e.setCancelled(true);
    }
}
