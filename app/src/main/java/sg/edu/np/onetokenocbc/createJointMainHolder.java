package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public EditText Password;
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
        String[] idList = new String[]{"Singapore NRIC", "Singapore PR"};
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idList);
        idType.setAdapter(idAdapter);


        //Gender
        this.Gender = findViewById(R.id.jointGender);
        String[] genderList = new String[]{"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList);
        Gender.setAdapter(genderAdapter);

        //Marital Status
        this.mStatus = findViewById(R.id.jointMarital);
        String[] mList = new String[]{"Single", "Married", "Others"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mList);
        mStatus.setAdapter(mAdapter);

        //Race
        this.race = findViewById(R.id.jointRace);
        String[] raceList = new String[]{"Chinese", "Malay", "Indian", "Others"};
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, raceList);
        race.setAdapter(raceAdapter);

        this.rType = findViewById(R.id.jointResidentType);
        String[] rList = new String[]{"HDB - Standard / Executive / Mansionette", "HDB - Studio Apartment for Senior Citizens", "HDB - HUDC / Executive Condominium','HDB - Shop with Accommodation ", "Condominium / Private Apartment", "Terrace / Bungalow"};
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
        this.Password = findViewById(R.id.jointPassword);


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
            if(genderList[i].startsWith(receive.getStringExtra("Sex"))){
                Gender.setSelection(i);
            }
        }

        for (int i = 0; i < idList.length; i++){
            if(receive.getStringExtra("NRIC").startsWith("T") || receive.getStringExtra("NRIC").startsWith("S") ){
                idType.setSelection(0);
                Nationality.setText("Singaporean");
            }
        }

        address.setText(receive.getStringExtra("Address"));
        postalCode.setText(receive.getStringExtra("PostalCode"));

        //initialise button
        this.Create = findViewById(R.id.jointCreate);




        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("title selected", titlesList[titles.getSelectedItemPosition()]);
                AccountHolder updated = new AccountHolder();
                Random rnd = new Random();
                int number = rnd.nextInt(999999999);
                updated.setCIFID(String.valueOf(number));
                updated.setName(Name.getText().toString());
                updated.setID(NRIC.getText().toString());
                updated.setEmail(emailAddress.getText().toString());
                updated.setPhoneNo(phoneNumber.getText().toString());
                updated.setNationality(Nationality.getText().toString());
                updated.setDOB(DOB.getText().toString());
                updated.setAddress(address.getText().toString());
                updated.setPostalCode(postalCode.getText().toString());
                updated.setOccupation(Occuption.getText().toString());
                updated.setSalutation(titlesList[titles.getSelectedItemPosition()]);
                updated.setIDType(idList[idType.getSelectedItemPosition()]);
                updated.setGender(genderList[Gender.getSelectedItemPosition()]);
                updated.setMaritalStatus(mList[mStatus.getSelectedItemPosition()]);
                updated.setRace(raceList[race.getSelectedItemPosition()]);
                updated.setTypeofResidence(rList[rType.getSelectedItemPosition()]);
                updated.setPassword(Password.getText().toString());

                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser Main = mAuth.getCurrentUser();
                mAuth.createUserWithEmailAndPassword(updated.getEmail(),updated.getPassword());
                mAuth.signOut();

                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                String email = loginInfo.getString("email", "def");
                String password = loginInfo.getString("password", "def");

                Log.d("email user", email);
                Log.d("password user", password);

                mAuth.signInWithEmailAndPassword(email, password);


                //Log.d("current user", mAuth.getCurrentUser().getUid());
                createAccount(updated);


                Intent i = new Intent(createJointMainHolder.this, AccountCreated.class);
                createJointMainHolder.this.startActivity(i);
                //create bank details and bank accounts


            }
        });








        //get info and create joint account holder










   }




    private void createAccount(AccountHolder holder) {

        try {
            //conn = connectionclass();

            //holder.setCIFID();
            Connection conn = new AccountHolderDAL().AccountHolderConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String query = "INSERT INTO AccountHolder (CIFID, ID, IDType, Nationality, Salutation, Name, DoB, Gender, MaritalStatus, Race, TypeofResidence, Address, " +
                        "PostalCode, Email, PhoneNo, Occupation, Password) " +
                        "VALUES ('" + holder.getCIFID() + "', '" + holder.getID() + "', '" + holder.getIDType() + "', '" + holder.getNationality() + "', '" + holder.getSalutation() + "', '" +
                        holder.getName() + "', '" + holder.getDOB() + "', '" + "Male" + "', '" + holder.getMaritalStatus() + "', '" + holder.getRace() + "', '" +
                        holder.getTypeofResidence()+ "', '" + holder.getAddress() + "', '" + holder.getPostalCode() + "', '" + holder.getEmail() + "', '" + holder.getPhoneNo() + "', '" +
                        holder.getOccupation() + "', '" + holder.getPassword() + "')";


                Statement stint = conn.createStatement();
                Log.d("query", query);
                stint.execute(query);
                conn.close();
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }
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
