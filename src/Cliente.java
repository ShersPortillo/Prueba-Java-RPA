public class Cliente {

    //Atribustoss a solicitar
    int id;
    String nombre;
    double saldo;
    String fecha;
    String tipo;

    //Constructor
    public Cliente(int id, String nombre, double saldo, String fecha, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.fecha = fecha;
        this.tipo = tipo;


    }

    @Override
    public String toString() {
        return id + ","+ nombre + ","+ saldo + ","+ fecha + ","+ tipo;
    }


}
