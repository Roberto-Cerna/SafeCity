package com.example.safecity.ui.incident_report;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.safecity.R;
import com.example.safecity.data.user.User;

import org.jetbrains.annotations.NotNull;


public class IncidentReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncidentReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncidentReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncidentReportFragment newInstance(String param1, String param2) {
        IncidentReportFragment fragment = new IncidentReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.incident_report_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button  yourselfButton = view.findViewById(R.id.yourselfButton);
        Button anotherPersonButton = view.findViewById(R.id.anotherPersonButton);

        final NavController navController = Navigation.findNavController(view);

        yourselfButton.setOnClickListener(v -> onIncidentReport(navController, User.name));
        anotherPersonButton.setOnClickListener(v -> onIncidentReport(navController, "Anónimo"));

    }

    private void onIncidentReport(NavController navController, String how) {

        Bundle bundle = new Bundle();
        bundle.putString("id", how);
        getParentFragmentManager().setFragmentResult("id",bundle);

        navController.navigate(R.id.nav_report_feature);
    }


}