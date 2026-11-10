package com.adhyaapan.lord_buddha_kawasoti;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseIdService";
    private static final String TOPIC_GLOBAL = "global";
    static Ringtone r;
    String link = "";
    String msg;
    databaseHelper myDb;
    String remark;
    String sender;
    String title;

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) {
        super.onNewToken(str);
        sendRegistrationToServer(str);
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Uri defaultUri;
        this.myDb = new databaseHelper(this);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            this.title = data.get("title");
            this.msg = data.get(NotificationCompat.CATEGORY_MESSAGE);
            this.sender = data.get("sender");
            this.remark = data.get("remark");
            this.link = data.get("link");
            if (this.remark.equals("Bus")) {
                String settings = this.myDb.getSettings("pickUp");
                String settings2 = this.myDb.getSettings("dropOff");
                String settings3 = this.myDb.getSettings("pick_time");
                String settings4 = this.myDb.getSettings("drop_time");
                String settings5 = this.myDb.getSettings("week_days");
                int i = Calendar.getInstance().get(11);
                int parseInt = Integer.parseInt(settings3);
                int parseInt2 = Integer.parseInt(settings4);
                if ((((i != parseInt - 1 && i != parseInt) || !settings.equals(this.link)) && ((i != parseInt2 - 1 && i != parseInt2) || !settings2.equals(this.link))) || settings5.indexOf(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis()))) == -1) {
                    return;
                }
            }
        } else if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Intent intent = new Intent(this, MyNotificationActivity.class);
        intent.putExtra("title", this.title);
        intent.putExtra(NotificationCompat.CATEGORY_MESSAGE, this.msg);
        intent.putExtra("sender", this.sender);
        intent.putExtra("remark", this.remark);
        intent.putExtra("link", this.link);
        intent.setFlags(268468224);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "stc_notification_1");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(this.title);
        builder.setContentText(this.msg);
        builder.setContentIntent(activity);
        builder.setPriority(2);
        builder.setAutoCancel(true);
        ((NotificationManager) getSystemService("notification")).notify(1, builder.build());
        try {
            if (this.remark.equals("Bus")) {
                defaultUri = RingtoneManager.getDefaultUri(1);
            } else {
                defaultUri = RingtoneManager.getDefaultUri(2);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), defaultUri);
            r = ringtone;
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    public void sendRegistrationToServer(final String str) {
        Log.d("FCMTOKEN", "FCM TOKEN: " + str);
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        String string = getString(R.string.baseUrl);
        StringRequest stringRequest = new StringRequest(1, string + "Mobile/Register", new Response.Listener<String>() { // from class: com.adhyaapan.lord_buddha_kawasoti.MyFirebaseMessagingService.1
            @Override // com.android.volley.Response.Listener
            public void onResponse(String str2) {
                str2.equals("Success");
            }
        }, new Response.ErrorListener() { // from class: com.adhyaapan.lord_buddha_kawasoti.MyFirebaseMessagingService.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) { // from class: com.adhyaapan.lord_buddha_kawasoti.MyFirebaseMessagingService.3
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("token", str);
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1.0f));
        newRequestQueue.add(stringRequest);
    }
}
