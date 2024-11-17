/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;

public class Serializador {
    
    public static void serializarEntorno(Entorno entorno, String rutaArchivo) {
        // Usamos Gson para convertir la clase Entorno a JSON
        Gson gson = new Gson();
        String json = gson.toJson(entorno);
        
        // Escribir el JSON en un archivo
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(json);
            System.out.println("Archivo JSON creado con éxito en: " + rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al escribir el archivo JSON.");
        }    
    }
}

