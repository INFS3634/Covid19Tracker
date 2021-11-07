package au.edu.unsw.infs3634.covid19tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> implements Filterable {
    private List<Country> mCountries, mCountriesFiltered;
    private RecyclerViewClickListener mListener;
    public static final int SORT_METHOD_NEW = 1;
    public static final int SORT_METHOD_TOTAL = 2;


    // A constructor method for the adapter class
    public CountryAdapter(List<Country> countries, RecyclerViewClickListener listener){
        mCountries = countries;
        mCountriesFiltered = countries;
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mCountriesFiltered = mCountries;
                } else {
                    ArrayList<Country> filteredList = new ArrayList<>();
                    for (Country country : mCountries) {
                        if (country.getCountry().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(country);
                        }
                    }
                    mCountriesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mCountriesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCountriesFiltered = (ArrayList<Country>) results.values;
                notifyDataSetChanged();
            }
        };
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
        // Retrieve the country by it's position in filtered list
        Country country = mCountriesFiltered.get(position);
        DecimalFormat df = new DecimalFormat( "#,###,###,###" );
        holder.country.setText(country.getCountry());
        holder.totalCases.setText(df.format(country.getTotalConfirmed()));
        holder.newCases.setText("+" + df.format(country.getNewConfirmed()));
        holder.itemView.setTag(country.getCountryCode());
    }

    // Return the number of data items available to display
    @Override
    public int getItemCount() {
        return mCountriesFiltered.size();
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

    // Use the Java Collection.sort() and Comparator methods to sort the list
    public void sort(final int sortMethod){
        if (mCountriesFiltered.size() > 0){
            Collections.sort(mCountriesFiltered, new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    if (sortMethod == SORT_METHOD_NEW){
                        // Sort by new cases
                        return o2.getNewConfirmed().compareTo(o1.getNewConfirmed());
                    } else if (sortMethod == SORT_METHOD_TOTAL) {
                        // Sort by total cases
                        return o2.getTotalConfirmed().compareTo(o1.getTotalConfirmed());
                    }
                    return o2.getNewConfirmed().compareTo(o1.getNewConfirmed());
                }
            });
        }
        notifyDataSetChanged();
    }

    public void setCountry(List<Country> data){
        mCountriesFiltered.clear();
        mCountriesFiltered.addAll(data);
        notifyDataSetChanged();
    }
}
