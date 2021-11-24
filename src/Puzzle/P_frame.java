package Puzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


// ������ ������ �̹��� �ҷ����� �� �ɰ���

// GUI Swing
@SuppressWarnings("serial")
public class P_frame extends JFrame implements MouseListener{
	
	public BufferedImage[] ori_img =new BufferedImage[2];	// ���� �̹���
	public BufferedImage[][] sub_img = new BufferedImage[3][3];	// ���� �̹��� ����
	public int[][] sub_index = new int[3][3];			// ���� �̹��� ��ȣ
	
	
	
	P_Music music; //���� ���
	P_Music sound =new P_Music("./effectsound/move.wav"); //ȿ����
	P_Music clear = new P_Music("./effectsound/clear.wav"); //��� ���� �ϼ� ��
	P_Music resetting = new P_Music("./effectsound/reset.wav");
	
	Graphics g;					// paint()�� ������ Graphics ��ü	
	int B_row, B_col;			// �� ĭ�� �ε��� 
	int M_count=0;				// ���� �ű� ��
	long S_Timer;				// ���� �ð�
	public long Timer=0;			// �ɸ� �ð�
	int stageCheck =0;			 //���� �������� üũ 
	int result;		 //���÷��̿� ����
	JLabel counter = new JLabel("Ŭ�� ��: " +M_count);
	JLabel timecount = new JLabel();
	P_Timer currentTimer; 
	String [] moviename = new String[] {"<����> �� ����뱺","<�����> �� ����"};
   
	// ������ ����
	public void Create_Frame(){
		setLayout(null);
		
		// UI
		setSize(1000,800);
		setTitle("Puzzle Matcher");
		setBackground(Color.white);
		
		
		Create_Image();		// �̹��� ���� �޼ҵ� ȣ��
		Sub_Image(9);		// �̹��� ���� �޼ҵ� ȣ��
	
		
		addMouseListener(this);		// MouseListener ����
		
		
		
		this.getContentPane().setLayout(null); //����Ʈ ���� ��ġ�����ڴ� ���밪
		
		//���� ���ΰ�ħ ��ư ����
		//��ư�� �°� �̹��� ũ�� ����
		ImageIcon resetimage= new ImageIcon("./setting/restore.png");
		Image tmpImg = resetimage.getImage();
		Image resetImg = tmpImg.getScaledInstance(140,140, java.awt.Image.SCALE_SMOOTH);
		ImageIcon restore = new ImageIcon(resetImg);
		
		//���� �ϼ� ��ư ����
		ImageIcon answarimage= new ImageIcon("./setting/check.png");
		Image tmp2Img = answarimage.getImage();
		Image reset2Img = tmp2Img.getScaledInstance(140,140, java.awt.Image.SCALE_SMOOTH);
		ImageIcon answarImg = new ImageIcon(reset2Img);
		
		JButton reset = new JButton();
	    JButton answer = new JButton();
	      
	    JLabel	info = new JLabel(moviename[0]);   
        if(stageCheck ==0){
            timecount = new JLabel();
            currentTimer = new P_Timer(timecount);
            timecount.setBounds(10,480,100,100);
            this.getContentPane().add(timecount);
            S_Timer = System.currentTimeMillis();	// ���� �ð� ����
            currentTimer.start();
        }
        
        if(stageCheck ==1){
            timecount = new JLabel();
            currentTimer = new P_Timer(timecount);
            timecount.setBounds(10,480,100,100);
            this.getContentPane().add(timecount);
            S_Timer = System.currentTimeMillis();	// ���� �ð� ����
            currentTimer.start();
        }
		reset.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Sub_Image(9);
					repaint();
					resetting.startMusic();
				}
				
		});
		
	    answer.addActionListener(new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e) {
	            int width = 200;      // ���κ��ұ���
	            int height = 200;    // ���κ��ұ���
	            sub_img[0][0] =ori_img[stageCheck].getSubimage(0,  0, width, height);
	            sub_img[0][1] =ori_img[stageCheck].getSubimage(width,  0, width, height);
	            sub_img[0][2] =ori_img[stageCheck].getSubimage(width*2,  0, width, height);
	            sub_img[1][0] =ori_img[stageCheck].getSubimage(0,  height, width, height);
	            sub_img[1][1] =ori_img[stageCheck].getSubimage(width,  height, width, height);
	            sub_img[1][2] =ori_img[stageCheck].getSubimage(width*2,  height, width, height);
	            sub_img[2][0] =ori_img[stageCheck].getSubimage(0,  height*2, width, height);
	            sub_img[2][1] =Make_White(); B_row = 2;B_col = 1;
	            sub_img[2][2] =ori_img[stageCheck].getSubimage(width,  height*2, width, height);
	            sub_index[0][0] = 1;
	            sub_index[0][1] = 2;
	            sub_index[0][2] = 3;
	            sub_index[1][0] = 4;
	            sub_index[1][1] = 5;
	            sub_index[1][2] = 6; 
	            sub_index[2][0] = 7; 
	            sub_index[2][1] = 9;
	            sub_index[2][2] = 8;
	            repaint();
	         }
	         
	   });

	      reset.setIcon(restore);
	      reset.setBounds(10,200,150,150);
	     
	      answer.setIcon(answarImg);
	      answer.setBounds(10,350,150,150);
	      counter.setBounds(10,500,100,100);
	      info.setBounds(10,520,200,100);
	      
         
	      this.getContentPane().add(reset);
		  this.getContentPane().add(answer);
		  this.getContentPane().add(counter);
		  this.getContentPane().add(info);
	      
	     //���� ���
		if(stageCheck == 0)
			music = new P_Music("./effectsound/face.wav");
		else 
			music = new P_Music("./effectsound/parasite.wav");
		music.startMusic();
		
		
		setVisible(true);	// paint() ����
		
		
		// â���� �̺�Ʈ(���μ��� ���� ����)
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	
	}
	
	
	// �̹��� ����
	public void Create_Image(){
		try {
			ori_img[0] = ImageIO.read(new File("./puzzle/face.png"));
			ori_img[1] = ImageIO.read(new File("./puzzle/parasite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// �̹��� ���� ����(���� ��)
	public void Sub_Image(int m_piece){
		int width = ori_img[stageCheck].getWidth(this);		// ���κ��ұ���
		int height = ori_img[stageCheck].getHeight(this);	 // ���κ��ұ���
		int R_number[] = new int[9];			// ���� ���� �迭
		int number = 0;							// ���� ���� ����
		boolean compare;						// ���� ��
		int row, col;							// ���� �̹��� ���� �ε���
		int R_index = 0;						// ���� �迭 �ε���
		
		if (m_piece == 9){			// 9����
			
			for (int i=0 ; i<9 ; i++){	// ���� ���� �迭 �ʱ�ȭ		
				R_number[i] = 0;
			}
			R_number[0] = Random_Setting();	// ���� ����
			for (int j=1 ; j<9 ; j++){	// ���� ���� �迭�� 1~9�� ���� �ٸ� �� ����
				compare = true;
				number = Random_Setting();
				for (int z=0 ; z<9 ; z++){
					if (R_number[z] == number){
						compare = false;
					}
				}
				if (compare == true){
					R_number[j] = number;
				}
				else{
					j--;
				}
			}
			width /= 3;
			height /= 3;
			
			for (row=0 ; row<3 ; row++){	// ���� ���� �迭���� ���� ���� ���� �̹��� ����
				for (col=0 ; col<3 ; col++){
					switch (R_number[R_index]){
					case 1:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(0,  0, width, height);
						sub_index[row][col] = 1;
						break;
					case 2:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(width,  0, width, height);
						sub_index[row][col] = 2;
						break;
					case 3:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(width * 2,  0, width, height);
						sub_index[row][col] = 3;
						break;
					case 4:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(0,  height, width, height);
						sub_index[row][col] = 4;
						break;
					case 5:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(width,  height, width, height);
						sub_index[row][col] = 5;
						break;
					case 6:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(width * 2,  height, width, height);
						sub_index[row][col] = 6;
						break;
					case 7:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(0,  height * 2, width, height);
						sub_index[row][col] = 7;
						break;
					case 8:
						sub_img[row][col] = ori_img[stageCheck].getSubimage(width,  height * 2, width, height);
						sub_index[row][col] = 8;
						break;
					case 9:		// ���� ���߱⸦ ���� �� ĭ 
						sub_img[row][col] = Make_White();
						sub_index[row][col] = 9;
						B_row = row;
						B_col = col;
						break;
					default:
						System.out.println("���� �� ���� ���� ����");
						break;
					}
					R_index++;
				}
			}
		}
	}	// Sub_Image
	
	
	// ���� ��ȯ
	public int Random_Setting(){
		int number = (int)(Math.random()*9 + 1);	// 1~9���� ����
		return number;
	}
	
	// Swing �� JFrame�� ����� Ŭ�������� paint()�޼ҵ带 �������̵�
	// paint()�� setVisible()�� ������ �� �����
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if (ori_img != null){
			// drawImage(��ü, x ��ǥ, y ��ǥ, width, height, Observer)
			g.drawImage(ori_img[stageCheck], 20, 50, 155, 155, this);
			g.drawImage(sub_img[0][0], 180, 50, 200, 200, this);
			g.drawImage(sub_img[0][1], 380, 50, 200, 200, this);
			g.drawImage(sub_img[0][2], 580, 50, 200, 200, this);
			g.drawImage(sub_img[1][0], 180, 250, 200, 200, this);
			g.drawImage(sub_img[1][1], 380, 250, 200, 200, this);
			g.drawImage(sub_img[1][2], 580, 250, 200, 200, this);
			g.drawImage(sub_img[2][0], 180, 450, 200, 200, this);
			g.drawImage(sub_img[2][1], 380, 450, 200, 200, this);
			g.drawImage(sub_img[2][2], 580, 450, 200, 200, this);
			 

		}
		else{
		
			System.out.println("�̹��� �ε� ����");
		}
	}

	// Ŭ���� ������ �̵� ���� ���� ��ȯ(���� ��ġ �ε���)
	public boolean Correct_clicked(int P_index){
		switch (P_index){
		case 1:
			if (sub_index[0][1] == 9 || sub_index[1][0] == 9){
				return true;
			}
			break;
		case 2:
			if (sub_index[0][0] == 9 || sub_index[0][2] == 9 || sub_index[1][1] == 9){
				return true;
			}
			break;
		case 3:
			if (sub_index[0][1] == 9 || sub_index[1][2] == 9){
				return true;
			}
			break;
		case 4:
			if (sub_index[0][0] == 9 || sub_index[1][1] == 9 || sub_index[2][0] == 9){
				return true;
			}
			break;
		case 5:
			if (sub_index[0][1] == 9 || sub_index[1][0] == 9 || sub_index[1][2] == 9 || sub_index[2][1] == 9){
				return true;
			}
			break;
		case 6:
			if (sub_index[0][2] == 9 || sub_index[1][1] == 9 || sub_index[2][2] == 9){
				return true;
			}
			break;
		case 7:
			if (sub_index[1][0] == 9 || sub_index[2][1] == 9){
				return true;
			}
			break;
		case 8:
			if (sub_index[1][1] == 9 || sub_index[2][0] == 9 || sub_index[2][2] == 9){
				return true;
			}
			break;
		case 9:
			if (sub_index[1][2] == 9 || sub_index[2][1] == 9){
				return true;
			}
			break;
		default:
			System.out.println("���� �迭 ����");
			break;
		}
		return false;
	}
	
	// �� ĭ �̹��� ��ȯ
	public BufferedImage Make_White(){
		BufferedImage m_white = null;
		try {
			m_white = ImageIO.read(new File("./setting/white.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m_white;
	}
	
	// ���� �̹��� ��ȯ(�̵�)ó�� �Լ�(2���� �迭 x, 2���� �迭 y, ���� ��ġ �ε���)
	public void Change_Image(int x, int y, int index){
		if (Correct_clicked(index)){	// Ŭ���� ������ ������ ���� �� ĭ�� ���� ���
			sub_img[B_row][B_col] = sub_img[x][y];	
			sub_img[x][y] = Make_White();	// �̹��� �̵� �� �� ĭ �̹��� ����
			sub_index[B_row][B_col] = sub_index[x][y];
			sub_index[x][y] = 9;			// �ε��� ��ȯ
			B_row = x;				
			B_col = y;						// �� ĭ �ε��� ����
			M_count++;	// ���� �ű� �� + 1
			counter.setText("Ŭ�� ��: "+ M_count);
			sound.startMusic();
		}
	}
	
	// ���� ���߱� �ϼ� üũ
	public boolean Check_Image(){
		if (sub_index[0][0] == 1 && sub_index[0][1] == 2 && sub_index[0][2] == 3 &&
				sub_index[1][0] == 4 && sub_index[1][1] == 5 && sub_index[1][2] == 6 &&
				sub_index[2][0] == 7 && sub_index[2][1] == 8 && sub_index[2][2] == 9){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	// ���� Ŭ����
	public void End_Game(){
		int width = ori_img[stageCheck].getWidth(this)/3;		// ���κ��ұ���
		int height = ori_img[stageCheck].getHeight(this)/3;	// ���κ��ұ���
		sub_img[2][2] = ori_img[stageCheck].getSubimage(width * 2,  height * 2, width, height);
		repaint();		// �׸� �ϼ�
		currentTimer.interrupt();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// ���콺 Ŭ�� �̺�Ʈ ó��
	@Override
	public void mousePressed(MouseEvent e) {
		int inX = e.getX();		// ���콺 x ��ǥ
		int inY = e.getY();		// ���콺 y ��ǥ
		if (inX > 180 && inX < 380 && inY > 50 && inY < 250){		// [0][0]
			Change_Image(0,0,1);// ���� �̹��� ��ȯ ó�� �Լ� 
		}
		else if(inX > 380 && inX < 580 && inY > 50 && inY < 250){	// [0][1]
			Change_Image(0,1,2);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 580 && inX < 780 && inY > 50 && inY < 250){	// [0][2]
			Change_Image(0,2,3);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 180 && inX < 380 && inY > 250 && inY < 450){	// [1][0]
			Change_Image(1,0,4);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 380 && inX < 580 && inY > 250 && inY < 450){	// [1][1]
			Change_Image(1,1,5);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 580 && inX < 780 && inY > 250 && inY < 450){	// [1][2]
			Change_Image(1,2,6);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 180 && inX < 380 && inY > 350 && inY < 650){	// [2][0]
			Change_Image(2,0,7);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 380 && inX < 580 && inY > 350 && inY < 650){	// [2][1]
			Change_Image(2,1,8);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 580 && inX < 780 && inY > 350 && inY < 650){	// [2][2]
			Change_Image(2,2,9);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		repaint();			// paint()�޼ҵ� ����
		
		if (Check_Image()){	// ���� �̹��� �ϼ� üũ
			 music.stopMusic();
			if(stageCheck == 0) { //1ź Ŭ�����
				End_Game();
				result = JOptionPane.showConfirmDialog(this, "Ŭ���� �ϼ̽��ϴ�. ���� ���������� �̵��Ͻðڽ��ϱ�?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.CLOSED_OPTION) { // x ��ư ���� ���
					 System.exit(0);
				}
				else if(result==JOptionPane.YES_OPTION) { // �� �� ���� ���
					stageCheck++;
					dispose(); //���� �����Ӹ� ����
					Create_Frame();
				}
				else { // �ƴϿ� �� ���� ��� 
					 System.exit(0);
				}
				
			}
			else if(stageCheck == 1){ //2ź���� Ŭ�����
				clear.startMusic();
				End_Game();
				//���â ��� �� ī�װ��� ����â���� �̵� ���� Ȯ�� 
				long E_Timer = System.currentTimeMillis();
				Timer = (E_Timer - S_Timer) / 1000;
				result = JOptionPane.showConfirmDialog(this, "���� Ŭ�� �� : " + M_count + "��, �ɸ� �ð� : " + Timer + "��" +"\r\n" + "ī�װ��� ����â���� �̵��ϰڽ��ϱ�?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.CLOSED_OPTION) { // x ��ư ���� ���
					 JOptionPane.showMessageDialog(null, "�����մϴ�");
					 System.exit(0);
				}
				else if(result==JOptionPane.YES_OPTION) { // �� �� ���� ���
					dispose();
					P_Title p = new P_Title();
					p.P_title();
				}
				else { // �ƴϿ� �� ���� ��� 
					 JOptionPane.showMessageDialog(null, "�����մϴ�");
					 System.exit(0);
				}
				stageCheck++;
			}
			else
				dispose();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}