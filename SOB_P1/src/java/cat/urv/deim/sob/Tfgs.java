/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.urv.deim.sob;

import java.util.LinkedList;


public class Tfgs {
    private LinkedList<Tfg> llista;

    public Tfgs(LinkedList<Tfg> tfg) {
        llista = tfg;
    }

    public LinkedList<Tfg> getLlista() {
        return llista;
    }

    public void setLlista(LinkedList<Tfg> llista) {
        this.llista = llista;
    }
    
}
