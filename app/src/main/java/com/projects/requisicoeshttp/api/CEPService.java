package com.projects.requisicoeshttp.api;

import com.projects.requisicoeshttp.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CEPService {

    @GET("01310100/json/")
    Call<CEP> recuperarCEP();
}
