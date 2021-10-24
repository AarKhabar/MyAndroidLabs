package algonquin.cst2335.khab0018;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

                messages.add(new ChatMessage(whatIsTyped,currentDateandTime ,1 ));



                text.setText("");

                chatAdapter.notifyItemInserted(messages.size() - 1);

            });
            receive.setOnClickListener(click ->{
            String whatIsTyped = text.getText().toString();
            Date timeNow = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);

            messages.add(new ChatMessage(whatIsTyped, currentDateandTime ,2));

            text.setText("");

            chatAdapter.notifyItemInserted(messages.size() - 1);

            });
        chatAdapter = new MyChatAdapter();
        chatList.setAdapter( chatAdapter );
        chatList.setLayoutManager(new LinearLayoutManager(this));

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

        public ChatMessage(String message, String timeSent,int sendOrReceive) {
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

