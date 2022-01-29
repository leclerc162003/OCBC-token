package sg.edu.np.onetokenocbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class updateMainHolder extends AppCompatActivity {

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
    private FirebaseAuth mAuth;
    public Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_main_holder);

        //initialise dropdown lists
        //titles
        this.titles = findViewById(R.id.maintitles);
        String[] titlesList = new String[]{"Dr", "Mr", "Mdm", "Mrs",  "Miss"};
        ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titlesList);
        titles.setAdapter(titleAdapter);
        //titles.getSelectedItemPosition()

        //id type
        this.idType = findViewById(R.id.mainIDtype);
        String[] idList = new String[]{"Singapore NRIC", "Singapore PR"};
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idList);
        idType.setAdapter(idAdapter);


        //Gender
        this.Gender = findViewById(R.id.mainGender);
        String[] genderList = new String[]{"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderList);
        Gender.setAdapter(genderAdapter);

        //Marital Status
        this.mStatus = findViewById(R.id.mainMarital);
        String[] mList = new String[]{"Single", "Married", "Others"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mList);
        mStatus.setAdapter(mAdapter);

        //Race
        this.race = findViewById(R.id.mainRace);
        String[] raceList = new String[]{"Chinese", "Malay", "Indian", "Others"};
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, raceList);
        race.setAdapter(raceAdapter);

        this.rType = findViewById(R.id.mainResidentType);
        String[] rList = new String[]{"HDB - Standard / Executive / Mansionette", "HDB - Studio Apartment for Senior Citizens", "HDB - HUDC / Executive Condominium','HDB - Shop with Accommodation ", "Condominium / Private Apartment", "Terrace / Bungalow"};
        ArrayAdapter<String> rAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rList);
        rType.setAdapter(rAdapter);


        //initialise textfields
        this.Name = findViewById(R.id.mainName);
        this.NRIC = findViewById(R.id.mainNRIC);
        this.emailAddress = findViewById(R.id.mainEmail);
        this.phoneNumber = findViewById(R.id.mainHP);
        this.Nationality = findViewById(R.id.mainNationality);
        this.DOB = findViewById(R.id.mainDOB);
        this.address = findViewById(R.id.mainAddress);
        this.postalCode = findViewById(R.id.mainPostal);
        this.Occuption = findViewById(R.id.mainOccupation);


        //currentholder
        mAuth = FirebaseAuth.getInstance();
        AccountHolder main =  getAccountHolder(mAuth.getCurrentUser().getEmail());

        Name.setText(main.getName());
        NRIC.setText(main.getID());
        emailAddress.setText(main.getEmail());
        phoneNumber.setText(main.getPhoneNo());
        Nationality.setText(main.getNationality());
        DOB.setText(main.getDOB());
        address.setText(main.getAddress());
        postalCode.setText(main.getPostalCode());
        Occuption.setText(main.getOccupation());



        //make fields not editable for some attributes
        Name.setEnabled(false);
        NRIC.setEnabled(false);
        Gender.setEnabled(false);
        DOB.setEnabled(false);

        for (int i = 0; i < titlesList.length; i++){
            if(titlesList[i].contains(main.getSalutation())){
                titles.setSelection(i);
            }
        }
        for (int i = 0; i < idList.length; i++){
            if(idList[i].contains(main.getIDType())){
                idType.setSelection(i);
            }
        }
        for (int i = 0; i < genderList.length; i++){
            if(genderList[i].contains(main.getGender())){
                Gender.setSelection(i);
            }
        }
        for (int i = 0; i < mList.length; i++){
            if(mList[i].contains(main.getMaritalStatus())){
                mStatus.setSelection(i);
            }
        }
        for (int i = 0; i < raceList.length; i++){
            if(raceList[i].contains(main.getRace())){
                race.setSelection(i);
            }
        }
        for (int i = 0; i < rList.length; i++){
            if(rList[i].contains(main.getTypeofResidence())){
                rType.setSelection(i);
            }
        }

        this.Create = findViewById(R.id.mainCreate);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountHolder updated = new AccountHolder();
                updated.setCIFID(main.getCIFID());
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
                updateAccountHolder(updated);
                Log.d("updated", "yes");

                Intent i = new Intent(updateMainHolder.this, jointHolderoption.class);
                updateMainHolder.this.startActivity(i);


                //intent to jointholder option
            }
        });









        //when creating a new joint account main holder check his details and directs it to choose existing user or new user to scan IC



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

    private void updateAccountHolder(AccountHolder holder){
        try {
            //conn = connectionclass();
            conn = new AccountHolderDAL().AccountHolderConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String query = "UPDATE AccountHolder SET ID= '" + holder.getID() + "', IDType= '" + holder.getIDType() + "' , Nationality= '" + holder.getNationality() +"' , Salutation= '" + holder.getSalutation() +"' , Name= '" + holder.getName()+
                        "' , DoB= '" + holder.getDOB() +"' , Gender= '"+ holder.getGender() +"' , MaritalStatus= '" + holder.getMaritalStatus() + "' , Race= '" + holder.getRace() + "' , TypeofResidence= '"+  holder.getTypeofResidence() +"' , Address= '"+ holder.getAddress() +
                        "' , PostalCode= '"+ holder.getPostalCode()+"' , Email= '"+ holder.getEmail()+"' , PhoneNo= '" + holder.getPhoneNo() +"' , Occupation= '" + holder.getOccupation() +
                        "' WHERE CIFID = '" + holder.getCIFID() +"'";
                Log.d("query", query);
                Log.d("id ", String.valueOf(holder.getCIFID().length()));
                Statement stint = conn.createStatement();
                //stint.executeUpdate(query);
                stint.executeUpdate(query);
                //ResultSet rs = stint.executeQuery(query);
                conn.close();
            }
        } catch (Exception ex) {
            Log.d("error", ex.getMessage());
        }

    }

//    private void createAccount(AccountHolder holder) {
//
//        try {
//            //conn = connectionclass();
//            conn = new AccountHolderDAL().AccountHolderConnection();
//            if (conn == null) {
//                Log.d("fuck", "you internet");
//            } else {
//                String query = "INSERT INTO AccountHolder (CIFID, ID, IDType, Nationality, Salutation, Name, DoB, Gender, MaritalStatus, Race, TypeofResidence, Address, " +
//                        "PostalCode, Email, PhoneNo, Occupation, Password) " +
//                        "VALUES ('" + holder.getCIFID().substring(0,8) + "', '" + holder.getID() + "', '" + "Singapore NRIC" + "', '" + holder.getNationality() + "', '" + holder.getSalutation() + "', '" +
//                        holder.getName() + "', '" + holder.getDOB() + "', '" + "Male" + "', '" + holder.getMaritalStatus() + "', '" + holder.getRace() + "', '" +
//                        "Terrace / Bungalow" + "', '" + holder.getAddress() + "', '" + holder.getPostalCode() + "', '" + holder.getEmail() + "', '" + holder.getPhoneNo() + "', '" +
//                        holder.getOccupation() + "', '" + holder.getPassword() + "')";
//
//                Statement stint = conn.createStatement();
//                Log.d("query", query);
//                stint.execute(query);
//                conn.close();
//            }
//        } catch (Exception ex) {
//            Log.d("error", ex.getMessage());
//        }
//    }
}
