package com.adhyaapan.lord_buddha_kawasoti;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.google.android.gms.common.internal.ImagesContract;

/* loaded from: classes.dex */
public class QrLogin extends BaseActivity {
    private String android_id;
    private Button buttonSubmit;
    private String cat;
    databaseHelper myDb;
    private EditText qrCode;

    @Override // com.adhyaapan.lord_buddha_kawasoti.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.login_student);
        this.cat = getIntent().getExtras().getString("cat");
        this.myDb = new databaseHelper(this);
        getList(this.cat);
        this.buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        this.qrCode = (EditText) findViewById(R.id.qrCode);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = QrLogin.this.qrCode.getText().toString().trim();
                QrLogin.this.qrCode.setText("");
                if (obj.equals("")) {
                    obj = "test_student_12345";
                }
                QrLogin.this.getAddress(obj);
            }
        });
    }

    public void getList(String str) {
        Cursor students = this.myDb.getStudents(str);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RL);
        float f = getResources().getDisplayMetrics().density;
        int i = 0;
        while (students.moveToNext()) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(14, 1);
            int i2 = (int) f;
            int i3 = i2 * 30;
            layoutParams.setMargins(i3, (int) ((380.0f * f) + (100.0f * f * i)), i3, i2 * 25);
            String string = students.getString(2);
            String str2 = "ID : " + students.getString(1);
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new RelativeSizeSpan(1.3f), 0, string.length(), 0);
            spannableString.setSpan(new ForegroundColorSpan((int) SupportMenu.CATEGORY_MASK), 0, string.length(), 0);
            spannableString.setSpan(new StyleSpan(1), 0, string.length(), 0);
            SpannableString spannableString2 = new SpannableString(str2);
            spannableString2.setSpan(new RelativeSizeSpan(0.9f), 0, str2.length(), 0);
            CharSequence concat = TextUtils.concat(spannableString, "\n\n", spannableString2);
            final TextView textView = new TextView(this);
            textView.setId(i);
            textView.setTag(students.getString(1));
            textView.setText(concat);
            textView.setBackgroundColor(-2367765);
            int i4 = i2 * 10;
            int i5 = i2 * 5;
            textView.setPadding(i4, i5, i5, i5);
            textView.setClickable(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    QrLogin.this.getAddress(String.valueOf(textView.getTag()));
                }
            });
            textView.setLayoutParams(layoutParams);
            relativeLayout.addView(textView, layoutParams);
            i++;
        }
    }

    public void getAddress(String str) {
        String string = getString(R.string.baseUrl);
        String string2 = getString(R.string.fake_id);
        this.android_id = this.myDb.getSettings("androidId");
        String str2 = string + "Mobile/" + this.cat + "/Index?id=" + str + "&device_id=" + this.android_id + "&fakeId=" + string2;
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(ImagesContract.URL, str2);
        startActivity(intent);
    }
}
