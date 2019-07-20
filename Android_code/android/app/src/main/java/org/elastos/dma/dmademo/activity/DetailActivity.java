package org.elastos.dma.dmademo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import org.elastos.dma.dmademo.R;
import org.elastos.dma.dmademo.bean.Game;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String webHtml="<head><style>img{width:320px !important;max-width:320px !important;height:auto!important}</style></head>\n" +
                "<img src=\"http://image.shang-ma.com/public/20190305/pcdabanner.jpg\"/>\n" +
                "<h1>\n" +
                "    马拉松\n" +
                "</h1>\n" +
                "<h2>\n" +
                "    （长跑比赛项目）\n" +
                "</h2>\n" +
                "\n" +
                "<p>\n" +
                "    马拉松（Marathon）长跑是国际上非常普及的<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E9%95%BF%E8%B7%91/9089232\">长跑</a>比赛项目，全程距离26英里385码，折合为42.195公里（也有说法为42.193公里）。分全程马拉松（Full Marathon）、<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%8D%8A%E7%A8%8B%E9%A9%AC%E6%8B%89%E6%9D%BE/6195382\">半程马拉松</a>（Half Marathon）和<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%9B%9B%E5%88%86%E9%A9%AC%E6%8B%89%E6%9D%BE/6064636\">四分马拉松</a>（Quarter Marathon）三种。以全程马拉松比赛最为普及，一般提及马拉松，即指全程马拉松。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <img src=\"https://www.shmarathon.com/images/bancheng/jincai1.JPG\" id=\"photo-main2\"/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    最近，知名女作家蒋方舟的《马拉松是中产无声的广场舞》文章在互联网上备受争议，文中提到跑马拉松的以中产阶级居多，引发了部分读者不满。从相关数据来看，蒋方舟的文章与事实没有太大出入，中国田径协会公布的数据显示，2016年中国一共有328场马拉松赛事，参与人数达到280万人；而2011年，这两个数字分别是22和40万人，而参加城市马拉松需要进行大量的“投资”，比如购买价值不菲的专业运动装备、飞到不同城市甚至国家参赛，靠马拉松运营上市的智美体育集团董事局主席任文均也表示，“马拉松爱好者大多为有消费能力的中高收入人群。”\n" +
                "</p>\n" +
                "<p>\n" +
                "    <img src=\"https://www.shmarathon.com/images/bancheng/zzdf1.JPG\" id=\"photo-main1\"/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <img src=\"https://imgsrc.baidu.com/baike/pic/item/10dfa9ec8a13632735413e839a8fa0ec09fac7d6.jpg\"/><br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <img src=\"https://imgsrc.baidu.com/baike/pic/item/0823dd54564e925801637a5e9782d158ccbf4e2f.jpg\"/><br/>\n" +
                "</p>";
        WebView web_desc=findViewById(R.id.web_desc);
        web_desc.loadDataWithBaseURL(null,webHtml, "text/html", "uft-8",null);
        findViewById(R.id.tv_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.launch(DetailActivity.this);
            }
        });
    }


    public static void launch(Context context, Game game) {
        Intent intent = new Intent();
        intent.setClass(context, DetailActivity.class);
        context.startActivity(intent);
    }
    public void goOrder(View view){
        Intent intent = new Intent();
        intent.setClass(DetailActivity.this, OrderActivity.class);
        startActivity(intent);
    }
}
