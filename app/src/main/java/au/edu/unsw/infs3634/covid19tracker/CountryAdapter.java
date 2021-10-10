package au.edu.unsw.infs3634.covid19tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private ArrayList<Country> mCountries;
    private RecyclerViewClickListener mListener;

    // A constructor method for the adapter class
    public CountryAdapter(ArrayList<Country> countries, RecyclerViewClickListener listener){
        mCountries = countries;
        mListener = listener;
    }

    // ClickListener interface
    public interface RecyclerViewClickListener {
        void onCountryClick(View view, String countryCode);
    }


    // Create a view and return it
    @NonNull
    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_row, parent, false);
        return new CountryViewHolder(v, mListener);
    }

    // Associate the data with the view holder for a given position in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryViewHolder holder, int position) {
        Country country = mCountries.get(position);
        DecimalFormat df = new DecimalFormat( "#,###,###,###" );
        holder.country.setText(country.getCountry());
        holder.totalCases.setText(df.format(country.getTotalConfirmed()));
        holder.newCases.setText("+" + df.format(country.getNewConfirmed()));
        holder.itemView.setTag(country.getCountryCode());
    }

    // Return the number of data items available to display
    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    // Extend the signature of CountryViewHolder to implement a click listener
    public static class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView country, totalCases, newCases;
        private RecyclerViewClickListener listener;

        // A constructor method for CountryViewHolder class
        public CountryViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            country = itemView.findViewById(R.id.tvCountry);
            totalCases = itemView.findViewById(R.id.tvTotalCases);
            newCases = itemView.findViewById(R.id.tvNewCases);
        }

        @Override
        public void onClick(View v) {
            listener.onCountryClick(v, (String) v.getTag());
        }
    }
}
