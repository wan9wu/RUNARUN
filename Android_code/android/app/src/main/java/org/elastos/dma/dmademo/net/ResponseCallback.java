package org.elastos.dma.dmademo.net;

public interface ResponseCallback<T> {

    void onSuccess(T res);

    void onError(String err);

}
