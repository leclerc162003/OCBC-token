package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class newHomepage extends AppCompatActivity {

    private TextView name;
    private TextView logout;
    private ImageView createButton;
    private FirebaseAuth mAuth;
    public Connection conn;
    ArrayList<AccountDetails> accntData = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://onetokenocbc-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_homepage);
        RecyclerView rv = findViewById(R.id.accntRV);
        accountRVAdapter adapter = new accountRVAdapter(this, accntData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder().permitAll().build() );
        }


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
                            Intent i = new Intent(newHomepage.this, prompt.class);
                            i.putExtras(extras);
                            newHomepage.this.startActivity(i);
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

//        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        String email = loginInfo.getString("email", "def");
//        String password = loginInfo.getString("password", "def");
//
//        Log.d("email user", email);
//        Log.d("password user", password);


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
//
//

        //mAuth = FirebaseAuth.getInstance();
        this.name = findViewById(R.id.name);
        this.logout = findViewById(R.id.logout);
        this.createButton = findViewById(R.id.jointCreatebutton);

        AccountHolder currentuser = getAccountHolder(mAuth.getCurrentUser().getEmail());
        Log.d("cidid", mAuth.getCurrentUser().getUid());
        name.setText(currentuser.getSalutation() + " " + currentuser.getName());
        getJointAccouunts(currentuser);
        SharedPreferences.Editor mainholderinfo = getSharedPreferences("mainholderinfo", MODE_PRIVATE).edit();
        mainholderinfo.putString("CIFID", currentuser.getCIFID().trim());
        mainholderinfo.apply();


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(newHomepage.this, updateMainHolder.class);
                newHomepage.this.startActivity(i);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(newHomepage.this, MainActivity.class);
                newHomepage.this.startActivity(i);
            }
        });

        adapter.notifyDataSetChanged();
    }


    private AccountHolder getAccountHolder(String email) {
        AccountHolder holder = new AccountHolder();
        try {
            //conn = connectionclass();
            conn = new AccountHolderDAL().AccountHolderConnection();
            //testConnection testConnection = new testConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String cifid = "";
                Log.d("fuck", "it worked");
                String query = "select * from AccountHolder" + " WHERE Email = '" + email + "'";
                Log.d("query", query);
                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                if (rs.next()) {
                    Log.d("cifid", rs.getString(1));
                    holder.setCIFID(rs.getString(1)); //get and set CIFID
                    holder.setID(rs.getString(2)); //get and set ID
                    holder.setIDType(rs.getString(3));
                    holder.setNationality(rs.getString(4));
                    holder.setSalutation(rs.getString(5));
                    holder.setName(rs.getString(6));
                    holder.setDOB(rs.getString(7));
                    holder.setGender(rs.getString(8));
                    holder.setMaritalStatus(rs.getString(9));
                    holder.setRace(rs.getString(10));
                    holder.setTypeofResidence(rs.getString(11));
                    holder.setAddress(rs.getString(12));
                    holder.setPostalCode(rs.getString(13));
                    holder.setEmail(rs.getString(14));
                    holder.setPhoneNo(rs.getString(15));
                    holder.setOccupation(rs.getString(16));
                    holder.setPassword(rs.getString(17));

                    Log.d("fuck", String.valueOf(rs.getRow()));
                    Log.d("fucks fuck", rs.getString(1));
                    //Log.d("fuck", String.valueOf(rs.));
                    conn.close();
                }
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }

        return holder;
    }

    private void getJointAccouunts(AccountHolder holder){
        try {
            //conn = connectionclass();
            conn = new AccountHolderDAL().AccountHolderConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String query = "SELECT BankAccount.AccountNo, AccountDetails.MainHolderID, AccountDetails.SubHolderID FROM BankAccount INNER JOIN AccountDetails ON BankAccount.AccountNo = AccountDetails.AccountNo INNER JOIN AccountHolder ON AccountDetails.MainHolderID = AccountHolder.CIFID" +
                        " WHERE AccountHolder.CIFID = '" + holder.getCIFID().trim() + "'";

                Log.d("query", query);
                Log.d("id ", String.valueOf(holder.getCIFID().length()));
                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                while (rs.next()) {
                    AccountDetails details = new AccountDetails();
                    details.setAccountNo(rs.getString(1));
                    details.setMainHolderID(rs.getString(2));
                    details.setSubHolderID(rs.getString(3));

                    Log.d("cifid", rs.getString(1));
                    Log.d("fuck", String.valueOf(rs.getRow()));
                    Log.d("fucks fuck", rs.getString(1));
                    //Log.d("fuck", String.valueOf(rs.));

                    accntData.add(details);
                }
                conn.close();
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }
    }
}


//    private void AddJointAccount(){
//
//
//
//    }


//    @SuppressLint("NewApi")
//    public Connection connectionclass(){
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Connection connection = null;
//        String ConnectionURL = null;
//
//        try {
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            ConnectionURL = "jdbc:jtds:sqlserver://pfd-asg2-jointacc-server.database.windows.net:1433;ssl=require;DatabaseName=JointAccHostedDB;user=pfdasg2team1@pfd-asg2-jointacc-server;password=Hongshaoji!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
//            connection = DriverManager.getConnection(ConnectionURL);
//            Log.d("connection", connection.toString());
//
//        }
//        catch (SQLException | ClassNotFoundException se){
//            Log.d("32434",se.getMessage());
//        }
//        return connection;
//
//    }
//}