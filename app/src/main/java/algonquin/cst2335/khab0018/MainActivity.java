package algonquin.cst2335.khab0018;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mytext = findViewById(R.id.textview);
        Button btn = findViewById(R.id.myButton);
        EditText myedit = findViewById(R.id.editmessage);
        //String editString = myedit.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString =mytext.getText().toString();
                btn.setOnClickListener(vw -> { mytext.setText("Your edit text has: " + myedit.getText());
                });
            }
        });

    }

}