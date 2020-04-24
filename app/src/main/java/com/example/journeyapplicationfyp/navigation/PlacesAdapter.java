package com.example.journeyapplicationfyp.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class PlacesAdapter extends ArrayAdapter<Places> {
    private Places[] places;
    private ArrayList<Places> items;
    private ArrayList<Places> itemsAll;
    private ArrayList<Places> suggestions;
    Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Places places : itemsAll) {
                    if (places.placeName.toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(places);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Places> filteredList = (ArrayList<Places>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Places c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }

        public String convertResultToString(Object resultValue) {
            String str = ((Places) resultValue).placeName;
            return str;
        }
    };
    private int viewResourceId;


    public PlacesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Places> objects) {
        super(context, 0, objects);
        viewResourceId = resource;
        this.items = objects;
        this.itemsAll = (ArrayList<Places>) items.clone();
        this.suggestions = new ArrayList<Places>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(parent.getContext()).inflate(viewResourceId, parent, false);

        Places place = items.get(position);
        TextView name = (TextView) listItem;
        name.setText(place.placeName);

        return listItem;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    public void update(Places[] items) {
        places = Arrays.copyOf(items, items.length);
        notifyDataSetChanged();

    }
}
