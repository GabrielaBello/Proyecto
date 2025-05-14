package mx.itson.proyecto;

import mx.itson.proyecto.Pedido;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CancelarPedidoActivity extends AppCompatActivity {

    TextView txtDetallePedido;
    Button btnEliminar, btnCancelar;
    Pedido pedidoSeleccionado; // Para almacenar el pedido actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_pedido);

        txtDetallePedido = findViewById(R.id.txtDetallePedido);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Aquí deberías obtener el pedido de la base de datos o de un intent
        // Simulamos que es el primer pedido para la demostración:
        pedidoSeleccionado = (Pedido) getIntent().getSerializableExtra("pedido");

        if (pedidoSeleccionado != null) {
            txtDetallePedido.setText(pedidoSeleccionado.toString());
        } else {
            Toast.makeText(this, "No se pudo cargar el pedido.", Toast.LENGTH_SHORT).show();
            finish();
        }

        txtDetallePedido.setText(pedidoSeleccionado.toString());

        // Eliminar pedido
        btnEliminar.setOnClickListener(v -> {
            if (pedidoSeleccionado != null) {
                eliminarPedido(pedidoSeleccionado.getId());
                Toast.makeText(this, "Pedido eliminado.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "No hay pedido para eliminar.", Toast.LENGTH_SHORT).show();
            }
        });

        // Cancelar
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void eliminarPedido(int id) {
        // Aquí iría la lógica para eliminar el pedido de la base de datos o la API
    }
}


