/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package p2;

/**
 *
 * @author carme
 */
public interface AgenteInterface {
    public void cargarMapa(String ruta);
    public void iniciarComportamiento();
    public Entorno getEntorno();
    public void moverAgente();
    public String Energia();
}

