/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laberintoRecursivo;

/**
 *
 * @author Carlotta
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class PanelLaberinto extends JPanel {
    private Laberinto laberinto;
    private int[][] marca; // 0 nada, 1 explorando, 2 descartada, 3 camino

    public PanelLaberinto() {
        // Clic sobre una celda: alterna pared/libre (para armar tus propios laberintos)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (laberinto == null) return;
                int tam = tamCelda();
                int x = e.getX() - offsetX();
                int y = e.getY() - offsetY();
                if (x < 0 || y < 0) return; // clic fuera del tablero
                int c = x / tam, f = y / tam;
                if (f >= laberinto.getFilas() || c >= laberinto.getColumnas()) return;
                int v = laberinto.get(f, c);
                if (v == Laberinto.LIBRE) laberinto.set(f, c, Laberinto.PARED);
                else if (v == Laberinto.PARED) laberinto.set(f, c, Laberinto.LIBRE);
                limpiarMarcas();
            }
        });
    }

    public void setLaberinto(Laberinto l) {
        this.laberinto = l;
        marca = new int[l.getFilas()][l.getColumnas()];
        repaint();
    }

    public Laberinto getLaberinto() { return laberinto; }

    public void limpiarMarcas() {
        marca = new int[laberinto.getFilas()][laberinto.getColumnas()];
        repaint();
    }

    public void aplicarPaso(Paso p) {
        marca[p.fila][p.columna] = p.tipo; // 1,2,3 coinciden con las constantes de Paso
        repaint();
    }

    private int tamCelda() {
        return Math.max(1, Math.min(getWidth() / laberinto.getColumnas(),
                                    getHeight() / laberinto.getFilas()));
    }

    private int offsetX() {
        return (getWidth() - tamCelda() * laberinto.getColumnas()) / 2;
    }

    private int offsetY() {
        return (getHeight() - tamCelda() * laberinto.getFilas()) / 2;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (laberinto == null) return;
        int tam = tamCelda();
        g.translate(offsetX(), offsetY());
        g.setFont(new Font("Arial", Font.BOLD, Math.max(10, tam / 3)));

        for (int f = 0; f < laberinto.getFilas(); f++) {
            for (int c = 0; c < laberinto.getColumnas(); c++) {
                int v = laberinto.get(f, c);
                Color color = Color.WHITE;
                String texto = "";

                switch (v) {
                    case Laberinto.PARED -> color = Color.DARK_GRAY;
                    case Laberinto.INICIO -> {
                        color = new Color(46, 204, 113);
                        texto = "I";
                    }
                    case Laberinto.SALIDA -> {
                        color = new Color(231, 76, 60);
                        texto = "S";
                    }
                    default -> {
                        switch (marca[f][c]) {
                            case 1 -> color = Color.YELLOW;
                            case 2 -> color = Color.ORANGE;
                            case 3 -> color = new Color(52, 152, 219);
                        }
                    }

                }
                // Inicio y salida conservan su color; el camino se nota por el borde
                g.setColor(color);
                g.fillRect(c * tam, f * tam, tam, tam);
                if (marca[f][c] == 3 && (v == Laberinto.INICIO || v == Laberinto.SALIDA)) {
                    g.setColor(new Color(52, 152, 219));
                    g.fillRect(c * tam + tam / 4, f * tam + tam / 4, tam / 2, tam / 2);
                }
                g.setColor(Color.BLACK);
                g.drawRect(c * tam, f * tam, tam, tam);
                if (!texto.isEmpty()) {
                    g.drawString(texto, c * tam + tam / 2 - 5, f * tam + tam / 2 + 5);
                }
            }
        }
    }
}
