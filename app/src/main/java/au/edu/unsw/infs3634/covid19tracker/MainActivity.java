package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CountyDatabase mDb;

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
        mAdapter = new CountryAdapter(new ArrayList<Country>(), listener);
        // Connect the adapter with the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        //Instantiate a CountryDatabase object
        mDb = Room.databaseBuilder(getApplicationContext(), CountyDatabase.class, "country-database").build();

        // Create an asynchronous database call using Java Runnable to:
        // get the list of countries from the database
        // Set the adapter using the result
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Country> countries = (ArrayList<Country>) mDb.countryDao().getCountries();
                mAdapter.setCountry(countries);
                mAdapter.sort(CountryAdapter.SORT_METHOD_NEW);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidService service = retrofit.create(CovidService.class);
        Call<Response> responseCall = service.getResponse();

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d(TAG, "API call successful");
                // Create an asynchronous database call using Java Runnable to:
                // Delete all rows currently in the database
                // Add all rows from API call result into the database
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.countryDao().deleteAll(mDb.countryDao().getCountries().toArray(new Country[0]));
                        mDb.countryDao().insertAll(response.body().getCountries().toArray(new Country[0]));
                        ArrayList<Country> countries = (ArrayList<Country>) mDb.countryDao().getCountries();
                        // Update the view in CountryAdapter in UI Thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setCountry(countries);
                                mAdapter.sort(CountryAdapter.SORT_METHOD_NEW);
                            }
                        });
                    }
                });
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG, "API call fails");
            }
        });
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