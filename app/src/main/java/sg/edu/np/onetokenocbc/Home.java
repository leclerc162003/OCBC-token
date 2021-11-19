package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://onetokenocbc-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        Log.d("User id", mAuth.getUid());

        mDatabase.child("Messages").child(mAuth.getUid()+"1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren() ){
                    //Requests request = postSnapshot.getValue(Requests.class);
                    Requests request = postSnapshot.getValue(Requests.class);
                    if (request.getUIDUser().contentEquals(mAuth.getUid())){
                        if(request.getReplied() == false){
                            Bundle extras = new Bundle();
                            extras.putString("UID", request.getUIDUser());
                            extras.putString("MessageID", request.getMessageID());
                            extras.putString("Message", request.getMessage());
                            extras.putBoolean("Authorise", request.getAuthorise());
                            extras.putBoolean("Replied", request.getReplied());
                            Intent i = new Intent(Home.this, prompt.class);
                            i.putExtras(extras);
                            Home.this.startActivity(i);
                        }
                    }


//                    if(request.getReplied() == false){
//                        Bundle extras = new Bundle();
//                        extras.putString("UID", request.getUIDUser());
//                        extras.putString("MessageID", request.getMessageID());
//                        extras.putString("Message", request.getMessage());
//                        extras.putBoolean("Authorise", request.getAuthorise());
//                        extras.putBoolean("Replied", request.getReplied());
//                        Intent i = new Intent(Home.this, prompt.class);
//                        i.putExtras(extras);
//                        Home.this.startActivity(i);
//                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}



