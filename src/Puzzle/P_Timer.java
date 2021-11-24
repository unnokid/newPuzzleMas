package Puzzle;

import java.awt.Color;

import javax.swing.JLabel;

public class P_Timer extends Thread
{
    int y = 0;
    JLabel myLabel = null;
    public P_Timer(JLabel myLabel)
    {
        this.myLabel = myLabel;
    }
    public void run() //.start 하면 호출됨
    {
   
    	try
    	{
    		while(!Thread.currentThread().isInterrupted())
    		{
    				myLabel.setBackground(Color.white);
                 	myLabel.setText("시간: "+y);
                 	Thread.sleep(1000);
                 	y++;
            }
    	}
    	catch(InterruptedException e)
    	{
    		
    	}
    }
}