/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pr1;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Scanner;
/**
 *
 * @author carmenxufdz
 */
public class Ejercicio4 extends Agent {
    
    int cantidad, suma;
    double media;
    int contador;
    
    private class RequestNumber extends Behaviour{

        int numero;
        Scanner scanner = new Scanner(System.in);
        
        @Override
        public void action(){
            if (contador < cantidad) {
                System.out.println("Introduce un número: ");
                if (scanner.hasNextInt()) {
                    int numero = scanner.nextInt();
                    suma += numero;
                    contador++;
                } else {
                    System.out.println("No has introducido un número válido");
                    scanner.next();  // Descartar la entrada inválida
                }
            }
            else {
                // Si ya se han introducido todos los números, calculamos la media
                media = (double) suma / cantidad;
                System.out.println("La media es: " + media);
                contador ++;
            }
        }
        
        @Override
        public boolean done() {
            // El comportamiento termina cuando se han introducido todos los números
            return contador >= cantidad+1;
        }
    }
    
    private class Request extends OneShotBehaviour{
        @Override
        public void action() {
            // Pedimos al usuario cuantos numeros va a introducir
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿Cuantos numeros vas a introducir?");

            cantidad = scanner.nextInt();
        }
    }
    
    @Override
    protected void setup()
    {
        suma = 0;
        media = 0.0;
        contador = 0;
        
        addBehaviour(new Request());
        addBehaviour(new RequestNumber());

    }
    
    protected void takeDown()
    {
        System.out.println( "Terminating agent...");
    }

}


