package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class singpasslogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public EditText singpassid;
    public EditText password;
    public Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_singpasslogin);


        mAuth = FirebaseAuth.getInstance();

        this.singpassid = findViewById(R.id.singpassID);
        this.password = findViewById(R.id.singpassword);

        //login and get details to create account and generate CIFID
        if(getfromsingpass(singpassid.getText().toString()).getPassword() == password.getText().toString()){

            createAccount(getfromsingpass(singpassid.getText().toString()));
        }
        else{
            password.setError("you have entered a wrong password");
        }

    }


    private AccountHolder getfromsingpass(String id){
        AccountHolder holder = new AccountHolder();
        try {
            //conn = connectionclass();
            conn = new SingpassDAL().SingpassConnection();
            //testConnection testConnection = new testConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String cifid = "";
                Log.d("fuck", "it worked");
                String query = "SELECT * FROM SingpassDetails" + " WHERE ID = '" + id + "'";
                Log.d("query", query);
                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                if (rs.next()) {
                    //Log.d("cifid", rs.getString(1));
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999999);
                    holder.setCIFID(rs.getString(number)); //get and set CIFID
                    holder.setID(rs.getString(1)); //get and set ID
                    holder.setIDType(rs.getString(2));
                    holder.setNationality(rs.getString(3));
                    holder.setSalutation(rs.getString(4));
                    holder.setName(rs.getString(5));
                    holder.setDOB(rs.getString(6));
                    holder.setGender(rs.getString(7));
                    holder.setMaritalStatus(rs.getString(8));
                    holder.setRace(rs.getString(9));
                    holder.setTypeofResidence(rs.getString(10));
                    holder.setAddress(rs.getString(11));
                    holder.setPostalCode(rs.getString(12));
                    holder.setEmail(rs.getString(13));
                    holder.setPhoneNo(rs.getString(14));
                    holder.setOccupation(rs.getString(15));
                    holder.setPassword(rs.getString(16));

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

    private void createAccount(AccountHolder holder) {

        try {
            //conn = connectionclass();
            conn = new AccountHolderDAL().AccountHolderConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String query = "INSERT INTO AccountHolder (CIFID, ID, IDType, Nationality, Salutation, Name, DoB, Gender, MaritalStatus, Race, TypeofResidence, Address, " +
                        "PostalCode, Email, PhoneNo, Occupation, Password) " +
                        "VALUES ('" + holder.getCIFID() + "', '" + holder.getID() + "', '" + holder.getIDType() + "', '" + holder.getNationality() + "', '" + holder.getSalutation() + "', '" +
                        holder.getName() + "', '" + holder.getDOB() + "', '" + holder.getGender() + "', '" + holder.getMaritalStatus() + "', '" + holder.getRace() + "', '" +
                        holder.getTypeofResidence() + "', '" + holder.getAddress() + "', '" + holder.getPostalCode() + "', '" + holder.getEmail() + "', '" + holder.getPhoneNo() + "', '" +
                        holder.getOccupation() + "', '" + holder.getPassword() + "'";

                Statement stint = conn.createStatement();
                ResultSet rs = stint.executeQuery(query);
                conn.close();
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }
    }
}