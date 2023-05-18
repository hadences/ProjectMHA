package hadences.projectmha.nms.entityglow;

import hadences.projectmha.ProjectMHA;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static hadences.projectmha.player.PlayerManager.FixQuirkSchedulers;

public class GlowManager {

    public static HashMap<UUID, GlowManager> glowManagerHashMap = new HashMap<>();

    private Player target;
    private List<Player> viewers = new ArrayList<>();
    private byte off;
    private int seconds = 0;

    public GlowManager(Player target, List<Player> viewers, int seconds,String tag){
        if(!glowManagerHashMap.containsKey(target.getUniqueId())){
            glowManagerHashMap.put(target.getUniqueId(),this);
        }else{
            glowManagerHashMap.get(target.getUniqueId()).target = target;
            glowManagerHashMap.get(target.getUniqueId()).viewers.addAll(viewers);
        }
        this.target = target;
        this.viewers = viewers;
        this.seconds = seconds;

        target.addScoreboardTag(tag);

        BukkitTask GlowTask = new GlowScheduler(target,ProjectMHA.getPlugin(ProjectMHA.class),seconds,tag).runTaskTimer(ProjectMHA.getPlugin(ProjectMHA.class),0,0);
        FixQuirkSchedulers(target,"GLOW",GlowTask);
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public List<Player> getViewers() {
        return viewers;
    }

    public void setViewers(List<Player> viewers) {
        this.viewers = viewers;
    }

    public void update(){
        ServerPlayer serverPlayer = ((CraftPlayer) target).getHandle();

        ClientboundSetEntityDataPacket clientboundSetEntityDataPacket = new ClientboundSetEntityDataPacket(serverPlayer.getId(),serverPlayer.getEntityData(),true);
        SynchedEntityData.DataItem DataItem = clientboundSetEntityDataPacket.getUnpackedData().get(0);
        byte b = (byte) DataItem.getValue();
        this.off = b;
        b |= 0b01000000;
        DataItem.setValue(b);

        ServerGamePacketListenerImpl sc;

        for(Player viewer : viewers){
            sc = ((CraftPlayer) viewer).getHandle().connection;
            sc.send(clientboundSetEntityDataPacket);
        }
    }

    public void stop(){
        ServerPlayer serverPlayer = ((CraftPlayer) target).getHandle();

        ClientboundSetEntityDataPacket packet = new ClientboundSetEntityDataPacket(serverPlayer.getId(),serverPlayer.getEntityData(),true);
        SynchedEntityData.DataItem DataItem = packet.getUnpackedData().get(0);

        DataItem.setValue(off);

        ServerGamePacketListenerImpl sc;

        for(Player viewer : viewers){
            sc = ((CraftPlayer) viewer).getHandle().connection;
            sc.send(packet);
        }
        viewers.clear();
    }

    public void removeViewers(List<Player> viewers){
        this.viewers.removeAll(viewers);

        ServerPlayer serverPlayer = ((CraftPlayer) target).getHandle();

        ClientboundSetEntityDataPacket packet = new ClientboundSetEntityDataPacket(serverPlayer.getId(),serverPlayer.getEntityData(),true);
        SynchedEntityData.DataItem DataItem = packet.getUnpackedData().get(0);

        DataItem.setValue(off);

        ServerGamePacketListenerImpl sc;

        for(Player viewer : viewers){
            sc = ((CraftPlayer) viewer).getHandle().connection;
            sc.send(packet);
        }


    }




}


class GlowScheduler extends BukkitRunnable {
    int max_ticks = 0;
    int ticks = 0;
    Player player;
    String tag;
    public GlowScheduler(Player p, ProjectMHA plugin, int seconds,String tag){
        max_ticks = seconds*20;
        this.player = p;
        this.tag = tag;
    }

    @Override
    public void run() {
        if(ticks >= max_ticks) {
            player.removeScoreboardTag(tag);
            GlowManager.glowManagerHashMap.get(player.getUniqueId()).stop();
            this.cancel();
        }
        GlowManager.glowManagerHashMap.get(player.getUniqueId()).update();
        ticks++;
    }
}
