package com.adg.gateway.utils;

// Generic Request Class
public class GenericRequest<T> {
    private T data;

    public GenericRequest() {
    }

    public GenericRequest(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GenericRequest{" +
                "data=" + data +
                '}';
    }
}
