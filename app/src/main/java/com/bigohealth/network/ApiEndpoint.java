package com.bigohealth.network;

import com.bigohealth.network.response.ApiResponse;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpoint {

    @POST("doctors/")
    Single<ApiResponse> getDoctors(
            @Query("language_id") int languageId
    );
}
