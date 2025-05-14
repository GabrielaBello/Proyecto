package mx.itson.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import mx.itson.proyecto.api.ApiClient;
import mx.itson.proyecto.api.PedidoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPedidoActivity extends AppCompatActivity {

    TextView txtNombreCliente, txtNombrePizza, txtDescripcion, txtCantidad, txtFecha, txtCostoTotal;
    Button btnTerminarOrden;
    PedidoApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido);

        txtNombreCliente = findViewById(R.id.txtNombreCliente);
        txtNombrePizza = findViewById(R.id.txtNombrePizza);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtFecha = findViewById(R.id.txtFecha);
        txtCostoTotal = findViewById(R.id.txtCostoTotal);
        btnTerminarOrden = findViewById(R.id.btnTerminarOrden);

        api = ApiClient.getClient().create(PedidoApi.class);

        cargarPedido();

        // Acción del botón "Terminar Orden"
        btnTerminarOrden.setOnClickListener(v -> {
            int idPedido = getIntent().getIntExtra("pedido_id", -1);
            if (idPedido != -1) {
                eliminarPedido(idPedido);
            }
        });
    }

    private void cargarPedido() {
        int idPedido = getIntent().getIntExtra("pedido_id", -1);
        if (idPedido == -1) {
            Toast.makeText(this, "ID de pedido no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        api.getPedido(idPedido).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Pedido pedido = response.body();
                    txtNombreCliente.setText("Cliente: " + pedido.getCliente());
                    txtNombrePizza.setText("Pizza: " + pedido.getNombre_pizza());
                    txtDescripcion.setText("Descripción: " + pedido.getDescripcion());
                    txtCantidad.setText("Cantidad: " + pedido.getCantidad());
                    txtFecha.setText("Fecha: " + pedido.getFecha());
                    txtCostoTotal.setText("Total: $" + String.format("%.2f", pedido.getTotal()));
                } else {
                    Toast.makeText(VerPedidoActivity.this, "Error al cargar el pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(VerPedidoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarPedido(int idPedido) {
        api.eliminarPedido(idPedido).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(VerPedidoActivity.this, "Pedido eliminado", Toast.LENGTH_SHORT).show();

                    // Regresar a MenuActivity con el resultado
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);  // Indicar que el pedido fue eliminado
                    finish();
                } else {
                    Toast.makeText(VerPedidoActivity.this, "Error al eliminar el pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(VerPedidoActivity.this, "Error al eliminar el pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}






