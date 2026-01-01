package net.zffu.hollowboard.tests;

import net.zffu.hollowboard.board.BoardLine;
import net.zffu.hollowboard.board.LinePart;

public class BoardLineCompileTest {

    public static void main(String[] args) {
        BoardLine line = BoardLine.compileLine("Hello this is a test line! {bob::player}!!");

        for(LinePart part : line.getParts()) {
            System.out.println(part.toString());
        }

    }

}
