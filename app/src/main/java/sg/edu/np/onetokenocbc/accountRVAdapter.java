package sg.edu.np.onetokenocbc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import sg.edu.np.onetokenocbc.R;
import sg.edu.np.onetokenocbc.accountViewHolder;

public class accountRVAdapter extends RecyclerView.Adapter<accountViewHolder> {
    Context context;
    ArrayList<AccountDetails> data;

    public accountRVAdapter(Context c, ArrayList<AccountDetails> d){
        context = c;
        data = d;
    }

    @NonNull
    @Override
    public accountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_joint_account_cell, parent, false);

        return new accountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull accountViewHolder holder, int position) {
        AccountDetails d = data.get(position);
        holder.txtAccountNo.setText("Account No. " + d.getAccountNo());
        holder.txtHolderName.setText("Main Name: " + getAccountHolder(d.getMainHolderID()).getName());
        holder.sub.setText("Sub Name: " + getAccountHolder(d.getSubHolderID()).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private AccountHolder getAccountHolder(String cfid) {
        AccountHolder holder = new AccountHolder();
        try {
            //conn = connectionclass();
            Connection conn = new AccountHolderDAL().AccountHolderConnection();
            //testConnection testConnection = new testConnection();
            if (conn == null) {
                Log.d("fuck", "you internet");
            } else {
                String cifid = "";
                Log.d("fuck", "it worked");
                String query = "select * from AccountHolder" + " WHERE CIFID = '" + cfid.trim() + "'";
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
}
