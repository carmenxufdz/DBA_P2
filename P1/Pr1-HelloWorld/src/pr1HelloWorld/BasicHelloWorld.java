/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pr1HelloWorld;

import jade.core.Agent;
import java.util.Iterator;
/**
 *
 * @author carmenxufdz
 */


/**
 *
 * @author carme
 */
public class BasicHelloWorld extends Agent{

    /**
     * @param args the command line arguments
     */
    @Override
    protected void setup() {
        // TODO code application logic here
        System.out.println("Hola! Soy tu primer agente");
        
        System.out.println("My local-name is" + getAID().getLocalName());
        System.out.println("My GUID is" + getAID().getName());
        System.out.println("My adresses are: ");
        
        Iterator it = getAID().getAllAddresses();
        
        while(it.hasNext()){
            System.out.println("- " + it.next());
        }
        
        doDelete();
    }
    
    @Override
    public void takeDown() {
        System.out.println("Terminating agent...");
    }
    
}