package com.adhyaapan.lord_buddha_kawasoti;

import android.os.Bundle;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;

/* loaded from: classes.dex */
public class MyNotificationActivity extends BaseActivity {
    String link;
    String msg;
    TextView myBody;
    TextView mySender;
    TextView myTitle;
    String remark;
    String sender;
    String title;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.adhyaapan.lord_buddha_kawasoti.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notification);
        Bundle extras = getIntent().getExtras();
        this.title = extras.getString("title");
        this.msg = extras.getString(NotificationCompat.CATEGORY_MESSAGE);
        this.sender = extras.getString("sender");
        this.remark = extras.getString("remark");
        this.link = extras.getString("link");
        TextView textView = (TextView) findViewById(R.id.titleView);
        this.myTitle = textView;
        textView.setText(this.title);
        TextView textView2 = (TextView) findViewById(R.id.msgView);
        this.myBody = textView2;
        textView2.setText(this.msg);
        TextView textView3 = (TextView) findViewById(R.id.senderView);
        this.mySender = textView3;
        textView3.setText(this.sender);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        try {
            MyFirebaseMessagingService.r.stop();
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        getWindow().setFlags(6816768, 6816768);
        super.onAttachedToWindow();
    }
}
