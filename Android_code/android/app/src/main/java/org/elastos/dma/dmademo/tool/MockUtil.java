package org.elastos.dma.dmademo.tool;

import org.elastos.dma.dmademo.bean.Game;

import java.util.ArrayList;
import java.util.List;

public class MockUtil {

    public static List<Game> mockData() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Game game = new Game();
            game.setName("马拉松");
            game.setProduction("关于2018上海国际马拉松赛违规参赛者的处罚公告. 2018/12/03. 2018上海国际马拉松赛前20名成绩公示");
            games.add(game);
        }
        return games;
    }

}
