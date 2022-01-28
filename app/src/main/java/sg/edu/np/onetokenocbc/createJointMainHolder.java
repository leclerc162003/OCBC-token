package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class createJointMainHolder extends AppCompatActivity {


    //initialise all from view
    public Spinner titles;
    public EditText Name;
    public Spinner idType;
    public EditText NRIC;
    public EditText emailAddress;
    public EditText phoneNumber;
    public Spinner Gender;
    public EditText Nationality;
    public EditText DOB;
    public Spinner mStatus;
    public Spinner race;
    public Spinner rType;
    public EditText address;
    public EditText postalCode;
    public EditText Occuption;
    public ImageView Create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_joint_main_holder);


        //initialise dropdown lists
        //titles
        this.titles = findViewById(R.id.titles);
        String[] titlesList = new String[]{"Dr", "Mr", "Mdm", "Mrs",  "Miss"};
        ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titlesList);
        titles.setAdapter(titleAdapter);
        //titles.getSelectedItemPosition()

        //id type
        this.idType = findViewById(R.id.jointIDtype);
        String[] idList = new String[]{"Singaporean", "PR", "Foreigner"};
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idList);
        idType.setAdapter(idAdapter);


        //Gender
        this.Gender = findViewById(R.id.jointGender);
        String[] genderList = new String[]{"M", "F"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList);
        Gender.setAdapter(genderAdapter);

        //Marital Status
        this.mStatus = findViewById(R.id.jointMarital);
        String[] mList = new String[]{"Single", "Married", "Windowed"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mList);
        mStatus.setAdapter(mAdapter);

        //Race
        this.race = findViewById(R.id.jointRace);
        String[] raceList = new String[]{"Chinese", "Malay", "Indian", "Caucasian", "Others"};
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, raceList);
        race.setAdapter(raceAdapter);

        this.rType = findViewById(R.id.jointResidentType);
        String[] rList = new String[]{"HDB", "Private", "Landed", "Condo", "Executive Apartment"};
        ArrayAdapter<String> rAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rList);
        rType.setAdapter(rAdapter);


        //initialise textfields
        this.Name = findViewById(R.id.jointName);
        this.NRIC = findViewById(R.id.jointNRIC);
        this.emailAddress = findViewById(R.id.jointEmail);
        this.phoneNumber = findViewById(R.id.jointHP);
        this.Nationality = findViewById(R.id.jointNationality);
        this.DOB = findViewById(R.id.jointDOB);
        this.address = findViewById(R.id.jointAddress);
        this.postalCode = findViewById(R.id.jointPostal);
        this.Occuption = findViewById(R.id.jointOccupation);


        //from OCR
        Intent receive = getIntent();

        //receive.getStringExtra("NRIC");
        //receive.getStringExtra("Name");
        //receive.getStringExtra("Race");
        //receive.getStringExtra("DOB");
        //receive.getStringExtra("Sex");
        NRIC.setText(receive.getStringExtra("NRIC"));
        Name.setText(receive.getStringExtra("Name"));
        for (int i = 0; i < raceList.length; i++){
            if(raceList[i].contains(receive.getStringExtra("Race"))){
                race.setSelection(i);
            }
        }
        DOB.setText(receive.getStringExtra("DOB"));

        for (int i = 0; i < genderList.length; i++){
            if(genderList[i].contains(receive.getStringExtra("Sex"))){
                Gender.setSelection(i);
            }
        }

        for (int i = 0; i < idList.length; i++){
            if(receive.getStringExtra("NRIC").startsWith("T") || receive.getStringExtra("NRIC").startsWith("S") ){
                idType.setSelection(0);
                Nationality.setText("Singaporean");
            }
        }
        //initialise button
        this.Create = findViewById(R.id.jointCreate);




        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("title selected", titlesList[titles.getSelectedItemPosition()]);
            }
        });








        //get info and create joint account holder










   }

}


//send otp

//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder().permitAll().build() );
//        }
//
//
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://api.twilio.com/2010-04-01/Accounts/"+"AC82b9e5b1bbf2e0a9a3c6b8f851e65756"+"/SMS/Messages";
//        String base64EncodedCredentials = "Basic " + Base64.encodeToString(("AC82b9e5b1bbf2e0a9a3c6b8f851e65756" + ":" + "c47afab4512f541f06bb91e7f72cb252").getBytes(), Base64.NO_WRAP);
//
//        RequestBody body = new FormBody.Builder()
//                .add("From", "+16067662293")
//                .add("To", "+6596966253")
//                .add("Body", "Yeji is fucking beautiful.")
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .header("Authorization", base64EncodedCredentials)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            Log.d("TAG", "sendSms: "+ response.body().string());
//        } catch (IOException e) { e.printStackTrace(); }
