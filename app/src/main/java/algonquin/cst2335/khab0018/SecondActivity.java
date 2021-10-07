package algonquin.cst2335.khab0018;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView next = findViewById(R.id.textView5);
        Intent fromPrevious = getIntent();
        EditText phoneNumber = findViewById(R.id.editTextPhone);
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        next.setText("Welcome back "+emailAddress);

        SharedPreferences preferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phone =  preferences.getString("phone", "");
        phoneNumber.setText(phone);

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
            startActivity(call);
        });

        Button btn2 = findViewById(R.id.button3);
        ImageButton img = findViewById(R.id.imageButton);
        File file = new File( getFilesDir(), "filename");
        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile("Picture.png");
            img.setImageBitmap(theImage);
        }
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        prefs.getString("VariableName", "defaultValue");
        String emailAddress2 = prefs.getString("LoginName", "");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", "value");
        editor.apply();
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            File MyData = getFilesDir();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            img.setImageBitmap(thumbnail);


                            FileOutputStream fOut = null;
                            try {
                                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });


        btn2.setOnClickListener(clk -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText phoneNumber = findViewById(R.id.editTextPhone);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phone =  phoneNumber.getText().toString();


        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone", phone);
        editor.apply();
    }
}