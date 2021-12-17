package hadences.projectmha.object;

import hadences.projectmha.ProjectMHA;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static hadences.projectmha.game.GameManager.BlockTimers;
import static hadences.projectmha.game.GameManager.MHABlocks;

public class MHABlock {

    private Block block;
    private Material original_material;
    private int revert_seconds;

    public MHABlock(Block block, Material change_material, int timer_seconds){
        this.block = block;
        this.revert_seconds = timer_seconds;
        if(!MHABlocks.containsKey(block.getLocation())){
            this.original_material = block.getType();
            MHABlocks.put(block.getLocation(),this);
        }else {
            this.original_material = MHABlocks.get(block.getLocation()).original_material;
            MHABlocks.replace(block.getLocation(),this);
        }


        block.setType(change_material);
        BukkitTask revert = new Revert(block,ProjectMHA.getPlugin(ProjectMHA.class),original_material).runTaskLater(ProjectMHA.getPlugin(ProjectMHA.class),revert_seconds*20);
        FixBlockSchedulers(block.getLocation(),revert);
    }

    public void FixBlockSchedulers(Location key, BukkitTask sched){
        if(BlockTimers.containsKey(key)){
            BlockTimers.get(key).cancel();
            BlockTimers.remove(key);
        }
        BlockTimers.put(key,sched);
    }


    public void revert_now(Location key){
        if(BlockTimers.containsKey(key)){
            BlockTimers.get(key).cancel();
            BlockTimers.remove(key);
        }

        block.setType(original_material);
    }

}
class Revert extends BukkitRunnable {

    Block b;
    Material original_material;
    ProjectMHA plugin;
    public Revert(Block b, ProjectMHA plugin, Material original_material){
        this.b = b;
        this.plugin = plugin;
        this.original_material = original_material;
    }

    @Override
    public void run() {
        b.setType(original_material);
        MHABlocks.remove(b.getLocation());
        BlockTimers.remove(b.getLocation());
    }
}
