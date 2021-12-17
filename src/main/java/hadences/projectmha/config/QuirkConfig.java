package hadences.projectmha.config;

import hadences.projectmha.game.quirk.Quirk;
import hadences.projectmha.game.quirk.QuirkCastManager;
import hadences.projectmha.ProjectMHA;
import hadences.projectmha.chat.Chat;

public class QuirkConfig {
    private static ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);

    public static void loadQuirks() {
        String name;
        String displayName;
        String classification;
        String role;

        if (!mha.getConfig().getConfigurationSection("Quirks").getKeys(false).isEmpty()) {
            for (String key : mha.getConfig().getConfigurationSection("Quirks").getKeys(false)) {
                name = key;
                displayName = Chat.hexformat(mha.getConfig().get("Quirks." + key + ".DisplayName").toString());
                classification = Chat.hexformat(mha.getConfig().get("Quirks." + key + ".Classification").toString());
                role = Chat.hexformat(mha.getConfig().get("Quirks." + key + ".Role").toString());
                Quirk.quirklist.add(new Quirk(name, displayName, classification, role));
            }
        }


    }
}