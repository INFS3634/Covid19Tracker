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

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.covid19tracker.intent_message";
    private TextView mCountry, mNewCases, mTotalCases, mNewDeaths, mTotalDeaths, mNewRecovered, mTotalRecovered;
    private ImageView mSearch;

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

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (intent.hasExtra(INTENT_MESSAGE)) {
            Log.d(TAG, "INTENT_MESSAGE = " + bundle.getStringArrayList(INTENT_MESSAGE) );
            String countryCode = intent.getStringExtra(INTENT_MESSAGE);
            ArrayList<Country> countries = Country.getCountries();
            for(final Country country : countries) {
                if (country.getCountryCode().equals(countryCode)) {
                    // Set title of the activity
                    setTitle(country.getCountryCode());
                    // Set the country name
                    mCountry.setText(country.getCountry());
                    // Set value for all other text view elements
                    mNewCases.setText(country.getNewConfirmed().toString());
                    mTotalCases.setText(country.getTotalConfirmed().toString());
                    mNewDeaths.setText(country.getNewDeaths().toString());
                    mTotalDeaths.setText(country.getTotalDeaths().toString());
                    mNewRecovered.setText(country.getNewRecovered().toString());
                    mTotalRecovered.setText(country.getTotalRecovered().toString());
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
    }
}