package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

public class jointHolderLogin extends AppCompatActivity {

    public EditText phoneNumberJointLogin;
    public ImageView otpProceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_joint_holder_login);


        phoneNumberJointLogin = findViewById(R.id.phoneNumberJointLogin);
        otpProceed = findViewById(R.id.proceedOTP);

        otpProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putString("Phone", phoneNumberJointLogin.getText().toString());
                extras.putString("UserType", "Existing");
                Intent i = new Intent(jointHolderLogin.this, otp.class);
                i.putExtras(extras);
                jointHolderLogin.this.startActivity(i);
            }
        });


    }
}