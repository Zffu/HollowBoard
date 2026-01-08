package net.zffu.hollowboard.board.components;

import net.zffu.hollowboard.board.DynamicLineComponent;
import org.bukkit.entity.Player;
import org.eclipse.sisu.Dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link BoardContentLike} that can be updatabable or not depending on it's contents.
 */
public class DynamicComponent implements BoardContentLike {

    private List<DynamicLineComponent> components;
    private boolean canUpdate;

    public DynamicComponent(List<DynamicLineComponent> components) {
        this.components = components;

        for(DynamicLineComponent lineComponent : this.components) {
            if(lineComponent.canUpdate()) {
                this.canUpdate = true;
                break;
            }
        }
    }

    public DynamicComponent() {
        this.components = new ArrayList<>();
    }

    public void append(String str) {
        DynamicLineComponent component = DynamicLineComponent.compileLine(str);

        this.append(component);
    }

    public void append(DynamicLineComponent component) {
        if(component.canUpdate()) {
            this.canUpdate = true;
        }

        this.components.add(component);
    }

    @Override
    public boolean canUpdate() {
        return this.canUpdate;
    }

    @Override
    public List<String> write(Player player) {
        List<String> str = new ArrayList<>();

        for(DynamicLineComponent component : this.components) {
            str.add(component.write(player));
        }

        return str;
    }

    @Override
    public int getSize(Player player) {
        return this.write(player).size();
    }
}
