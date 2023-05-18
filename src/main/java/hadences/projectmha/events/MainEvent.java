package hadences.projectmha.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.player.PlayerManager;
import hadences.projectmha.ProjectMHA;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.game.quirk.Cooldown.init;
import static hadences.projectmha.player.PlayerManager.playerdata;
import static hadences.projectmha.config.PlayerConfig.AddPlayerToConfig;
import static hadences.projectmha.config.PlayerConfig.CheckPlayerInConfig;

public class MainEvent implements Listener {
    private static ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    //When Player Joins
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        p.setGameMode(GameMode.ADVENTURE);
        p.setScoreboard(mha.board.getScoreboard());
        mha.board.showMainBoard();

        //Give MenuItem
        mha.resetInventory(p);
        if (playerdata.containsKey(p.getUniqueId())) {
            playerdata.remove(p.getUniqueId());
        }
        //playerdata.put(p.getUniqueId(), new PlayerManager(p, 0));


        if (CheckPlayerInConfig(p)) {
            if (!playerdata.containsKey(p.getUniqueId())) {
                playerdata.put(p.getUniqueId(), new PlayerManager(p, Integer.parseInt(mha.getConfig().get("Users." + p.getUniqueId() + ".WINS").toString())));
                init(p);
            }
        } else {
            AddPlayerToConfig(p);
            playerdata.put(p.getUniqueId(), new PlayerManager(p, 0));
            init(p);
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent e){
        e.setCancelled(true);
        Player p = e.getPlayer();
        Bukkit.broadcast(Component.text(Chat.format( "&e" + playerdata.get(p.getUniqueId()).getWINS() +" &6&l♚ &d"+ PlainTextComponentSerializer.plainText().serialize(p.displayName()) + "&7: &f" + PlainTextComponentSerializer.plainText().serialize(e.message()))));
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!playerdata.get(p.getUniqueId()).isDEV_MODE()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void rightClickMenu(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.hasItem())
                if (e.getItem().getItemMeta().getLocalizedName().equalsIgnoreCase("menu") && playerdata.get(p.getUniqueId()).isIN_GAME() && console.getGamemodeManager().getGamemode().equalsIgnoreCase("Playground")) {
                    p.openInventory(playerdata.get(p.getUniqueId()).getGuiMenuManager().getPlaygroundMenu());
                }else if(playerdata.get(p.getUniqueId()).isIS_READY()){
                    return;
                }else if (e.getItem().getItemMeta().getLocalizedName().equalsIgnoreCase("menu") && playerdata.get(p.getUniqueId()).isIN_LOBBY() == false && playerdata.get(p.getUniqueId()).isIN_GAME() == false) {
                    p.openInventory(playerdata.get(p.getUniqueId()).getGuiMenuManager().getMainMenu());
                } else if (e.getItem().getItemMeta().getLocalizedName().equalsIgnoreCase("menu") && playerdata.get(p.getUniqueId()).isIN_LOBBY() == true && playerdata.get(p.getUniqueId()).isIN_GAME() == false) {
                    p.openInventory(playerdata.get(p.getUniqueId()).getGuiMenuManager().getCharacterMenu());
                }
        }
    }

    @EventHandler
    public void MoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!playerdata.get(p.getUniqueId()).isCAN_MOVE() && e.hasExplicitlyChangedPosition())
            e.setCancelled(true);
    }

    @EventHandler
    public void JumpEvent(PlayerJumpEvent e) {
        Player p = e.getPlayer();
        float yaw = p.getEyeLocation().getYaw();
        float pitch = p.getEyeLocation().getPitch();
        Location location = p.getLocation();

        if (!playerdata.get(p.getUniqueId()).isCAN_JUMP()) {
            e.setCancelled(true);
            p.teleport(new Location(p.getWorld(), location.getX(), location.getY(), location.getZ(), yaw, pitch));
        }
    }

}