package Puzzle;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class P_Title extends JFrame
{
	ImageIcon image1 = new ImageIcon("./category/love.png");
	ImageIcon image2 = new ImageIcon("./category/dream.png");
	ImageIcon image3 = new ImageIcon("./category/happy.png");
	ImageIcon image4 = new ImageIcon("./category/justice.png");
	ImageIcon image5 = new ImageIcon("./category/logo.png");
	ImageIcon image6 = new ImageIcon("./category/miracle.png");
	ImageIcon image7 = new ImageIcon("./category/passion.png");
	ImageIcon image8 = new ImageIcon("./category/rich.png");
	ImageIcon image9 = new ImageIcon("./category/villain.png");
	
	JButton []btn = new JButton[9];//카테고리 버튼
	ImageIcon []ima = new ImageIcon[9];//이미지
	JPanel gamePanel= new JPanel();
	
	public void P_title()//JFrame 생성
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ima[0]=image1;
		ima[1]=image2;
		ima[2]=image3;
		ima[3]=image4;
		ima[4]=image5;
		ima[5]=image6;
		ima[6]=image7;
		ima[7]=image8;
		ima[8]=image9;
		
		//이미지들 규격화
		for(int i = 0; i<=ima.length-1;i++) {
		Image tmpImg = ima[i].getImage();
		Image changeImg = tmpImg.getScaledInstance(230,230, java.awt.Image.SCALE_SMOOTH);
		ImageIcon Finallmg = new ImageIcon(changeImg);
		ima[i] = Finallmg;
		}
		
		Container contentPane = (JPanel)getContentPane();
		contentPane.setLayout(null); //컨텐트 팬의 배치관리자는 절대값

		GridLayout grid = new GridLayout(3,3);
		gamePanel.setBounds(30,10,600,600);
		gamePanel.setLayout(grid);//gamePanel의 배치관리자는 gridLayout
		setBackground(Color.white);
		contentPane.add(gamePanel);
	
		
		for(int i=0;i<9;i++)//gamePanel 에 퍼즐 나열하기
		{
			btn[i] = new JButton();
			btn[i].setIcon(ima[i]);
			gamePanel.add(btn[i]);
			btn[i].addActionListener(new MyActionListener());
		}
		
		setSize(800,700);
		setTitle("Puzzle Matcher");
		
		setVisible(true);
	}
	////
	class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{  
			if(e.getSource()==btn[0])
			{
				setVisible(false);
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[1])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[2])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[3])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[5])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[6])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[7])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
			if(e.getSource()==btn[8])
			{
				P_frame Pframe = new P_frame();
				Pframe.Create_Frame();
			}
		}
	}
	
	////
	public static void main(String[] args)
	{

	}

}
