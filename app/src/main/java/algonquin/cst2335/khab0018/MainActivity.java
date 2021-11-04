package algonquin.cst2335.khab0018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Aar Khabar
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * This holds the text to the centre of the screen
     */
    private TextView tv = null;
    /**
     * This holds the password to the centre of the screen
     */
    private EditText et = null;
    /**
     * This holds the button to the centre of the screen
     */
    private Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.textView);
        EditText et = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener( clk ->{
            String password = et.getText().toString();
            checkPasswordComplexity(password);
            tv.setText("You shall not pass!");
        });
    }

    /**
     * This function checks if the password meets all the requirement
     *
     * @param pw the password that the user will type
     * @return it will return true if it met the requirements or else it will return false
     */
    boolean checkPasswordComplexity(String pw){
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i = 0; i < pw.length(); i++){
            char c = pw.charAt(i);
            if(Character.isLowerCase(c))
                foundLowerCase = true;
            else if(Character.isUpperCase(c)){
               foundUpperCase = true;
            }
            else if(Character.isDigit(c)){
                foundNumber = true;
            }
            else if( isSpecialCharacter(c) )
            {
                foundSpecial = true;
            }
        }
        if(!foundUpperCase) {
            Context context = getApplicationContext();
            CharSequence text = "Missing upper case";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }

        else if(!foundLowerCase) {
            Context context = getApplicationContext();
            CharSequence text = "Missing lower case";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }

        else if(!foundNumber) {
            Context context = getApplicationContext();
            CharSequence text = "Missing a number";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }

        else if(!foundSpecial) {
            Context context = getApplicationContext();
            CharSequence text = "Missing a special character";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;}

        else

            return true;
    }

    /**
     * This function will check if the password has any of these characters
     * @param c The character that will be checked
     * @return will return true if it has any of these character or else it will return false
     */
    boolean isSpecialCharacter(char c) {
        switch(c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}