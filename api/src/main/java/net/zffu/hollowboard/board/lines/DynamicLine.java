package net.zffu.hollowboard.board.lines;

import net.zffu.hollowboard.HollowPlayer;
import net.zffu.hollowboard.board.lines.parts.LinePart;
import net.zffu.hollowboard.board.lines.parts.TextLinePart;
import net.zffu.hollowboard.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicLine {

    public static final DynamicLine empty = DynamicLine.compileLine("");

    private List<LinePart> parts;
    private boolean canUpdate;

    public DynamicLine() {
        this.parts = new ArrayList<>();
        this.canUpdate = false;
    }

    public DynamicLine(List<LinePart> parts) {
        this.parts = parts;
        this.canUpdate = false;

        this.updateUpdatableState();
    }

    public void appendPart(LinePart part) {
        if(part.isUpdatePossible()) {
            this.canUpdate = true;
        }

        this.parts.add(part);
    }

    private void updateUpdatableState() {
        for(int i = 0; i < this.parts.size(); ++i) {
            LinePart part = this.parts.get(i);

            if(part.isUpdatePossible()) {
                this.canUpdate = true;
            }
        }
    }

    public String write(HollowPlayer player) {
        StringBuilder builder = new StringBuilder();

        for(LinePart part : this.parts) {
            builder.append(part.write(player));
        }

        return builder.toString();
    }

    public static DynamicLine compileLine(String original) {
        List<LinePart> parts = new ArrayList<>();

        for(int i = 0; i < original.length(); ++i) {
            char c = original.toCharArray()[i];

            int ind = ParseUtils.getFirstOpeningCharInStr(original, i);

            parts.add(new TextLinePart(original.substring(i, ind)));

            // TODO: add the missing line parts.
        }

        return new DynamicLine(parts);
    }

    public List<LinePart> getParts() {
        return this.parts;
    }

    public boolean canUpdate() {
        return this.canUpdate;
    }

    @Override
    public String toString() {
        return "BoardLine{" +
                "parts=" + parts +
                ", canUpdate=" + canUpdate +
                '}';
    }
}
