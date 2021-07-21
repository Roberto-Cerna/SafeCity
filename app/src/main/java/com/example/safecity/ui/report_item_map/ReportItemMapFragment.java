package com.example.safecity.ui.report_item_map;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safecity.R;
import com.example.safecity.data.report_item.AttendingReport;
import com.example.safecity.data.report_item.ReportItem;
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

public class ReportItemMapFragment extends Fragment {

    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLng;
    Marker currentLocationMarker;
    Location lastKnownLocation;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng reportLatLng = new LatLng(Double.parseDouble(ReportItem.locationLatitude), Double.parseDouble(ReportItem.locationLongitude));
            googleMap.addMarker(new MarkerOptions()
                    .position(reportLatLng).icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(ReportItem.victim));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(reportLatLng, 14));

            locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    googleMap.clear();
                    if(currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    currentLocationMarker = googleMap.addMarker(new MarkerOptions()
                            .position(userLatLng).icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).title("Usted está aquí"));

                    LatLng reportLatLng = new LatLng(Double.parseDouble(ReportItem.locationLatitude), Double.parseDouble(ReportItem.locationLongitude));
                    googleMap.addMarker(new MarkerOptions()
                                .position(reportLatLng).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(ReportItem.victim));
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

            //Setting location permissions
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
        return inflater.inflate(R.layout.fragment_report_item_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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
}