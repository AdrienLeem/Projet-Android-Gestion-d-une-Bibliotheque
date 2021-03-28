package com.example.gestionbibliotheque.Model;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gestionbibliotheque.DB.DataBaseHelper;
import com.example.gestionbibliotheque.R;
import com.example.gestionbibliotheque.User.ConsultEmpruntActivity;

import java.util.List;

public class ListViewEmpruntAdapter extends BaseAdapter {
    private List<Emprunt> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListViewEmpruntAdapter(Context aContext,  List<Emprunt> listData) {
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
            convertView = layoutInflater.inflate(R.layout.activity_list_view_emprunt, null);
            holder = new ListViewCommandAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textViewListTitle3);
            holder.date = (TextView) convertView.findViewById(R.id.textViewListDate2);
            convertView.setTag(holder);
        } else {
            holder = (ListViewCommandAdapter.ViewHolder) convertView.getTag();
        }

        Emprunt emprunt = this.listData.get(position);

        holder.title.setText("Emprunt de \"" + emprunt.getBook().getTitle() + "\" de " + emprunt.getBook().getAuthor());
        holder.date.setText("> Emprunté le : " + emprunt.getDate_deb() + " | À rendre le : " + emprunt.getDate_fin());

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView date;
    }
}
