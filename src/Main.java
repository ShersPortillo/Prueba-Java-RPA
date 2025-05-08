import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Cliente> clientes = new ArrayList<>();
        String entrada = "data/clientes.txt";
        String salida = "data/clientes_ordenados.txt";
        String logErrores = "data/errores.log";

        // Leer archivo
        try (BufferedReader br = Files.newBufferedReader(Paths.get(entrada));
             BufferedWriter logWriter = Files.newBufferedWriter(Paths.get(logErrores))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 5) {
                    logWriter.write("Error: línea mal formada -> " + linea + "\n");
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    double saldo = Double.parseDouble(partes[2].trim());
                    String fecha = partes[3].trim();
                    String tipo = partes[4].trim();
                    clientes.add(new Cliente(id, nombre, saldo, fecha, tipo));
                } catch (Exception e) {
                    logWriter.write("Error al parsear línea -> " + linea + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ordenamiento burbuja descendente
        for (int i = 0; i < clientes.size(); i++) {
            for (int j = 0; j < clientes.size() - i - 1; j++) {
                if (clientes.get(j).saldo < clientes.get(j + 1).saldo) {
                    Cliente temp = clientes.get(j);
                    clientes.set(j, clientes.get(j + 1));
                    clientes.set(j + 1, temp);
                }
            }
        }

        // Imprimir clientes ordenados
        System.out.println("Clientes ordenados (De forma descendente) :");
        for (Cliente c : clientes) {
            System.out.println("ID: " + c.id + ", Nombre: " + c.nombre + ", Saldo: " + c.saldo + ", Fecha: " + c.fecha + ", Tipo: " + c.tipo);
        }

        // Filtrado por fecha y tipo
        String fechaFiltro = "2024-12-31";
        String tipoFiltro = "Premium";
        System.out.println("\nClientes filtrados por fecha y tipo:");
        for (Cliente c : clientes) {
            if (c.fecha.compareTo(fechaFiltro) < 0 && c.tipo.equalsIgnoreCase(tipoFiltro)) {
                System.out.println(c);
            }
        }

        // Agrupar por tipo y calcular saldo promedio
        Map<String, List<Double>> grupos = new HashMap<>();
        for (Cliente c : clientes) {
            grupos.putIfAbsent(c.tipo, new ArrayList<>());
            grupos.get(c.tipo).add(c.saldo);
        }

        System.out.println("\nSaldo promedio por tipo de cliente:");
        for (String tipo : grupos.keySet()) {
            List<Double> saldos = grupos.get(tipo);
            double promedio = saldos.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            System.out.println(tipo + ": " + String.format("%.2f", promedio));
        }

        // Escribir archivo ordenado
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(salida))) {
            for (Cliente c : clientes) {
                writer.write(c.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}