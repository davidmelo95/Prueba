/*
 AQUÍ ES DONDE COMIENZA LA DIVERSIÓN WUUU :V

SE COMIENZA CON EL ANALIZADOR LÉXICO, CUANDO OBTENGA EL TOKEN LO ENVIAREMOS AL ANALIZADOR SINTÁCTICO
 */
package analizadorsintax;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author LissetRoman
 */
public class comenzar implements ActionListener {

    Map<String, String> elementos = new HashMap<>();
    ArrayList<String> programa = new ArrayList<>();

    //elementos del lenguaje 3ML
    public void cargarElementos() {
        elementos.put("iguana", "tipo de dato entero");
        elementos.put("dog", "tipo de dato double");
        elementos.put("sheep", "tipo de dato String");
        elementos.put("charmander", "tipo de dato char");
        elementos.put("fish", "tipo de dato float");
        elementos.put("bat", "tipo de dato boolean");
        elementos.put("simona", "palabra reservada if");
        elementos.put("nel", "palabra reservada else");
        elementos.put("principal", "palabra reservada main");
        elementos.put("see", "palabra reservada true");
        elementos.put("nah", "palabra reservada false");
        elementos.put("selector", "palabra reservada switch");
        elementos.put("soup", "System.out.println()");
        elementos.put("para", "palabra reservada, ciclo for");
        elementos.put("mientras", "palabra reservada, ciclo while");
        elementos.put("hacer mientras", "palabra reservada, ciclo do while");
        elementos.put("=", "operador de asignación");
        elementos.put("[", "inicio de bloque");
        elementos.put("]", "fin de bloque");
        elementos.put("(", "inicio de expresión");
        elementos.put(")", "fin de expresión");
        elementos.put(";", "fin de línea");
        elementos.put("+", "operador suma");
        elementos.put("-", "operador resta");
        elementos.put("*", "operador multiplicación");
        elementos.put("/", "operador división");
        elementos.put("<", "operador relacional");
        elementos.put(">", "operador relacional");
        elementos.put("++", "incremento");
        elementos.put(",", "mas asignaciones");
        elementos.put("--", "Decremento");
    }

    public GUI ref;
    //Constructor de la clase principal

    comenzar(GUI x) {
        super();
        //Guarda la referencia al frame principal
        ref = x;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        String direccion = ref.txt_archivo.getText();
        try {
            //comienza la lectura del archivo especificado
            lectura(direccion);
        } catch (IOException ex) {
            Logger.getLogger(comenzar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void lectura(String direccion) throws FileNotFoundException, IOException {
        ref.consola.setText(ref.consola.getText() + "Lectura del archivo: " + direccion + "\n");
        ref.consola.setText(ref.consola.getText() + "\n");

        FileReader file = new FileReader(direccion);
        BufferedReader b = new BufferedReader(file);
        try {
            cargarElementos();
            String cadena = b.readLine();
            while (cadena != null) {
                //por defecto divide por espacios
                StringTokenizer tokens = new StringTokenizer(cadena);
                while (tokens.hasMoreTokens()) {
                    String tokenLeido = tokens.nextToken();
                    ref.consola.setText(ref.consola.getText() + "Token: " + tokenLeido + "\n");
                    buscarToken(tokenLeido);
                    /*if(buscarToken(tokenLeido) == false) { //envia el token y busca su significado
                        System.out.println("Error en etapa léxico");
                    }*/
                }
                cadena = b.readLine();
            }
            b.close();

            ref.consola.setText(ref.consola.getText() + "\n");
            ref.consola.setText(ref.consola.getText() + "El análisis léxico ha finalizado" + "\n");

            Sintax sintaxis1 = new Sintax(programa);
            System.out.println(programa.toString());
            System.out.println(sintaxis1.sauceLloron.toString());
        } 
        catch (IOException e) {
        }
    }

    public boolean esIdentificador(String token) {
        boolean identificador = false;
        String regex = "([a-z0-9]){1,3}";
        if (Pattern.matches(regex, token)) {
            identificador = true;
        }

        return identificador;
    }

    public boolean esConstante(String token) {
        boolean constante = false;
        String regexCons = "([^a-zA-Z][.0-9]+)";
        if (Pattern.matches(regexCons, token)) {
            constante = true;
        }
        return constante;
    }

    public boolean buscarToken(String token) {
        if (elementos.containsKey(token)) {//si el token es elemento del lenguaje
            ref.consola.setText(ref.consola.getText() + token + " = " + elementos.get(token) + "\n");
            
        } else {//si el elemento no pertenece al lenguaje, checar si es un identificador
            if (esIdentificador(token) == true) {
                ref.consola.setText(ref.consola.getText() + token + " = identificador" + "\n");
                
            } else {//si el elemento no es un identificador checar si es constante
                if (esConstante(token) == true) {
                    ref.consola.setText(ref.consola.getText() + token + " = constante" + "\n");
                    
                } else {
                    //si no está definido; es un elemento inválido
                    ref.consola.setText(ref.consola.getText() + token + " = elemento inválido" + "\n");
                    return false;
                }
            }
        }
        programa.add(token);
        return true;
    }
}
