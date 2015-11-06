/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tommy
 */
public class CareTaker implements Serializable {
    private ArrayList<Memento> states = new ArrayList();

    public void addMemento(Memento m) {
        states.add(m);
    }
    
    public Memento getMemento(int index) {
        return states.get(index);
    }
    
    public ArrayList<Memento> getStates() {
        return states;
    }
}
