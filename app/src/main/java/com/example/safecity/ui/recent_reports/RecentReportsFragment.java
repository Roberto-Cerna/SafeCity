package com.example.safecity.ui.recent_reports;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.safecity.R;
import com.example.safecity.databinding.RecentReportsFragmentBinding;
import com.example.safecity.data.reports_list.Report;
import com.example.safecity.data.reports_list.ReportsList;
import com.example.safecity.data.report_item.ReportItem;

import java.util.ArrayList;
import java.util.Objects;

public class RecentReportsFragment extends Fragment {

    private RecentReportsViewModel mViewModel;
    private RecentReportsFragmentBinding binding;

    //public ArrayList<Report> reports = new ArrayList<>();
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

        reportsListAdapter = new ReportsListAdapter(requireContext(),
                R.layout.list_item_report, ReportsList.reports_list);
        reportsListView.setAdapter(reportsListAdapter);

        reportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportItem.type = ReportsList.reports_list.get(position).type;
                ReportItem.details = ReportsList.reports_list.get(position).details;

                ReportsList.reports_list.get(position).isSeen = true;

                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_report);

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecentReportsViewModel.class);
        // TODO: Use the ViewModel
    }

}