package net.zffu.hollowboard.board;

import net.zffu.hollowboard.HollowPlayer;
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

        this.title = title;

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

    private int getTotalPreviousScoreboardSize(HollowPlayer player, IndexedContentLike content) {
        int sz = 0;

        for(int i = 0; i < content.index; ++i) {
            BoardContentLike like = this.lines.get(i);

            sz += like.canUpdate() ? player.lastSizes.getOrDefault(like, 0) : like.write(player).size();
        }

        return sz;
    }

    private void onComponentSizeChange(IndexedContentLike like, HollowPlayer player) {
        int index = this.getTotalPreviousScoreboardSize(player, like);

        List<String> comp = like.line.write(player);
        player.lastSizes.put(like.line, comp.size());

        for(String str : comp) {
            player.scoreboardController.setLine(index, str);
            ++index;
        }

        for(int i = like.index + 1; i < this.lines.size(); ++i) {
            BoardContentLike l = this.lines.get(i);

            for(String str : l.write(player)) {
                player.scoreboardController.setLine(index, str);
                ++index;
            }
        }

        for(int i = index; i < 15; ++i) {
            player.scoreboardController.remove(i);
        }
    }

    public void update(IndexedContentLike like, HollowPlayer player) {
        int oldSize = player.lastSizes.getOrDefault(like.line, 0);

        List<String> list = like.line.write(player);

        if(oldSize != list.size()) {
            this.onComponentSizeChange(like, player);
            return;
        }

        int index = this.getTotalPreviousScoreboardSize(player, like);

        List<String> comp = like.line.write(player);

        player.lastSizes.put(like.line, comp.size());

        for(String str : list) {
            player.scoreboardController.setLine(index, str);
            ++index;
        }
    }

    private void appendUpdatable(BoardContentLike like) {
        if(!like.canUpdate()) return;

        this.updatableLines.add(new IndexedContentLike(like, this.lines.size() - 1));
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

        public IndexedContentLike(BoardContentLike line, int index) {
            this.line = line;
            this.index = index;
        }
    }

}
