package android.practice.com.signaturelibrary;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignActivity extends AppCompatActivity {

    CanvasView customCanvas;
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/UserSignature/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";
    File file;
    ColorPickerDialog colorPickerDialog;
    TextView mcolorshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mcolorshow = findViewById(R.id.colorshow_txt);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        mcolorshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colorPickerDialog = ColorPickerDialog.createColorPickerDialog(SignActivity.this);
                colorPickerDialog.show();
                colorPickerDialog.hideHexaDecimalValue();

                colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                    @Override
                    public void onColorPicked(int color, String hexVal) {
                        mcolorshow.setBackgroundColor(color);
                        customCanvas.setcolor(color);
                    }
                });
            }
        });

    }
    public void clearCanvas(View v) {
        customCanvas.clear();
    }


    public void save(View v) {
        customCanvas.save(customCanvas,StoredPath);
        Intent _intent_Result = new Intent();
        _intent_Result.putExtra("path",StoredPath);
        setResult(RESULT_OK, _intent_Result);
        finish();
    }


}
