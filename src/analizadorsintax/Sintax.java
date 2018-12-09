/*
 ANALIZADOR SINTÁCTICO
 */
package analizadorsintax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;


public class Sintax {

    ArrayList<String> elementos = new ArrayList<String>();
    int posicion = 0;
    Arbol sauceLloron;
    //Los nodos hechos en esta sección únicamente son nodos "raíz", en español, tendrán hijos
    //Aquí irán los nodos hijos, nietos que tienen más hijos, etc.
    Nodo nodoPrograma = new Nodo("Programa", false);
    Nodo nodoCuerpo = new Nodo("Cuerpo", false);
    Nodo nodoCuerpoMientras = new Nodo("CuerpoMientras", false);
    Nodo nodoCuerpoSimona = new Nodo("CuerpoSimona", false);
    Nodo nodoCuerpoNel = new Nodo("CuerpoNel", false);
    Nodo nodoDecla = new Nodo("Decla", false);
    Nodo nodoPara = new Nodo("Para", false);
    Nodo nodoMientras = new Nodo("Mientras", false);
    Nodo rSimona = new Nodo("Simona", false);
    Nodo nodoCondicion = new Nodo("Cond", false);
    Nodo nodoHacer = new Nodo("Hacer", false);
    Nodo nodoSimona = new Nodo("Simona", true);
    Nodo nodoSimonaNel = new Nodo("Nel", true);
    Nodo nodoSigue = new Nodo("Sigue", false);
    Nodo nodoAsignacion = new Nodo("Asignacion", false); //Nodo "Asig", hijo directo de "nodoDecla"

    //ArrayList<String> reglaPrograma = new ArrayList<String>();
    //ArrayList<String> reglaDecla = new ArrayList<String>();
    public Sintax(ArrayList<String> programa) {
        elementos = (ArrayList<String>) programa.clone();
        buscarReglas();
    }

    public void buscarReglas() {

        //toma el primer elemento de la lista
        String primerElm = elementos.get(posicion);
        System.out.println("entro al sintactico");
        switch (primerElm) {
            case "principal":
                //nogal.setRaiz(new Nodo("Programa",false));//se define un nodo padre de todos los hijitos
                sauceLloron = new Arbol(nodoPrograma); //Se crea el árbol y su raíz o nodo principal es "Programa"
                if (ReglaPrograma(primerElm)) {
                    System.out.println("Se identificó un programa");
                    /* elementos.remove("pricipal");
                    elementos.remove("[");
                    elementos.remove("]");*/
                } else {
                    System.out.println("Se esperaba un Programa");
                }
                break;
            default:
                break;
        }
        System.out.println("Análisis sintáctico finalizado");
    }

    public boolean ReglaPrograma(String token) {

        if (token.equals("principal")) {
            sauceLloron.getRaiz().hijos.add(new Nodo("Principal", true)); //Nodo "Principal", es hoja
            posicion++; //como recorrer en preorden
            if (elementos.get(posicion).equals("[")) {
                sauceLloron.getRaiz().hijos.add(new Nodo("CA", true)); //Nodo "Corchete de Apertura", es hoja
                posicion++;
                if (ReglaCuerpo(elementos.get(posicion), "ReglaPrograma")) {
                    if (elementos.get(elementos.size() - 1).equals("]")) {
                        sauceLloron.getRaiz().hijos.add(new Nodo("CC", true)); //Nodo "Corchete de Cerradura", es hoja
                        //System.out.println("se identificó un programa");

                        /*if(posicion<elementos.size()){
                            System.out.println("aun no termina el programa y vuelve a buscar cuerpo");
                            posicion++;
                            
                        }*/
                        return true;
                    }
                }
                /*if (buscarElemento("]")) {
                    return true;
                }*/
            }
        }
        return false;
    }

    public boolean ReglaCuerpo(String token, String sel) {
        /* ANTERIOR BLOQUE
        if ((ReglaDecla(token) || ReglaPara(token) || ReglaSimona(token) || ReglaMientras(token) || ReglaHacerMientras(token) || ReglaIncDec2(token) || ReglaOperacion(token) || Cuerpo2(token))) {
            System.out.println("se identificó un cuerpo");

            if (posicion < elementos.size() - posicion) {
                System.out.println("aun no termina el progrma y vuelve a buscar cuerpo");
                System.out.println("nos quedamos en el token: " + elementos.get(posicion));
                //posicion++;
                //busca si es un cuerpo provisional
                if (CuerpoProvisional(elementos.get(posicion))) {
                    return true;
                } else {
                    ReglaCuerpo(elementos.get(posicion));
                }

            } else {
                return true;
            }
            //posicion++;

        }*/
        
        //String token=elementos.get(posicion);
        String destino = "";
        switch(sel) {
            case "ReglaPara":
                break;
            case "ReglaPrograma":
                sauceLloron.getRaiz().hijos.add(nodoCuerpo); //Nodo "Cuerpo", no es hoja
                destino = "ReglaCuerpo";
                break;
            case "ReglaCuerpo2":
                break;
            case "ReglaMientras":
                nodoMientras.hijos.add(nodoCuerpoMientras); //Nodo Cuerpo dentro de Mientras
                destino = "ReglaCuerpoMientras";
                break;
            case "ReglaSimona":
                nodoSimona.hijos.add(nodoCuerpoSimona);
                destino = "ReglaSimona";
                break;
            case "ReglaSimonaNel":
                nodoSigue.hijos.add(nodoCuerpoNel);
                destino = "ReglaSimonaNel";
                break;
                    
        }
        
        boolean respuesta = false;
        System.out.println("token: "+token);
        switch (token) {
            
            //Verificar el sauce llorón para las reglas: para, simona y mientras
            
            case "para":
                respuesta = ReglaPara(token);
                break;
            case "simona":
                respuesta = ReglaSimona(token);
                break;
            case "mientras":
                respuesta = ReglaMientras(token);
                break;
            case "hacer":
                respuesta = ReglaHacerMientras(token);
                break;
            case "]":
                respuesta = true;
                return respuesta;
            default:
                respuesta = ReglaDecla(token, destino);
                break;
        }
        
        if(respuesta) {
            System.out.println("Se identificó un cuerpo");
            System.out.println("Elementos: "+elementos.size());
            System.out.println("Pos: "+posicion);
                if (posicion < (elementos.size()-posicion)+2) { 
                    System.out.println("aun no termina el progrma y vuelve a buscar cuerpo");
                    System.out.println("nos quedamos en el token: " + elementos.get(posicion));
                    //posicion++;
                    //busca si es un cuerpo provisional
                    if (CuerpoProvisional(elementos.get(posicion))) {
                        return true;
                    } else {
                        ReglaCuerpo(elementos.get(posicion), "");
                    }

                } else {
                    return true;
                }
        }
        return false;

    }

    public boolean CuerpoProvisional(String token) {
        boolean respuesta = false;
        switch(token) {
            case "]":
                respuesta = true;
                
                return respuesta;
            case ";":
                respuesta = true;
                return respuesta;
            default:
                respuesta = false;
                return false;
        }
        /*if (token.equals("]") || token.equals(";")) {
            System.out.println("se identificó un cuerpo provisional");
            return true;
        }
        return false;*/
    }

    public boolean Cuerpo2(String token) {
        //String token=elementos.get(posicion);
        if (ReglaCuerpo(elementos.get(posicion), "ReglaCuerpo2")) {
            posicion++;
            return true;
        } else {
            //vacío
            return true;
        }
        //return false;
    }

    public boolean ReglaDecla(String token, String sel) {
        switch(sel) {
            case "ReglaPara":
                //nodoCuerpo.hijos.add(nodoDecla);+
                Nodo nodoDeclaPara = new Nodo("Decla", false);
                nodoCuerpo.hijos.add(nodoDeclaPara);
                break;
            case "ReglaCuerpo":
                nodoCuerpo.hijos.add(nodoDecla); //Añado el nodo "Decla" que es hijo del nodo "Cuerpo"
                break;
            case "ReglaCuerpoMientras":
                nodoCuerpoMientras.hijos.add(nodoDecla);
                break;
            case "ReglaSimona":
                nodoCuerpoSimona.hijos.add(nodoDecla);
                break;
            case "ReglaSimonaNel":
                nodoCuerpoNel.hijos.add(nodoDecla);
                break;
        }
        
        if (ReglaTipoDato(token)) { //En ReglaTipoDato crearé otros nodos hijos de "nodoDecla"
            System.out.println(elementos.get(posicion));
            posicion++;
            if (ReglaVariable(elementos.get(posicion))) { //En ReglaVariable crearé otros nodos hijos de "nodoDecla"
                posicion++;
                if (ReglaAsignacion(elementos.get(posicion))) { //En ReglaAsignacion crearé el nodo Asignación que tendrá hijos
                    if (elementos.get(posicion).equals(";")){
                        Nodo finDecla = new Nodo(";", true); //Es el nodo ';', hijo directo de nodoDecla, es hoja
                        nodoDecla.hijos.add(finDecla);//Se hace hijo de nodoDecla
                        System.out.println("se identificó una declaración");
                        posicion++;
                        return true;
                    } else {
                        if (ReglaMasIdentificadores(elementos.get(posicion))) {
                            return true;
                        }
                    }
                }
                /*if(elementos.get(posicion).equals("=")){
                        return true;
                    }
                    else{
                        if(elementos.get(posicion).equals(";")){
                            return true;
                        }
                    }*/
            }
        }
        System.out.println("no hay decla, termina");
        return false;

    }

    public boolean ReglaMasIdentificadores(String token) {
        if (elementos.get(posicion).equals(",")) {
            posicion++;
            if (ReglaVariable(elementos.get(posicion))) {
                posicion++;
                if (ReglaAsignacion(elementos.get(posicion))) {
                    System.out.println("se identificarosn más variables");
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean ReglaTipoDato(String token) {
        /* ANTERIOR BLOQUE
        if (token.equals("iguana") || token.equals("dog") || token.equals("fish") || token.equals("bat") || token.equals("sheep") || token.equals("charmander")) {
            System.out.println("se identificó un tipo de dato: " + token);
            return true;
        }
        return false;*/
        
        //NUEVO BLOQUE
        Nodo nodoTipoDato = new Nodo("TipoDato", true);
        nodoDecla.hijos.add(nodoTipoDato); //Creo el nodo "TipoDato", hijo del nodo "Decla", es hoja
        boolean resp = false;
        switch(token) {
            case "iguana":
                resp = true;
                System.out.println("Tipo de dato detectado: "+token);
                break; 
            case "dog":
                resp = true;
                break;
            case "fish":
                resp = true;
                break;
            case "bat": 
                resp = true;
                break;
            case "sheep":
                resp = true;
                break;
            case "charmander":
                resp = true;
                break;
        }
        if(resp) {
            //Creo el nodo hijo de "tipoDato"
            nodoTipoDato.hijos.add(new Nodo(token, true)); //Nodo "tipoDato" se le crea un hijo Iguana/Fish/Sheep/Bat según sea el caso, es hoja
            return true;
        }
        else
            return false;
    }

    public boolean ReglaVariable(String token) {
        String regex = "[a-z]+[0-9]{1,3}|[0-9]+[a-z]{1,3}|[a-z]{1,3}"; //Antigua regex -> ([a-z0-9]){1,3}
        
        if (Pattern.matches(regex, token)) {
            System.out.println("se identificó una variable: " + token);
            Nodo nodoVar = new Nodo("Variable", false); //Creo el nodo "var", no es hoja
            nodoDecla.hijos.add(nodoVar); //Añado el "nodoVar" al "nodoDecla" porque es su bendición (hijazo de su vidaza)
            nodoVar.hijos.add(new Nodo(token, true)); //Creo mi nodo variable y es hijo de "nodoVar", 
                                                      //esto es, si mi variable es "i", éste es mi nodo y 
                                                      //su padre es nodoVar automáticamente, es hoja
            return true;
        }
        return false;
    }

    public boolean ReglaIdNum(String token, String sel) {
        Nodo nodoIdNum = new Nodo("Num", false);
        switch(sel) {
            case "ReglaCondicion":
                nodoCondicion.hijos.add(nodoIdNum);
                break;
            case "ReglaMasCond":
                break;
            case "ReglaAsignacion":
                nodoAsignacion.hijos.add(nodoIdNum);
                break;
            case "ReglaOperacion":
                break;
            case "ReglaMasOperacion":
                break;
        }
        String regexNum = "([0-9]+)";
        //String cadena = "[a-zA-Z]";
        if (ReglaVariable(token) || Pattern.matches(regexNum, token)) { 
            System.out.println("se identificó un idNum: " + token);
            Nodo nodoVal = new Nodo(token, true); //Éste nodo es un número para una asignación, es hoja
            nodoIdNum.hijos.add(nodoVal); //Se hace hijo de nodoAsignación
            return true;
        }
        return false;
    }

    public boolean ReglaAsignacion(String token) {
        nodoDecla.hijos.add(nodoAsignacion); //El nodo anterior lo hago hijito de nodoDecla
        if (token.equals("=")) {
            Nodo nodoIgual = new Nodo(token, true); //Es el nodo "=", hijo directo de Asignacion
            nodoAsignacion.hijos.add(nodoIgual); //Aquí lo hago hijo de "nodoAsignacion"
            posicion++;
            if (ReglaIdNum(elementos.get(posicion), "ReglaAsignacion")) { //En reglaIdNum creare otros nodos...
                posicion++;
                if (masAsignaciones(elementos.get(posicion))) {
                    System.out.println("se identificó una asignación: " + token);
                    return true;
                }
            }
        } else {
            //vacío
            System.out.println("no hay asignacion");
            return true;
        }
        return true;
    }

    //Verificar...
    public boolean masAsignaciones(String token) {
        if (token.equals(",")) {
            Nodo nodoComa = new Nodo(token, true); //Habrá más asignaciones asi que va este nodo, es hoja
            nodoDecla.hijos.add(nodoComa); //Hijo de nodoDecla
            posicion++;
            if (ReglaVariable(elementos.get(posicion))) {
                posicion++;
                if (ReglaAsignacion(elementos.get(posicion))) {
                    //posicion++;
                    return true;
                }
            }
        } else {
            //vacío
            return true;
        }
        return false;
    }

    /* public boolean ReglaCuerpo(String token) {
        String token = elementos.get(pos);

        return false;
    }*/
    public boolean ReglaPara(String token) {
        System.out.println("comparando reglapara");
        if (token.equals("para")) {
            Nodo nodoPara = new Nodo("Para", true);
            nodoCuerpo.hijos.add(nodoPara);
            System.out.println("para");
            posicion++;
            System.out.println("vamos en el token: " + elementos.get(posicion));
            if (elementos.get(posicion).equals("(")) {
                Nodo nodoPPA = new Nodo("(", true);
                nodoCuerpo.hijos.add(nodoPPA);
                posicion++;
                System.out.println("vamos en el token: " + elementos.get(posicion));
                if (ReglaDecla(elementos.get(posicion), "ReglaPara")) {
                    //posicion++;
                    //if(elementos.get(posicion).equals(";")){
                    // posicion++;
                    Nodo PpYc = new Nodo(";", true);
                    nodoCuerpo.hijos.add(PpYc);
                    System.out.println("vamos en el token: " + elementos.get(posicion));
                    if (ReglaCondicion(elementos.get(posicion), "ReglaPara")) {
                        posicion++;
                        nodoCuerpo.hijos.add(PpYc);
                        System.out.println("vamos en el token: " + elementos.get(posicion));
                        if (elementos.get(posicion).equals(";")) {
                            
                            posicion++;
                            System.out.println("este elemento le llega a reglainc: " + elementos.get(posicion));
                            if (ReglaIncDec(elementos.get(posicion))) {
                                //System.out.println(elementos.get(posicion));
                                posicion++;
                                //System.out.println(elementos.get(posicion));
                                if (elementos.get(posicion).equals(")")) {
                                    Nodo nodoPPC = new Nodo(")", true);
                                    nodoCuerpo.hijos.add(nodoPPC);
                                    System.out.println(elementos.get(posicion));
                                    posicion++;

                                    if (elementos.get(posicion).equals("[")) {
                                        Nodo nodoPCA = new Nodo("[", true);
                                       nodoCuerpo.hijos.add(nodoPCA);
                                        posicion++;
                                        
                                        System.out.println("buscando el cuerpo del for");
                                        System.out.println(elementos.get(posicion));
                                        if (ReglaCuerpo(elementos.get(posicion),"ReglaPara")) {
                                            posicion++;
                                            if (elementos.get(posicion).equals("]")) {
                                                Nodo nodoPCC = new Nodo("]", true);
                                                nodoCuerpo.hijos.add(nodoPCC);
                                                System.out.println("se identificó un ciclo for");
                                                posicion++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //}
                }
            }
        }
        //String token = elementos.get(posicion);

        return false;
    }

    public boolean ReglaMientras(String token) {
        if (token.equals("mientras")) {
            nodoCuerpo.hijos.add(nodoMientras);
            posicion++;
            if (elementos.get(posicion).equals("(")) {
                nodoMientras.hijos.add(new Nodo("(", true)); //Nodo PA
                posicion++;
                if (ReglaCondicion(elementos.get(posicion), "ReglaMientras")) {
                    posicion++;
                    if (elementos.get(posicion).equals(")")) {
                        nodoMientras.hijos.add(new Nodo(")", true)); //Nodo PC
                        posicion++;
                        if (elementos.get(posicion).equals("[")) {
                            nodoMientras.hijos.add(new Nodo("[", true)); //Nodo CA
                            posicion++;
                            if (ReglaCuerpo(elementos.get(posicion), "ReglaMientras")) {
                                posicion++;
                                if (elementos.get(posicion).equals("]")) {
                                    nodoMientras.hijos.add(new Nodo("]", true)); //Nodo CC
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean ReglaHacerMientras(String token) {
        //String token = elementos.get(pos);

        return false;
    }

    public boolean ReglaSimona(String token) {
        System.out.println("comparando regla simona, le llegó el token: " + token);
        if (elementos.get(posicion).equals("simona")) {
            nodoCuerpo.hijos.add(nodoSimona);
            posicion++;
            System.out.println("vamos en el token: " + elementos.get(posicion));
            if (elementos.get(posicion).equals("(")) {
                nodoSimona.hijos.add(new Nodo("(", true)); //Nodo PA
                posicion++;
                System.out.println("vamos en el token: " + elementos.get(posicion));
                if (ReglaCondicion(elementos.get(posicion), "ReglaSimona")) {
                    posicion++;
                    System.out.println("vamos en el token: " + elementos.get(posicion));
                    System.out.println(elementos.get(posicion));
                    if (elementos.get(posicion).equals(")")) {
                        nodoSimona.hijos.add(new Nodo(")", true)); //Nodo PC
                        posicion++;
                        System.out.println("buscando el cuerpo de simona");
                        System.out.println(elementos.get(posicion));
                        if (elementos.get(posicion).equals("[")) {
                           nodoSimona.hijos.add(new Nodo("[", true)); //Nodo CA
                            posicion++;
                            if (ReglaCuerpo(elementos.get(posicion), "ReglaSimona")) {
                                posicion++;
                                System.out.println("vamos en el token: " + elementos.get(posicion));
                                nodoSimona.hijos.add(new Nodo("]", true)); //Nodo CC de un cuerpo provisional
                                //como aquí compara la opción de cuerpo provisional nos pasamos directo al nel
                                if (elementos.get(posicion).equals("nel")) {
                                    nodoSimona.hijos.add(nodoSigue);
                                    nodoSigue.hijos.add(nodoSimonaNel);
                                    posicion++;
                                    if (elementos.get(posicion).equals("[")) {
                                        nodoSigue.hijos.add(new Nodo("[", true)); //Nodo CA Nel
                                        posicion++;
                                        System.out.println("vamos en el token: " + elementos.get(posicion));
                                        if (ReglaCuerpo(elementos.get(posicion), "ReglaSimonaNel")) {
                                            System.out.println("se identificó un if-else");
                                            nodoSigue.hijos.add(new Nodo("]", true)); //Nodo CC Nel
                                            return true;
                                        }
                                    }
                                }
                                /*if(elementos.get(posicion).equals("]")){
                                    System.out.println("se identificó un if");
                                    posicion++;
                                    return true;
                                }*/
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean ReglaOperacion(String token) {
        System.out.println("comparando reglaOPERACIÓN, recibió este token: " + token);
        System.out.println("VAMOS EN ESTE TOKEN: " + elementos.get(posicion));
        if (ReglaIdNum(token, "ReglaOperacion")) { ///CHECK
            posicion++;//posicion++;
            System.out.println("VAMOS EN ESTE TOKEN: " + elementos.get(posicion));
            if (elementos.get(posicion).equals("=")) {
                posicion++;
                System.out.println("VAMOS EN ESTE TOKEN: " + elementos.get(posicion));
                if (ReglaIdNum(elementos.get(posicion), "ReglaOperacion")) { ///CHECK
                    posicion++;
                    if (ReglaMasOperacion(elementos.get(posicion))) {
                        System.out.println("se identificó una operación");
                        return true;
                    } else {
                        if (elementos.get(posicion).equals(";")) {
                            System.out.println("se identificó una operación sin más operaciones");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean ReglaMasOperacion(String token) {
        if (ReglaOpArit(elementos.get(posicion))) {
            posicion++;
            if (ReglaIdNum(elementos.get(posicion), "ReglaMasOperacion") || ReglaMasOperacion(elementos.get(posicion))) {
                posicion++;
                if (elementos.get(posicion).equals(";")) {
                    return true;
                }

            }
        } else {
            return true;
        }
        return false;
    }

    public boolean ReglaIncDec(String token) {
        System.out.println("esto le llegó a inc: " + token);
        if (ReglaVariable(elementos.get(posicion))) {
            posicion++;
            if (ReglaOperadores(elementos.get(posicion))) {
                //posicion++;
                System.out.println("se identificó un incremento/decremento: empieza en: " + token + " y termina en: " + elementos.get(posicion));
                return true;
            }
            else {
                System.out.println("fallo operadores");
            }
        }
        else {
            System.out.println("fallo variable");
        }

        return false;
    }

    public boolean ReglaIncDec2(String token) {
        System.out.println("esto le llegó a inc2: " + token);
        if (ReglaVariable(elementos.get(posicion))) {
            posicion++;
            System.out.println("vamos en el token: " + elementos.get(posicion));
            if (ReglaOperadores(elementos.get(posicion))) {
                posicion++;
                System.out.println("vamos en el token: " + elementos.get(posicion));
                if (elementos.get(posicion).equals(";")) {
                    System.out.println("se identificó un incremento/decremento: empieza en: " + token + " y termina en: " + elementos.get(posicion));
                    posicion++;
                    System.out.println("estamos en inc2 vamos en el token: " + elementos.get(posicion));
                    return true;
                }

            }
        }
        posicion--;

        return false;
    }

    public boolean ReglaOperadores(String token) {
        boolean resp = false;
        switch (token) {
            case "+":
                resp = true;
                break;
            case "-":
                resp = false;
                break;
            case "++":
                resp = true;
                break;
            case "--":
                resp = true;
                break;
        }

        if (resp) {
            System.out.println("se identifico un operador");
            return true;
        } else {
            return false;
        }

    }

    public boolean ReglaCondicion(String token, String sel) {
        switch(sel) {
            case "ReglaPara":
                nodoPara.hijos.add(nodoCondicion);
                break;
            case "ReglaMientras":
                nodoMientras.hijos.add(nodoCondicion);
                break;
            case "ReglaSimona":
                nodoSimona.hijos.add(nodoCondicion);
                break;
            case "ReglaMasCond":
                //nodoMasCondic.hijos.add(nodoCondicion);
                break;    
        }
        System.out.println("comparando Regla condición: " + token);
        
        
        if (ReglaIdNum(elementos.get(posicion),"ReglaCondicion")) {
            posicion++;
            System.out.println("vamos en el token idNum: " + elementos.get(posicion));
            if (ReglaOpRel(elementos.get(posicion), "ReglaCondicion") || ReglaOpLog(elementos.get(posicion))) {
                posicion++;
                System.out.println("vamos en el token opRel: " + elementos.get(posicion));
                if (ReglaIdNum(elementos.get(posicion), "ReglaCondicion")) {
                    System.out.println("vamos en el token idNum(otra vez): " + elementos.get(posicion));
                    //posicion++;
                    if (ReglaMasCond(elementos.get(posicion))) {
                        //posicion++;
                        System.out.println("se identificó una condición empieza en: " + token);
                        System.out.println("termina en: " + elementos.get(posicion));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean ReglaMasCond(String token) {
        if (ReglaOpRel(elementos.get(posicion), "ReglaMasCond") || ReglaOpLog(elementos.get(posicion))) {
            posicion++;
            if (ReglaCondicion(elementos.get(posicion), "ReglaMasCond") || ReglaIdNum(elementos.get(posicion), "ReglaMasCond")) {
                posicion++;
                return true;
            }
        } else {
            //vacío
            return true;
        }
        return false;
    }

    public boolean ReglaOpLog(String token) {
        if (elementos.get(posicion).equals("AND") || elementos.get(posicion).equals("OR") || elementos.get(posicion).equals("NOT")) {
            //posicion++;
            return true;
        }
        return false;
    }

    public boolean ReglaOpRel(String token, String sel) {
        /*if (elementos.get(posicion).equals("<=") || elementos.get(posicion).equals(">=") || elementos.get(posicion).equals("!=") || elementos.get(posicion).equals("==") || elementos.get(posicion).equals("<") || elementos.get(posicion).equals(">")) {
            //posicion++;
            return true;
        }*/
        Nodo nodoOpRel = new Nodo(token, true);
        boolean respuesta = false;
        switch(token) {
            case "<=":
                respuesta = true;
                break;
            case ">=":
                respuesta = true;
                break;
            case "!=":
                respuesta = true;
                break;
            case "==":
                respuesta = true;
                break;
            case "<":
                respuesta = true;
                break;
            case ">":
                respuesta = true;
                break;
            default:
                respuesta = false;
                break;
        }
        switch(sel) {
            case "ReglaCondicion":
                nodoCondicion.hijos.add(nodoOpRel);
                break;
            case "ReglaMasCond":
                //nodoMasCond.hijos.add(nodoOpRel);
                break;
        }
        return respuesta;
    }

    public boolean ReglaOpArit(String token) {
        if (elementos.get(posicion).equals("+") || elementos.get(posicion).equals("-") || elementos.get(posicion).equals("=") || elementos.get(posicion).equals("*") || elementos.get(posicion).equals("/") || elementos.get(posicion).equals("%")) {
            //posicion++;
            return true;
        }
        return false;
    }

    /* public boolean Operacion(String token) {

        return false;
    }*/
    public void imprimirElementos() {
        Iterator<String> it = elementos.iterator();
        while (it.hasNext()) {
            String elemento = it.next();
            System.out.println(elemento);
        }
    }

    public boolean buscarElemento(String cadena) {
        boolean respuesta = false;
        Iterator<String> it = elementos.iterator();
        while (it.hasNext()) {

            String elemento = it.next();

            if (elemento.equals(cadena)) {
                respuesta = true;
            }
        }

        return respuesta;

    }
}
