/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberintoRecursivo;

/**
 *
 * @author Carlotta
 */
import java.util.ArrayList;
import java.util.List;

public class Laberinto {
    public static final int LIBRE = 0, PARED = 1, INICIO = 2, SALIDA = 3;

    private final int[][] celdas;
    private boolean[][] visitada;
    private List<Paso> pasos = new ArrayList<>();

    // Direcciones: arriba, derecha, abajo, izquierda
    private final int[] DF = {-1, 0, 1, 0};
    private final int[] DC = {0, 1, 0, -1};

    public Laberinto(int[][] datos) {
        celdas = new int[datos.length][datos[0].length];
        for (int i = 0; i < datos.length; i++) {
            celdas[i] = datos[i].clone();
        }
    }

    public int getFilas()    { return celdas.length; }
    public int getColumnas() { return celdas[0].length; }
    public int get(int f, int c) { return celdas[f][c]; }
    public void set(int f, int c, int valor) { celdas[f][c] = valor; }
    public List<Paso> getPasos() { return pasos; }

    /** Busca el inicio en la matriz (no hay coordenadas escritas a mano).
     * @return  */
    public boolean resolver() {
        pasos = new ArrayList<>();
        visitada = new boolean[getFilas()][getColumnas()];
        for (int f = 0; f < getFilas(); f++) {
            for (int c = 0; c < getColumnas(); c++) {
                if (celdas[f][c] == INICIO) {
                    return buscar(f, c);
                }
            }
        }
        return false;
    }

    /** Método recursivo con retroceso (backtracking). */
    private boolean buscar(int f, int c) {
        // 1. Dentro de los límites
        if (f < 0 || f >= getFilas() || c < 0 || c >= getColumnas()) return false;
        // 2. Válida: no es pared y no fue visitada
        if (celdas[f][c] == PARED || visitada[f][c]) return false;

        // 3. Marcar como visitada y registrar la exploración
        visitada[f][c] = true;
        pasos.add(new Paso(f, c, Paso.EXPLORAR));

        // 4. Caso base: llegó a la salida
        if (celdas[f][c] == SALIDA) {
            pasos.add(new Paso(f, c, Paso.CAMINO));
            return true;
        }

        // 5. Caso recursivo: probar las 4 direcciones
        for (int i = 0; i < 4; i++) {
            if (buscar(f + DF[i], c + DC[i])) {
                pasos.add(new Paso(f, c, Paso.CAMINO)); // forma parte de la ruta
                return true;
            }
        }

        // 6. Retroceso: ninguna dirección funcionó
        pasos.add(new Paso(f, c, Paso.DESCARTAR));
        return false;
    }

    /** Configuraciones de prueba.
     * @param n
     * @return  */
    public static int[][] prueba(int n) {
        return switch (n) {
            case 0 -> new int[][]{
                {2, 0, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 0, 3}};
            case 1 -> new int[][]{
                {2, 0, 0, 1, 0, 0, 0},
                {1, 1, 0, 1, 0, 1, 1},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 3}};
            default -> new int[][]{
                {2, 0, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1},
                {0, 0, 0, 1, 3}};
        }; // Ruta sencilla
        // Callejones sin salida
        // Salida bloqueada
    }
}