package mx.itson.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mx.itson.proyecto.api.ApiClient;
import mx.itson.proyecto.api.EmpleadoApi;
import mx.itson.proyecto.api.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCorreo, edContrasenia;
    private Button btnIniciar, btnRegistro, btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCorreo = findViewById(R.id.etCorreo);
        edContrasenia = findViewById(R.id.edContrasenia);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnCrear = findViewById(R.id.btnCrear);

        btnIniciar.setOnClickListener(view -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = edContrasenia.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_fill_all), Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear el objeto de Retrofit para el login
            EmpleadoApi empleadoApi = ApiClient.getClient().create(EmpleadoApi.class);
            LoginRequest request = new LoginRequest(correo, contrasena);
            Call<LoginResponse> call = empleadoApi.loginEmpleado(request);


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.isSuccess()) {
                            Toast.makeText(MainActivity.this, getString(R.string.msg_login_success), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.msg_login_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnRegistro.setOnClickListener(view -> {
            startActivity(new Intent(this, RegistroActivity.class));
        });

        btnCrear.setOnClickListener(view -> {
            Toast.makeText(this, "Ya no se usa SQLite. Se usa API web.", Toast.LENGTH_SHORT).show();
        });
    }
}
