package io.byu.reaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Collection;
import java.util.Date;
import java.util.*;
import java.util.TreeMap;

public class SignedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://loginandroid.firebaseio.com/scores");
        setContentView(R.layout.activity_signed_in);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        final TextView box = (TextView)findViewById(R.id.box);
        final TextView scores = (TextView)findViewById(R.id.scores);
        //final TextView scores2 = (TextView)findViewById(R.id.scores2);
        final TextView greet = (TextView)findViewById(R.id.greet);

        greet.setText("Hello " + email + "!");

        ref.addChildEventListener(new ChildEventListener() {
            TreeMap<String, Long> scoreMap = new TreeMap<>();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TreeMap<String, Long> newScore = dataSnapshot.getValue(TreeMap.class);

//                System.out.println("Email: " + newScore.getEmail());
//                System.out.println("Time: " + newScore.getTime());
                Set temp = newScore.keySet();
                Object[] lol = temp.toArray();
                Object lol2 = lol[0];
                String lol3 = lol2.toString().replace("@DOT@", ".");
                scoreMap.put(lol3, newScore.get(lol2));

                int l = 0;
                String str = "";
                String str2 = "";
                for(Map.Entry<String,Long> entry : scoreMap.entrySet()) {
                    String k = entry.getKey();
                    String v = String.valueOf(entry.getValue());
                    if(l < 5) {
                        str += k + ": " + v + " ms\n";
                    } else if(l < 6){
                        str2 += k + ": " + v + " ms\n";
                    } else {
                        break;
                    }
                    l++;
                }
                scores.setText(str);
                //scores2.setText(str2);
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        box.setOnClickListener(new View.OnClickListener() {
            Firebase ref = new Firebase("https://loginandroid.firebaseio.com/scores");
            int count =0;
            long start = System.currentTimeMillis();
            Handler handler = new Handler();
            boolean canPress = false;
            Runnable timer = new Runnable() {
                public void run() {
                    // Actions to do after delay
                    canPress = true;
                    box.setText("Go!");
                    box.setBackgroundColor(Color.parseColor("#2ecc71"));
                    start = System.currentTimeMillis();
                }
            };
            public void onClick(View v) {
                count++;

                if(count == 1) {
                    box.setBackgroundColor(Color.parseColor("#e74c3c"));
                    box.setText("Wait...");

                    Random r = new Random();
                    int delay = r.nextInt(7000 - 250) + 250;
                    handler.postDelayed(timer, delay);


                } else if (count == 2 && canPress == true) {
                    long stop = System.currentTimeMillis();
                    box.setBackgroundColor(Color.parseColor("#8e44ad"));

                    long time = stop - start;

                    box.setText(String.valueOf(time) + "ms\nTap again to restart.");

                    TreeMap<String, Long> s = new TreeMap<>();
                    String newEmail = email.replace(".", "@DOT@");
                    s.put(newEmail, time);


                    ref.push().setValue(s);

                } else if(count == 2 && canPress == false) {
                    box.setBackgroundColor(Color.parseColor("#e67e22"));
                    box.setText("Whoops! False start. \nTap again to restart.");
                    handler.removeCallbacks(timer);
                } else if(count == 3) {
                    box.setBackgroundColor(Color.parseColor("#3498db"));
                    box.setText("Tap to begin");
                    canPress = false;
                    count = 0;
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signed_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
