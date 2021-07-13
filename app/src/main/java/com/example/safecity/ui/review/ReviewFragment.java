package com.example.safecity.ui.review;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safecity.R;
import com.example.safecity.connection.review.ReviewAPI;


import com.example.safecity.databinding.ReviewFragmentBinding;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewFragment extends Fragment {

    private ReviewViewModel mViewModel;
    private ReviewFragmentBinding binding;

    private Retrofit retrofit;
    private ReviewAPI reviewAPIInterface;
    private final String BASE_URL = "https://safecity-api.herokuapp.com/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ReviewFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {



        super.onViewCreated(view, savedInstanceState);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        reviewAPIInterface = retrofit.create(ReviewAPI.class);

        RatingBar rating = (RatingBar) view.findViewById(R.id.rating);
        TextView tvRatingScale = (TextView) view.findViewById(R.id.tvRatingScale);
        TextInputLayout reviewLayout = (TextInputLayout) view.findViewById(R.id.ReviewLayout);
        TextInputEditText etReview = (TextInputEditText) reviewLayout.getEditText();
        Button btnSubmitReview =  (Button) view.findViewById(R.id.btnSubmitReview);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRatingScale.setText(String.valueOf(rating));
                switch ((int) ratingBar.getRating()){
                    case 1:
                        tvRatingScale.setText("Pésimo Servicio");
                        break;
                    case 2:
                        tvRatingScale.setText("Puede estar mejor");
                        break;
                    case 3:
                        tvRatingScale.setText("Ok");
                        break;
                    case 4:
                        tvRatingScale.setText("Buena app");
                        break;
                    case 5:
                        tvRatingScale.setText("Increíble");
                        break;
                    default:
                        tvRatingScale.setText("");
                }
            }
        });

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etReview.getText().toString();
                int calification = (int) rating.getRating();



                if (content.isEmpty()){
                    reviewLayout.setError("Este campo no puede ser vacío");
                }
                else{
                    postReview(content,calification);
                }
            }

            private void postReview(String content, int calification) {
                reviewLayout.setError("");
                HashMap<String,String> map = new HashMap<>();
                map.put("content",content);
                map.put("calification",Integer.toString(calification));


                Call<Void> call = reviewAPIInterface.postNewReview(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code()==200){
                            Toast.makeText(getContext(), "Gracias por dejarnos tu opinión!", Toast.LENGTH_SHORT).show();
                            etReview.setText("");
                        }
                        else {
                            Toast.makeText(getContext(), "Envío fallido, inténtelo más tarde", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Envío fallido, inténtelo más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}