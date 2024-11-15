package p2;

import jade.core.behaviours.Behaviour;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class Comportamiento extends Behaviour {
    private Entorno entorno;
    private Agente agente;
    private boolean move = false;
    
    private boolean terminado = false;
 
    public Comportamiento(Entorno e, Agente a) {
        entorno = e;
        agente = a;
    }
    
    @Override
    public void action() {
        ArrayList<Movimiento> movimientos = agente.getMovs();

        Pair<Movimiento, Double> movimiento;
        Pair<Movimiento, Double> optimo = valorarMovimiento(movimientos.get(0));
        for (int i=1; i<movimientos.size() && optimo.getValue()!=0 ; i++) {
            movimiento = valorarMovimiento(movimientos.get(i));
            if (optimo.getValue()<0 || (movimiento.getValue()>=0 && movimiento.getValue()<optimo.getValue())){
                optimo = movimiento;
            }
        }
        
        if(move){
            agente.mover();
            entorno.moverAgente(optimo.getKey());
            agente.setDistanciaActual();
            move = false;
        }
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Comportamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
    }

    @Override
    public boolean done() {
        if (entorno.agenteEnMeta())
            agente.doDelete();
            terminado = true;
        return entorno.agenteEnMeta();
    }
    
    // Devuelve un pair del movimiento y el coste
    private Pair<Movimiento,Double> valorarMovimiento (Movimiento direccion) {
        Pair<Double, Integer> estado = entorno.getEstado(direccion);
        double valor = estado.getKey();
        double tam_mapa = Math.sqrt(entorno.getFilas()*entorno.getFilas() + entorno.getColumnas()*entorno.getColumnas());
        
        if(valor > 0){
            int veces = estado.getValue();
            
            // Solo puede valer 0 la meta. Si no estuviera esta condicional
            // también valdrian 0 todos las casillas colindantes a ella.
            if(agente.getDistanciaActual() > valor && valor > 1){
                valor -= 1;
                
            }

            valor += veces*tam_mapa;
        
            
        }
        return new Pair<> (direccion, valor);
    }
    
    public void moverAgente(){
        move = true;
    }
    
    public boolean terminado(){
        return terminado;
    }

}