package com.example.gestionbibliotheque.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gestionbibliotheque.R;

import java.util.List;

public class ListViewCommandAdapter extends BaseAdapter {
    private List<Commande> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListViewCommandAdapter(Context aContext, List<Commande> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewCommandAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_list_view_commande, null);
            holder = new ListViewCommandAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textViewListTitle2);
            holder.date = (TextView) convertView.findViewById(R.id.textViewListDate);
            convertView.setTag(holder);
        } else {
            holder = (ListViewCommandAdapter.ViewHolder) convertView.getTag();
        }

        Commande commande = this.listData.get(position);
        holder.title.setText("Commande de " + commande.getTitle() + " (x" + commande.getNbExemplaire() + ") ");
        holder.date.setText("> Commandé le : " + commande.getDate_commande() + "| Livraison le : " + commande.getDate_livraison());

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView date;
    }
}