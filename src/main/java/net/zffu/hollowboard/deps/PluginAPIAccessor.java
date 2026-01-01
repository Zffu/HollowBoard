package net.zffu.hollowboard.deps;

import net.zffu.hollowboard.HollowBoardPlugin;
import org.bukkit.Bukkit;

/**
 * Allows to use plugin APIs without breaking anything
 */
public class PluginAPIAccessor {

    public static Object getSkriptVariable(String name) {
        if(!Bukkit.getServer().getPluginManager().isPluginEnabled("Skript")) {
            HollowBoardPlugin.INSTANCE.getLogger().warning("Cannot gather Skript variable " + name + " as Skript wasn't enabled!");
            return "Unknown!";
        }

        return ch.njol.skript.variables.Variables.getVariable(name, null, false);
    }

}
