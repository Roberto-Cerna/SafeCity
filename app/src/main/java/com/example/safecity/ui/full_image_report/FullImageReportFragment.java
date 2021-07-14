package com.example.safecity.ui.full_image_report;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safecity.R;
import com.example.safecity.data.report_item.ReportItem;
import com.example.safecity.databinding.FullImageReportFragmentBinding;
import com.example.safecity.databinding.ReportFragmentBinding;
import com.squareup.picasso.Picasso;

public class FullImageReportFragment extends Fragment {

    private FullImageReportViewModel mViewModel;
    private FullImageReportFragmentBinding binding;

    public static FullImageReportFragment newInstance() {
        return new FullImageReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FullImageReportFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String fileURL = ReportItem.fileURL;
        if(!fileURL.equals("")) {
            Picasso.get().load(fileURL).into(binding.fullSizeImageView);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FullImageReportViewModel.class);
        // TODO: Use the ViewModel
    }

}