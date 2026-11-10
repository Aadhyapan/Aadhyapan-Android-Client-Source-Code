package com.adhyaapan.lord_buddha_kawasoti;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

/* loaded from: classes.dex */
public class BaseActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        super.onCreate(bundle);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        super.setContentView(i);
        View findViewById = findViewById(16908290);
        if (findViewById instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) findViewById;
            if (viewGroup.getChildCount() > 0) {
                applyInsetsToView(viewGroup.getChildAt(0));
            }
        }
    }

    private void applyInsetsToView(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.adhyaapan.lord_buddha_kawasoti.BaseActivity.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                int i = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top;
                int i2 = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
                View findViewById = view2.findViewById(BaseActivity.this.getResources().getIdentifier("toolbar", "id", BaseActivity.this.getPackageName()));
                if (findViewById instanceof Toolbar) {
                    findViewById.setPadding(findViewById.getPaddingLeft(), i, findViewById.getPaddingRight(), findViewById.getPaddingBottom());
                    return windowInsetsCompat;
                }
                view2.setPadding(0, i, 0, i2);
                return windowInsetsCompat;
            }
        });
        ViewCompat.requestApplyInsets(view);
    }
}
