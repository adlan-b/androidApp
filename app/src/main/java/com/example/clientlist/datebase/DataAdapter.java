package com.example.clientlist.datebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.R;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {

    private List<Client> clientListArray;
    private AdapterOnItemClicked adapterOnItemClicked;
    private Context context;
    private SharedPreferences def_pref;


    public int[] colorArray = {R.drawable.circle_red, R.drawable.circle_blue, R.drawable.circle_green};


    public DataAdapter(List<Client> clientListArray, AdapterOnItemClicked adapterOnItemClicked, Context context) {
        this.clientListArray = clientListArray;
        this.adapterOnItemClicked = adapterOnItemClicked;
        this.context = context;
        def_pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolderData(view, adapterOnItemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setData(clientListArray.get(position));
    }

    @Override
    public int getItemCount() {
        return clientListArray.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvSecName;
        TextView tvTel;
        TextView textName;
        TextView textTel;
        ImageView imImportance;
        ImageView imSpecial;
        private AdapterOnItemClicked adapterOnItemClicked;

        public ViewHolderData(@NonNull View itemView, AdapterOnItemClicked adapterOnItemClicked) {
            super(itemView);
            textName = itemView.findViewById(R.id.textView2);
            textTel = itemView.findViewById(R.id.textView4);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSecName = itemView.findViewById(R.id.tv_sec_name);
            tvTel = itemView.findViewById(R.id.tv_telefon);
            imImportance = itemView.findViewById(R.id.imImportance);
            imSpecial = itemView.findViewById(R.id.imSpecial);
            this.adapterOnItemClicked = adapterOnItemClicked;
            itemView.setOnClickListener(this);

        }

        public void setData(Client client) {
            tvName.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                    .getString(R.string.text_name_color_key), "#070707")));
            tvName.setText(client.getName());
            tvSecName.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                    .getString(R.string.text_sec_name_color_key), "#070707")));
            tvSecName.setText(client.getSec_name());
            tvTel.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                    .getString(R.string.text_tel_color_key), "#070707")));
            textName.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                    .getString(R.string.text_tel_and_name_color_key), "#070707")));
            textTel.setTextColor(Color.parseColor(def_pref.getString(context.getResources()
                    .getString(R.string.text_tel_and_name_color_key), "#070707")));
            tvTel.setText(client.getNumber());
            imImportance.setImageResource(colorArray[client.getImportance()]);
            if (client.getSpecial() == 1) {
                imSpecial.setVisibility(View.VISIBLE);
            } else {
                imSpecial.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View view) {
            adapterOnItemClicked.onAdapterItemClicked(getAdapterPosition());

        }
    }

    public void updateAdapter(List<Client> clientList) {
        clientListArray.clear();
        clientListArray.addAll(clientList);
        notifyDataSetChanged();
    }

    public interface AdapterOnItemClicked {
        void onAdapterItemClicked(int position);
    }


}
