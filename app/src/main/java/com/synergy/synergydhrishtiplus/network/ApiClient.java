package com.synergy.synergydhrishtiplus.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiClient {
    private static Retrofit retrofit = null;
    private static String URL_CI = "http://test.synergyteletech.com/burnet/ofo/Api/";



  //  private static String SOAP_URL = "https://" + SharedPref.getIpId() /*+ ":" + SharedPref.getPortNo()*/ + "/";


  ///  SSLContext sslContext = createCertificate(getApplicationContext().getResources().openRawResource(R.raw.client_certificate));
    public static Retrofit getClientCI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1200, TimeUnit.SECONDS);
        builder.readTimeout(1200, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        builder.addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_CI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;

    }


}
