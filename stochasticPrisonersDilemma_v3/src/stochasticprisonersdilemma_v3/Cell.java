/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stochasticprisonersdilemma_v3;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import sun.applet.Main;

/**
 *
 * @author Wild
 */
public class Cell extends JLabel{
    URL url = Main.class.getResource("/o.gif");
    URL url1 = Main.class.getResource("/co.gif");
    URL url2 = Main.class.getResource("/closedGate.gif");
    private ImageIcon icon1 =  new ImageIcon(url);//"images/o.gif" 
    private ImageIcon icon2 =  new ImageIcon(url1);//"images/co.gif" 
    private ImageIcon icon3 =  new ImageIcon(url2);//"images/closedGate.gif"
    private int isClosed =0;
    int x;
    int y;
    private int reward;
    /*
    public Cell(final int reward, int[] coords){
        
        
        this.reward=reward;
        
        this.x=coords[0];
        this.y=coords[1];
        //String text = String.valueOf(reward1)+','+String.valueOf(reward2);
        //super.setText(text);
        super.setIcon(icon1);       //Sets the image.
        super.setIconTextGap(-35);//Sets the number of pixels between the label's text and its image.   
    }
    
    public Cell(int[] coords, boolean isFilled){
        
        this.x=coords[0];
        this.y=coords[1];
        
        if(!isFilled)
            super.setIcon(icon1);  
        else 
            super.setIcon(icon2);//Sets the image.
        super.setIconTextGap(-35);//Sets the number of pixels between the label's text and its image.   
    }
    
    //methods
    //returns reward for current cell
    public int getReward(){
            return reward;
        }
    */
    
     public Cell(final int reward, int[] coords){
        this.reward=reward;
        this.x=coords[0];
        this.y=coords[1];
        super.setIcon(icon1);       //Sets the image.
          
    }
    
    public Cell(final int reward, int coord1, int coord2){
        this.reward=reward;
        
        this.x=coord1;
        this.y=coord2;
        
        super.setIcon(icon1);       //Sets the image.
           
    }
    
    public Cell(int[] coords,boolean isFilled){
        
        this.x=coords[0];
        this.y=coords[1];
        
        if(!isFilled)
            super.setIcon(icon1);  
        else 
            super.setIcon(icon2);//Sets the image.
        super.setIconTextGap(-35);//Sets the number of pixels between the label's text and its image.   
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int isClosed(){
        return isClosed;
    }
    public void setClosed(){
        super.setIcon(icon3);
        this.isClosed=1;
        reward=0;
    }
    public void reset(){
        super.setIcon(icon1);
        //this.isClosed=false;
    }
    public void resetClosed(){
        super.setIcon(icon1);
        this.isClosed=0;
        reward=10;
    }
    //returns reward of first player
    public int getReward(){
            return reward;
        }
    
    @Override
    public int getX(){
        return x;
    }
    
    @Override
    public int getY(){
        return y;
    }
   
}
