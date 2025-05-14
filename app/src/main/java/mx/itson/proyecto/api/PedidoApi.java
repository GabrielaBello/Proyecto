package mx.itson.proyecto.api;
import java.util.List;

import mx.itson.proyecto.Pedido;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface PedidoApi {

    @GET("api/pedidos.php")
    Call<List<Pedido>> obtenerPedidos();

    @GET("api/pedidos.php")
    Call<Pedido> getPedido(@Query("id") int id);  // ← CORREGIDO

    @DELETE("api/pedidos.php")
    Call<Void> eliminarPedido(@Query("id") int id);

}

