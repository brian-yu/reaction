package io.byu.reaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences storedEmail = getSharedPreferences("email", 0);
        final String email = storedEmail.getString("email", "missing");
        if (email == "missing") {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        final TextView reaction = (TextView)findViewById(R.id.textView);
        final TextView speed = (TextView)findViewById(R.id.textView2);
        final TextView leaderboard = (TextView)findViewById(R.id.textView3);
        final TextView logout = (TextView)findViewById(R.id.textView4);

        reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SignedInActivity.class);
                startActivity(intent);
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SpeedActivity.class);
                startActivity(intent);
            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LeaderActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences storedEmail = getSharedPreferences("email", 0);
                SharedPreferences.Editor editor = storedEmail.edit();
                editor.remove("email");
                editor.commit();

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}