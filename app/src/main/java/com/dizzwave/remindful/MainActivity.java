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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        Set<String> messages = getMessages();
        renderMessages(messages);
    }

    private void renderMessages(Set<String> messages){
        String[] arrayMessages = messages.toArray(new String[messages.size()]);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        R.layout.trashable_list_item, arrayMessages);
        TrashListItemAdapter adapter = new TrashListItemAdapter(this, arrayMessages);

        //create new layout (trashable_list_item.xml) and reference that instead.
        //use AndroidBootstrap (https://github.com/Bearded-Hen/Android-Bootstrap)
        //  in the new layout -- make it pretty, with an X on the right to delete
        //  (and maybe some other icon to notificationify it).
        ListView messageList = (ListView) findViewById(R.id.messageList);
        Log.d("mytag","hello you");

        /*messageList.setOnItemClickListener(
            new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                        long arg3) {
                    Log.d("onclick","a click");
                    if(view.getTag()=="notify"){
                        createNotification("hi");
                    }
                    else if(view.getTag()=="trash"){
                        createNotification("trash!!!");
                    }
                }
            }
        );
        */
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
        //int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
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
