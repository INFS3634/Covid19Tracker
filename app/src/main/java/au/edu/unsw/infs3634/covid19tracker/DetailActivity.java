package au.edu.unsw.infs3634.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.covid19tracker.intent_message";
    private TextView mCountry, mNewCases, mTotalCases, mNewDeaths, mTotalDeaths, mNewRecovered, mTotalRecovered;
    private ImageView mSearch, mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mCountry = findViewById(R.id.tvCountry);
        mNewCases = findViewById(R.id.tvNewCases);
        mTotalCases = findViewById(R.id.tvTotalCases);
        mNewDeaths = findViewById(R.id.tvNewDeaths);
        mTotalDeaths = findViewById(R.id.tvTotalDeaths);
        mNewRecovered = findViewById(R.id.tvNewRecovered);
        mTotalRecovered = findViewById(R.id.tvTotalRecovered);
        mSearch = findViewById(R.id.ivSearch);
        mFlag = findViewById(R.id.ivFlag);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            Log.d(TAG, "INTENT_MESSAGE = " + bundle.getStringArrayList(INTENT_MESSAGE) );
            String countryCode = intent.getStringExtra(INTENT_MESSAGE);

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
                    List<Country> countries = response.body().getCountries();
                    for(final Country country : countries) {
                        if (country.getCountryCode().equals(countryCode)) {
                            DecimalFormat df = new DecimalFormat( "#,###,###,###" );
                            // Set title of the activity
                            setTitle(country.getCountryCode());
                            Glide.with(DetailActivity.this)
                                    .load("https://flagcdn.com/96x72/" + country.getCountryCode().toLowerCase() + ".png")
                                    .fitCenter()
                                    .into(mFlag);

                            // Set the country name
                            mCountry.setText(country.getCountry());
                            // Set value for all other text view elements
                            mNewCases.setText(df.format(country.getNewConfirmed()));
                            mTotalCases.setText(df.format(country.getTotalConfirmed()));
                            mNewDeaths.setText(df.format(country.getNewDeaths()));
                            mTotalDeaths.setText(df.format(country.getTotalDeaths()));
                            mNewRecovered.setText(df.format(country.getNewRecovered()));
                            mTotalRecovered.setText(df.format(country.getTotalRecovered()));
                            // Add an intent to open Google search for "Covid19" + country name
                            mSearch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.au/search?q=covid " + country.getCountry()));
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d(TAG, "API call fails");
                }
            });



        }
    }
}