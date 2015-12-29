/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stochasticprisonersdilemma_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Wild
 */
public class stochasticPrisonersDilemma_v3 extends JFrame{
    private BufferedWriter writer;
    int numOfRounds;
    private boolean threadRunning=true;
    
    static JTextField jtfReward1 = new JTextField();
    static JTextField jtfReward2 = new JTextField();
    static JTextField jtfTotalUtilityPlayer1 = new JTextField();
    static JTextField jtfTotalUtilityPlayer2 = new JTextField();
    static final JTextField jtfLRPlayer1 = new JTextField("0.1");
    static final JTextField jtfGammaPlayer1 = new JTextField("0.95");
    static final JTextField jtfLRPlayer2 = new JTextField("0.1");
    static final JTextField jtfGammaPlayer2 = new JTextField("0.95");
    final JTextField jtfNumOfRounds = new JTextField("100");
    static final JTextField jtfTransProb1 = new JTextField("0.3");
    static final JTextField jtfTransProb2 = new JTextField("0.3");
    
    final JLabel jlblCurrentRound = new JLabel("0");
    
    JButton jbtReset1 =new JButton("Reset p1");
    JButton jbtReset2 =new JButton("Reset p2");
    JButton jbtRepeat=new JButton("Repeat");
    JButton jbtTraining=new JButton("Start Training");
    JButton jbtReset=new JButton("Reset Matrix");
    static JButton jbtStop=new JButton("Stop");
    
    JPanel p1 = new JPanel(new GridLayout(8, 5,-2,-2));//panel containing all 20 cells
    JPanel p2 = new JPanel(new GridLayout(7, 2,10,10));//panel containing measures
    JPanel p3 = new JPanel(new GridLayout(7, 2,10,10));//panel containing parameters
    JPanel p23= new JPanel(new GridLayout(2, 1,0,0));//panel grouping panels p2 and p3
    JPanel p4= new JPanel(new BorderLayout(0,0));//panel for arranging panels
    
    JLayeredPane lpane = new JLayeredPane();//panel works as overlay layout to display agents on p1
    
    
    
    static int [][] cellCoordinates =   {{0,0},{50,0},{100,0},{150,0},{200,0},
                                       {0,50},{50,50},{100,50},{150,50},{200,50},
                                       {0,100},{50,100},{100,100},{150,100},{200,100},
                                       {0,150},{50,150},{100,150},{150,150},{200,150},
                                       {0,200},{50,200},{100,200},{150,200},{200,200},
                                       {0,250},{50,250},{100,250},{150,250},{200,250},
                                       {0,300},{50,300},{100,300},{150,300},{200,300},
                                       {0,350},{50,350},{100,350},{150,350},{200,350}
                                      };
    
    Cell st11=new Cell(-1,cellCoordinates[0]);
    Cell st12=new Cell(-1,cellCoordinates[1]);
    static final Cell st13=new Cell(10,cellCoordinates[2]);
    Cell st14=new Cell(-1,cellCoordinates[3]);
    Cell st15=new Cell(-1,cellCoordinates[4]);
    
    Cell st21=new Cell(-1,cellCoordinates[5]);
    Cell st22=new Cell(-1,cellCoordinates[6]);
    Cell st23=new Cell(cellCoordinates[7],true);
    Cell st24=new Cell(-1,cellCoordinates[8]);
    Cell st25=new Cell(-1,cellCoordinates[9]);
    
    Cell st31=new Cell(-1,cellCoordinates[10]);
    Cell st32=new Cell(-1,cellCoordinates[11]);
    static final  Cell st33=new Cell(10,cellCoordinates[12]);
    Cell st34=new Cell(-1,cellCoordinates[13]);
    Cell st35=new Cell(-1,cellCoordinates[14]);
    
    Cell st41=new Cell(-1,cellCoordinates[15]);
    Cell st42=new Cell(-1,cellCoordinates[16]);
    Cell st43=new Cell(cellCoordinates[17],true);
    Cell st44=new Cell(-1,cellCoordinates[18]);
    Cell st45=new Cell(-1,cellCoordinates[19]);
    
    Cell st51=new Cell(-1,cellCoordinates[20]);
    Cell st52=new Cell(-1,cellCoordinates[21]);
    static final Cell st53=new Cell(10,cellCoordinates[22]);
    Cell st54=new Cell(-1,cellCoordinates[23]);
    Cell st55=new Cell(-1,cellCoordinates[24]);
    
    Cell st61=new Cell(-1,cellCoordinates[25]);
    Cell st62=new Cell(-1,cellCoordinates[26]);
    Cell st63=new Cell(cellCoordinates[27],true);
    Cell st64=new Cell(-1,cellCoordinates[28]);
    Cell st65=new Cell(-1,cellCoordinates[29]);
    
    Cell st71=new Cell(-1,cellCoordinates[30]);
    static final Cell st72=new Cell(-1,cellCoordinates[31]);
    static final Cell st73=new Cell(10,cellCoordinates[32]);
    static final Cell st74=new Cell(-1,cellCoordinates[33]);
    Cell st75=new Cell(-1,cellCoordinates[34]);
    
    static final Cell st81=new Cell(-1,cellCoordinates[35]);
    Cell st82=new Cell(-1,cellCoordinates[36]);
    Cell st83=new Cell(cellCoordinates[37],true);
    Cell st84=new Cell(-1,cellCoordinates[38]);
    static final Cell st85=new Cell(-1,cellCoordinates[39]);
    
    //positions of player1
    Cell[] setOfPositions1={st81,st82,
                             st71,st72, st73,  
                             st61,st62,
                             st51,st52,st53,
                             st41,st42,
                             st31,st32,st33,
                             st21,st22,
                             st11,st12,st13
                            };
    
    //positions of player2
    Cell[] setOfPositions2={st85,st84,
                             st75,st74, st73,  
                             st65,st64,
                             st55,st54,st53,
                             st45,st44,
                             st35,st34,st33,
                             st25,st24,
                             st15,st14,st13
                            };
    
        //player1
        public final Agent a1=new Agent(st81,setOfPositions1,Color.BLACK, "Player");
        //player2
        public final Agent a2=new Agent(st85,setOfPositions2,Color.GREEN,"Associate");
        //currently used expert
        final expertAlgorithm expert1= new expertAlgorithm(a1,a2);
        int k=0;
        
        
       
        
        
    //constructor
    public stochasticPrisonersDilemma_v3(){
        
        //initialize elements of the gameboard
        initElements(a1,a2);
        expert1.initializeStateMatrix();
        expert1.fillStateMatrix();
        
        
                
        
        
        
        
        
        /**
        *  Repeat button
        */
        jbtRepeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                repaint();
                expert1.run();
                repaint();
                }
            });
        
        /**
         * Training the agents
         */
       jbtTraining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                 //System.out.println(threadRunning);
                
                     t.start();
                     repaint();
                
            }
       });
                
    
        
        
         /**
         * Stop button
         */
       jbtStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e3) {
                //threadRunning=false;
                t.stop();
                resetGame();
                jlblCurrentRound.setText(String.valueOf(0));
                
            }
       });
       
        /*
        * Reseting the player's matrix
        */
       jbtReset1.addActionListener(new ActionListener() {
            private BufferedWriter writer;

            @Override
            public void actionPerformed(ActionEvent e) {
                
                a1.initializeQMatrix(0);
                
                //Writting q matrix to a file
                    try {
                        writer = new BufferedWriter(new FileWriter("qValuesMatrix1.txt"));
                        writeToFile(writer,a1);
                        } 
                    catch (IOException ex) {
                        
                        }
                     //end of writting to a file 
                    
            }
       });
       
      /*
        * Reseting the associate's matrix
        */
       jbtReset2.addActionListener(new ActionListener() {
            private BufferedWriter writer;

            @Override
            public void actionPerformed(ActionEvent e) {
                
                a2.initializeQMatrix(0);
                
                //Writting q matrix to a file
                    try {
                        writer = new BufferedWriter(new FileWriter("qValuesMatrix2.txt"));
                        writeToFile(writer,a2);
                        } 
                    catch (IOException ex) {
                        
                        }
                     //end of writting to a file 
                    
            }
       });
    
    }//end of constructor
            
 
    Thread t = new Thread(new Runnable(){
        public void run(){
            repaint();//adding repaint improves blinking of GUI
            numOfRounds=Integer.parseInt(jtfNumOfRounds.getText());
            for(int round=1;round<=numOfRounds;round++){
                jlblCurrentRound.setText(Integer.toString(round));
                while(!a2.isStopped || !a1.isStopped){
                    click(jbtRepeat,5);
                    //revalidate();
                    repaint();
                    //expert1.run();
                }
                resetGame();
                repaint();
            } 
            
        }
    });
    
     public static void main(String[] args) {
        // TODO code application logic here
        stochasticPrisonersDilemma_v3 frame = new stochasticPrisonersDilemma_v3();
        frame.setTitle("Prisoners' Dilemma");
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); 
        
       
    }
     /**
     *  Resets the gameboard
     */
     public  void resetGame(){
              //threadRunning=true;
              st73.resetClosed();
              st53.resetClosed();
              st33.resetClosed();
              st13.resetClosed();
              a2.setStartPosition(st85);
              a1.setStartPosition(st81);
              a1.isStopped=false;
              a2.isStopped=false;
              
              a1.setTotalUtility(0);
              a2.setTotalUtility(0);  
              a1.setReward(0);
              a2.setReward(0);
              
              jtfTotalUtilityPlayer1.setText(null);
              jtfTotalUtilityPlayer2.setText(null);
              jtfReward1.setText(null);
              jtfReward2.setText(null);
              repaint();
              a1.paintImmediately(0,0,600,600);
              a2.paintImmediately(0,0,600,600); 
              repaint();
              
     }
     
     
     /**
     *  Repaints the canvas
     */
     public void redrawCanvas(){
              
              st73.paintImmediately(0,0,600,600);
              st53.paintImmediately(0,0,600,600);
              st33.paintImmediately(0,0,600,600);
              st13.paintImmediately(0,0,600,600);
              a1.paintImmediately(0,0,600,600);
              a2.paintImmediately(0,0,600,600);
              p1.paintImmediately(0,0,600,600);
              p2.paintImmediately(0,0,600,600);
              p23.paintImmediately(0,0,600,600);
              p3.paintImmediately(0,0,600,600);
     }
     
     /**
     *  Initializing elements of gameboard
     */
     public void initElements(Agent a1, Agent a2){
        
        setLayout(new FlowLayout());
        lpane.setLayout(new OverlayLayout(lpane));
        p2.add(new JLabel("First player's Reward"));
        p2.add(jtfReward1);
        p2.add(new JLabel("Second player's Reward"));
        p2.add(jtfReward2);
        p2.add(new JLabel("1st Player's Total Utility"));
        p2.add(jtfTotalUtilityPlayer1);
        p2.add(new JLabel("2-nd Player's Total Utility"));
        p2.add(jtfTotalUtilityPlayer2);
        p2.add(new JLabel("Number of Rounds to Train"));
        p2.add(jtfNumOfRounds);
        p2.add(new JLabel("Current round: #"));
        p2.add(jlblCurrentRound);
        p2.add(jbtRepeat);
        p2.add(jbtTraining);
        p2.setBorder(new TitledBorder("Measures"));
        
        p3.add(new JLabel("1-st Player's Learning Rate"));
        p3.add(jtfLRPlayer1);
        p3.add(new JLabel("1-st Player's Discount Factor"));
        p3.add(jtfGammaPlayer1);
        p3.add(new JLabel("2-nd Player's Learning Rate"));
        p3.add(jtfLRPlayer2);
        p3.add(new JLabel("2-nd Player's Discount Factor"));
        p3.add(jtfGammaPlayer2);
        p3.add(new JLabel("Transition Prob. Player 1"));
        p3.add(jtfTransProb1);
        p3.add(new JLabel("Transition Prob. Player 2"));
        p3.add(jtfTransProb2);
        p3.add(jbtReset1);
        p3.add(jbtReset2);
        p3.setBorder(new TitledBorder("Algorithm parameters"));
       
        p23.add(p2);
        p23.add(p3);
        
        
        
        p1.add(st11);
        p1.add(st12);
        p1.add(st13);
        p1.add(st14);
        p1.add(st15);
        
        p1.add(st21);
        p1.add(st22);
        p1.add(st23);
        p1.add(st24);
        p1.add(st25);
        
        p1.add(st31);
        p1.add(st32);
        p1.add(st33);
        p1.add(st34);
        p1.add(st35);
        
        p1.add(st41);
        p1.add(st42);
        p1.add(st43);
        p1.add(st44);
        p1.add(st45);
        
        p1.add(st51);
        p1.add(st52);
        p1.add(st53);
        p1.add(st54);
        p1.add(st55);
        
        p1.add(st61);
        p1.add(st62);
        p1.add(st63);
        p1.add(st64);
        p1.add(st65);
        
        p1.add(st71);
        p1.add(st72);
        p1.add(st73);
        p1.add(st74);
        p1.add(st75);
        
        p1.add(st81);
        p1.add(st82);
        p1.add(st83);
        p1.add(st84);
        p1.add(st85);

        a1.setOpaque(false);
        a2.setOpaque(false);
        
        lpane.add(a1,JLayeredPane.DEFAULT_LAYER, new Integer(4));
        lpane.add(a2,JLayeredPane.DEFAULT_LAYER, new Integer(5));
        lpane.add(p1,JLayeredPane.DEFAULT_LAYER, new Integer(2));
        
        p4.add(p23,BorderLayout.PAGE_START);
        p4.add(jbtStop);
        add(p4);
        add(lpane);
     }
    
     /**
    * Click a button on screen
    *
    * @param button Button to click
    * @param millis Time that button will remain "clicked" in milliseconds
    */
    public void click(AbstractButton button, int millis) {
        button.doClick(millis);
    }
    
     void writeToFile(BufferedWriter writer, Agent a){
         try {
             for(int i=0;i<a.qValuesMatrix.length;i++){
                for(int j=0;j<a.qValuesMatrix[i].length;j++)
                    writer.write(String.format("%8.1f",a.qValuesMatrix[i][j]));
                writer.newLine();
             }
             writer.close();
         } catch (IOException ex) {
             Logger.getLogger(expertAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    
}
