/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pr1;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author carmenxufdz
 */
public class Ejercicio3 extends Agent{
    private class Comportamiento extends CyclicBehaviour{
        public void action(){
            System.out.println("Soy el agente del Ejercicio 3");
            try{
                Thread.sleep(2000); // wait 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void setup(){
        addBehaviour(new Comportamiento());
    }
}
