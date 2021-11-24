package algonquin.cst2335.khab0018;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    boolean isTablet = false;
    MessageListFragment chatFragment;
    Button send;
    SQLiteDatabase db;
    ArrayList<MessageListFragment.ChatMessage> messages = new ArrayList<>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        //send = chatlayout.find
        isTablet = findViewById(R.id.detailsRoom) != null;
        chatFragment = new MessageListFragment();
        MyOpenHelper opener = new MyOpenHelper(this);
        db = opener.getWritableDatabase();
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx =  fMgr.beginTransaction();
        tx.add(R.id.fragmentRoom, chatFragment);
        tx.commit();
    }

    public void userClickedMessage(MessageListFragment.ChatMessage chatMessage, int position) {
        MessageDetailsFragment mdFragment = new MessageDetailsFragment(chatMessage, position);
        if(isTablet){
            getSupportFragmentManager().beginTransaction().replace(R.id.detailsRoom, mdFragment).commit(); }
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentRoom, mdFragment).commit();
        }
    }
    public void notifyMessageDeleted(MessageListFragment.ChatMessage chosenMessage, int chosenPosition) {
        chatFragment.notifyMessageDeleted(chosenMessage, chosenPosition);
    }


    public void notifyMessageDelete(MessageListFragment.ChatMessage chosenMessage, int chosenPosition) {
    }
}

