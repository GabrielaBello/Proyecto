package mx.itson.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mx.itson.proyecto.api.ApiClient;
import mx.itson.proyecto.api.EmpleadoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText edID, edNombres, edCorreo, edUsuario;
    private Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edNombres = findViewById(R.id.edNombres);

        edCorreo = findViewById(R.id.edCorreo);
        edUsuario = findViewById(R.id.edUsuario);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(v -> {
            String nombre = edNombres.getText().toString().trim();
            String correo = edCorreo.getText().toString().trim();
            String contrasena = edUsuario.getText().toString().trim();

            // Verificar si los campos están vacíos
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_fill_all), Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear un objeto Empleado con los datos ingresados
            Empleado empleado = new Empleado(nombre, correo, contrasena);

            // Crear el objeto de Retrofit para el registro
            EmpleadoApi apiInterface = ApiClient.getClient().create(EmpleadoApi.class);

            Call<EmpleadoResponse> call = apiInterface.registrarEmpleado(empleado);

            // Realizar la llamada asíncrona
            call.enqueue(new Callback<EmpleadoResponse>() {
                @Override
                public void onResponse(Call<EmpleadoResponse> call, Response<EmpleadoResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        EmpleadoResponse empleadoResponse = response.body();
                        if (empleadoResponse.isSuccess()) {
                            Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EmpleadoResponse> call, Throwable t) {
                    Toast.makeText(RegistroActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

