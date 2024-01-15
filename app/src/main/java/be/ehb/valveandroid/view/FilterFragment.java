package be.ehb.valveandroid.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.ehb.valveandroid.R;
import be.ehb.valveandroid.view.viewutils.GameAdapter;

public class FilterFragment extends DialogFragment {

    private EditText filterRatingInput;
    private Spinner filterReleaseDateInput;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        filterRatingInput = rootView.findViewById(R.id.et_filter_rating);
        filterReleaseDateInput = rootView.findViewById(R.id.sp_filter_released);

        populateYearSpinner();

        // Apply filter on button click
        Button applyFilterButton = rootView.findViewById(R.id.btn_filter);
        applyFilterButton.setOnClickListener(v -> applyFilter());

        return rootView;
    }

    private void populateYearSpinner() {
        // Assuming you want to show years from 2000 to the current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<String> years = new ArrayList<>();
        for (int year = 2000; year <= currentYear; year++) {
            years.add(String.valueOf(year));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, years);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        filterReleaseDateInput.setAdapter(adapter);
    }

    private void applyFilter() {
        String minRating = filterRatingInput.getText().toString();
        String selectedYear = filterReleaseDateInput.getSelectedItem().toString();
        String startDate = selectedYear + "-01-01";
        String endDate = selectedYear + "-12-31";

        // Find the GamesFragment using the fragment manager
        GamesFragment gamesFragment = (GamesFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.gamesFragment);

        // Call a method in GamesFragment to apply the filter
        if (gamesFragment != null) {
            gamesFragment.applyFilter(minRating, startDate, endDate);

            // Show a Toast message indicating the filter is applied
            Toast.makeText(requireContext(), "Filter applied successfully", Toast.LENGTH_SHORT).show();

            // Navigate to the GamesFragment after applying the filter
            new GameAdapter().navigateToGamesFragment();
        } else {
            // Handle the case when GamesFragment is not found by printing a log message
            Log.e("FilterFragment", "GamesFragment not found");
        }

        // Dismiss the current dialog fragment
        dismiss();
    }
}
