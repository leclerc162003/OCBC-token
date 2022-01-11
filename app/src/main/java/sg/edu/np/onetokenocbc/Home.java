package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://onetokenocbc-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();
    public Connection conn;
    public String namel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        // trying out SQL
        try{
            conn = connectionclass();
            //testConnection testConnection = new testConnection();

            if(conn == null){
                Log.d("fuck", "you internet");
            }
            else{
                String cifid = "";
                Log.d("fuck", "it worked");
                String query = "select * from AccountHolder" + "WHERE CIFID =" + cifid;
                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                if(rs.next()){
                    AccountHolder holder = new AccountHolder();
                    holder.setCIFID(rs.getRowId(1).toString()); //get and set CIFID
                    holder.setID(rs.getRowId(2).toString()); //get and set ID
                    holder.setIDType(rs.getRowId(3).toString());
                    holder.setNationality(rs.getRowId(4).toString());
                    holder.setSalutation(rs.getRowId(5).toString());
                    holder.setName(rs.getRowId(6).toString());
                    holder.setDOB(rs.getRowId(7).toString());
                    holder.setGender(rs.getRowId(8).toString());
                    holder.setMaritalStatus(rs.getRowId(9).toString());
                    holder.setRace(rs.getRowId(10).toString());
                    holder.setTypeofResidence(rs.getRowId(11).toString());
                    holder.setAddress(rs.getRowId(12).toString());
                    holder.setPostalCode(rs.getRowId(13).toString());
                    holder.setEmail(rs.getRowId(14).toString());
                    holder.setPhoneNo(rs.getRowId(15).toString());
                    holder.setOccupation(rs.getRowId(16).toString());
                    holder.setPassword(rs.getRowId(17).toString());

                    Log.d("fuck", String.valueOf(rs.getRow()));
                    //Log.d("fuck", String.valueOf(rs.));
                    conn.close();
                }
                else{

                }
            }
        }
        catch(Exception ex){
            Log.d("error", ex.getMessage());
        }
//        try {
//
//            String SQL = "SELECT * FROM AccountHolder";
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            String url = String.format("jdbc:jtds:sqlserver://pfd-asg2-jointacc-server.database.windows.net:1433;DatabaseName=JointAccHostedDB;user=pfdasg2team1@pfd-asg2-jointacc-server;password=Hongshaoji!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
//            conn = DriverManager.getConnection(url);
//
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(SQL);
//            Log.d("works", "it works! 11");
//            while (rs.next()) {
//                System.out.println(rs.getRow());
//                Log.d("works", "it works! 22");
//            }
//            conn.close();
//        } catch (Exception e) {
//            Log.d("not works", "not FUCKING works! 22");
//            e.printStackTrace();
//        }
        //trying out SQL

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


    @SuppressLint("NewApi")
    public Connection connectionclass(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://pfd-asg2-jointacc-server.database.windows.net:1433;DatabaseName=JointAccHostedDB;user=pfdasg2team1@pfd-asg2-jointacc-server;password=Hongshaoji!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
            Log.d("connection", connection.toString());

        }
        catch (SQLException | ClassNotFoundException se){
            Log.d("32434","aegsdga");
        }
        return connection;

    }


}




