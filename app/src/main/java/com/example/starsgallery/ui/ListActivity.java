package com.example.starsgallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starsgallery.R;
import com.example.starsgallery.adapter.StarAdapter;
import com.example.starsgallery.service.StarService;

public class ListActivity extends AppCompatActivity {

    private StarAdapter starAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView rv = findViewById(R.id.recycle_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        
        starAdapter = new StarAdapter(this, StarService.getInstance().findAll());
        rv.setAdapter(starAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("ListActivity", "Texte saisi : " + newText);
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Stars Gallery App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Découvrez les meilleures stars sur Stars Gallery !");
            startActivity(Intent.createChooser(shareIntent, "Partager via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}