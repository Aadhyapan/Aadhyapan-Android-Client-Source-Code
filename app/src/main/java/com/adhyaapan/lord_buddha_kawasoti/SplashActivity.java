package com.adhyaapan.lord_buddha_kawasoti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.common.internal.ImagesContract;

/* loaded from: classes.dex */
public class SplashActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.adhyaapan.lord_buddha_kawasoti.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(ImagesContract.URL, "file:///android_asset/splash.html");
        startActivity(intent);
        new Handler().postDelayed(new Runnable() { // from class: com.adhyaapan.lord_buddha_kawasoti.SplashActivity.1
            @Override // java.lang.Runnable
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 4000L);
    }
}
