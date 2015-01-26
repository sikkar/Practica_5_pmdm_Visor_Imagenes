package com.izv.angel.imageviewer;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;


public class Principal extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private GridView grid;
    private Adaptador ad;
    private Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_VIEW) && intent.getType() !=null ) {
            Intent verImagen = new Intent(this, Visor.class);
            path = intent.getData();
            verImagen.putExtra("path", path);
            startActivity(verImagen);
        }else {
            initComponents();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA
        };
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ad.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ad.swapCursor(null);
    }

    public void initComponents(){
        grid = (GridView) findViewById(R.id.gridView);
        ad = new Adaptador(this, null);
        grid.setAdapter(ad);
        getLoaderManager().initLoader(0, null, this);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verImagen = new Intent(view.getContext(), Visor.class);
                Object o = view.getTag();
                Adaptador.ViewHolder vh;
                vh = (Adaptador.ViewHolder)o;
                String s=(String)vh.iv1.getTag();
                Uri u = Uri.parse("file://"+s);
                verImagen.putExtra("path",u);
                Log.v("uri",u.toString());
                startActivity(verImagen);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
