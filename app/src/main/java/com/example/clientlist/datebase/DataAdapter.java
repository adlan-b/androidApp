package com.example.clientlist.datebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.R;

import java.util.List;



public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {

    private List<Client> clientListArray;
    private AdapterOnItemClicked adapterOnItemClicked;


    public int[] colorArray = {R.drawable.circle_red, R.drawable.circle_blue, R.drawable.circle_green,
            R.drawable.circle_gold};


    public DataAdapter(List<Client> clientListArray, AdapterOnItemClicked adapterOnItemClicked)
    {
        this.clientListArray = clientListArray;
        this.adapterOnItemClicked = adapterOnItemClicked;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolderData(view, adapterOnItemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position)
    {
        holder.setData(clientListArray.get(position));
    }

    @Override
    public int getItemCount()
    {
        return clientListArray.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView tvName;
        TextView tvSecName;
        TextView tvTel;
        ImageView imImportance;
        ImageView imSpecial;
        private AdapterOnItemClicked adapterOnItemClicked;

        public ViewHolderData(@NonNull View itemView, AdapterOnItemClicked adapterOnItemClicked)
        {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSecName = itemView.findViewById(R.id.tv_sec_name);
            tvTel = itemView.findViewById(R.id.tv_telefon);
            imImportance = itemView.findViewById(R.id.imImportance);
            imSpecial = itemView.findViewById(R.id.imSpecial);
            this.adapterOnItemClicked = adapterOnItemClicked;
            itemView.setOnClickListener(this);

        }

        public void setData(Client client)
        {
            tvName.setText(client.getName());
            tvSecName.setText(client.getSec_name());
            tvTel.setText(client.getNumber());
            imImportance.setImageResource(colorArray[client.getImportance()]);
            if (client.getSpecial() == 1)imSpecial.setVisibility(View.VISIBLE);


        }

        @Override
        public void onClick(View view)
        {
            adapterOnItemClicked.onAdapterItemClicked(getAdapterPosition());

        }
    }

    public void updateAdapter(List<Client> clientList)
    {
        clientListArray.clear();
        clientListArray.addAll(clientList);
        notifyDataSetChanged();


    }

    public interface AdapterOnItemClicked
    {
         void onAdapterItemClicked(int position);
    }


}
