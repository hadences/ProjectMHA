package hadences.projectmha.item;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static hadences.projectmha.player.PlayerManager.playerdata;


public class GameItems {
    private ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);
    ItemStack Ability1;
    ItemStack Ability2;
    ItemStack Ultimate;
    ItemStack QuirkInfo;
    ItemMeta meta;
    ArrayList<Component> lore = new ArrayList<>();

    public void givePlayerQuirkInfo(Player p, int slot){
        //QuirkInfo = new ItemStack(Material.getMaterial((String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Item")));
        QuirkInfo = new ItemStack(Material.GRAY_DYE);
        meta = QuirkInfo.getItemMeta();
        meta.setCustomModelData(1);
        meta.displayName(Component.text(Chat.format("&c&l<< &eRC Ability &c&l>>")));

        String Name = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.RCAbility.Name");
        double Damage = (double) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.RCAbility.Damage");
        int Stamina = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.RCAbility.StaminaCost");
        int Cooldown = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.RCAbility.Cooldown");
        String description = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.RCAbility.Description");

        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――――「 &f"+Name+" &7」―――――――")));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&4▸ Ability Damage: &f" + Damage)));
        lore.add(Component.text(Chat.format("&6▸ Stamina Cost: &f" + Stamina)));
        lore.add(Component.text(Chat.format("&3▸ Cooldown: &f" + Cooldown)));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――「 &fDESCRIPTION &7」―――――")));
        addDescription(lore, Chat.format(description));
        meta.setLocalizedName("skill");
        meta.lore(lore);
        QuirkInfo.setItemMeta(meta);
        p.getInventory().setItem(slot,QuirkInfo);
    }

    public void givePlayerGameItems(Player p){
        lore.clear();
        Ability1 = new ItemStack(Material.LIME_DYE);
        meta = Ability1.getItemMeta();
        meta.setCustomModelData(1);
        meta.displayName(Component.text(Chat.format("&c&l<< &eAbility 1 &c&l>>")));
        String Name = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability1.Name");
        double Damage = (double) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability1.Damage");
        int Stamina = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability1.StaminaCost");
        int Cooldown = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability1.Cooldown");
        String description = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability1.Description");

        meta.setLocalizedName("skill");
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――――「 &f"+Name+" &7」―――――――")));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&4▸ Ability Damage: &f" + Damage)));
        lore.add(Component.text(Chat.format("&6▸ Stamina Cost: &f" + Stamina)));
        lore.add(Component.text(Chat.format("&3▸ Cooldown: &f" + Cooldown)));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――「 &fDESCRIPTION &7」―――――")));
        addDescription(lore, Chat.format(description));
        meta.lore(lore);
        Ability1.setItemMeta(meta);

        lore.clear();

        Ability2 = new ItemStack(Material.LIME_DYE);
        meta = Ability2.getItemMeta();
        meta.setCustomModelData(2);
        meta.displayName(Component.text(Chat.format("&c&l<< &eAbility 2 &c&l>>")));
        Name = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability2.Name");
        Damage = (double) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability2.Damage");
        Stamina = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability2.StaminaCost");
        Cooldown = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability2.Cooldown");
        description = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ability2.Description");

        meta.setLocalizedName("skill");
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――――「 &f"+Name+" &7」―――――――")));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&4▸ Ability Damage: &f" + Damage)));
        lore.add(Component.text(Chat.format("&6▸ Stamina Cost: &f" + Stamina)));
        lore.add(Component.text(Chat.format("&3▸ Cooldown: &f" + Cooldown)));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――「 &fDESCRIPTION &7」―――――")));
        addDescription(lore, Chat.format(description));
        meta.lore(lore);
        Ability2.setItemMeta(meta);

        lore.clear();

        Ultimate = new ItemStack(Material.MAGENTA_DYE);
        meta = Ultimate.getItemMeta();
        meta.setCustomModelData(2);
        meta.displayName(Component.text(Chat.format("&c&l<< &dUltimate &c&l>>")));
        Name = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ultimate.Name");
        Damage = (double) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ultimate.Damage");
        Stamina = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ultimate.StaminaCost");
        Cooldown = (int) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ultimate.Cooldown");
        description = (String) mha.getConfig().get("Quirks." + playerdata.get(p.getUniqueId()).getQUIRK().getQUIRK_NAME() + ".Abilities.Ultimate.Description");

        meta.setLocalizedName("skill");
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――――「 &f"+Name+" &7」―――――――")));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&4▸ Ability Damage: &f" + Damage)));
        lore.add(Component.text(Chat.format("&6▸ Stamina Cost: &f" + Stamina)));
        lore.add(Component.text(Chat.format("&3▸ Cooldown: &f" + Cooldown)));
        lore.add(Component.text(Chat.format(" ")));
        lore.add(Component.text(Chat.format("&7―――――「 &fDESCRIPTION &7」―――――")));
        addDescription(lore, Chat.format(description));
        meta.lore(lore);
        Ultimate.setItemMeta(meta);

        lore.clear();
        p.getInventory().setItem(0,Ability1);
        p.getInventory().setItem(1,Ability2);
        p.getInventory().setItem(2,Ultimate);
    }

    public static void addDescription(ArrayList<Component> lore, String description) {
        String[] wormha = description.split(" ");
        String sentence = "";
        int index = 0;
        for (int i = 0; i < wormha.length; i++) {
            sentence += wormha[i] + " ";
            index++;

            if (index >= 5) {
                lore.add(Component.text(Chat.format("&7" + sentence)));
                sentence = "";
                index = 0;
            }
        }
        lore.add(Component.text(ChatColor.GRAY + sentence));
    }

}
