package com.example.safecity.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
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
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.report.SendMessage;
import com.example.safecity.connection.user.DefaultResult;
import com.example.safecity.data.user.User;
import com.example.safecity.databinding.FragmentHomeBinding;
import com.example.safecity.ui.incident_report.IncidentReportFragment;
import com.example.safecity.ui.login.LoginActivity;
import com.example.safecity.ui.login.NewPassword;
import com.google.android.gms.location.LocationListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements LocationListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    FragmentTransaction transaction;
    Fragment fragmentReport;


    protected LocationManager locationManager;
    Location location = new Location("dummyprovider");
    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.ACCESS_FINE_LOCATION",

    };

    private double TIME =  5 * 60 * 1000;

    double latitude;
    double longitude;
    Button sosButton;
    Button reportarButton;
    Button sosCancelarButton;

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

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // estado de GPS
        final boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // estado de la conexion
        final boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            settingMessage();
        }

        sosButton = view.findViewById(R.id.sosButton);
        reportarButton = view.findViewById(R.id.reportarButton);
        sosCancelarButton = view.findViewById(R.id.sosCancelButton);

        final NavController navController = Navigation.findNavController(view);

        sosButton.setOnClickListener(v -> showSosDialog());
        sosButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                }else{
                    Toast.makeText(getContext(), "Su dispositivo no soporta uso del micrófono", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        reportarButton.setOnClickListener(v -> onReportClicked(navController));
        sosCancelarButton.setOnClickListener(v -> onCancelar());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode == -1 && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).equals("ayuda")){
                        showSosDialog();
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showSosDialog() {

        if (allPermissionsGranted()) {
            performSos();
        } else {
            settingMessage();
        }

    }

    private void performSos() {
        Log.i(getTag(),"Coordenada del boton: "+ latitude+"," + longitude);
        String message = User.name+" necesita ayuda, esta es su ubicación :"+"https://www.google.es/maps?q="+latitude+","+longitude;

        Log.i(getTag(), message);
        SendMessage sendMessage = new SendMessage(message);
        Call<Void> call = MainRetrofit.reportAPI.postSendMessage(User.id, sendMessage);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200){
                    Toast.makeText(getContext(), "Se envió la solicitud de ayuda.",Toast.LENGTH_LONG ).show();
                    sosButton.setVisibility(View.INVISIBLE);
                    sosCancelarButton.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getContext(), "No se pudo enviar la solicitud, intente nuevamente.",Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude =  location.getLatitude();
        longitude = location.getLongitude();

        location.setLatitude(latitude);
        location.setLongitude(longitude);

        Log.i(getTag(),"Coordenada: "+ location.getLatitude()+"," + location.getLongitude());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                settingMessage();
                return;
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) TIME, 5, this::onLocationChanged);
            }
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, (long) TIME, 5, this::onLocationChanged);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this::onLocationChanged);
    }


    private void onReportClicked(NavController navController) {
        navController.navigate(R.id.nav_incident_report);
    }

    private void onCancelar() {
        sosButton.setVisibility(View.VISIBLE);
        sosCancelarButton.setVisibility(View.INVISIBLE);
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

    private void settingMessage() {
        new AlertDialog.Builder(getContext())
                .setTitle("ERROR")
                .setMessage("Compruebe su conexión a internet o GPS")
                .setPositiveButton("Configuración",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("cancelar", (((dialog, which) -> dialog.dismiss())))
                .show();
    }


}