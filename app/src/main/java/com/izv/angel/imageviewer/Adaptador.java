package com.izv.angel.imageviewer;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Adaptador extends CursorAdapter {

    private View v;

    public class ViewHolder {
        ImageView iv1;
    }

    public Adaptador(Context context, Cursor c) {
        super(context, c,true);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        v = i.inflate(R.layout.detalle, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh = new ViewHolder();
        vh.iv1 = (ImageView) view.findViewById(R.id.ivFotos);
        Uri path = Uri.parse(cursor.getString(1));
        vh.iv1.setTag(cursor.getString(1));
        Picasso.with(context).load("file://" + path).resize(350, 350).centerCrop().into(vh.iv1);
        view.setTag(vh);
    }

}
