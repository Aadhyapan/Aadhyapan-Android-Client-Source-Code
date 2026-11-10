package com.adhyaapan.lord_buddha_kawasoti;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

/* loaded from: classes.dex */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1240;
    String[] appPermissions = {"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_MEDIA_IMAGES", "android.permission.POST_NOTIFICATIONS"};
    private Button buttonSchool;
    private Button buttonStudent;
    private Button buttonTeacher;
    private Button btnSchoolTest;
    private Button btnTeacherTest;
    private Button btnStudentTest;
    databaseHelper myDb;

    public void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") + ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_MEDIA_IMAGES", "android.permission.POST_NOTIFICATIONS"}, MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.adhyaapan.lord_buddha_kawasoti.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() { // from class: com.adhyaapan.lord_buddha_kawasoti.MainActivity.1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public void onComplete(Task<String> task) {
            }
        });
        this.myDb = new databaseHelper(this);
        this.buttonStudent = (Button) findViewById(R.id.student);
        this.buttonSchool = (Button) findViewById(R.id.school);
        this.buttonTeacher = (Button) findViewById(R.id.teacher);
        this.btnSchoolTest = (Button) findViewById(R.id.btnSchoolTest);
        this.btnTeacherTest = (Button) findViewById(R.id.btnTeacherTest);
        this.btnStudentTest = (Button) findViewById(R.id.btnStudentTest);

        this.buttonStudent.setOnClickListener(this);
        this.buttonSchool.setOnClickListener(this);
        this.buttonTeacher.setOnClickListener(this);
        this.btnSchoolTest.setOnClickListener(this);
        this.btnTeacherTest.setOnClickListener(this);
        this.btnStudentTest.setOnClickListener(this);

        TextView tvFooter = findViewById(R.id.tvFooterDeveloper);
        tvFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ramanchapagain.github.io/"));
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        String string = getString(R.string.baseUrl);
        String fakeId = getString(R.string.fake_id);
        String androidId = this.myDb.getSettings("androidId");
        int id = view.getId();
        if (id == R.id.school || id == R.id.btnSchoolTest) {
            String str = string + "login";
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(ImagesContract.URL, str);
            startActivity(intent);
        } else if (id == R.id.student) {
            Intent intent2 = new Intent(this, QrLogin.class);
            intent2.putExtra("cat", "Student");
            startActivity(intent2);
        } else if (id == R.id.btnStudentTest) {
            String url = string + "Mobile/Student/Index?id=test_student_12345&device_id=" + androidId + "&fakeId=" + fakeId;
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(ImagesContract.URL, url);
            startActivity(intent);
        } else if (id == R.id.teacher) {
            Intent intent3 = new Intent(this, QrLogin.class);
            intent3.putExtra("cat", "Teacher");
            startActivity(intent3);
        } else if (id == R.id.btnTeacherTest) {
            String url = string + "Mobile/Teacher/Index?id=test_teacher_12345&device_id=" + androidId + "&fakeId=" + fakeId;
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(ImagesContract.URL, url);
            startActivity(intent);
        }
    }
}
