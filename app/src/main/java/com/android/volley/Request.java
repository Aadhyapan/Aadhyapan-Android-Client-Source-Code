package com.android.volley;

import java.util.Map;

public abstract class Request<T> {
    private int method;
    private String url;
    private DefaultRetryPolicy retryPolicy;

    public static interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

    public Request(int method, String url, Response.ErrorListener listener) {
        this.method = method;
        this.url = url;
    }

    public int getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    protected Map<String, String> getParams() {
        return null;
    }

    public void setRetryPolicy(DefaultRetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public DefaultRetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    abstract protected void deliverResponse(T response);
    abstract public void deliverError(VolleyError error);
}
