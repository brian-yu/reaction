package io.byu.reaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        final TextView reaction = (TextView)findViewById(R.id.textView);
        final TextView speed = (TextView)findViewById(R.id.textView2);
        final TextView leaderboard = (TextView)findViewById(R.id.textView3);

        reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SignedInActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SpeedActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MenuActivity.this, SignedInActivity.class);
//                intent.putExtra("email", email);
//                startActivity(intent);
            }
        });
    }
}