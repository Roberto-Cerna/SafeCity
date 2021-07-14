package com.example.safecity.ui.recent_reports;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safecity.R;
import com.example.safecity.connection.MainRetrofit;
import com.example.safecity.connection.report.GetRecentReportsResult;
import com.example.safecity.databinding.RecentReportsFragmentBinding;
import com.example.safecity.data.reports_list.ReportsList;
import com.example.safecity.data.report_item.ReportItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentReportsFragment extends Fragment {

    private RecentReportsViewModel mViewModel;
    private RecentReportsFragmentBinding binding;

    //public ArrayList<Report> reports = new ArrayList<>();
    public ReportsListAdapter reportsListAdapter;
    public ListView reportsListView;
    public TextView emptyRecentReportsTextView;

    public static RecentReportsFragment newInstance() {
        return new RecentReportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RecentReportsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reportsListView = binding.recentReportsListView;
        emptyRecentReportsTextView = binding.emptyRecentReportsTextView;

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
        alertDialog.setMessage("Cargando...");
        alertDialog.show();
        Call<GetRecentReportsResult> call = MainRetrofit.reportAPI.getLastNIncidents("3");
        call.enqueue(new Callback<GetRecentReportsResult>() {
            @Override
            public void onResponse(Call<GetRecentReportsResult> call, Response<GetRecentReportsResult> response) {
                if(response.code() == 200) {
                    GetRecentReportsResult getRecentReportsResult = response.body();
                    assert getRecentReportsResult != null;
                    ReportsList.reports_list = getRecentReportsResult.getRecentReports();
                    reportsListAdapter = new ReportsListAdapter(requireContext(),
                            R.layout.list_item_report, ReportsList.reports_list);
                    reportsListView.setAdapter(reportsListAdapter);
                }
                else {
                    Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
                emptyRecentReportsTextView.setVisibility(View.VISIBLE);
                reportsListView.setEmptyView(emptyRecentReportsTextView);
            }

            @Override
            public void onFailure(Call<GetRecentReportsResult> call, Throwable t) {
                Toast.makeText(getContext(), "Ocurrrió un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                emptyRecentReportsTextView.setVisibility(View.VISIBLE);
                reportsListView.setEmptyView(emptyRecentReportsTextView);
            }
        });

        reportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportItem.id = ReportsList.reports_list.get(position).id;
                ReportItem.type = ReportsList.reports_list.get(position).incident;
                ReportItem.details = ReportsList.reports_list.get(position).details;
                ReportItem.fileURL = ReportsList.reports_list.get(position).fileURL;
                ReportItem.locationLatitude = ReportsList.reports_list.get(position).locationLatitude;
                ReportItem.locationLongitude = ReportsList.reports_list.get(position).locationLongitude;
                ReportItem.daysAgo = ReportsList.reports_list.get(position).daysAgo;
                ReportItem.victim = ReportsList.reports_list.get(position).victim;
                //ReportsList.reports_list.get(position).isSeen = true;

                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_report);

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecentReportsViewModel.class);
        // TODO: Use the ViewModel
    }

}