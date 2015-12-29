/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stochasticprisonersdilemma_v3;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Wild
 */
public class Agent extends JPanel{
     private Color color;
     public int x0;
     public int y0;
     public int agentsReward=0;
     public int totalUtility=0;
     public Cell[] setOfPositions;
     public Cell currentPosition;
     private Cell startPosition;
     public double [][] qValuesMatrix;
     public boolean isStopped=false;
     public String name="";
     public double probability;
     public double gamma;
     public double learningRate;
     
     
     
     public Agent(Cell startPosition, Cell[] setOfPositions,Color color,String name){
         this.name=name;
         this.color=color;
         this.setOfPositions=setOfPositions;
         this.setStartPosition(startPosition); 
         this.currentPosition=startPosition;
         qValuesMatrix =new double[1600][4];
         qValuesMatrix=initializeQMatrix(0);
         
     }
     
     public void setStartPosition(Cell s){
         this.startPosition=s;
         this.x0=s.getX()+25;
         this.y0=s.getY()+25;
         agentsReward=s.getReward();
         this.currentPosition=this.startPosition;
     }
    
     public Cell getStartPosition(){
        return this.startPosition;
     }
     
     public void setX0(int newX0){
         this.x0=newX0;
     }
     
     public int getX0(){
         return x0;
     }
     
     public void setY0(int newY0){
         this.y0=newY0;
     }
     public int getY0(){
         return y0;
     }
     
     public Cell getCurrentPosition(){
        return this.currentPosition;
     }
     
     public void setCurrentPosition(Cell currentPosition){
        this.currentPosition=currentPosition;
     }
     
     public void stop(){
        this.isStopped=true;
     }
     
     public void setTotalUtility(int totalUtility){
         this.totalUtility=totalUtility;
     }
     
     public int getTotalUtility(){
         return this.totalUtility;
     }
     
     public void setReward (int agentsReward){
         this.agentsReward=agentsReward;
     }
     
     public int getReward(){
         return this.agentsReward;
     }
     
     public void setQValueMatrix(double [][] qValuesMatrix){
         this.qValuesMatrix=qValuesMatrix;
     }
     
     /*
      * Initialize q matrix
      */
     public double[][] initializeQMatrix(double value){
        
          for(int i=0;i<qValuesMatrix.length;i++){
             for(int j=0;j<qValuesMatrix[i].length;j++)
                 qValuesMatrix[i][j]=value;
         }
          return qValuesMatrix;
     }
     
public void moveToPosition(Cell position){
         setX0(position.getX()+25); 
         setY0(position.getY()+25);
         agentsReward=position.getReward();
         
         //setting the total utility
         if(currentPosition.getX()!=position.getX()|| currentPosition.getY()!=position.getY())
             totalUtility=totalUtility+agentsReward;
         setTotalUtility(totalUtility);
         setCurrentPosition(position);
         repaint();
         
     }
     
     
     @Override
         protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setColor(color);
         g.fillOval(x0, y0, 10, 10);
     }
     
}



