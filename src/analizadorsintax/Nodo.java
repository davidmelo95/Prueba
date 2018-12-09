/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorsintax;

import java.util.ArrayList;


public class Nodo {
    ArrayList<Nodo> hijos;
    Object valor;
    boolean esHoja;

    public Nodo(Object valor, boolean esHoja) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
        this.esHoja = esHoja;
    }
    
    public Nodo(){}

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Nodo{" + "hijos=" + hijos + ", valor=" + valor + '}';
    }
    
    
}
