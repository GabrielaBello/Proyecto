package mx.itson.proyecto.api;

import mx.itson.proyecto.Empleado;
import mx.itson.proyecto.EmpleadoResponse;
import mx.itson.proyecto.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmpleadoApi {
    @POST("api/registrar_empleado.php")
    Call<EmpleadoResponse> registrarEmpleado(@Body Empleado empleado);

    @POST("api/login_empleado.php")
    Call<LoginResponse> loginEmpleado(@Body LoginRequest loginRequest);

}

