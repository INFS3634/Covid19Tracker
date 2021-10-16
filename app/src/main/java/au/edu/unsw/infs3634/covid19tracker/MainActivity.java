package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a handle to the RecyclerView
        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);

        // Set the layout manager (Linear or Grid)
        layoutManager = new LinearLayoutManager(this);
        // layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Implement the ClickListener for the adapter
        CountryAdapter.RecyclerViewClickListener listener = new CountryAdapter.RecyclerViewClickListener() {
            @Override
            public void onCountryClick(View view, String countryCode) {
                launchDetailActivity(countryCode);
            }
        };
        // Create an adapter and supply the countries data to be displayed
        mAdapter = new CountryAdapter(Country.getCountries(), listener);
        // Connect the adapter with the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }

    // Called when the user taps the Launch Detail Activity button
    private void launchDetailActivity(String message){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }

    // Instantiate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // React to user interaction with the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_new:
                // sort by new cases
                mAdapter.sort(CountryAdapter.SORT_METHOD_NEW);
                return true;
            case R.id.sort_total:
                // sort by total cases
                mAdapter.sort(CountryAdapter.SORT_METHOD_TOTAL);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}