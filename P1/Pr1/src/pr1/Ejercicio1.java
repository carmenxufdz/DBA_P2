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
public class Ejercicio1 extends Agent{
    @Override
    protected void setup()
    {
        System.out.println("Soy el agente del Ejercicio 1");
        doDelete();
    }
    
    @Override
    protected void takeDown()
    {
        System.out.println( " Terminating agent...");
    }
}
