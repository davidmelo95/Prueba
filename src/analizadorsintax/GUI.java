/*
 INTERFAZ GRÁFICA
 */
package analizadorsintax;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author LissetRoman
 */
public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    // elementos de la interfaz grafica
    public JTextArea consola;
    public JTextField txt_archivo;
    private JPanel contentPane;
    private JPanel panelSup;
    private JButton btn_seleccion;
    private JButton btn_comenzar;

    //Constructor de la clase
    public GUI() {
        //Establecemos las propiedades del frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setMinimumSize(this.getSize());
        this.setTitle("Analizador Sintáctico 3ML");
        //Establecemos las propiedades del contenedor principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        //Agrega un Panel a la parte norte del Frame
        panelSup = new JPanel();
        contentPane.add(panelSup, BorderLayout.NORTH);
        //Declara los botones del frame
        btn_seleccion = new JButton("Selecciona Archivo");
        btn_comenzar = new JButton("Comenzar");
        //Declara el TextField para mostrar la ruta del archivo
        txt_archivo = new JTextField();
        txt_archivo.setText("Archivo no seleccionado");
        txt_archivo.setEditable(false);
        txt_archivo.setBackground(new Color(255, 255, 255));
        txt_archivo.setColumns(30);
        panelSup.add(btn_seleccion);
        panelSup.add(txt_archivo);
        panelSup.add(btn_comenzar);

        //Agrega un scrollPane al Centro del frame
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);
        //Agrega un textArea donde mostraremos los resultados al ScrolaPanel
        consola = new JTextArea();
        consola.setEditable(false);
        scrollPane.setViewportView(consola);

        //Añade los acton Listener correspondientes a cada boton
        //Se les envía la referencia al frame principal
        btn_seleccion.addActionListener(new Seleccion(this));
        btn_comenzar.addActionListener(new comenzar(this));
    }
}
