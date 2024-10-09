/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pr1;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;

import java.util.Scanner;
/**
 *
 * @author carmenxufdz
 */
public class Ejercicio4 extends Agent {
    
    int cantidad, suma;
    double media;
    
    private class RequestNumber extends OneShotBehaviour{

        int numero;
        
       @Override
       public void action(){
           Scanner scanner = new Scanner(System.in);
           System.out.println("Introduce un número: ");
           
            if(scanner.hasNextInt())
            {
                numero = scanner.nextInt();
            }else{
                System.out.println("No has introducido un numero valido");
            }
            
            suma += numero;
       }
    }
    
    private class Media extends OneShotBehaviour{
        @Override
        public void action() {
            
           media = (double) suma/cantidad;
           
           System.out.println("La media es: " + media);
        }
    }
    
    @Override
    protected void setup()
    {
        suma = 0;
        media = 0.0;
        //iteracion = 0;
        // Pedimos al usuario cuantos numeros va a introducir
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Cuantos numeros vas a introducir?");
        
        cantidad = scanner.nextInt();
        
        // Creamos la secuencia de comportamientos
        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour();
        
        // introducimos los comportamientos requeridos para pedir todos los numeros
        // que va  introducir el usuario
        for(int i = 0; i < cantidad ; i++)
        {
            sequentialBehaviour.addSubBehaviour(new RequestNumber());
        }
        //Añadimos el comportamiento que hace la media al final
        sequentialBehaviour.addSubBehaviour(new Media());
        
        // Activamos la secuencia de comportamientos
        addBehaviour(sequentialBehaviour);

    }
    
    protected void takeDown()
    {
        System.out.println( " Terminating agent...");
    }

}
