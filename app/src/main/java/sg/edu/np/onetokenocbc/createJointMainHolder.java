package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import static spark.Spark.*;



public class createJointMainHolder extends AppCompatActivity {
    public static final String ACCOUNT_SID = System.getenv("AC82b9e5b1bbf2e0a9a3c6b8f851e65756");
    public static final String AUTH_TOKEN = System.getenv("c47afab4512f541f06bb91e7f72cb252");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_joint_main_holder);

        sendSms("97238972");

        //get data of main account holder from database


    }
    
    public static void sendSms(String number){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+16067662293"),
                new com.twilio.type.PhoneNumber(number),
                "Test message")
                .create();

        System.out.println(message.getSid());
    }

    // to respond to an incomming text
/*    public static void smsBuilder(String[] args) {
        get("/", (req, res) -> "Hello Web");

        post("/sms", (req, res) -> {
            res.type("application/xml");
            Body body = new Body
                    .Builder("The Robots are coming! Head for the hills!")
                    .build();
            Message sms = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
    }*/
}