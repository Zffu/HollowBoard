package net.zffu.hollowboard.internals;

/**
 * <p>The controller for the internal packet scoreboard.</p>
 * @since 1.0.0
 */
public interface PacketScoreboardController {

    void setLine(int index, String line);
    void remove(int index);

    void setTitle(String title);
    String getTitle();

    String getText(int index);

    void show();
    void hide();

    void clear();

}
