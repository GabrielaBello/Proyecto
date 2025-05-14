package mx.itson.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import mx.itson.proyecto.api.PedidoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout contenedorPedidos;
    private PedidoApi pedidoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        contenedorPedidos = findViewById(R.id.contenedorPedidos);

        // Crear instancia Retrofit para hacer llamadas al API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://93b4-2806-263-8400-17b1-4530-ace9-63ae-9cb2.ngrok-free.app/") // Reemplaza con tu URL de ngrok real
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pedidoApi = retrofit.create(PedidoApi.class);

        // Cargar los pedidos desde la API
        cargarPedidosDesdeAPI();
    }

    private void cargarPedidosDesdeAPI() {
        Call<List<Pedido>> call = pedidoApi.obtenerPedidos();

        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarPedidos(response.body());
                } else {
                    Toast.makeText(MenuActivity.this, "Error al obtener pedidos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API", "Error", t);
            }
        });
    }

    private void mostrarPedidos(List<Pedido> pedidos) {
        contenedorPedidos.removeAllViews();

        for (Pedido pedido : pedidos) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setPadding(8, 16, 8, 16);

            // Texto del pedido (solo nombre de pizza)
            TextView txtPedido = new TextView(this);
            txtPedido.setText("Pizza: " + pedido.getNombre_pizza());
            txtPedido.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            txtPedido.setTextSize(14);
            txtPedido.setTextColor(0xFF333333);

            // Botón Ver
            Button btnVer = new Button(this);
            btnVer.setText("Ver pedido");
            btnVer.setBackgroundColor(0xFF6B8E23);
            btnVer.setTextColor(0xFFFFFFFF);
            btnVer.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, VerPedidoActivity.class);
                intent.putExtra("pedido_id", pedido.getId()); // Pasar el ID del pedido
                startActivity(intent);
            });

            // Botón Eliminar
            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setBackgroundColor(0xFFD32F2F);
            btnEliminar.setTextColor(0xFFFFFFFF);
            btnEliminar.setOnClickListener(v -> {
                eliminarPedido(pedido.getId(), layout);
            });

            layout.addView(txtPedido);
            layout.addView(btnVer);
            layout.addView(btnEliminar);

            contenedorPedidos.addView(layout);
        }
    }

    private void eliminarPedido(int idPedido, View layoutPedido) {
        // Eliminar el pedido a través de la API
        pedidoApi.eliminarPedido(idPedido).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "Pedido eliminado", Toast.LENGTH_SHORT).show();
                    contenedorPedidos.removeView(layoutPedido);
                } else {
                    Toast.makeText(MenuActivity.this, "Error al eliminar el pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error al eliminar el pedido", Toast.LENGTH_SHORT).show();
                Log.e("EliminarPedido", "Error", t);
            }
        });
    }
}



