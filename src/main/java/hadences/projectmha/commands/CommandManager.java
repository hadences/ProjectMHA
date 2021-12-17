package hadences.projectmha.commands;

import hadences.projectmha.ProjectMHA;
import hadences.projectmha.arena.ArenaManager;
import hadences.projectmha.chat.Chat;
import hadences.projectmha.commands.summondummy.Dummy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static hadences.projectmha.game.GameManager.console;
import static hadences.projectmha.arena.Arena.arenalist;


public class CommandManager implements CommandExecutor {
    ProjectMHA mha = JavaPlugin.getPlugin(ProjectMHA.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true; // returns false if the user who send the command is not a player

        Player p = (Player) sender;
        if (!(label.equalsIgnoreCase("mha"))) return true; // checks if player typed '/mha'

        // /mha
        if (args.length == 0) {
            p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " Invalid Syntax. Usage : /mha <command>");
            return true;
        }

        // /mha create
        else if (args.length == 1 && args[0].equalsIgnoreCase("create")) {
            p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " Invalid Syntax. Usage : /mha create <arena name>");
            return true;
        }
        // /mha create <name>
        else if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            if (ArenaManager.ArenaNameInList(args[1])) { // if the arena name is in the list of arenas then returns true
                p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " This arena name is already in the list!");
                return true;
            } else { // arena name is not in the list of arenas; creates arena
                p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Arena " + ChatColor.WHITE + args[1] + ChatColor.GREEN + " created!");
                ArenaManager.addArena(args[1]);
                return true;
            }
        }

        // /mha arenalist
        else if (args.length == 1 && args[0].equalsIgnoreCase("arenalist")) {
            p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + ArenaManager.getArenaList());
            return true;
        }

        // /mha reloadconfig
        else if (args.length == 1 && args[0].equalsIgnoreCase("reloadconfig")) {
            p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Config reloaded!");
            mha.reloadConfig();
            return true;
        }

        // /mha summondummy
        else if (args.length == 1 && args[0].equalsIgnoreCase("summondummy")){
            Dummy dummy = new Dummy();
            dummy.spawn(p);

            return true;
        }

        // /mha get <name>
        else if ((args.length == 1 && args[0].equalsIgnoreCase("get")) || (args.length == 2 && ArenaManager.ArenaNameInList(args[1]))) {
            p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " Invalid Syntax. Usage : /mha get <name> setlobbyspawn/sett1spawn/sett2spawn/addspawnpoint/finalize");
            return true;
        }

        // /mha endgame
        else if (args.length == 1 && args[0].equalsIgnoreCase("endgame")) {
            console.getGamemodeManager().setEndGame(true);

        }

        // /mha get <name> <command>
        else if (args.length == 3 && args[0].equalsIgnoreCase("get") && ArenaManager.ArenaNameInList(args[1])) {
            // /mha get <name> setLobbySpawn
            if (args[2].equalsIgnoreCase("setlobbyspawn")) {
                arenalist.get(args[1]).setLobbyspawn(p.getLocation());
                p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Lobby Spawn Set for Arena " + ChatColor.WHITE + args[1]);
                return true;
            }
            // /mha get <name> setT1Spawn
            else if (args[2].equalsIgnoreCase("sett1spawn")) {
                p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Team 1 Spawn Set for Arena " + ChatColor.WHITE + args[1]);
                arenalist.get(args[1]).setT1spawn(p.getLocation());
                return true;
            }
            // /mha get <name> setT2Spawn
            else if (args[2].equalsIgnoreCase("sett2spawn")) {
                p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Team 2 Spawn Set for Arena " + ChatColor.WHITE + args[1]);
                arenalist.get(args[1]).setT2spawn(p.getLocation());
                return true;
            }
            // /mha get <name> addspawnpoint
            else if (args[2].equalsIgnoreCase("addspawnpoint")) {
                p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Spawnpoint added for Arena " + ChatColor.WHITE + args[1]);
                arenalist.get(args[1]).getSpawnpoints().add(p.getLocation());
                return true;
            }
            // /mha get <name> finalize
            else if (args[2].equalsIgnoreCase("finalize")) {
                if (arenalist.get(args[1]).getT1spawn() == null || arenalist.get(args[1]).getT2spawn() == null || arenalist.get(args[1]).getLobbyspawn() == null) {
                    p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " Arena Cannot be finalized for " + ChatColor.WHITE + args[1]);
                    return true;
                }else if(arenalist.get(args[1]).getSpawnpoints().size() < 8){
                    p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " 8 or more spawnpoints need to be added to the arena! " + ChatColor.WHITE + args[1] + ChatColor.RED + " currently has " + ChatColor.GREEN + arenalist.get(args[1]).getSpawnpoints().size() + ChatColor.RED +" spawnpoints!");
                }else {
                    arenalist.get(args[1]).setFinalized(true);
                    p.sendMessage(Chat.getConsoleName() + ChatColor.GREEN + " Arena is finalized!");
                    return true;
                }
            }

        } else {
            p.sendMessage(Chat.getConsoleName() + ChatColor.RED + " Arena does not exist!");
            return true;
        }


        return false;
    }
}
