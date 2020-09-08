package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.helloapplication.R;
import java.util.ArrayList;
import Model.Model_class;

public class New_Adapter extends RecyclerView.Adapter<New_Adapter.MyViewHolder>{

    private ArrayList<Model_class> Grocery_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, item, price;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.textViewId);
            item = (TextView) view.findViewById(R.id.textViewItem);
            price = (TextView) view.findViewById(R.id.textPrice);

        }

    }
    public New_Adapter(ArrayList<Model_class> grocery_list) {
        Grocery_list = grocery_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(Grocery_list.get(position).getId()));
        holder.item.setText(Grocery_list.get(position).getItem());
        holder.price.setText(String.valueOf(Grocery_list.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return Grocery_list.size();
    }


}
