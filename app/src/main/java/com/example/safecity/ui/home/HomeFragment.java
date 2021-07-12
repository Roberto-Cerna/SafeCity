package com.example.safecity.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
//    private Button sosButton;
//    private Button reportButton;

    protected LocationManager locationManager;
    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.ACCESS_FINE_LOCATION"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentReport = new IncidentReportFragment();

        if(!allPermissionsGranted()){
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        return root;

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sosButton = view.findViewById(R.id.sosButton);
        Button reportarButton = view.findViewById(R.id.reportarButton);

        final NavController navController = Navigation.findNavController(view);

        sosButton.setOnClickListener(v -> showSosDialog());
        reportarButton.setOnClickListener(v -> onReportClicked(navController));

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
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // estado de GPS
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // estado de la conexion
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isGPSEnabled && !isNetworkEnabled){
            new AlertDialog.Builder(getContext())
                    .setTitle("ERROR")
                    .setMessage("Compruebe su conexión a internet o GPS")
                    .setPositiveButton("ok", (((dialog, which) -> dialog.dismiss())))
                    .show();
        }else {
//            locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER);
            performSos();
            Log.i(getTag(), "GAAAAAAAA");
        }


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

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            Log.i(getTag(), permission);
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}