package com.android.volley;

public class VolleyError extends Exception {
    public VolleyError() {
        super();
    }
    public VolleyError(String message) {
        super(message);
    }
    public VolleyError(Throwable cause) {
        super(cause);
    }
}
