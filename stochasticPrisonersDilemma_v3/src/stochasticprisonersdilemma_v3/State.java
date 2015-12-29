/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stochasticprisonersdilemma_v3;

/**
 *
 * @author Wild
 */
public class State {
    Cell positionPlayer1;
    Cell positionPlayer2;
    int statusGate1; //0 if opened, 1 if closed
    int statusGate2; //0 if opened, 1 if closed
    int statusGate3; //0 if opened, 1 if closed
   
    public State(){
    
    }
    
    public State(Cell positionPlayer1, Cell positionPlayer2, int statusGate1,
            int statusGate2, int statusGate3){
        this.positionPlayer1=positionPlayer1;
        this.positionPlayer2=positionPlayer2;
        this.statusGate1=statusGate1;
        this.statusGate2=statusGate2;
        this.statusGate3=statusGate3;
    }
    
   
}
