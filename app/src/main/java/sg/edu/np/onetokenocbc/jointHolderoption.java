package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class jointHolderoption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_joint_holderoption);
        //chose between new or existing ocbc user

        LottieAnimationView existingUser = findViewById(R.id.otplottie);
        LottieAnimationView newUser = findViewById(R.id.optionNewUser);

        existingUser.setRepeatCount(LottieDrawable.INFINITE);
        newUser.setRepeatCount(LottieDrawable.INFINITE);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(jointHolderoption.this, secretactivity.class);
                jointHolderoption.this.startActivity(i);
            }
        });


        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(jointHolderoption.this, );
            }
        });



    }
}