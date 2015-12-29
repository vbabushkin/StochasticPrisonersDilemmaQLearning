/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stochasticprisonersdilemma_v3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfReward1;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfReward2;

import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfTransProb1;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfTransProb2;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfLRPlayer1;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfLRPlayer2;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfGammaPlayer1;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfGammaPlayer2;


import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfTotalUtilityPlayer1;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.jtfTotalUtilityPlayer2;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st13;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st33;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st53;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st72;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st73;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st74;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st81;
import static stochasticprisonersdilemma_v3.stochasticPrisonersDilemma_v3.st85;




/**
 *
 * @author Wild
 */
public class expertAlgorithm extends JLabel{
    private BufferedWriter writer;
    private BufferedReader reader;
    Agent a1;
    Agent a2;
    //declare q-values matrices for agents
    //final double [][] newQMatrix=new double[1600][4];
    
    //final double [][] newQMatrix1=new double[1600][4];
    //final double [][] newQMatrix2=new double[1600][4];
    
     
    public expertAlgorithm(Agent a1,Agent a2){
        this.a1=a1;
        this.a2=a2;
    }
    
    
    
    //matrix where all possible atates are stored:
    //20 states for agent 1,
    //20 states for agent 2
    //4 status of gates
    // 20 X 20 X 4 = 1600 possible states in total
    public State [] stateMatrix=new State[1600];
    
    /**
     * Initialize State Matrix values to default value provided by State's const-
     * ructor
     */
    public void initializeStateMatrix(){
        for(int i=0;i<stateMatrix.length;i++)
            stateMatrix[i]=new State();
    }
    
    
    /**
     * Fill elements of State Matrix with corresponding values
     */
    public void fillStateMatrix(){
        int agentCellIndex=0;
        
        //all gates are opened
        for(Cell a1Position:a1.setOfPositions){
            for(Cell a2Position:a2.setOfPositions){
                
                    stateMatrix[agentCellIndex].positionPlayer1=a1Position;
                    stateMatrix[agentCellIndex].positionPlayer2=a2Position;
              
                agentCellIndex++;
            }   
        }
        
        //first gate is closed, gates 2 and 3 are opened
        for(Cell a1Position:a1.setOfPositions){
            for(Cell a2Position:a2.setOfPositions){
                    stateMatrix[agentCellIndex].positionPlayer1=a1Position;
                    stateMatrix[agentCellIndex].positionPlayer2=a2Position;
                    stateMatrix[agentCellIndex].statusGate1=1;
                agentCellIndex++;
            }   
        }
        
        //gates 1 and 2 are closed, gate 3 is opened
        for(Cell a1Position:a1.setOfPositions){
            for(Cell a2Position:a2.setOfPositions){
                    stateMatrix[agentCellIndex].positionPlayer1=a1Position;
                    stateMatrix[agentCellIndex].positionPlayer2=a2Position;
                    stateMatrix[agentCellIndex].statusGate1=1;
                    stateMatrix[agentCellIndex].statusGate2=1;
                agentCellIndex++;
            }   
        }
        
        //gates 1, 2  and 3 are closed
        for(Cell a1Position:a1.setOfPositions){
            for(Cell a2Position:a2.setOfPositions){
                    stateMatrix[agentCellIndex].positionPlayer1=a1Position;
                    stateMatrix[agentCellIndex].positionPlayer2=a2Position;
                    stateMatrix[agentCellIndex].statusGate1=1;
                    stateMatrix[agentCellIndex].statusGate2=1;
                    stateMatrix[agentCellIndex].statusGate3=1;
                agentCellIndex++;
            }   
        }
    }
    
   /**
    *   Printing state matrix
    */ 
    public void printingStates(){
        for(State givenState: stateMatrix){
            System.out.println("( "+givenState.positionPlayer1.x+" ,"+givenState.positionPlayer1.y+
                               " ), ( "+givenState.positionPlayer2.x+" ,"+givenState.positionPlayer2.y+
                               " ), gate1 is closed: "+givenState.statusGate1+
                               " , gate2 is closed: "+givenState.statusGate2+
                               " , gate3 is closed: "+givenState.statusGate3);
         
        }
    }
    
    /**
    * Printing given state
    * @param i
    * @return 
    */
    public String printGivenState(int i){
        return String.format("( %4d , %4d ),( %4d , %4d ), %2d , %2d , %2d", 
                stateMatrix[i].positionPlayer1.x,stateMatrix[i].positionPlayer1.y,
                stateMatrix[i].positionPlayer2.x,stateMatrix[i].positionPlayer2.y,
                stateMatrix[i].statusGate1,
                stateMatrix[i].statusGate2,
                stateMatrix[i].statusGate3);                
    }
    
    
    
    /**
     * Searching for given state s
     * @param State s
     * @return 
     */
    public int findState(State s){
        int res=-1;
        for(int i=0;i<stateMatrix.length;i++)
            if(stateMatrix[i].positionPlayer1.x==s.positionPlayer1.x && 
               stateMatrix[i].positionPlayer1.y==s.positionPlayer1.y &&
               stateMatrix[i].positionPlayer2.x==s.positionPlayer2.x && 
               stateMatrix[i].positionPlayer2.y==s.positionPlayer2.y &&
               stateMatrix[i].statusGate1==s.statusGate1 &&
               stateMatrix[i].statusGate2==s.statusGate2 &&
               stateMatrix[i].statusGate3==s.statusGate3)
                res= i;
        return res;
    }
    
    
    
   
    
    /**
     * Printing Q-Values for given agent
     * @param Agent a 
     */
    public void printQvaluesMatrix(Agent a){
        System.out.println("Q values for "+a.name);
        System.out.println("States                                                 UP    DOWN    LEFT   RIGHT");
        for(int i=0;i<a.qValuesMatrix.length;i++){
            System.out.printf("%s    ",printGivenState(i));
            for(int j=0;j<a.qValuesMatrix[i].length;j++)
                System.out.printf("%8.1f",a.qValuesMatrix[i][j]);
            System.out.println();
        }
    }
    
    /**
     * Write agent's a q-values matrix to a file 
     * @param filename 
     * @param a
     */
    public void writeMatrixToFile(String filename, Agent a){
        try {
            //Writting q matrix to a file
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException ex) {
            Logger.getLogger(expertAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        writeToFile(writer,a);
        //end of writting to a file 
    }
    
    /**
     * Read agent's a q-values matrix from a file
     * @param filename
     * @param a 
     * @param matrix
     */
    public void readMatrixFromFile(String filename, Agent a, double[][] matrix){
        String strLine=null;
        try {
            FileInputStream in = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int i=0;
            while((strLine = br.readLine())!= null){
                for(int j=0;j<4;j++)
                    matrix[i][j]=Double.parseDouble(strLine.split("\\s+")[j+1]);
                i++;
            }
        }catch(Exception b){}
        a.setQValueMatrix(matrix);
    }
    
    /**
     * returns current state of the world
     * @return 
     */
    public State getCurrentState(){
        Cell a1CurrentPosition=a1.getCurrentPosition();
        Cell a2CurrentPosition=a2.getCurrentPosition();
        State currentState=new State(a1CurrentPosition,a2CurrentPosition,st73.isClosed(),st53.isClosed(),st33.isClosed());
        return currentState;
    }
    
    
    public State updateCurrentState(){
        Cell a1CurrentPosition=a1.getCurrentPosition();
        Cell a2CurrentPosition=a2.getCurrentPosition();
        State currentState=new State(a1CurrentPosition,a2CurrentPosition,st73.isClosed(),st53.isClosed(),st33.isClosed());
        return currentState;
    }
    
    /**
     * printing information about current state of the world
     */
    public void printCurrentStateInfo(){
        Cell a1CurrentPosition=a1.getCurrentPosition();
        Cell a2CurrentPosition=a2.getCurrentPosition();
        
        System.out.println("Agent 1 now in cell ("+a1CurrentPosition.x+" , "+a1CurrentPosition.y+" )");
        System.out.println("Agent 2 now in cell ("+a2CurrentPosition.x+" , "+a2CurrentPosition.y+" )");
        State currentState=getCurrentState();
        System.out.println("current world state is ");
        System.out.println(printGivenState(findState(currentState)));
    }
    
    
    /**
     * q-learning algorithm
     */
/*
    public void qLearn(){
        
        
        a1.setQValueMatrix(newQMatrix1);
        readMatrixFromFile("qValuesMatrix1.txt",a1,newQMatrix1);
        writeMatrixToFile("qValuesMatrix1.txt",a1);
        printQvaluesMatrix(a1);
        
        a2.setQValueMatrix(newQMatrix2);
        readMatrixFromFile("qValuesMatrix2.txt",a2,newQMatrix2);
        writeMatrixToFile("qValuesMatrix2.txt",a2);
        //printQvaluesMatrix(a2);
        
    }
*/
    
    /**
     * 
     * @param Agent a 
     */
    /*
    public void updateQMatrix(Agent a){
        if(a.name.equals("Player")){
            a.setQValueMatrix(newQMatrix1);
            readMatrixFromFile("qValuesMatrix1.txt",a,newQMatrix1);
            writeMatrixToFile("qValuesMatrix1.txt",a);
            //printQvaluesMatrix(a);
        }
        else{
            a.setQValueMatrix(newQMatrix2);
            readMatrixFromFile("qValuesMatrix2.txt",a,newQMatrix2);
            writeMatrixToFile("qValuesMatrix2.txt",a);
            //printQvaluesMatrix(a);
        }
    }
     
    */
    
    public Cell findCell(int action, Agent a){
        int x=a.getCurrentPosition().x;
        int y=a.getCurrentPosition().y;
        Cell currentPosition=a.getCurrentPosition();

        
        switch(action){
                case 0://UP
                    y=y-50;
                    break;
                case 1: //DOWN
                    y=y+50;
                    break;
                case 2://LEFT
                    x=x-50;
                    break;
                case 3://RIGHT
                    x=x+50;
                    break;  
            } 
        
        for(int i=0;i<a.setOfPositions.length;i++){
            if(a.setOfPositions[i].isClosed()!=1 && (x==a.setOfPositions[i].x && y==a.setOfPositions[i].y)){
                currentPosition=a.setOfPositions[i];
            }
            
        }
        return currentPosition;
        
    }
    
    
    /**
     * 
     * @param action
     * @param a 
     */
    public void moveToCell(int action, Agent a){
        int x=a.getCurrentPosition().x;
        int y=a.getCurrentPosition().y;
        Cell position=a.getCurrentPosition();

        System.out.println("***Agent"+a.name+" is in position "+x+"  ,  "+y+" and takes action "+action);
        
        switch(action){
                case 0://UP
                    y=y-50;
                    break;
                case 1: //DOWN
                    y=y+50;
                    break;
                case 2://LEFT
                    x=x-50;
                    break;
                case 3://RIGHT
                    x=x+50;
                    break;  
            } 
        
        for(int i=0;i<a.setOfPositions.length;i++){
            if(a.setOfPositions[i].isClosed()!=1 && (x==a.setOfPositions[i].x && y==a.setOfPositions[i].y)){
                position=a.setOfPositions[i];
            }
            
        }
        a.moveToPosition(position);
        System.out.println("***************Agent "+a.name+" now is in position "+position.x+"  ,  "+position.y);
    }
    /**
     * 
     * @param a 
     */
  
    int greedyMove(Agent a){
        
        int action;
        double n=Math.round(1+9*Math.random());
        State currentState=getCurrentState();
        int currentStateIndex=findState(currentState);
        
        //Selecting a state with maximum q value
        //better to  make it as a function
        int bestStateIndex=0;
        
        double maxQ=a.qValuesMatrix[currentStateIndex][0];
       
        
        for(int s=0;s<4;s++){
            if(a.qValuesMatrix[currentStateIndex][s]>maxQ){
                maxQ=a.qValuesMatrix[currentStateIndex][s];
                bestStateIndex=s;
            }
        }
        
        
        System.out.println("n= "+n+" prob = "+a.probability*10);
        if(n<=a.probability*10){//if all q values are equal -- randomize
            if(a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][1] &&
               a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][2] &&
               a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][3] ) 
                    action=(int)Math.round(3*Math.random());
            else 
                    action=bestStateIndex; //else -- choose the best action with highest q value
                }
            else
                action=(int)Math.round(3*Math.random());
        
        return action;
    }
    /*
    controller Q-learning(S,A,γ,α) 
2:           Inputs
3:                     S is a set of states 
4:                     A is a set of actions 
5:                     γ the discount 
6:                     α is the step size 
7:           Local
8:                     real array Q[S,A] 
9:                     previous state s 
10:                     previous action a 
11:           initialize Q[S,A] arbitrarily 
12:           observe current state s 
13:           repeat
14:                     select and carry out an action a 
15:                     observe reward r and state s' 
16:                     Q[s,a] ←Q[s,a] + α(r+ γmaxa' Q[s',a'] - Q[s,a]) 
17:                     s ←s' until termination
 
    */
    
  /*  
    
    public void qLearn1(Agent a){
        
        
        //load q-values matrix
        String fileName = new String();
        if(a.name=="Player"){
            fileName="qValuesMatrix1.txt";
        }
        if(a.name=="Associate"){
            fileName="qValuesMatrix2.txt";
        }
        //reading q-values matrix from the file
        readMatrixFromFile(fileName,a,a.qValuesMatrix);
        
        //while agent is not stopped
        if (!a.isStopped){
           //retrieving the current state of the world
            State currentState=getCurrentState();
            //defining the next state
            State nextState;
            //finding the index corresponding to this current state in q-Values matrix
            int currentStateIndex=findState(currentState);
        
            printCurrentStateInfo();
            int action;
        
            //Selecting a state with maximum q value
            //better to  make it as a function
            int bestStateIndex=0;
        
            double maxQ=a.qValuesMatrix[currentStateIndex][0];
       
        
            for(int s=0;s<4;s++){
               if(a.qValuesMatrix[currentStateIndex][s]>maxQ){
                   maxQ=a.qValuesMatrix[currentStateIndex][s];
                   bestStateIndex=s;
               }
            }
            System.out.println("current state Q values: "+a.qValuesMatrix[currentStateIndex][0] + ", "+
                                                          a.qValuesMatrix[currentStateIndex][1] + ", "+
                                                          a.qValuesMatrix[currentStateIndex][2] + ", "+
                                                          a.qValuesMatrix[currentStateIndex][3]);
        
            //now agent selects an action based on his current states q values or randomizes:
            double n=Math.round(1+9*Math.random());
            System.out.println("n= "+n+" prob = "+a.probability*10);
            if(n<=a.probability*10){//if all q values are equal -- randomize
                if(a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][1] &&
                    a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][2] &&
                    a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][3] ) 
                    action=(int)Math.round(3*Math.random());
                else 
                    action=bestStateIndex; //else -- choose the best action with highest q value
                }
            else
                action=(int)Math.round(3*Math.random());
            
            System.out.println("Action taken "+action);
            //move to this new position
            moveToCell(action,a);
            
            //get a state
            nextState=getCurrentState();
            
            //find next position where agent finds himself if he selects action
            Cell nextPosition=findCell(action, a);
            
            //finds the next state index
            int nextStateIndex=findState(nextState);
            
            //update Q value
            a.qValuesMatrix[currentStateIndex][action]=(1-a.learningRate)*a.qValuesMatrix[currentStateIndex][action]+a.learningRate*(nextPosition.getReward()+a.gamma*maxQ);
           
           
            //update agent's Q values matrix
            //a.setQValueMatrix(newQMatrix);
        
            currentState=updateCurrentState();
        
            //printQvaluesMatrix(a);
        
            printCurrentStateInfo();
            
        }//agent is stopped now 
        
        paintImmediately(0,0,600,600);   
    }//end of qLearn1
    
    */
    
    
    
  
    public void qLearn(Agent a){
       int nextAction;
       String fileName = new String();
            if(a.name=="Player"){
                fileName="qValuesMatrix1.txt";
                
            }
            if(a.name=="Associate"){
                fileName="qValuesMatrix2.txt";
            }
            //reading q-values matrix from the file
            readMatrixFromFile(fileName,a,a.qValuesMatrix);
        
            int prevAction=greedyMove(a);//by default
       
       if (!a.isStopped){
           System.out.println("AGENT "+a.name+" TAKES AN ACTION");
           //retrieving the current state of the world
            State currentState=getCurrentState();
        
            //defining the next state
            State nextState;
            //finding the index corresponding to this current state in q-Values matrix
            int currentStateIndex=findState(currentState);
        
            printCurrentStateInfo();
        
            //find next position where agent finds himself if he selects action
            Cell nextPosition=findCell(prevAction, a);
        
            //move agent to corresponding cell
        
            moveToCell(prevAction,a);
        
            System.out.println("current state index is "+currentStateIndex);
            System.out.println("agent selected an action:  "+prevAction);
         
            nextState=getCurrentState();
        
            //finds the next state index
            int nextStateIndex=findState(nextState);
        
            //Selecting a state with maximum q value
            //better to  make it as a function
            int bestStateIndex=0;
        
            double maxNextQ=a.qValuesMatrix[currentStateIndex][0];
       
        
            for(int s=0;s<4;s++){
               if(a.qValuesMatrix[nextStateIndex][s]>maxNextQ){
                   maxNextQ=a.qValuesMatrix[nextStateIndex][s];
                   bestStateIndex=s;
               }
            }
            System.out.println("Q value: "+a.qValuesMatrix[currentStateIndex][0] + ", "+a.qValuesMatrix[currentStateIndex][1] + ", "+a.qValuesMatrix[currentStateIndex][2] + ", "+a.qValuesMatrix[currentStateIndex][3]);
        
            //chooses whether to follow the q matrix or select a random action  
            //with some probability
            //again, better make it as function
            double n=Math.round(1+9*Math.random());
            System.out.println("n= "+n+" prob = "+a.probability*10);
            if(n<=a.probability*10){
                //if all q values are equal -- randomize
                if(a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][1] &&
                        a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][2] &&
                        a.qValuesMatrix[currentStateIndex][0]==a.qValuesMatrix[currentStateIndex][3] ) 
                    nextAction=(int)Math.round(3*Math.random());
                else 
                    nextAction=bestStateIndex; //else -- choose the best action with highest q value
            }
            else
                nextAction=(int)Math.round(3*Math.random());
            
            //update Q value
            a.qValuesMatrix[currentStateIndex][prevAction]=(1-a.learningRate)*a.qValuesMatrix[currentStateIndex][prevAction]+a.learningRate*(nextPosition.getReward()+a.gamma*maxNextQ);
           
           
            //update agent's Q values matrix
            //a.setQValueMatrix(newQMatrix);
        
            currentState=updateCurrentState();
        
            //printQvaluesMatrix(a);
        
            printCurrentStateInfo();
            controlGates();
            prevAction=nextAction;
       }
       else{
           paintImmediately(0,0,600,600);
       }
    }
    
   
    /**
     * 
     * runs the expert algorithm
     * 
     */
    public void run(){
        
        a1.learningRate=Double.parseDouble(jtfLRPlayer1.getText());
        a1.gamma=Double.parseDouble(jtfGammaPlayer1.getText());
        a1.probability=Double.parseDouble(jtfTransProb1.getText());
        qLearn(a1);
        writeMatrixToFile("qValuesMatrix1.txt",a1);
        
        
        a2.learningRate=Double.parseDouble(jtfLRPlayer2.getText());
        a2.gamma=Double.parseDouble(jtfGammaPlayer2.getText());
        a2.probability=Double.parseDouble(jtfTransProb2.getText());
        qLearn(a2);
        writeMatrixToFile("qValuesMatrix2.txt",a2);
        
        controlGates();
        jtfTotalUtilityPlayer1.setText(String.valueOf(a1.getTotalUtility()));
        jtfTotalUtilityPlayer2.setText(String.valueOf(a2.getTotalUtility()));
        
        jtfReward1.setText(String.valueOf(a1.getReward()));
        jtfReward2.setText(String.valueOf(a2.getReward()));
        
    }
    
    
    
    /**
     * 
     * @return 
     */
    public int selectAction(){
        return (int)Math.round(3*Math.random());
    }
    
    /**
     * Tests whether all elements in array are equal
     * @param a
     * @return 
     */
    public static boolean isAllEqual(double[][] a, int currentIndex){
        for(int i=1; i<a[currentIndex].length; i++){
            if(a[currentIndex][0] != a[currentIndex][i]){
                return false;
            }
        }

        return true;
    }

    
    /**
     * move an agent
     */
    public void move(Agent a){
        //updateQMatrix(a);
        int action;
        action=selectAction();
        if (!a.isStopped){
            moveToCell(action,a);
            controlGates();
        }
        else
            a.stop();
        controlGates();
    }
    
    
   
    
    
    
    
    
    
    /*
     * Control gates
     */
     public void controlGates(){
         // either a1 or a2 enters gate 1 gates 1,2 and 3 close
         if((a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==300)^(a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==300 )){
             stochasticPrisonersDilemma_v3.st33.setClosed();
             stochasticPrisonersDilemma_v3.st53.setClosed();
             stochasticPrisonersDilemma_v3.st73.setClosed();
             if((a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==300)&&!(a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==300 )){
                 a1.stop();
                 jtfTotalUtilityPlayer1.setText(String.valueOf(a1.getTotalUtility()+10));
             }
             if((a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==300)&&! (a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==300)){
                 a2.stop();
                 jtfTotalUtilityPlayer2.setText(String.valueOf(a2.getTotalUtility()+10));
             }
         }
         // both a1 and a2 enter gate 1 gates 1,2 close
         if((a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==300 ) && (a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==300 )){
             stochasticPrisonersDilemma_v3.st73.setClosed();
             stochasticPrisonersDilemma_v3.st53.setClosed();
             //a1.setCurrentPosition(st72);
             //a2.setCurrentPosition(st74);
             paintImmediately(0,0,600,600);
             a1.setStartPosition(st72);
             a2.setStartPosition(st74);
             a1.setReward(-1);
             a2.setReward(-1);
             a1.setTotalUtility(a1.getTotalUtility()-11); //it is too artificial...
             a2.setTotalUtility(a2.getTotalUtility()-11); //needs to be changed
         }
         //a1  enters gate 2 gate 1 closes
         if((a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==200 )){
             a1.stop();
             stochasticPrisonersDilemma_v3.st73.setClosed();
             jtfTotalUtilityPlayer1.setText(String.valueOf(a1.getTotalUtility()+10));
         }
        // a2 enters gate 2 gate 1 closes
         if((a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==200 )){
             a2.stop();
             jtfTotalUtilityPlayer2.setText(String.valueOf(a2.getTotalUtility()+10));
             stochasticPrisonersDilemma_v3.st73.setClosed();
         }
        //other cases (gates 3,4)
         if((a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==0)|| (a1.getCurrentPosition().getX()==100 && a1.getCurrentPosition().getY()==100 )){
             a1.stop();
             jtfTotalUtilityPlayer1.setText(String.valueOf(a1.getTotalUtility()+10));
         }
         if((a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==0)|| (a2.getCurrentPosition().getX()==100 && a2.getCurrentPosition().getY()==100)){
             a2.stop();
             jtfTotalUtilityPlayer2.setText(String.valueOf(a2.getTotalUtility()+10));
         }
     }
     
     /**
      * 
      * Writing agent a q-values matrix to a file
      * @param writer
      * @param a 
      */
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
