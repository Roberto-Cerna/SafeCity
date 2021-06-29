package com.example.safecity.ui.incident_report;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.safecity.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textFeatureReportInput;
    public ReportFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFormFragment newInstance(String param1, String param2) {
        ReportFormFragment fragment = new ReportFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle ) {
                String reportFeature = bundle.getString("key");

                Log.i(getTag(), reportFeature);
                textFeatureReportInput.setText(reportFeature);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        textFeatureReportInput = view.findViewById(R.id.textFeatureReportInput);

        Button denunciarButton = view.findViewById(R.id.reportButton);

        denunciarButton.setOnClickListener(v -> onReport(navController));
    }

    private void onReport(NavController navController) {

        new AlertDialog.Builder(getContext())
                .setTitle("Formulario de Denuncia")
                .setMessage("Se envió su denuncia correctamente.")
                .setPositiveButton("OK", ((dialog, which) -> dialog.dismiss()))
                .show();
        navController.navigate(R.id.nav_home);

    }
}