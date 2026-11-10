package com.adhyaapan.lord_buddha_kawasoti;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class WebActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long DOUBLE_PRESS_INTERVAL = 500000000;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private long lastPressTime;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessages;
    databaseHelper myDb;
    private String url;
    private WebView webView;

    /* loaded from: classes.dex */
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public void playSound() {
            ((AudioManager) this.mContext.getSystemService("audio")).playSoundEffect(0);
        }

        @JavascriptInterface
        public void remember(String str, String str2, String str3, String str4) {
            if (str4.equals("true")) {
                if (WebActivity.this.myDb.getStudent(str).getCount() == 0) {
                    WebActivity.this.myDb.insertStudent(str, str2, str3);
                    WebActivity.this.subscribTopic(str3, str, str4);
                    return;
                }
                return;
            }
            WebActivity.this.myDb.deleteStudent(str);
            WebActivity.this.subscribTopic(str3, str, str4);
        }

        @JavascriptInterface
        public void getTransport(String str) {
            final String str2 = "javascript:getUrl('/Mobile/Student/Transport?id=" + str + "&gpsId=" + WebActivity.this.myDb.getSettings("gpsId") + "&pickUp=" + WebActivity.this.myDb.getSettings("pickUp") + "&dropOff=" + WebActivity.this.myDb.getSettings("dropOff") + "&pick_time=" + WebActivity.this.myDb.getSettings("pick_time") + "&drop_time=" + WebActivity.this.myDb.getSettings("drop_time") + "&week_days=" + WebActivity.this.myDb.getSettings("week_days") + "')";
            WebActivity.this.webView.post(new Runnable() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.WebAppInterface.1
                @Override // java.lang.Runnable
                public void run() {
                    WebActivity.this.webView.loadUrl(str2);
                }
            });
        }

        @JavascriptInterface
        public void saveTransport(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            String settings = WebActivity.this.myDb.getSettings("pickUp");
            String settings2 = WebActivity.this.myDb.getSettings("dropOff");
            if (!settings.equals("") && !settings.equals(str3)) {
                WebActivity.this.subscribTopic("Transport", settings, "false");
            }
            if (!settings2.equals("") && !settings2.equals(str4)) {
                WebActivity.this.subscribTopic("Transport", settings2, "false");
            }
            final String str8 = "javascript:getUrl('/Mobile/Student/Transport?id=" + str + "&gpsId=" + str2 + "&pickUp=" + str3 + "&dropOff=" + str4 + "&pick_time=" + str5 + "&drop_time=" + str6 + "&week_days=" + str7 + "')";
            WebActivity.this.webView.post(new Runnable() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.WebAppInterface.2
                @Override // java.lang.Runnable
                public void run() {
                    WebActivity.this.webView.loadUrl(str8);
                }
            });
            WebActivity.this.myDb.updateSettings("gpsId", str2);
            WebActivity.this.myDb.updateSettings("pickUp", str3);
            WebActivity.this.myDb.updateSettings("dropOff", str4);
            WebActivity.this.myDb.updateSettings("pick_time", str5);
            WebActivity.this.myDb.updateSettings("drop_time", str6);
            WebActivity.this.myDb.updateSettings("week_days", str7);
            Toast.makeText(WebActivity.this, "Saved !!", 1).show();
            if (str2.equals("")) {
                return;
            }
            if (!str3.equals("")) {
                WebActivity.this.subscribTopic("Transport", str3, "true");
            }
            if (str4.equals("")) {
                return;
            }
            WebActivity.this.subscribTopic("Transport", str4, "true");
        }
    }

    public void subscribTopic(final String str, final String str2, String str3) {
        if (str3.equals("true")) {
            FirebaseMessaging.getInstance().subscribeToTopic(str2).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.1
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public void onComplete(Task<Void> task) {
                    String str4;
                    if (!task.isSuccessful()) {
                        str4 = "Subscription Failed";
                    } else {
                        str4 = "Subscribed " + str2;
                        WebActivity.this.sendSubscriptionToServer(str, str2, "true");
                    }
                    Log.d("Subscribtion", str4);
                    Toast.makeText(WebActivity.this, str4, 0).show();
                }
            });
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(str2).addOnCompleteListener(new OnCompleteListener<Void>() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.2
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public void onComplete(Task<Void> task) {
                    String str4;
                    if (!task.isSuccessful()) {
                        str4 = "UnSubscription Failed ";
                    } else {
                        str4 = "UnSubscribed " + str2;
                        WebActivity.this.sendSubscriptionToServer(str, str2, "false");
                    }
                    Log.d("UnSubscribtion", str4);
                    Toast.makeText(WebActivity.this, str4, 0).show();
                    WebActivity.this.startActivity(Build.VERSION.SDK_INT >= 33 ? new Intent(WebActivity.this, MainActivity.class) : null);
                    WebActivity.this.finish();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.adhyaapan.lord_buddha_kawasoti.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web);
        this.myDb = new databaseHelper(this);
        this.url = getIntent().getExtras().getString(ImagesContract.URL);
        WebView webView = (WebView) findViewById(R.id.webview);
        this.webView = webView;
        webView.getSettings().setJavaScriptEnabled(true);
        this.webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setCacheMode(-1);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.getSettings().setDisplayZoomControls(false);
        this.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        this.webView.setSoundEffectsEnabled(true);
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.3
            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                WebActivity.this.webView.loadUrl("file:///android_asset/errorPage.html");
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                if (str.contains("tel:")) {
                    Intent intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse(str));
                    WebActivity.this.startActivity(intent);
                    return true;
                }
                webView2.loadUrl(str);
                return true;
            }
        });
        if (!isNetworkAvailable()) {
            this.webView.getSettings().setCacheMode(1);
        }
        this.webView.loadUrl(this.url);
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.4
            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView2, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                WebActivity.this.mUploadMessages = valueCallback;
                openImageChooser();
                return true;
            }

            private void openImageChooser() {
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FolderName");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    WebActivity.this.mCapturedImageURI = Uri.fromFile(new File(file + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra("output", WebActivity.this.mCapturedImageURI);
                    Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                    intent2.addCategory("android.intent.category.OPENABLE");
                    intent2.setType("image/*");
                    intent2.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                    Intent createChooser = Intent.createChooser(intent2, "Image Chooser");
                    createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", new Parcelable[]{intent});
                    WebActivity.this.startActivityForResult(createChooser, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            ValueCallback<Uri> valueCallback = this.mUploadMessage;
            if (valueCallback == null && this.mUploadMessages == null) {
                return;
            }
            if (valueCallback != null) {
                handleUploadMessage(i, i2, intent);
            } else if (this.mUploadMessages != null) {
                handleUploadMessages(i, i2, intent);
            }
        }
    }

    private void handleUploadMessage(int i, int i2, Intent intent) {
        Uri data = null;
        if (i2 == -1) {
            try {
                data = intent == null ? this.mCapturedImageURI : intent.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mUploadMessage.onReceiveValue(data);
            this.mUploadMessage = null;
            return;
        }
        this.mUploadMessage.onReceiveValue(data);
        this.mUploadMessage = null;
    }

    private void handleUploadMessages(int i, int i2, Intent intent) {
        Uri[] uriArr;
        if (i2 != -1) {
            uriArr = null;
        } else {
            try {
                if (intent != null) {
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null) {
                        uriArr = new Uri[clipData.getItemCount()];
                        for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                            try {
                                uriArr[i3] = clipData.getItemAt(i3).getUri();
                            } catch (Exception e) {
                                e = e;
                                e.printStackTrace();
                                this.mUploadMessages.onReceiveValue(uriArr);
                                this.mUploadMessages = null;
                            }
                        }
                    } else {
                        uriArr = null;
                    }
                    if (dataString != null) {
                        uriArr = new Uri[]{Uri.parse(dataString)};
                    }
                } else {
                    uriArr = new Uri[]{this.mCapturedImageURI};
                }
            } catch (Exception e2) {
                Exception e = e2;
                e.printStackTrace();
                uriArr = null;
            }
        }
        this.mUploadMessages.onReceiveValue(uriArr);
        this.mUploadMessages = null;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_settings) {
            this.webView.loadUrl("javascript:showMenu()");
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        this.webView.loadUrl(this.url);
        long nanoTime = System.nanoTime();
        if (nanoTime - this.lastPressTime <= DOUBLE_PRESS_INTERVAL) {
            super.onBackPressed();
        }
        this.lastPressTime = nanoTime;
    }

    public void sendSubscriptionToServer(final String str, final String str2, final String str3) {
        Log.d("topic", "Subscription Topic: " + str2);
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        String string = getString(R.string.baseUrl);
        StringRequest stringRequest = new StringRequest(1, string + "Mobile/Subscription", new Response.Listener<String>() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.5
            @Override // com.android.volley.Response.Listener
            public void onResponse(String str4) {
                if (str4.equals("Success")) {
                    Toast.makeText(WebActivity.this, "Done !!", 0).show();
                } else {
                    Log.i("Subscription : ", str4);
                }
            }
        }, new Response.ErrorListener() { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.6
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) { // from class: com.adhyaapan.lord_buddha_kawasoti.WebActivity.7
            @Override // com.android.volley.Request
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("cat", str);
                hashMap.put("topic", str2);
                hashMap.put("subscription", str3);
                hashMap.put("device_id", WebActivity.this.myDb.getSettings("android_id"));
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1.0f));
        newRequestQueue.add(stringRequest);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        this.webView.onPause();
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.webView.onPause();
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        this.webView.onResume();
        super.onResume();
    }
}
