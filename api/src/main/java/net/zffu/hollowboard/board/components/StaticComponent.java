package net.zffu.hollowboard.board.components;

import net.zffu.hollowboard.HollowPlayer;

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
    public List<String> write(HollowPlayer player) {
        return this.contents;
    }

    @Override
    public int getSize(HollowPlayer player) {
        return this.contents.size();
    }

}
