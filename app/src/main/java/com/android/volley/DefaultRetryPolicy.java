package com.android.volley;

public class DefaultRetryPolicy {
    private int currentTimeoutMs;
    private int maxNumRetries;
    private float backoffMultiplier;

    public DefaultRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
        this.currentTimeoutMs = initialTimeoutMs;
        this.maxNumRetries = maxNumRetries;
        this.backoffMultiplier = backoffMultiplier;
    }

    public int getCurrentTimeoutMs() {
        return currentTimeoutMs;
    }

    public int getCurrentRetryCount() {
        return maxNumRetries;
    }

    public void retry(VolleyError error) throws VolleyError {
        maxNumRetries--;
        if (maxNumRetries < 0) {
            throw error;
        }
        currentTimeoutMs += (int) (currentTimeoutMs * backoffMultiplier);
    }
}
