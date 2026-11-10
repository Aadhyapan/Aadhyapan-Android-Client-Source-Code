package com.android.volley.toolbox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class StringRequest extends Request<String> {
    private final Response.Listener<String> listener;
    private final Response.ErrorListener errorListener;

    public StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    protected void deliverResponse(String response) {
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }
}
