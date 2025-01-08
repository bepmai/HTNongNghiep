package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.MessageAdapter1;
import tlu.edu.vn.ht63.htnongnghiep.Model.Message1;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ChatActivity1 extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message1> messageList;
    MessageAdapter1 messageAdapter;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(tlu.edu.vn.ht63.htnongnghiep.R.layout.activity_chat1);
        messageList = new ArrayList<>();


        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);


        //setup recycler view
        messageAdapter = new MessageAdapter1(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);


        sendButton.setOnClickListener((v) -> {
            String question = messageEditText.getText().toString().trim();
            if (question.isEmpty()) {
                Toast.makeText(ChatActivity1.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            addToChat(Message1.SENT_BY_ME, question);
            messageEditText.setText(""); // Xóa nội dung sau khi gửi
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });
    }

    void addToChat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                Log.d("DEBUG", "Adding message: " + message + ", sentBy: " + sentBy);
                messageList.add(new Message1(message, sentBy));
                Log.d("DEBUG", "Message List Size: " + messageList.size());
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
    void addResponse(String response){
        addToChat(Message1.SENT_BY_BOT, response);
    }
    void callAPI(String question){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","command-r-08-2024");
            jsonBody.put("message", question);
            jsonBody.put("stream", false);
            jsonBody.put("preamble", "You are an AI-assistant chatbot. You are trained to assist users by providing thorough and helpful responses to their queries.");

        }catch (JSONException e){
            e.printStackTrace();;
        }
        RequestBody body = RequestBody.create(JSON, jsonBody.toString());
        Request request = new Request.Builder()
                .url("https://api.cohere.com/v1/chat")
                .header("Authorization","Bearer x9jg61gS0GFoIcpvxOSD3O4BJNMg7nm3eq3IuOWv")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response dư to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        assert response.body() != null;
                        jsonObject = new JSONObject(response.body().string());
                        String jsonArray = jsonObject.getString("text");
                        String result = String.valueOf(jsonArray);
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    addResponse("Failed to load response dư to "+response.body().toString());
                }
            }
        });
    }
}