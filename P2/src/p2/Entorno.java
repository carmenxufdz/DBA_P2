package p2;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

public class Entorno {
    private Mapa mapa;
    private int[] posAgente = new int[2];
    private int[] posObjetivo = new int[2];
    private ArrayList<int[]> recorrido = new ArrayList<>();
    
    public Entorno (String ruta, int xAgente, int yAgente, int xObjetivo, int yObjetivo) {
        mapa = new Mapa (ruta);
        posAgente[0] = xAgente;
        posAgente[1] = yAgente;
        recorrido.add(new int[] {xAgente, yAgente});
        posObjetivo[0] = xObjetivo;
        posObjetivo[1] = yObjetivo;
    }
    
    public Entorno (Mapa m, int xAgente, int yAgente, int xObjetivo, int yObjetivo) {
        mapa = m;
        posAgente[0] = xAgente;
        posAgente[1] = yAgente;
        recorrido.add(new int[] {xAgente, yAgente});
        posObjetivo[0] = xObjetivo;
        posObjetivo[1] = yObjetivo;
    }
    
    public void mostrarEntorno() {
        for (int x=0; x<mapa.getColumnas() ; x++) {
            for (int y=0; y<mapa.getFilas(); y++) {
                if (x==posAgente[0] && y==posAgente[1])
                    System.out.print("A"); // A de agente
                else if (x==posObjetivo[0] && y==posObjetivo[1])
                    System.out.print("M"); // M de meta
                else if (mapa.getPos(x, y)==-1)
                    System.out.print("O"); // O de obstáculo
                else if (enRecorrido(x, y))
                    System.out.print("X");
                else System.out.print("-");
                System.out.print("\t");
            }
            System.out.print("\n");
        }
    }
    
    public boolean agenteEnMeta() {
        return posAgente[0] == posObjetivo[0] && posAgente[1] == posObjetivo[1];
    }
    
    public double getEstado (Movimiento mov) {
        // Hay que inicializarlas para que permita hacer calcularDistancia al final
        int x = -1, y = -1;
        // =-2 fuera del mapa, =-1 obstáculo, =0 libre
        double estado = -2;
        // En sentido de las agujas del reloj
        switch (mov) {
            case NORTE:
                y = posAgente[1]-1;
                if (y > -1) {
                    x = posAgente[0];
                    estado = mapa.getPos(x, y);
                }
                break;
            case NORESTE:
                // Si no se puede norte ni este, no se puede noreste
                if (getEstado(Movimiento.NORTE)>=0 || getEstado(Movimiento.ESTE)>=0) {
                    y = posAgente[1]-1;
                    x = posAgente[0]+1;
                    if (y > -1 && x < mapa.getColumnas()) {
                        estado = mapa.getPos(x, y);
                    }
                }
                break;
            case ESTE:
                x = posAgente[0]+1;
                if (x < mapa.getColumnas()) {
                    y = posAgente[1];
                    estado = mapa.getPos(x, y);
                }
                break;
            case SURESTE:
                // Si no se puede sur ni este, no se puede sureste
                if (getEstado(Movimiento.SUR)>=0 || getEstado(Movimiento.ESTE)>=0) {
                    x = posAgente[0]+1;
                    y = posAgente[1]+1;
                    if (x < mapa.getColumnas() && y < mapa.getFilas()) {
                        estado = mapa.getPos(x, y);
                    }
                }
                break;
            case SUR:
                y = posAgente[1]+1;
                if (y < mapa.getFilas()) {
                    x = posAgente[0];
                    estado = mapa.getPos(x, y);
                }
                break;
            case SUROESTE:
                // Si no se puede sur ni oeste, no se puede suroeste
                if (getEstado(Movimiento.SUR)>=0 || getEstado(Movimiento.OESTE)>=0) {
                    y = posAgente[1]+1;
                    x = posAgente[0]-1;
                    if (y < mapa.getFilas() && x > -1) {
                        estado = mapa.getPos(x, y);
                    }
                }
                break;
            case OESTE:
                x = posAgente[0]-1;
                if (x > -1) {
                    y = posAgente[1];
                    estado = mapa.getPos(x, y);
                }
                break;
            case NOROESTE:
                // Si no se puede norte ni oeste, no se puede noroeste
                if (getEstado(Movimiento.NORTE)>=0 || getEstado(Movimiento.OESTE)>=0) {
                    x = posAgente[0]-1;
                    y = posAgente[1]-1;
                    if (x > -1 && y > -1) {
                        estado = mapa.getPos(x, y);
                    }
                }
                break;
        }
        // Celda libre
        if (estado == 0)
            estado = calcularDistancia(x, y);
        return estado;
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
        recorrido.add(new int[] {posAgente[0], posAgente[1]});
        mostrarEntorno();
    }
    
    private boolean enRecorrido (int x, int y) {
        boolean encontrado = false;
        for (int pos=0; pos<recorrido.size() && !encontrado; pos++)
            if (recorrido.get(pos)[0]==x && recorrido.get(pos)[1]==y)
                encontrado = true;
        return encontrado;
    }
}
