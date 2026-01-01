package net.zffu.hollowboard.board;

import net.zffu.hollowboard.deps.PluginAPIAccessor;
import net.zffu.hollowboard.utils.UpdatableValue;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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

        @Override
        public String toString() {
            return "Text{" +
                    "text='" + text + '\'' +
                    '}';
        }
    }

    class SkriptVariable implements LinePart {

        private final String variableName;
        private final boolean isPlayerDependant;
        private UpdatableValue<String> globalValue; // non-player dependant optimization

        public SkriptVariable(String variableName) {
            this.variableName = variableName;
            this.isPlayerDependant = variableName.contains("%player%") || variableName.contains("%player-uuid%");

            if(!this.isPlayerDependant) {
                this.globalValue = new UpdatableValue<>(() -> {
                    return PluginAPIAccessor.getSkriptVariable(variableName).toString();
                }, 2000);
            }
        }

        @Override
        public String write(Player player) {

            if(!this.isPlayerDependant) {
                return this.globalValue.gather();
            }

            return PluginAPIAccessor.getSkriptVariable(variableName.replaceAll("%player%", player.getName()).replaceAll("%player-uuid%", player.getUniqueId().toString())).toString();
        }

        @Override
        public boolean isUpdatePossible() {
            return true;
        }

        @Override
        public String toString() {
            return "SkriptVariable{" +
                    "variableName='" + variableName + '\'' +
                    ", isPlayerDependant=" + isPlayerDependant +
                    '}';
        }
    }

}
