package com.example.safecity.ui.report;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.safecity.R;
import com.example.safecity.data.report_item.AttendingReport;
import com.example.safecity.data.reports_list.ReportsList;
import com.example.safecity.databinding.ReportFragmentBinding;
import com.example.safecity.data.report_item.ReportItem;
import com.example.safecity.ui.recent_reports.RecentReportsFragment;
import com.example.safecity.ui.report_maps.ReportMapsFragment;
import com.squareup.picasso.Picasso;

public class ReportFragment extends Fragment {

    private ReportViewModel mViewModel;
    private ReportFragmentBinding binding;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ReportFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.reportTypeTextField.getEditText().setText(ReportItem.type);
        binding.detailsTextField.getEditText().setText(ReportItem.details);

        String fileURL = ReportItem.fileURL;
        if(!fileURL.equals("")) {
            Picasso.get().load(fileURL).into(binding.reportImageView);
        }

        boolean attending = (ReportItem.id.equals(AttendingReport.id));
        binding.supportButton.setVisibility((attending) ? View.INVISIBLE : View.VISIBLE);
        binding.unsupportButton.setVisibility((attending) ? View.VISIBLE : View.INVISIBLE);

        binding.supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportMapsFragment.attending = true;
                binding.supportButton.setVisibility(View.INVISIBLE);
                binding.unsupportButton.setVisibility(View.VISIBLE);
                AttendingReport.id = ReportItem.id;
                AttendingReport.type = ReportItem.type;
                AttendingReport.locationLatitude = ReportItem.locationLatitude;
                AttendingReport.locationLongitude = ReportItem.locationLongitude;
                AttendingReport.daysAgo = ReportItem.daysAgo;
                AttendingReport.victim = ReportItem.victim;
            }
        });

        binding.openReportMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_report_item_map);
            }
        });

        binding.unsupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportMapsFragment.attending = false;
                binding.unsupportButton.setVisibility(View.INVISIBLE);
                binding.supportButton.setVisibility(View.VISIBLE);
                AttendingReport.id = "";
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}