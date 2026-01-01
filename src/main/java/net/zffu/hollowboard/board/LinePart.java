package net.zffu.hollowboard.board;

import net.zffu.hollowboard.deps.PluginAPIAccessor;
import org.bukkit.entity.Player;

/**
 * Represents a line part
 */
public interface LinePart {

    String write(Player player);
    boolean isUpdatePossible();

    /**
     * A static text part
     */
    class Text implements LinePart {

        private final String text;

        public Text(String text) {
            this.text = text;
        }

        @Override
        public String write(Player player) {
            return this.text;
        }

        @Override
        public boolean isUpdatePossible() {
            return false;
        }
    }

    class SkriptVariable implements LinePart {

        private final String variableName;

        public SkriptVariable(String variableName) {
            this.variableName = variableName;
        }

        @Override
        public String write(Player player) {
            return PluginAPIAccessor.getSkriptVariable(variableName.replaceAll("%player%", player.getName()).replaceAll("%player-uuid%", player.getUniqueId().toString())).toString();
        }

        @Override
        public boolean isUpdatePossible() {
            return true;
        }
    }

}
