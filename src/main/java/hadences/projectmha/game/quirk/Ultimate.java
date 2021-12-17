package hadences.projectmha.game.quirk;

import org.bukkit.entity.Player;

public class Ultimate {
    private boolean inUltimate = false;
    private Player player;
    public void ability(Player p) {
        player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isInUltimate() {
        return inUltimate;
    }

    public void setInUltimate(boolean inUltimate) {
        this.inUltimate = inUltimate;
    }
}
