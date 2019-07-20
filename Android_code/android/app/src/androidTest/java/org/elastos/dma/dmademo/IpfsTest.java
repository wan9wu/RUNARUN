package org.elastos.dma.dmademo;

import android.support.test.runner.AndroidJUnit4;

import org.elastos.dma.service.storage.ipfs.IpfsService;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(AndroidJUnit4.class)
public class IpfsTest {

    @Test
    public void testStorage() throws IOException {


        String htpUrl = "http://47.105.136.158:8080";
        IpfsService instance = IpfsService.getInstance("/ip4/47.105.136.158/tcp/5001", htpUrl);//此方法需要异步
       //写字符
        String hash = instance.putString("{\"nickName\":\"小明\",\"mobile\":\"136*****1\"}");
        System.out.println("存入数据Hash = " + hash);
//读字符
        String json = instance.getString(hash);
        System.out.println("读出的json = " + json);
//根据hash 串url
        String httpUrl=IpfsService.getHttpUrlByHash(htpUrl,"QmbnuLCpoTtW8zM2ykkjfwCywtrbY1JEv4zHw8rVvfqSyV");
        System.out.println("httpUrlByHash = " + httpUrl);

    }
}
