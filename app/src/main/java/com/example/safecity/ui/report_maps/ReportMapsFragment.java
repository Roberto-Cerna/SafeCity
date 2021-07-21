package com.example.safecity.ui.report_maps;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.safecity.R;
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.report.GetRecentReportsResult;
import com.example.safecity.data.report_item.AttendingReport;
import com.example.safecity.data.reports_list.Report;
import com.example.safecity.data.reports_list.ReportsList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Attr;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportMapsFragment extends Fragment {

    public static boolean attending = false;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLng;
    Marker currentLocationMarker;
    Location lastKnownLocation;
    boolean flag = false;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            //Setting of location service
            locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    googleMap.clear();
                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    currentLocationMarker = googleMap.addMarker(new MarkerOptions()
                            .position(userLatLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).title("Usted está aquí"));
                    if (!flag) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 16));
                        flag = true;
                    }

                    if (attending) {
                        LatLng attendingLatLng = new LatLng(Double.parseDouble(AttendingReport.locationLatitude), Double.parseDouble(AttendingReport.locationLongitude));
                        googleMap.addMarker(new MarkerOptions()
                                .position(attendingLatLng).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(AttendingReport.victim));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(attendingLatLng, 14));
                    } else {
                        for (Report report : ReportsList.reports_list) {
                            float color;
                            String title = report.incident;
                            Double daysAgo = report.daysAgo;
                            if (daysAgo >= 1) {
                                title += ", hace " + daysAgo.intValue() + " día(s)";
                                color = BitmapDescriptorFactory.HUE_GREEN;
                            } else {
                                double hoursAgo = 24 * daysAgo;
                                if (hoursAgo >= 1) {
                                    title += ", hace " + (int) (hoursAgo) + " hora(s)";
                                    color = BitmapDescriptorFactory.HUE_YELLOW;
                                } else {
                                    double minutesAgo = 60 * hoursAgo;
                                    if (minutesAgo == 0) title += ", hace unos instantes";
                                    else title += ", hace " + (int) minutesAgo + " minuto(s)";
                                    color = BitmapDescriptorFactory.HUE_RED;
                                }
                            }

                            LatLng reportLatLng = new LatLng(Double.parseDouble(report.locationLatitude),
                                    Double.parseDouble(report.locationLongitude));
                            googleMap.addMarker(new MarkerOptions()
                                    .position(reportLatLng).icon(BitmapDescriptorFactory
                                            .defaultMarker(color)).title(title));
                        }
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    final AlertDialog alert = new AlertDialog.Builder(requireContext()).setMessage("Parece que tu Ubicación está deshabilitada, ¿podrías activarla?")
                            .setCancelable(false)
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    dialog.cancel();
                                }
                            }).create();
                    alert.show();
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {

                }


            };

            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    userLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    currentLocationMarker = googleMap.addMarker(new MarkerOptions()
                            .position(userLatLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 16));
                }
            } else {
                requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (!attending) {
            new GetRecentReportsExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (!attending) {
                        Call<GetRecentReportsResult> call = MainRetrofit.reportAPI.getLastNIncidents("2");
                        call.enqueue(new Callback<GetRecentReportsResult>() {
                            @Override
                            public void onResponse(Call<GetRecentReportsResult> call, Response<GetRecentReportsResult> response) {
                                if (response.code() == 200) {
                                    GetRecentReportsResult getRecentReportsResult = response.body();
                                    assert getRecentReportsResult != null;
                                    ReportsList.reports_list = getRecentReportsResult.getRecentReports();
                                } else {
                                    Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GetRecentReportsResult> call, Throwable t) {
                                Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        return inflater.inflate(R.layout.fragment_report_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.reportsMap);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @SuppressLint("MissingPermission")
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
                }
            });

    static class GetRecentReportsExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }
}