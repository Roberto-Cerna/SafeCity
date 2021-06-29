package com.example.safecity.ui.recent_reports;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.safecity.R;
import com.example.safecity.databinding.EmergencyContactsFragmentBinding;
import com.example.safecity.databinding.RecentReportsFragmentBinding;
import com.example.safecity.ui.emergency_contacts.EmergencyContact;
import com.example.safecity.ui.emergency_contacts.EmergencyContactsListAdapter;

import java.util.ArrayList;

public class RecentReportsFragment extends Fragment {

    private RecentReportsViewModel mViewModel;
    private RecentReportsFragmentBinding binding;

    public ArrayList<Report> reports = new ArrayList<>();
    public ReportsListAdapter reportsListAdapter;
    public ListView reportsListView;

    public static RecentReportsFragment newInstance() {
        return new RecentReportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RecentReportsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reportsListView = binding.recentReportsListView;

        reports.add(new Report("Edwin Yauyo", "Robo", false, "5h"));
        reports.add(new Report("Donald Kun", "Accidente", true, "5h"));
        reports.add(new Report("Edwin Yauyo", "Robo", false, "5h"));
        reports.add(new Report("Donald Kun", "Accidente", true, "5h"));
        reports.add(new Report("Edwin Yauyo", "Robo", false, "5h"));
        reports.add(new Report("Donald Kun", "Accidente", true, "5h"));
        reports.add(new Report("Edwin Yauyo", "Robo", false, "5h"));
        reports.add(new Report("Donald Kun", "Accidente", true, "5h"));

        reportsListAdapter = new ReportsListAdapter(requireContext(),
                R.layout.list_item_report, reports);
        reportsListView.setAdapter(reportsListAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecentReportsViewModel.class);
        // TODO: Use the ViewModel
    }

}