package io.byu.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.Date;

public class SignedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://loginandroid.firebaseio.com/");
        setContentView(R.layout.activity_signed_in);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        final TextView box = (TextView)findViewById(R.id.box);
        final TextView scores = (TextView)findViewById(R.id.scores);
        final TextView greet = (TextView)findViewById(R.id.greet);

        greet.setText("Hello " + email + "! \nWhen the box turns green, press it as fast as you can!");



        box.setOnClickListener(new View.OnClickListener() {
            int count =0;
            long start = System.currentTimeMillis();
            Handler handler = new Handler();
            boolean canPress = false;
            Runnable timer = new Runnable() {
                public void run() {
                    // Actions to do after 10 seconds
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


                    handler.postDelayed(timer, 3000);


                } else if(count == 2 && canPress == true) {
                    long stop = System.currentTimeMillis();
                    box.setBackgroundColor(Color.parseColor("#8e44ad"));

                    long time = stop - start;

                    box.setText(String.valueOf(time) + "ms\nTap again to restart.");

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
