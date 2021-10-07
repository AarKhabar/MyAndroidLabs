package algonquin.cst2335.khab0018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w( TAG, "In onCreate() - Loading Widgets" );
        Button loginButton = findViewById(R.id.button);
        EditText emailEditText = findViewById(R.id.EmailAddress);
        //Intent fromPrevious = getIntent();
        loginButton.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );
            startActivity( nextPage);
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "In onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "In onPause() - The application no longer responds to user input" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( TAG, "In onDestroy() - Any memory used by the application is freed" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( TAG, "In onStop() - The application is no longer visibl" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "In onStart() - The application is now visible on screen" );
    }


}