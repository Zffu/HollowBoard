package net.zffu.hollowboard.board.lines.parts;

import net.zffu.hollowboard.HollowPlayer;

/**
 * <p>A static text-based {@link LinePart}. Basically stores a text within it.</p>
 * @since 1.0.0
 */
public class TextLinePart implements LinePart {

    private final String text;

    /**
     * Creates a {@link TextLinePart} with the given text.
     * @param text
     */
    public TextLinePart(String text) {
        this.text = text;
    }

    @Override
    public String write(HollowPlayer player) {
        return this.text;
    }

    @Override
    public boolean isUpdatePossible() {
        return false;
    }

    @Override
    public String toString() {
        return "TextLinePart{" +
                "text='" + text + '\'' +
                '}';
    }
}
