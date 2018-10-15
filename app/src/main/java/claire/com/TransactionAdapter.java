package claire.com;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> trans;

    public TransactionAdapter(List<Transaction> trans){
        this.trans = trans;
    }

    //RecycleView需要顯示一列記錄，會呼叫Adapter內的這個方法先取得一個ViewHolder物件
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_trans, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //當RecyclerView準備要展示一列特定位置(position)的記錄時，會呼叫此方法，
    //在此方法前已自動執行了上一個方法得得ViewHolder物件了，也就是參數中的holder
    //因此這個方法中只需要將holder物件中的元件設定為想要的內容
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TRANS", position + "");
        Transaction tran = trans.get(position);
        holder.dateTextView.setText(tran.getDate());
        holder.amountTextView.setText(tran.getAmount() + "");
        holder.typeTextView.setText(tran.getType() + "");

    }

    @Override
    public int getItemCount() {
        //取得目前資料筆數
        if (trans != null){
            return trans.size();
        } else
            return 0;
    }

    //設計一個類別層級的ViewHolder類別，繼承RecyclerView.ViewHolder
    //在此內類別中設計一筆交易在畫面上的元件屬性
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateTextView;
        TextView amountTextView;
        TextView typeTextView;

        public ViewHolder(View itemView){
            super(itemView);
            dateTextView = itemView.findViewById(R.id.col_date);
            amountTextView = itemView.findViewById(R.id.col_amount);
            typeTextView = itemView.findViewById(R.id.col_type);
        }

    }
}
