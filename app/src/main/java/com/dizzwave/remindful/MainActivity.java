package com.dizzwave.remindful;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Console;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Set<String> messages = getMessages();
        renderMessages(messages);
    }

    private void renderMessages(Set<String> messages){
        for(String message : messages){
            Log.d("dave",message);
            //render cell
        }
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);

        storeMessage(message);
        renderMessages(getMessages());
    }

    private void storeMessage(String message){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> messages = new HashSet<String>(sharedPref.getStringSet("com.dizzwave.remindful.messages", new HashSet<String>()));
        messages.add(message);
        editor.putStringSet("com.dizzwave.remindful.messages", messages);
        editor.commit();
    }

    private Set<String> getMessages(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Set<String> messages = sharedPref.getStringSet("com.dizzwave.remindful.messages", new HashSet<String>());
        return messages;
    }
}
