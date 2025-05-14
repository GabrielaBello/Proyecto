package mx.itson.proyecto;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Pedido implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("cliente")
    private String cliente;

    @SerializedName("nombre_pizza")  // Asegura que coincida con el nombre exacto del JSON
    private String nombre_pizza;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("total")
    private float total;

    @SerializedName("fecha")
    private String fecha;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombre_pizza() {
        return nombre_pizza;
    }

    public void setNombre_pizza(String nombre_pizza) {
        this.nombre_pizza = nombre_pizza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}


