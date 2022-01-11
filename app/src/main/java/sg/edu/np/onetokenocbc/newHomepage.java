package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class newHomepage extends AppCompatActivity {

    private TextView name;
    private TextView logout;
    private FirebaseAuth mAuth;
    public Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_homepage);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_homepage);

        mAuth = FirebaseAuth.getInstance();
        this.name = findViewById(R.id.name);
        this.logout = findViewById(R.id.logout);
        AccountHolder currentuser = getAccountHolder(mAuth.getCurrentUser().getEmail());
        name.setText("Mr" + currentuser.getName());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(newHomepage.this, MainActivity.class);
                newHomepage.this.startActivity(i);
            }
        });


    }


    private AccountHolder getAccountHolder(String email){
        AccountHolder holder = new AccountHolder();
        try{
            conn = connectionclass();
            //testConnection testConnection = new testConnection();
            if(conn == null){
                Log.d("fuck", "you internet");
            }
            else{
                String cifid = "";
                Log.d("fuck", "it worked");
                String query = "select * from AccountHolder" + "WHERE Email =" + email;
                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                if(rs.next()){
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
                    Log.d("fucks fuck", rs.getString(1));
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

        return holder;
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://pfd-asg2-jointacc-server.database.windows.net:1433;ssl=require;DatabaseName=JointAccHostedDB;user=pfdasg2team1@pfd-asg2-jointacc-server;password=Hongshaoji!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
            Log.d("connection", connection.toString());

        }
        catch (SQLException | ClassNotFoundException se){
            Log.d("32434",se.getMessage());
        }
        return connection;

    }
}