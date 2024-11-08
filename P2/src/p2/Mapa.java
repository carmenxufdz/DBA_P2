package p2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Usuario
 */
public class Mapa {
    private String ruta;
    
    // Representado por matriz de tamaño NxM
    private int N, M;
    private int[][] matriz;
    
    public Mapa (String r) {
        ruta = r;
        leerMapa();
    }
    
    public void leerMapa() {
        try {
            try (BufferedReader lector = new BufferedReader (new FileReader(ruta))) {
                String linea = lector.readLine();
                N = Integer.parseInt(linea);
                linea = lector.readLine();
                M = Integer.parseInt(linea);
                matriz = new int[N][M];
                String[] enteros;
                for (int fila=0 ; fila<N ; fila++) {
                    linea = lector.readLine();
                    enteros = linea.split("\t");
                    for (int columna=0 ; columna<M ; columna++)
                        matriz[fila][columna] = Integer.parseInt(enteros[columna]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("No se pudo convertir a entero.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error accediendo al archivo.");
            e.printStackTrace();
        }
    }
    
    public int getFilas() {
        return N;
    }
    
    public int getColumnas() {
        return M;
    }
    
    public int[][] getMatriz() {
        return matriz;
    }
    
    public int getPos (int fila, int columna) {
        return matriz[fila][columna];
    }
    
    public void mostrarMapa() {
        for (int fila=0; fila<N; fila++) {
            for (int columna=0; columna<M; columna++)
                System.out.print(matriz[fila][columna]+"\t");
            System.out.print("\n");
        }
    }
}
