package mx.itson.proyecto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.itson.proyecto.api.PedidoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private List<Pedido> pedidos;
    private PedidoApi api;
    private Context context;

    public PedidoAdapter(List<Pedido> pedidos, PedidoApi api, Context context) {
        this.pedidos = pedidos;
        this.api = api;
        this.context = context;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        holder.nombrePizza.setText(pedido.getNombre_pizza());

        // Botón "Ver Pedido"

        holder.verPedidoButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), VerPedidoActivity.class);
            intent.putExtra("pedido_id", pedido.getId());
            v.getContext().startActivity(intent);
        });

        // Botón "Eliminar"
        holder.eliminarButton.setOnClickListener(v -> {
            eliminarPedido(pedido.getId(), position);
        });
    }

    private void eliminarPedido(int id, int position) {
        api.eliminarPedido(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la eliminación fue exitosa, eliminamos el pedido de la lista local
                    pedidos.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Pedido eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePizza;
        Button verPedidoButton;
        Button eliminarButton;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            nombrePizza = itemView.findViewById(R.id.nombrePizza);
            verPedidoButton = itemView.findViewById(R.id.verPedidoButton);
            eliminarButton = itemView.findViewById(R.id.eliminarButton);
        }
    }
}

