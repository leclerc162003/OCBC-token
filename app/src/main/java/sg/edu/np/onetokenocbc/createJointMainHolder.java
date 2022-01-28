package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class createJointMainHolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_joint_main_holder);



        //get data of main account holder from database


    }
    
    public static void sendSms(String number){
        //sid: AC82b9e5b1bbf2e0a9a3c6b8f851e65756
        // token: c47afab4512f541f06bb91e7f72cb252
    }
}