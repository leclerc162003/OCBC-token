package sg.edu.np.onetokenocbc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.txtHolderName.setText("Main ID. " + d.getMainHolderID());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
