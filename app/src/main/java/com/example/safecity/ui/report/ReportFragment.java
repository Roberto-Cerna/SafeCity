package com.example.safecity.ui.report;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.safecity.R;
import com.example.safecity.databinding.ReportFragmentBinding;
import com.example.safecity.data.report_item.ReportItem;
import com.example.safecity.ui.recent_reports.RecentReportsFragment;

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}