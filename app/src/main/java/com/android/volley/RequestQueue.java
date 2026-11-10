package com.android.volley;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestQueue {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public void add(final Request<?> request) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(request.getUrl());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    
                    if (request.getMethod() == Request.Method.POST) {
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        Map<String, String> params = request.getParams();
                        if (params != null && !params.isEmpty()) {
                            StringBuilder postData = new StringBuilder();
                            for (Map.Entry<String, String> entry : params.entrySet()) {
                                if (postData.length() != 0) postData.append('&');
                                postData.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                                postData.append('=');
                                postData.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
                            }
                            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                            OutputStream os = conn.getOutputStream();
                            os.write(postDataBytes);
                            os.flush();
                            os.close();
                        }
                    } else {
                        conn.setRequestMethod("GET");
                    }

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        
                        final String resString = response.toString();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ((Request<String>) request).deliverResponse(resString);
                            }
                        });
                    } else {
                        final VolleyError error = new VolleyError("HTTP error code: " + responseCode);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                request.deliverError(error);
                            }
                        });
                    }
                } catch (final Exception e) {
                    final VolleyError error = new VolleyError(e);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            request.deliverError(error);
                        }
                    });
                }
            }
        });
    }
}
