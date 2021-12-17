package hadences.projectmha.config;

import hadences.projectmha.ProjectMHA;
import org.bukkit.entity.Player;

import java.util.UUID;

import static hadences.projectmha.player.PlayerManager.playerdata;


public class PlayerConfig {
    private static ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public static void updatePlayerConfig() {
        String Name = "";
        String Wins = "";

        // Plugin shutdown logic
        for (UUID p : playerdata.keySet()) {

            Name = playerdata.get(p).getPLAYER_NAME();
            Wins = playerdata.get(p).getWINS() + "";

            mha.getConfig().set("Users." + p + ".NAME", Name);
            mha.getConfig().set("Users." + p + ".WINS", Wins);
            mha.saveConfig();
        }
    }

    public static boolean CheckPlayerInConfig(Player p) {
        if (mha.getConfig().getConfigurationSection("Users").getKeys(false).isEmpty())
            return false;
        for (String key : mha.getConfig().getConfigurationSection("Users").getKeys(false)) {
            if (key.equals(p.getUniqueId().toString())) {
                return true;
            }
        }

        return false;
    }

    public static void AddPlayerToConfig(Player p) {
        mha.getConfig().set("Users." + p.getUniqueId() + ".NAME", p.getName());
        mha.getConfig().set("Users." + p.getUniqueId() + ".WINS", "0");
    }

}
