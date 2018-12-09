/*
 PERMITE SELECCIONAR UN ARCHIVO DE TEXTO PARA LECTURA
 */
package analizadorsintax;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author LissetRoman
 */
public class Seleccion implements ActionListener {

    GUI ref;
    //Constructor de la Clase

    Seleccion(GUI x) {
        super();
        //Guarda la referencia al frame principal
        ref = x;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Crea un objeto tipo FileChooser
        JFileChooser chooser = new JFileChooser();
        //Crea un filtro de archivos, tipo txt y arff
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt", "arff");
        chooser.setFileFilter(filter);
        //Llama a l fileChooser, si el archivo seleccionado existe procede
        int returnVal = chooser.showOpenDialog(ref);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().exists()) {
                //Imprime la direccion del archivo en el textfFile
                ref.txt_archivo.setText(chooser.getSelectedFile().toString());
            } else {
                JOptionPane.showMessageDialog(ref, "ERROR: El archivo no existe");
            }
        }
    }
}

