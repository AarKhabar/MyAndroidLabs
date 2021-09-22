package algonquin.cst2335.khab0018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mytext = findViewById(R.id.textview);
        Button btn = findViewById(R.id.myButton);
        EditText myedit = findViewById(R.id.editmessage);
        CheckBox checkBox = findViewById(R.id.check);
        RadioButton radioButton = findViewById(R.id.radio);
        Switch sw = findViewById(R.id.sw);
        //String editString = myedit.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString =mytext.getText().toString();
                btn.setOnClickListener(vw -> { mytext.setText("Your edit text has: " + myedit.getText());
                });
            }
        });

        sw.setOnCheckedChangeListener( (b, c) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the Switch and it is now:" + c;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        checkBox.setOnCheckedChangeListener( (b, c) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the Checkbox and it is now:" + c;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        });

        radioButton.setOnCheckedChangeListener((b, c) -> {
            Context context = getApplicationContext();
            CharSequence text = "You clicked on the Radiobutton and it is now:" + c;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

    }

}