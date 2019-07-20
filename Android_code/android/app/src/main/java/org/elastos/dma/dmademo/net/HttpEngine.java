package org.elastos.dma.dmademo.net;

import java.io.IOException;
import java.util.Map;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpEngine {

    private static OkHttpClient sClient = new OkHttpClient();
    private static String sBaseURL = "";

    public static Response sendGetRequest(String api, Map<String, String> params, ResponseCallback callback) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(sBaseURL + api).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();
        try {
            return sClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
