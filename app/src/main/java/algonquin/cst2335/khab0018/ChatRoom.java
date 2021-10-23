package algonquin.cst2335.khab0018;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);
        send =  findViewById(R.id.button4);
        receive = findViewById(R.id.button5);
        text = findViewById(R.id.editTextTextPersonName);
        chatList.setAdapter(new MyChatAdapter());


            send.setOnClickListener(click ->{
                String whatIsTyped = text.getText().toString();
                Date timeNow = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

                String currentDateandTime = sdf.format(timeNow);

                messages.add(new ChatMessage(whatIsTyped, 1, currentDateandTime));

                text.setText("");

                chatAdapter.notifyItemInserted(messages.size() - 1);

            });
            receive.setOnClickListener(click ->{
            String whatIsTyped = text.getText().toString();
            Date timeNow = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);

            messages.add(new ChatMessage(whatIsTyped,  2, currentDateandTime));

            text.setText("");

            chatAdapter.notifyItemInserted(messages.size() - 2);

            });
        chatAdapter = new MyChatAdapter();
        chatList.setAdapter( chatAdapter );
        chatList.setLayoutManager(new LinearLayoutManager(this));

    }

        private class MyRoomViews extends RecyclerView.ViewHolder{
            TextView messageText;
            TextView timeText;
            public MyRoomViews(View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.message);
                timeText = itemView.findViewById(R.id.time);
            }
        }
        private class MyChatAdapter extends RecyclerView.Adapter<MyRoomViews>{

            @Override
            public MyRoomViews onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                int layoutID;
                if(viewType==1)
                    layoutID = R.layout.sent_message;
                else
                    layoutID = R.layout.receive_message;
                View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
                return new MyRoomViews(loadedRow);
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage thisRow = messages.get(position);
                return messages.get(position).getSendOrReceive();
            }

            @Override
            public void onBindViewHolder(MyRoomViews holder, int position) {
                holder.messageText.setText(messages.get(position).getMessage());
                holder.timeText.setText(messages.get(position).getTimeSent());
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

        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
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
    }
}

