package com.android.volley;

public interface Response {
    public interface Listener<T> {
        void onResponse(T response);
    }

    public interface ErrorListener {
        void onErrorResponse(VolleyError error);
    }
}
