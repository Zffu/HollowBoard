package net.zffu.hollowboard.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HollowBoard {

    public static final int MAX_LINE_LENGTH = 15;

    private List<BoardLine> lines;

    public HollowBoard(Collection<BoardLine> lines) {
        if(lines.size() > MAX_LINE_LENGTH) throw new IllegalArgumentException("Provided Collection has more than " + MAX_LINE_LENGTH + " lines! Cannot make a valid board!");

        this.lines = new ArrayList<>();
        this.lines.addAll(lines);
    }

    public void append(BoardLine line) {
        if(this.lines.size() >= MAX_LINE_LENGTH) return;

        this.lines.add(line);
    }

}
