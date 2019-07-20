package org.elastos.dma.dmademo.tool;

import org.elastos.dma.dmademo.bean.Game;
import org.elastos.dma.dmademo.bean.Message;

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

    public static List<Message> mockMessage() {
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message message = new Message();
            message.setTitle("我的消息");
            message.setContent("关于2018上海国际马拉松赛违规参赛者的处罚公告");
        }
        return messageList;
    }

}
