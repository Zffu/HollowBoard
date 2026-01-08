package net.zffu.hollowboard.board;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DynamicLineComponent {

    public static final DynamicLineComponent empty = DynamicLineComponent.compileLine("");

    private List<LinePart> parts;
    private boolean canUpdate;

    public DynamicLineComponent() {
        this.parts = new ArrayList<>();
        this.canUpdate = false;
    }

    public DynamicLineComponent(List<LinePart> parts) {
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

    public String write(Player player) {
        StringBuilder builder = new StringBuilder();

        for(LinePart part : this.parts) {
            builder.append(part.write(player));
        }

        return builder.toString();
    }

    private static int getFirstCharInStr(String str, int index, char c) {
        for(int i = index; i < str.length(); ++i) {
            if(str.toCharArray()[i] == c) {
                return i;
            }
        }

        return str.length();
    }

    private static int getFirstOpeningCharInStr(String str, int index) {
        for(int i = index; i < str.length(); ++i) {
            char c = str.toCharArray()[i];

            if(c == '{' || c == '%') return i - 1;
        }

        return str.length();
    }

    public static DynamicLineComponent compileLine(String original) {
        List<LinePart> parts = new ArrayList<>();

        for(int i = 0; i < original.length(); ++i) {
            char c = original.toCharArray()[i];

            switch(c) {
                case '{':
                    int ind = getFirstCharInStr(original, i + 1, '}');
                    parts.add(new LinePart.SkriptVariable(original.substring(i + 1, ind)));

                    i = ind;
                    break;

                default:
                    ind = getFirstOpeningCharInStr(original, i);
                    parts.add(new LinePart.Text(original.substring(i, ind)));

                    i = ind;
                    break;
            }
        }

        return new DynamicLineComponent(parts);
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
