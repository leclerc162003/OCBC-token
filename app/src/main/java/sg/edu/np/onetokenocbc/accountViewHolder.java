package sg.edu.np.onetokenocbc;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class accountViewHolder extends RecyclerView.ViewHolder {
    TextView txtAccountNo;
    TextView txtHolderName;
    TextView sub;

    public accountViewHolder(@NonNull View itemView) {
        super(itemView);
        txtAccountNo = itemView.findViewById(R.id.txtAccounts);
        txtHolderName = itemView.findViewById(R.id.txtName);
        sub = itemView.findViewById(R.id.subName);
    }
}
