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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    RecyclerView chatList;
    Button send;
    Button receive;
    EditText text;
    MyChatAdapter chatAdapter;
    ArrayList<ChatMessage> messages = new ArrayList<>();

    SQLiteDatabase db;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);
        send =  findViewById(R.id.button4);
        receive = findViewById(R.id.button5);
        text = findViewById(R.id.editTextTextPersonName);
        chatList.setAdapter(new MyChatAdapter());

        MyOpenHelper opener = new MyOpenHelper(this);
        db = opener.getWritableDatabase();

            send.setOnClickListener(click ->{
                String whatIsTyped = text.getText().toString();
                Date timeNow = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

                String currentDateandTime = sdf.format(timeNow);
                ChatMessage cm = new ChatMessage(text.getText().toString(),currentDateandTime ,1);
                ContentValues newRow = new ContentValues();
                newRow.put(MyOpenHelper.col_message, cm.getMessage());
                newRow.put(MyOpenHelper.col_send_receive, cm.getSendOrReceive());
                newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());
                db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
                long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
                cm.setId(newId);

                messages.add(cm);

                text.setText("");

                chatAdapter.notifyItemInserted(messages.size() - 1);

            });
            receive.setOnClickListener(click ->{
            String whatIsTyped = text.getText().toString();
            Date timeNow = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);
                ChatMessage cm = new ChatMessage(text.getText().toString(),currentDateandTime ,2);
                ContentValues newRow = new ContentValues();
                newRow.put(MyOpenHelper.col_message, cm.getMessage());
                newRow.put(MyOpenHelper.col_send_receive, cm.getSendOrReceive());
                newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());
                db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
                long newId = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);
                cm.setId(newId);

                messages.add(cm);

            text.setText("");

            chatAdapter.notifyItemInserted(messages.size() - 1);

            });
        Cursor results = db.rawQuery("SELECT * FROM " + MyOpenHelper.TABLE_NAME, null);

        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);

        while(results.moveToNext()) {
            long id = results.getInt(_idCol);
            String message = results.getString(messageCol);
            String time = results.getString(timeCol);
            int sendOrReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message, sendOrReceive, time, id));
        }

        chatAdapter = new MyChatAdapter();
        chatList.setAdapter( chatAdapter );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(layoutManager);

    }

        private class MyRowViews extends RecyclerView.ViewHolder{
            TextView messageText;
            TextView timeText;
            public MyRowViews(View itemView) {
                super(itemView);
                itemView.setOnClickListener(click -> {
                            int position = getAbsoluteAdapterPosition();
                            ChatMessage removeMessage = messages.get(position);
                            MyRowViews newRow = chatAdapter.onCreateViewHolder(null, chatAdapter.getItemViewType(position));

                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                    builder.setMessage("Do you want to delete this message:" + messageText.getText())
                            .setTitle("Question")
                            .setNegativeButton("No", (dialog, cl)->{})
                            .setPositiveButton("Yes", (dialog, cl)-> {
                                messages.remove(position);
                                chatAdapter.notifyItemRemoved(position);
                                Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                        .setAction("Undo", clk ->{
                                         messages.add(position, removeMessage);
                                         chatAdapter.notifyItemInserted(position);
                                        }).show();


                            }).create().show();

                        });
                messageText = itemView.findViewById(R.id.message);
                timeText = itemView.findViewById(R.id.time);
            }
        }
             private class MyChatAdapter extends RecyclerView.Adapter< MyRowViews > {

            @Override

            public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                int layoutID;
                if (viewType == 1 ) //Send
                    layoutID = R.layout.sent_message;
                else //Receive
                    layoutID = R.layout.receive_message;

                View loadedRow = inflater.inflate(layoutID,parent,false);

                return new MyRowViews(loadedRow);
            }

            @Override
            public int getItemViewType(int position) {

                return messages.get(position).getSendOrReceive();
            }

            @Override
            public void onBindViewHolder(MyRowViews holder, int position) {
                ChatMessage thisRow = messages.get(position);;
                holder.messageText.setText(thisRow.getMessage());
                holder.timeText.setText(thisRow.getTimeSent());
            }

            @Override
            public int getItemCount() {

                return messages.size();
            }
        }
    private class ChatMessage{
        String message;
        int sendOrReceive;
        String timeSent;
        long id;

        public ChatMessage(String message, String timeSent,int sendOrReceive) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public ChatMessage(String message,int sendOrReceive,String timeSent, long id) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }


        public String getMessage() {

            return message;
        }

        public int getSendOrReceive() {

            return sendOrReceive;
        }

        public String getTimeSent() {

            return timeSent;
        }

        public long getId() {
            return id;
        }

        public void setId(long l) {
            id = l;
        }
    }
}

