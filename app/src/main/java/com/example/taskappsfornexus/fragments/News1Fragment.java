package com.example.taskappsfornexus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappsfornexus.NewsDetailsActivity;
import com.example.taskappsfornexus.R;
import com.example.taskappsfornexus.adapters.NewsAdapter;
import com.example.taskappsfornexus.dataclass.ApiResponse;
import com.example.taskappsfornexus.endpoint.ApiService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class News1Fragment extends Fragment implements NewsAdapter.OnItemClickListener {

    private static final String API_BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "1519a1fc55cf4f84a651ec3dff9f6bd7";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_1, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.idRVCourse);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("ReadingHistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the ApiService instance
        ApiService apiService = retrofit.create(ApiService.class);

        // Make the API request
        Call<ApiResponse> call = apiService.fetchData(API_KEY);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    // Get the Data object from ApiResponse
                    ApiResponse.Data data = apiResponse.getData();
                    if (data != null) {
                        // Assuming Data has a method to get data list
                        List<ApiResponse> dataList = data.getDataList();
                        if (dataList != null && !dataList.isEmpty()) {
                            Set<String> readingHistory = sharedPreferences.getStringSet("readingHistory", new HashSet<>());
                            // Create and set up the adapter
                            newsAdapter = new NewsAdapter(dataList, requireContext(), readingHistory);
                            recyclerView.setAdapter(newsAdapter);
                        }
                    }
                } else {
                    // Handle the error response
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });

        return view;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }


    public void onItemClick(ApiResponse.Data newsItemData) {
        // Handle the item click, for example, display the details
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("image", newsItemData.getImage());
        intent.putExtra("title", newsItemData.getTitle());
        intent.putExtra("summary", newsItemData.getSummary());
        startActivity(intent);


    }
}