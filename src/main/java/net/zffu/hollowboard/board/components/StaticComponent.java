package net.zffu.hollowboard.board.components;

import org.bukkit.entity.Player;

import java.util.List;

public class StaticComponent implements BoardContentLike {

    private List<String> contents;

    public StaticComponent(List<String> contents) {
        this.contents = contents;
    }


    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public List<String> write(Player player) {
        return this.contents;
    }

    @Override
    public int getSize(Player player) {
        return this.contents.size();
    }
}
