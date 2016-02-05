package io.byu.reaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LeaderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        final TextView rScores = (TextView)findViewById(R.id.rScores);
        Firebase ReactRef = new Firebase("https://loginandroid.firebaseio.com/scores/reaction");
        Query rQueryRef = ReactRef.orderByValue();
        rQueryRef.addChildEventListener(new ChildEventListener() {
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

                String str = "";
                for(Map.Entry<String,Long> entry : scoreMap.entrySet()) {
                    String k = entry.getKey();
                    String v = String.valueOf(entry.getValue());
                        str += k + ": " + v + " ms\n";
                    }


                rScores.setText(str);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase sRef = new Firebase("https://loginandroid.firebaseio.com/scores/speed");
        Query sQueryRef = sRef.orderByValue();
        final TextView sScores = (TextView)findViewById(R.id.sScores);
        sQueryRef.addChildEventListener(new ChildEventListener() {
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

                String str = "";
                for(Map.Entry<String,Long> entry : scoreMap.entrySet()) {
                    String k = entry.getKey();
                    String v = String.valueOf(entry.getValue());
                    str += k + ": " + v + " Taps\n";
                }


                sScores.setText(str);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
