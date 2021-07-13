package com.example.safecity.ui.emergency_contacts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.safecity.R;
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.user.DeleteEmergencyContactsBody;
import com.example.safecity.connection.user.DeleteEmergencyContactsResult;
import com.example.safecity.connection.user.EmergencyContactsResult;
import com.example.safecity.databinding.EmergencyContactsFragmentBinding;
import com.example.safecity.data.user.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergencyContactsFragment extends Fragment {

    private EmergencyContactsViewModel emergencyContactsViewModel;
    private EmergencyContactsFragmentBinding binding;

    public Button addNewContactButton;
    public EmergencyContactsListAdapter emergencyContactsListAdapter;
    public ListView emergencyContactsListView;
    public static boolean isActionMode = false;
    public static ArrayList<String> contactPhonesSelection = new ArrayList<>();
    public static ActionMode actionMode;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        emergencyContactsViewModel = new ViewModelProvider(this).get(EmergencyContactsViewModel.class);
        binding = EmergencyContactsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addNewContactButton = binding.addContactButton;
        emergencyContactsListView = binding.emergenyContactsListView;
        emergencyContactsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        emergencyContactsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.context_menu, menu);
                isActionMode = true;
                actionMode = mode;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if(item.getItemId() == R.id.action_delete) {
                    Call<DeleteEmergencyContactsResult> call = MainRetrofit.userAPI.deleteEmergencyContacts(User.id, new DeleteEmergencyContactsBody(contactPhonesSelection));
                    call.enqueue(new Callback<DeleteEmergencyContactsResult>() {
                        @Override
                        public void onResponse(Call<DeleteEmergencyContactsResult> call, Response<DeleteEmergencyContactsResult> response) {
                            if(response.code() == 200) {
                                DeleteEmergencyContactsResult deleteEmergencyContactsResult = response.body();
                                assert deleteEmergencyContactsResult != null;
                                User.emergencyContacts = deleteEmergencyContactsResult.getEmergencyContacts();
                                emergencyContactsListAdapter = new EmergencyContactsListAdapter(requireContext(),
                                        R.layout.list_item_emergency_contact, User.emergencyContacts);
                                emergencyContactsListView.setAdapter(emergencyContactsListAdapter);
                                Toast.makeText(getContext(), deleteEmergencyContactsResult.getMsg(), Toast.LENGTH_SHORT).show();
                                mode.finish();
                            }
                            else {
                                Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteEmergencyContactsResult> call, Throwable t) {
                            Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                isActionMode = false;
                actionMode = null;
                contactPhonesSelection.clear();
            }
        });

        Call<EmergencyContactsResult> call = MainRetrofit.userAPI.getEmergencyContacts(User.id);
        call.enqueue(new Callback<EmergencyContactsResult>() {
            @Override
            public void onResponse(@NotNull Call<EmergencyContactsResult> call, @NotNull Response<EmergencyContactsResult> response) {
                if(response.code() == 200) {
                    EmergencyContactsResult emergencyContactsResult = response.body();
                    assert emergencyContactsResult != null;
                    User.emergencyContacts = emergencyContactsResult.getEmergencyContacts();
                    emergencyContactsListAdapter = new EmergencyContactsListAdapter(requireContext(),
                            R.layout.list_item_emergency_contact, User.emergencyContacts);
                    emergencyContactsListView.setAdapter(emergencyContactsListAdapter);
                }
                else {
                    Toast.makeText(requireContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<EmergencyContactsResult> call, @NotNull Throwable t) {
                Toast.makeText(requireContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
            }
        });

        //final TextView textView = binding.textEmergency;
        emergencyContactsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //textView.setText(s);
            }
        });

        addNewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_new_emergency_contact);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}