package p2;



import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import javafx.util.Pair;

public class Entorno {
    private Mapa mapa;
    private int[] posAgente = new int[2];
    private int[] posObjetivo = new int[2];
    private int[][] recorrido;
    public double distancia_actual;
  
    public Entorno (String ruta, int filAgente, int colAgente, int filObjetivo, int colObjetivo) {
        mapa = new Mapa (ruta);
        // Al inicio por todas las celdas se ha pasado 0 veces
        recorrido = new int[mapa.getFilas()][mapa.getColumnas()];
        posAgente[0] = filAgente;
        posAgente[1] = colAgente;
        // El agente ya ha estado una vez en la coordenada inicial
        pasarPor(filAgente, colAgente);
        posObjetivo[0] = filObjetivo;
        posObjetivo[1] = colObjetivo;
        distancia_actual = calcularDistancia(posAgente[0], posAgente[1]);
    }
    
    public Entorno (Mapa m, int filAgente, int colAgente, int filObjetivo, int colObjetivo) {
        mapa = m;
        // Al inicio por todas las celdas se ha pasado 0 veces
        recorrido = new int[mapa.getFilas()][mapa.getColumnas()];
        posAgente[0] = filAgente;
        posAgente[1] = colAgente;
        // El agente ya ha estado una vez en la coordenada inicial
        pasarPor(filAgente, colAgente);
        posObjetivo[0] = filObjetivo;
        posObjetivo[1] = colObjetivo;
        distancia_actual = calcularDistancia(posAgente[0], posAgente[1]);
    }
    
    public void mostrarEntorno() {
        for (int fila=0; fila<mapa.getFilas() ; fila++) {
            for (int columna=0; columna<mapa.getColumnas(); columna++) {
                if (fila==posAgente[0] && columna==posAgente[1])
                    System.out.print("A"); // agente
                else if (fila==posObjetivo[0] && columna==posObjetivo[1])
                    System.out.print("X"); // objetivo
                else if (mapa.getPos(fila, columna)==-1)
                    System.out.print("M"); // muro
                else System.out.print(getVeces(fila, columna));
                
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    
    public boolean agenteEnMeta() {
        return posAgente[0] == posObjetivo[0] && posAgente[1] == posObjetivo[1];
    }
    
    public Pair<Double, Integer> getEstado (Movimiento mov) {
        // Hay que inicializarlas para que permita hacer calcularDistancia al final
        int fila= -1,columna= -1;
        // =-2 fuera del mapa, =-1 obstáculo, =0 libre
        double estado = -2;
        // En sentido de las agujas del reloj
        switch (mov) {
            case NORTE:
               columna = posAgente[1]-1;
                if (columna > -1) {
                   fila = posAgente[0];
                    estado = mapa.getPos(fila, columna);
                }
                break;
            case NORESTE:
                // Si no se puede norte ni este, no se puede noreste
                if (getEstado(Movimiento.NORTE).getKey() >=0 || getEstado(Movimiento.ESTE).getKey() >=0) {
                   columna = posAgente[1]-1;
                   fila = posAgente[0]+1;
                    if (columna > -1 && fila< mapa.getFilas()) {
                        estado = mapa.getPos(fila, columna);
                    }
                }
                break;
            case ESTE:
               fila = posAgente[0]+1;
                if (fila < mapa.getFilas()) {
                   columna = posAgente[1];
                   estado = mapa.getPos(fila, columna);
                }
                break;
            case SURESTE:
                // Si no se puede sur ni este, no se puede sureste
                if (getEstado(Movimiento.SUR).getKey() >=0 || getEstado(Movimiento.ESTE).getKey() >=0) {
                   fila= posAgente[0]+1;
                   columna= posAgente[1]+1;
                    if (fila < mapa.getFilas() && columna < mapa.getColumnas()) {
                        estado = mapa.getPos(fila, columna);
                    }
                }
                break;
            case SUR:
               columna = posAgente[1]+1;
                if (columna < mapa.getColumnas()) {
                   fila = posAgente[0];
                    estado = mapa.getPos(fila, columna);
                }
                break;
            case SUROESTE:
                // Si no se puede sur ni oeste, no se puede suroeste
                if (getEstado(Movimiento.SUR).getKey() >=0 || getEstado(Movimiento.OESTE).getKey() >=0) {
                   columna = posAgente[1]+1;
                   fila = posAgente[0]-1;
                    if (columna < mapa.getColumnas() && fila> -1) {
                        estado = mapa.getPos(fila, columna);
                    }
                }
                break;
            case OESTE:
               fila = posAgente[0]-1;
                if (fila > -1) {
                   columna = posAgente[1];
                   estado = mapa.getPos(fila, columna);
                }
                break;
            case NOROESTE:
                // Si no se puede norte ni oeste, no se puede noroeste
                if (getEstado(Movimiento.NORTE).getKey() >=0 || getEstado(Movimiento.OESTE).getKey() >=0) {
                   fila = posAgente[0]-1;
                   columna = posAgente[1]-1;
                    if (fila > -1 && columna > -1) {
                        estado = mapa.getPos(fila, columna);
                    }
                }
                break;
        }
        
        int veces = 0;
        // Celda libre
        if (estado == 0) {
            // Se le añade el coste de la distancia
            estado += calcularDistancia(fila, columna);     
            veces += getVeces(fila, columna);
        }
        
        return new Pair<>(estado, veces);
    }
    
    public double calcularDistancia (int x, int y) {
        // Distancia Euclidiana
        return sqrt(pow(x-posObjetivo[0],2) + pow(y-posObjetivo[1],2));
    }
    
    public void moverAgente(Movimiento mov) {
        // En sentido de las agujas del reloj
        switch (mov) {
            case NORTE:
                posAgente[1]--;
                break;
            case NORESTE:
                posAgente[1]--;
                posAgente[0]++;
                break;
            case ESTE:
                posAgente[0]++;
                break;
            case SURESTE:
                posAgente[0]++;
                posAgente[1]++;
                break;
            case SUR:
                posAgente[1]++;
                break;
            case SUROESTE:
                posAgente[1]++;
                posAgente[0]--;
                break;
            case OESTE:
                posAgente[0]--;
                break;
            case NOROESTE:
                posAgente[0]--;
                posAgente[1]--;
                break;
        }
        pasarPor(posAgente[0], posAgente[1]);
        distancia_actual = calcularDistancia(posAgente[0], posAgente[1]);
        mostrarEntorno();
    }
    
    private int getVeces (int f, int c) {
        return recorrido[f][c];
    }
    
    private void pasarPor(int f, int c) {
        recorrido[f][c]++;
    }
    
    public int getFilas(){
        return mapa.getFilas();
    }
    
    public int getColumnas(){
        return mapa.getColumnas();
    }
    
   
}


