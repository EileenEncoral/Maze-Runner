/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberintoRecursivo;

/**
 *
 * @author Carlotta
 */
public class Paso {
    
    public static final int EXPLORAR = 1;   // se visita la celda
    public static final int DESCARTAR = 2;  // retroceso: celda sin salida
    public static final int CAMINO = 3;     // celda de la ruta final

    public final int fila, columna, tipo;

    public Paso(int fila, int columna, int tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }
}
