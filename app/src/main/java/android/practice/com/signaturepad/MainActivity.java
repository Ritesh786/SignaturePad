package android.practice.com.signaturepad;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

     ImageView mgetsignbtn;
     public static final int SIGNATURE_CONSTANT = 2;
     Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mgetsignbtn = findViewById(R.id.getsign_btn);
        activity = MainActivity.this;
        mgetsignbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(activity)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void
                            onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(MainActivity.this, SignActivity.class);
                                activity.startActivityForResult(intent, SIGNATURE_CONSTANT);
                            }
                            @Override
                            public void
                            onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(activity, "Pls Give Permission", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap originalBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        String path;

        switch (requestCode) {
            case SIGNATURE_CONSTANT:
                if (resultCode != Activity.RESULT_OK) return;
                path = data.getStringExtra("path");
                BitmapFactory.decodeFile(path, options);
                options.inSampleSize = 0;//ImageUtil.inSampleSize(options, img.getControl().getW().intValue(), img.getControl().getH().intValue());
                options.inJustDecodeBounds = false;
                originalBitmap = BitmapFactory.decodeFile(path, options);
                mgetsignbtn.setImageBitmap(originalBitmap);
                break;
        }
    }
}
