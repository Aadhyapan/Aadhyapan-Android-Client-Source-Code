package com.adhyaapan.lord_buddha_kawasoti;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class BusActivity extends Activity {
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private BarcodeCallback callback = new BarcodeCallback() { // from class: com.adhyaapan.lord_buddha_kawasoti.BusActivity.1
        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void possibleResultPoints(List<ResultPoint> list) {
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void barcodeResult(BarcodeResult barcodeResult) {
            if (barcodeResult.getText() == null || barcodeResult.getText().equals(BusActivity.this.lastText)) {
                return;
            }
            BusActivity.this.lastText = barcodeResult.getText();
            BusActivity.this.qrCheck.setTypeface(Typeface.defaultFromStyle(1));
            if (BusActivity.this.lastText.equals("aCq-h92q-wjzg") || BusActivity.this.lastText.equals("aCq-3z1i-grlk")) {
                BusActivity.this.qrCheck.setTextColor(-16711936);
                BusActivity.this.qrCheck.setText(BusActivity.this.getResources().getString(R.string.tick));
            } else {
                BusActivity.this.qrCheck.setTextColor(SupportMenu.CATEGORY_MASK);
                BusActivity.this.qrCheck.setText("X");
            }
            BusActivity.this.beepManager.playBeepSoundAndVibrate();
            ((ImageView) BusActivity.this.findViewById(R.id.barcodePreview)).setImageBitmap(barcodeResult.getBitmapWithResultPoints(InputDeviceCompat.SOURCE_ANY));
        }
    };
    private String lastText;
    private TextView qrCheck;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bus);
        this.qrCheck = (TextView) findViewById(R.id.qrCheck);
        this.barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
        this.barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)));
        this.barcodeView.initializeFromIntent(getIntent());
        this.barcodeView.decodeContinuous(this.callback);
        this.beepManager = new BeepManager(this);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.barcodeView.resume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.barcodeView.pause();
    }

    public void triggerScan(View view) {
        this.barcodeView.decodeSingle(this.callback);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.barcodeView.onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }
}
