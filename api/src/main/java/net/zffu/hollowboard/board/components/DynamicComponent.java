package net.zffu.hollowboard.board.components;

import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.board.lines.DynamicLine;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link BoardContentLike} that can be updatabable or not depending on its contents.
 */
public class DynamicComponent implements BoardContentLike {

    private List<DynamicLine> components;
    private boolean canUpdate;

    public DynamicComponent(List<DynamicLine> components) {
        this.components = components;

        for(DynamicLine lineComponent : this.components) {
            if(lineComponent.canUpdate()) {
                this.canUpdate = true;
                break;
            }
        }
    }

    public DynamicComponent() {
        this.components = new ArrayList<>();
    }

    public List<DynamicLine> getComponents() {
        return components;
    }

    public void append(String str) {
        DynamicLine component = DynamicLine.compileLine(str);

        this.append(component);
    }

    public void append(DynamicLine component) {
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
    public List<String> write(HollowPlayer player) {
        List<String> str = new ArrayList<>();

        for(DynamicLine component : this.components) {
            str.add(component.write(player));
        }

        return str;
    }

    @Override
    public int getSize(HollowPlayer player) {
        return this.write(player).size();
    }
}
