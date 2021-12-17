package hadences.projectmha.player;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static hadences.projectmha.player.PlayerManager.playerdata;

public class Stamina {
    public static boolean checkStamina(Player p, int ability) {


        int Stamina = playerdata.get(p.getUniqueId()).getSTAMINA();
        int StaminaReq = 0;
        if (ability == 0) {
            StaminaReq = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY1_STAMINACOST();
        } else if (ability == 1) {
            StaminaReq = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getABILITY2_STAMINACOST();
        } else if (ability == 2) {
            StaminaReq = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getULT_STAMINACOST();
        } else if (ability == 3) {
            StaminaReq = playerdata.get(p.getUniqueId()).getQUIRK().getQUIRKCASTMANAGER().getRCABILITY_STAMINACOST();
        }
        if (Stamina < StaminaReq) {
            p.sendTitle(" ", ChatColor.RED + "Not Enough Stamina!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
            return false;
        }

        return true;
    }
}
