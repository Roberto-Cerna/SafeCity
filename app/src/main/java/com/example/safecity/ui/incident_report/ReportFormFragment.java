package com.example.safecity.ui.incident_report;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.safecity.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ReportFormFragment extends Fragment {

    TextView textFeatureReportInput;
    ImageView cameraButton;
    ImageView galleryButton;
    ImageView reportImage;
    Button denunciarButton;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private int SELECT_PICTURE = 200;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.ACCESS_FINE_LOCATION"
    };

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
                    Log.i(getTag(), "Result: "+result);
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Uri selectedImageUri = result.getData().getData();
                        if(null != selectedImageUri){
                            reportImage.setImageURI(selectedImageUri);
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
                        Bitmap finalImage = (Bitmap) result.getData().getExtras().get("data");
                        reportImage.setImageBitmap(finalImage);
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

        new AlertDialog.Builder(getContext())
                .setTitle("Formulario de Denuncia")
                .setMessage("Se envió su denuncia correctamente.")
                .setPositiveButton("OK", ((dialog, which) -> dialog.dismiss()))
                .show();
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
