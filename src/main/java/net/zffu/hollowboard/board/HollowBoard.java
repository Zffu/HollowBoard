package net.zffu.hollowboard.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HollowBoard {

    public static final int MAX_LINE_LENGTH = 15;

    private String title;
    private List<BoardLine> lines;
    private List<IndexedLine> updatableLines;

    public HollowBoard(String title, Collection<BoardLine> lines) {
        if(lines.size() > MAX_LINE_LENGTH) throw new IllegalArgumentException("Provided Collection has more than " + MAX_LINE_LENGTH + " lines! Cannot make a valid board!");

        this.lines = new ArrayList<>();
        this.lines.addAll(lines);

        this.updatableLines = new ArrayList<>();

        for(int i = 0; i < this.lines.size(); ++i) {
            BoardLine line = this.lines.get(i);

            if(line.canUpdate()) {
                this.updatableLines.add(new IndexedLine(line, i));
            }
        }
    }

    public HollowBoard(String title) {
        this.lines = new ArrayList<>();
        this.updatableLines = new ArrayList<>();

        this.title = title;
    }

    public void append(BoardLine line) {
        if(this.lines.size() >= MAX_LINE_LENGTH) return;

        this.lines.add(line);

        if(line.canUpdate()) {
            this.updatableLines.add(new IndexedLine(line, this.lines.size() - 1));
        }
    }

    public boolean isUpdatable() {
        return !this.updatableLines.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public List<BoardLine> getLines() {
        return lines;
    }

    public List<IndexedLine> getUpdatableLines() {
        return updatableLines;
    }

    public static class IndexedLine {
        public BoardLine line;
        public int index;

        public IndexedLine(BoardLine line, int index) {
            this.line = line;
            this.index = index;
        }
    }

}
