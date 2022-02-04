package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class otp extends AppCompatActivity {

    public ImageView otpenter;
    public EditText otpentered;
    public LottieAnimationView viewlottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);

        viewlottie = findViewById(R.id.otploadinglottie);
        viewlottie.setVisibility(View.INVISIBLE);
        otpentered = findViewById(R.id.otpassword);
        otpenter = findViewById(R.id.otpenter);
        Intent i = getIntent();
        String usertype = i.getStringExtra("UserType");

        if(usertype ==  "New") {
            AccountHolder joint = (AccountHolder) i.getSerializableExtra("jointholder");
            BankAccount account = (BankAccount) i.getSerializableExtra("bankaccount");
            AccountDetails details = (AccountDetails) i.getSerializableExtra("details");

            String otp = sendPhoneNumber(joint.getPhoneNo());


            //set on click listener
            otpenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewlottie.setVisibility(View.VISIBLE);
                    if (otpentered.getText().toString().equals(otp)) {
                        viewlottie.setVisibility(View.VISIBLE);
                        FirebaseAuth mAuth;
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser Main = mAuth.getCurrentUser();
                        mAuth.createUserWithEmailAndPassword(joint.getEmail(), joint.getPassword());
                        mAuth.signOut();

                        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                        String email = loginInfo.getString("email", "def");
                        String password = loginInfo.getString("password", "def");

                        Log.d("email user", email);
                        Log.d("password user", password);

                        mAuth.signInWithEmailAndPassword(email, password);


                        //Log.d("current user", mAuth.getCurrentUser().getUid());
                        createAccount(joint);

                        addBankAccount(account);
                        addAccountDetails(details);


                        Intent k = new Intent(otp.this, AccountCreated.class);
                        otp.this.startActivity(k);


                    }
                }
            });

        }
        else{
            String phoneNumber = i.getStringExtra("Phone");
            String otp = sendPhoneNumber(phoneNumber);

            otpenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewlottie.setVisibility(View.VISIBLE);
                    if (otpentered.getText().toString().equals(otp)) {
                        viewlottie.setVisibility(View.VISIBLE);
                        Bundle extras = new Bundle();
                        extras.putString("UserType", "Existing");
                        extras.putString("PhoneNumber", phoneNumber);
                        Intent k = new Intent(otp.this, createJointMainHolder.class);
                        k.putExtras(extras);
                        otp.this.startActivity(k);


                    }
                }
            });




        }


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


    private void addBankAccount(BankAccount account){
        try {
            //conn = connectionclass();

            //holder.setCIFID();
            Connection conn = new AccountHolderDAL().AccountHolderConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String query = "INSERT INTO BankAccount (AccountNo, CIFID,  CardNo, CVV, Balance) VALUES ('" +
                        account.getAccountNo() +"', '" + account.getCIFID() + "', '" +  account.getCardNo() + "', '" + account.getCVV() + "', '" + account.getBalance() + "')";


                Statement stint = conn.createStatement();
                Log.d("query", query);
                stint.execute(query);
                conn.close();
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }

    }
    private void addAccountDetails(AccountDetails details){
        try {
            //conn = connectionclass();

            //holder.setCIFID();
            Connection conn = new AccountHolderDAL().AccountHolderConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String query = "INSERT INTO AccountDetails (AccountNo, MainHolderID, SubHolderID)" +
                        "VALUES ('" + details.getAccountNo() + "', '" + details.getMainHolderID() + "', '" + details.getSubHolderID() + "')";



                Statement stint = conn.createStatement();
                Log.d("query", query);
                stint.execute(query);
                conn.close();
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }

    }


    private String sendPhoneNumber(String phone){
        //Log.d("f", joint.getCIFID());
        //send otp
        Random rnd = new Random();
        String otp = String.valueOf(rnd.nextInt(900000) + 100000);

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.twilio.com/2010-04-01/Accounts/" + "AC82b9e5b1bbf2e0a9a3c6b8f851e65756" + "/SMS/Messages";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString(("AC82b9e5b1bbf2e0a9a3c6b8f851e65756" + ":" + "c47afab4512f541f06bb91e7f72cb252").getBytes(), Base64.NO_WRAP);

        RequestBody body = new FormBody.Builder()
                .add("From", "+16067662293")
                .add("To", "+65" + phone)
                .add("Body", "Your One-Time Password is: " + otp + ". Do not share it with anyone else.")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", base64EncodedCredentials)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("TAG", "sendSms: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return otp;
    }

}