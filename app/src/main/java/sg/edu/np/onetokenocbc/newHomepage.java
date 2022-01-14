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
        name.setText("Mr " + currentuser.getName());

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
                String query = "select * from AccountHolder" + " WHERE Email = '" + email + "'";
                Log.d ("query", query);
                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                if(rs.next()){
                    Log.d ("cifid", rs.getString(1));
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
                else{

                }
            }
        }
        catch(Exception ex){
            Log.d("error", ex.getMessage());
        }

        return holder;
    }


    //private void AddJointAccount()





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