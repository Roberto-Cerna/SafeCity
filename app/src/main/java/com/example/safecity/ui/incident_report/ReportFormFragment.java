package com.example.safecity.ui.incident_report;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.safecity.R;
import com.example.safecity.data.user.User;
import com.example.safecity.ui.login.LoginActivity;
import com.example.safecity.ui.login.LoginResult;
import com.example.safecity.ui.login.RetrofitInterface;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportFormFragment extends Fragment {

    TextView textFeatureReportInput;
    ImageView cameraButton;
    ImageView galleryButton;
    ImageView reportImage;
    Button denunciarButton;
    TextInputEditText descripcionText;
    TextInputEditText addressText;

    Bitmap finalImage;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private int SELECT_PICTURE = 200;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.ACCESS_FINE_LOCATION"
    };
    private String API_KEY = "AIzaSyC5ePqeLOUYinLV_9YaFnSZ8pvNWdHCBPE";
    private String latitude , longitude;
    private Retrofit retrofit;
    private RetrofitInterface service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String reportFeature = bundle.getString("key");

                Log.i(getTag(), reportFeature);
                textFeatureReportInput.setText(reportFeature);
            }
        });
        if(!allPermissionsGranted()){
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        retrofit = RetrofitClient.getClient();
        service = retrofit.create(RetrofitInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_form, container, false);
    }

    ActivityResultLauncher<Intent> intentLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i(getTag(), "Result: "+result.getData().getData());
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Uri selectedImageUri = result.getData().getData();
                        if(null != selectedImageUri){
//                            reportImage.setImageURI(selectedImageUri);
                            try {
                                finalImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                                        selectedImageUri);
                                reportImage.setImageBitmap(finalImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> intent2Launch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        finalImage = (Bitmap) result.getData().getExtras().get("data");
                        reportImage.setImageBitmap(finalImage);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> intent3Launch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK ){

                        Place place = Autocomplete.getPlaceFromIntent(result.getData());
                        addressText.setText(place.getAddress());

                        Log.i(getTag(), "Nombre de localidad:"+place.getName());
                        Log.i(getTag(), "Nombre de localidad:"+place.getLatLng());

                        latitude = String.valueOf(place.getLatLng().latitude);
                        longitude = String.valueOf(place.getLatLng().longitude);

                    }else if(result.getResultCode() == AutocompleteActivity.RESULT_ERROR){
                        Status status = Autocomplete.getStatusFromIntent(result.getData());

                        Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        textFeatureReportInput = view.findViewById(R.id.textFeatureReportInput);
        denunciarButton = view.findViewById(R.id.reportButton);
        galleryButton = view.findViewById(R.id.galleryButton);
        cameraButton = view.findViewById(R.id.cameraButton);
        reportImage = view.findViewById(R.id.ReportImage);
        descripcionText = view.findViewById(R.id.detailInput);
        addressText = view.findViewById(R.id.addressInput);

        //Iniciacion place
        Places.initialize(getContext().getApplicationContext(), API_KEY);

        addressText.setFocusable(false);

        addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,
                        Place.Field.NAME);

                Intent addressIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(getContext());

                intent3Launch.launch(addressIntent);
            }
        });

        denunciarButton.setOnClickListener(v -> onReport(navController));

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast para confirmacion y salta a nueva activity camera
                try {
                    Intent intentCamera = new Intent();
                    intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "templ.jpg");
//                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    intent2Launch.launch(intentCamera);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra("REQUEST_CODE_PERMISSIONS",101 );
                intent.setAction(Intent.ACTION_PICK);

                intentLaunch.launch(intent);
            }
        });
    }

    private void onReport(NavController navController) {

        String victim = User.name;
        String incident = textFeatureReportInput.getText().toString();
        String details = descripcionText.getText().toString();
        String address = addressText.getText().toString();
        //latitude, longitude , reportImage
        Save saveFile = new Save();
        String imageReportPath = saveFile.SaveImage(getContext(), finalImage);

        if(details.isEmpty()){
            descripcionText.setError("Campo descripción no debe estar vacío.");
        }else if (address.isEmpty()){
            addressText.setError("Campo lugar de incidente no debe estar vacío.");
        }else {
            sendForm(victim, incident, details,imageReportPath, navController);
        }
    }

    private void sendForm(String victim, String incident, String details,String imageReportPath, NavController navController) {
        RequestBody victimBody = RequestBody.create(MediaType.parse("multipart/form-data"), victim);
        RequestBody incidentBody = RequestBody.create(MediaType.parse("multipart/form-data"), incident);
        RequestBody detailsBody = RequestBody.create(MediaType.parse("multipart/form-data"), details);
        RequestBody longitudeBody = RequestBody.create(MediaType.parse("multipart/form-data"), longitude);
        RequestBody latitudeBody = RequestBody.create(MediaType.parse("multipart/form-data"), latitude);
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), User.id);

        // Preparacion de la imagen
        MultipartBody.Part profilePic = null;
        Log.i(getTag(), "Parte 1 de subida");
        if(imageReportPath != null){
            File file = new File(imageReportPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            profilePic = MultipartBody.Part.createFormData("imageReport", file.getName(), requestFile);
            Log.i(getTag(), "Parte 2 de subida");
        }
        //imagen preparada
        Log.i(getTag(), "Parte 3 de subida");
        Call<FormIncident> call = service.executeSendIncident(victimBody, incidentBody, detailsBody,
                longitudeBody,latitudeBody, userId,profilePic);

        call.enqueue(new Callback<FormIncident>() {
            @Override
            public void onResponse(Call<FormIncident> call, Response<FormIncident> response) {
                if(response.code() == 200){

                    new AlertDialog.Builder(getContext())
                            .setTitle("Reporte enviado")
                            .setMessage("Se envió el reporte de incidencia correctamente")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                }
                else{
                    Toast.makeText(getContext(), "No se pudo enviar el formulario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FormIncident> call, Throwable t) {
                Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT);
                Log.d(getTag(), "onFailure: " + t);
            }
        });
        //latitude, longitude , reportImage
        navController.navigate(R.id.nav_home);
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
