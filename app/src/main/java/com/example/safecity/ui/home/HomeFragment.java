package com.example.safecity.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.safecity.R;
import com.example.safecity.databinding.FragmentHomeBinding;
import com.example.safecity.ui.incident_report.IncidentReportFragment;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    FragmentTransaction transaction;
    Fragment fragmentReport;
//    private Button sosButton;
//    private Button reportButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentReport = new IncidentReportFragment();

        return root;

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sosButton = view.findViewById(R.id.sosButton);
        Button reportButton = view.findViewById(R.id.reportarButton);

        final NavController navController = Navigation.findNavController(view);

        sosButton.setOnClickListener(v -> showSosDialog());
        reportButton.setOnClickListener(v -> onReportClicked(navController));

    }

    private void onReportClicked(NavController navController) {
        navController.navigate(R.id.nav_incident_report);
    }

//    private void startReportActivity() {
//        Navigation.findNavController(v).navigate(R.id.);
//        Intent intent = new Intent(getContext(), IncidentReportFragment.class);
//        startActivity(intent);
//    }

    private void showSosDialog() {
        Log.i(getTag(), "GAAAAAAAAAAAAAAAA");
        performSos();
    }

    private void performSos() {
        new AlertDialog.Builder(getContext())
                .setTitle("SOS Activado")
                .setMessage("Se envió la solicitud de ayuda.")
                .setPositiveButton("OK", ((dialog, which) -> dialog.dismiss()))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}