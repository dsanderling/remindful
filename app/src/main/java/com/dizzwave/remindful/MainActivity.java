package com.dizzwave.remindful;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Console;
import java.util.HashSet;
import java.util.Set;

import static com.dizzwave.remindful.R.id.messageList;


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
        String[] arrayMessages = messages.toArray(new String[messages.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayMessages);
        //create new layout (trashable_list_item.xml) and reference that instead.
        //use AndroidBootstrap (https://github.com/Bearded-Hen/Android-Bootstrap)
        //  in the new layout -- make it pretty, with an X on the right to delete
        //  (and maybe some other icon to notificationify it).
        ListView messageList = (ListView) findViewById(R.id.messageList);
        messageList.setAdapter(adapter);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        storeMessage(message);
        renderMessages(getMessages());
    }


    //looks like if we want to preserve order, saving a JSONArray into
    //one sharedPreference might be the easiest way. See accepted answer here:
    //http://stackoverflow.com/questions/35567517/cant-control-order-of-string-set-in-shared-preferences

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
