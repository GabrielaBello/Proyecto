package mx.itson.proyecto;

import mx.itson.proyecto.api.EmpleadoApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://00dd-201-175-208-43.ngrok-free.app/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static EmpleadoApi getApi() {
        return retrofit.create(EmpleadoApi.class);
    }
}

