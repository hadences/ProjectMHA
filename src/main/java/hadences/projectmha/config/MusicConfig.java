package hadences.projectmha.config;

import hadences.projectmha.ProjectMHA;


import java.util.ArrayList;


public class MusicConfig {

    private static ProjectMHA mha = ProjectMHA.getPlugin(ProjectMHA.class);
    public static ArrayList<String> music = new ArrayList<>();
    public static void loadMusic() {
        String name;

        if (!mha.getConfig().getConfigurationSection("Music").getKeys(false).isEmpty()) {
            for (String key : mha.getConfig().getConfigurationSection("Music").getKeys(false)) {
                name = (String) mha.getConfig().get("Music."+key);
                music.add(name);
            }
        }


    }
}

