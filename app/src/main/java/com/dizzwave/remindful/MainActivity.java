package com.dizzwave.remindful;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.TypefaceProvider;

import java.io.Console;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.dizzwave.remindful.R.id.message;
import static com.dizzwave.remindful.R.id.messageList;


public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);
        preferences = getPreferences(Context.MODE_PRIVATE);

        renderMessages();
    }



    public void renderMessages(){
        Set<String> messages = getMessages();
        String[] arrayMessages = messages.toArray(new String[messages.size()]);
        TrashListItemAdapter adapter = new TrashListItemAdapter(this, this, arrayMessages);
        ListView messageList = (ListView) findViewById(R.id.messageList);
        messageList.setAdapter(adapter);
    }

    //looks like if we want to preserve order, saving a JSONArray into
    //one sharedPreference might be the easiest way. See accepted answer here:
    //http://stackoverflow.com/questions/35567517/cant-control-order-of-string-set-in-shared-preferences

    public void createNotification(String message){
        //get the text

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(17301504)
                        .setContentTitle("My notification")
                        .setContentText(message);

        // Sets an ID for the notification
        Random random = new Random();
        int mNotificationId = random.nextInt(9999 - 1000) + 1000;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        storeMessage(message);
        renderMessages();
    }
    private void storeMessage(String message){
        Set<String> messages = new HashSet<String>(preferences.getStringSet("com.dizzwave.remindful.messages", new HashSet<String>()));
        messages.add(message);
        preferences.edit().putStringSet("com.dizzwave.remindful.messages", messages).commit();
    }

    public void deleteMessage(String key){
        preferences.edit().remove(key).commit();
    }

    private Set<String> getMessages(){
        Set<String> messages = preferences.getStringSet("com.dizzwave.remindful.messages", new HashSet<String>());
        return messages;
    }
}
