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
 
    public Comportamiento(Entorno e, Agente a) {
        entorno = e;
        agente = a;
    }
    
    @Override
    public void action() {
        ArrayList<Movimiento> movimientos = agente.getMovs();
        
        Pair<Movimiento, Double> movimiento;
        Pair<Movimiento, Double> optimo = valorarMovimiento(movimientos.get(0));
        for (int i=1; i<movimientos.size() && optimo.getValue()!=0; i++) {
            movimiento = valorarMovimiento(movimientos.get(i));
            if (optimo.getValue()<0 || (movimiento.getValue()>=0 && movimiento.getValue()<optimo.getValue())){
                optimo = movimiento;
            }
        }
        
        if(move){
            agente.mover();
            entorno.moverAgente(optimo.getKey());
            entorno.mostrarEntorno();
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
        return entorno.agenteEnMeta();
    }
    
    // Devuelve un pair del movimiento y el coste
    private Pair<Movimiento,Double> valorarMovimiento (Movimiento direccion) {
        Pair<Double, Integer> estado = entorno.getEstado(direccion);
        double valor = estado.getKey();
        
        if(valor > 0){
            valor += estado.getValue()*estado.getKey(); // veces * distancia     
        }
        return new Pair<> (direccion, valor);
    }
    
    public void moverAgente(){
        move = true;
    }
}