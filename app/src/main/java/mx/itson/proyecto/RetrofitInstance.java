package mx.itson.proyecto;

import mx.itson.proyecto.api.EmpleadoApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://93b4-2806-263-8400-17b1-4530-ace9-63ae-9cb2.ngrok-free.app/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static EmpleadoApi getApi() {
        return retrofit.create(EmpleadoApi.class);
    }
}

