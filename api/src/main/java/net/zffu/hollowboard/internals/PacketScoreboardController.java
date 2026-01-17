package net.zffu.hollowboard.internals;

/**
 * <p>The controller for the internal packet scoreboard.</p>
 * @since 1.0.0
 */
public interface PacketScoreboardController {

    /**
     * <p>Sets the given scoreboard line.</p>
     * @param index the line index, 0 is on top.
     * @param line the line text.
     */
    void setLine(int index, String line);

    /**
     * <p>Removes the given line.</p>
     * @param index the line index, 0 is on top.
     */
    void remove(int index);

    /**
     * <p>Sets the scoreboard title</p>
     * @param title the title.
     */
    void setTitle(String title);

    /**
     * <p>Gets the scoreboard title</p>
     */
    String getTitle();

    String getText(int index);

    void show();
    void hide();

    void clear();

}
