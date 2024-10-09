/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pr1;


import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
/**
 *
 * @author carmenxufdz
 */
public class Ejercicio2 extends Agent{
    private class PrintMessage extends OneShotBehaviour{
        @Override
        public void action(){
            System.out.println("Soy el agente del Ejercicio 2");
            System.out.println("Este comportamiento solo se realizará una vez");
            doDelete();
        }
        
    }
    
    @Override
    protected void setup(){
        addBehaviour(new PrintMessage());
    }
    
    @Override
    protected void takeDown(){
        System.out.println( "Terminating agent...");
    }
}

