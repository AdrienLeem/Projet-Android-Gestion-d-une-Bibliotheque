package com.example.gestionbibliotheque.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gestionbibliotheque.R;

import java.util.List;

public class ListViewBookAdapter extends BaseAdapter {
    private List<Book> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListViewBookAdapter(Context aContext,  List<Book> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_list_view, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.imageView_book);
            holder.title = (TextView) convertView.findViewById(R.id.textViewListTitle);
            holder.author = (TextView) convertView.findViewById(R.id.textViewListAuthor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = this.listData.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        byte[] bookImage = book.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bookImage, 0, bookImage.length);
        holder.image.setImageBitmap(bitmap);

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView title;
        TextView author;
    }

}
