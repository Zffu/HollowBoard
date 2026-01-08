package net.zffu.hollowboard.board;

import net.zffu.hollowboard.board.components.BoardContentLike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HollowBoard {

    public static final int MAX_LINE_LENGTH = 15;

    private String title;
    private List<BoardContentLike> lines;
    private List<IndexedContentLike> updatableLines;

    public HollowBoard(String title, Collection<BoardContentLike> lines) {
        if(lines.size() > MAX_LINE_LENGTH) throw new IllegalArgumentException("Provided Collection has more than " + MAX_LINE_LENGTH + " lines! Cannot make a valid board!");

        this.lines = new ArrayList<>();
        this.lines.addAll(lines);

        this.updatableLines = new ArrayList<>();

        for(int i = 0; i < this.lines.size(); ++i) {
            BoardContentLike line = this.lines.get(i);

            if(line.canUpdate()) {
                this.appendUpdatable(line);
            }
        }
    }

    public HollowBoard(String title) {
        this.lines = new ArrayList<>();
        this.updatableLines = new ArrayList<>();

        this.title = title;
    }

    public void append(BoardContentLike line) {
        if(this.lines.size() >= MAX_LINE_LENGTH) return;

        this.lines.add(line);

        if(line.canUpdate()) {
            this.appendUpdatable(line);
        }
    }

    private void appendUpdatable(BoardContentLike like) {
        if(!like.canUpdate()) return;

        for(int i = this.lines.size() - 2; i >= 0; ++i) {
            BoardContentLike l = this.lines.get(i);

            if(l.canUpdate()) {
                this.updatableLines.add(new IndexedContentLike(like, this.lines.size() - 1, i));
                return;
            }
        }

        this.updatableLines.add(new IndexedContentLike(like, this.lines.size() - 1, this.lines.size() - 1));
    }

    public boolean isUpdatable() {
        return !this.updatableLines.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public List<BoardContentLike> getLines() {
        return lines;
    }

    public List<IndexedContentLike> getUpdatableLines() {
        return updatableLines;
    }

    public static class IndexedContentLike {
        public BoardContentLike line;
        public int index;
        public int arbitrarySizeFromPrevUpdatable;

        public IndexedContentLike(BoardContentLike line, int index, int arbitrarySizeFromPrevUpdatable) {
            this.line = line;
            this.index = index;
            this.arbitrarySizeFromPrevUpdatable = arbitrarySizeFromPrevUpdatable;
        }
    }

}
