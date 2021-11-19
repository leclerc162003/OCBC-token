package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class prompt extends AppCompatActivity {
    public TextView message;
    public Button Authorise;
    public Button noAuthorise;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://onetokenocbc-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prompt);

        this.message = findViewById(R.id.message);
        this.Authorise = findViewById(R.id.trueButton);
        this.noAuthorise = findViewById(R.id.falseButton);
        mAuth = FirebaseAuth.getInstance();


        Intent receive = getIntent();
        receive.getStringExtra("UID");
        receive.getStringExtra("MessageID");
        receive.getStringExtra("Message");
        receive.getBooleanExtra("Authorise", false);
        receive.getBooleanExtra("Replied", false);

        Requests request = new Requests(receive.getStringExtra("UID"), receive.getStringExtra("Message"),receive.getStringExtra("MessageID"), receive.getBooleanExtra("Authorise", false), receive.getBooleanExtra("Replied", false) );

        message.setText(request.getMessage());

        Authorise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setAuthorise(true);
                request.setReplied(true);
                mDatabase.child("Messages").child(mAuth.getUid()+"1").child(mAuth.getUid()).setValue(request);

                Intent i = new Intent(prompt.this, Home.class);
                prompt.this.startActivity(i);
            }
        });

        noAuthorise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setAuthorise(false);
                request.setReplied(true);
                mDatabase.child("Messages").child(mAuth.getUid()+"1").child(mAuth.getUid()).setValue(request);
                Intent i = new Intent(prompt.this, Home.class);
                prompt.this.startActivity(i);
            }
        });




    }
}