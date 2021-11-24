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


// 프레임 생성과 이미지 불러오기 및 쪼개기

// GUI Swing
@SuppressWarnings("serial")
public class P_frame extends JFrame implements MouseListener{
	
	public BufferedImage[] ori_img =new BufferedImage[2];	// 원본 이미지
	public BufferedImage[][] sub_img = new BufferedImage[3][3];	// 분할 이미지 버퍼
	public int[][] sub_index = new int[3][3];			// 분할 이미지 번호
	
	
	
	P_Music music; //음악 재생
	P_Music sound =new P_Music("./effectsound/move.wav"); //효과음
	P_Music clear = new P_Music("./effectsound/clear.wav"); //모든 게임 완수 시
	P_Music resetting = new P_Music("./effectsound/reset.wav");
	
	Graphics g;					// paint()를 수행할 Graphics 객체	
	int B_row, B_col;			// 빈 칸의 인덱스 
	int M_count=0;				// 조각 옮긴 수
	long S_Timer;				// 시작 시간
	public long Timer=0;			// 걸린 시간
	int stageCheck =0;			 //현재 스테이지 체크 
	int result;		 //리플레이용 변수
	JLabel counter = new JLabel("클릭 수: " +M_count);
	JLabel timecount = new JLabel();
	P_Timer currentTimer; 
	String [] moviename = new String[] {"<관상> 중 수양대군","<기생충> 중 김기우"};
   
	// 프레임 생성
	public void Create_Frame(){
		setLayout(null);
		
		// UI
		setSize(1000,800);
		setTitle("Puzzle Matcher");
		setBackground(Color.white);
		
		
		Create_Image();		// 이미지 생성 메소드 호출
		Sub_Image(9);		// 이미지 분할 메소드 호출
	
		
		addMouseListener(this);		// MouseListener 연결
		
		
		
		this.getContentPane().setLayout(null); //컨텐트 팬의 배치관리자는 절대값
		
		//퍼즐 새로고침 버튼 생성
		//버튼에 맞게 이미지 크기 조정
		ImageIcon resetimage= new ImageIcon("./setting/restore.png");
		Image tmpImg = resetimage.getImage();
		Image resetImg = tmpImg.getScaledInstance(140,140, java.awt.Image.SCALE_SMOOTH);
		ImageIcon restore = new ImageIcon(resetImg);
		
		//퍼즐 완성 버튼 생성
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
            S_Timer = System.currentTimeMillis();	// 시작 시간 저장
            currentTimer.start();
        }
        
        if(stageCheck ==1){
            timecount = new JLabel();
            currentTimer = new P_Timer(timecount);
            timecount.setBounds(10,480,100,100);
            this.getContentPane().add(timecount);
            S_Timer = System.currentTimeMillis();	// 시작 시간 저장
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
	            int width = 200;      // 가로분할길이
	            int height = 200;    // 세로분할길이
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
	      
	     //음악 재생
		if(stageCheck == 0)
			music = new P_Music("./effectsound/face.wav");
		else 
			music = new P_Music("./effectsound/parasite.wav");
		music.startMusic();
		
		
		setVisible(true);	// paint() 수행
		
		
		// 창종료 이벤트(프로세스 완전 종료)
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	
	}
	
	
	// 이미지 생성
	public void Create_Image(){
		try {
			ori_img[0] = ImageIO.read(new File("./puzzle/face.png"));
			ori_img[1] = ImageIO.read(new File("./puzzle/parasite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 이미지 분할 셋팅(조각 수)
	public void Sub_Image(int m_piece){
		int width = ori_img[stageCheck].getWidth(this);		// 가로분할길이
		int height = ori_img[stageCheck].getHeight(this);	 // 세로분할길이
		int R_number[] = new int[9];			// 난수 저장 배열
		int number = 0;							// 난수 저장 버퍼
		boolean compare;						// 난수 비교
		int row, col;							// 분할 이미지 저장 인덱스
		int R_index = 0;						// 난수 배열 인덱스
		
		if (m_piece == 9){			// 9조각
			
			for (int i=0 ; i<9 ; i++){	// 난수 저장 배열 초기화		
				R_number[i] = 0;
			}
			R_number[0] = Random_Setting();	// 난수 생성
			for (int j=1 ; j<9 ; j++){	// 난수 저장 배열에 1~9의 서로 다른 수 저장
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
			
			for (row=0 ; row<3 ; row++){	// 난수 저장 배열값에 따른 랜덤 조각 이미지 저장
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
					case 9:		// 퍼즐 맞추기를 위한 빈 칸 
						sub_img[row][col] = Make_White();
						sub_index[row][col] = 9;
						B_row = row;
						B_col = col;
						break;
					default:
						System.out.println("범위 밖 난수 생성 오류");
						break;
					}
					R_index++;
				}
			}
		}
	}	// Sub_Image
	
	
	// 난수 반환
	public int Random_Setting(){
		int number = (int)(Math.random()*9 + 1);	// 1~9난수 생성
		return number;
	}
	
	// Swing 의 JFrame을 상속한 클래스에서 paint()메소드를 오버라이드
	// paint()는 setVisible()을 수행할 때 실행됨
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if (ori_img != null){
			// drawImage(객체, x 좌표, y 좌표, width, height, Observer)
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
		
			System.out.println("이미지 로드 오류");
		}
	}

	// 클릭한 조각의 이동 가능 유무 반환(조각 위치 인덱스)
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
			System.out.println("조각 배열 오류");
			break;
		}
		return false;
	}
	
	// 빈 칸 이미지 반환
	public BufferedImage Make_White(){
		BufferedImage m_white = null;
		try {
			m_white = ImageIO.read(new File("./setting/white.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m_white;
	}
	
	// 조각 이미지 교환(이동)처리 함수(2차원 배열 x, 2차원 배열 y, 조각 위치 인덱스)
	public void Change_Image(int x, int y, int index){
		if (Correct_clicked(index)){	// 클릭한 조각과 인접한 곳에 빈 칸이 있을 경우
			sub_img[B_row][B_col] = sub_img[x][y];	
			sub_img[x][y] = Make_White();	// 이미지 이동 및 빈 칸 이미지 저장
			sub_index[B_row][B_col] = sub_index[x][y];
			sub_index[x][y] = 9;			// 인덱스 교환
			B_row = x;				
			B_col = y;						// 빈 칸 인덱스 갱신
			M_count++;	// 조각 옮긴 수 + 1
			counter.setText("클릭 수: "+ M_count);
			sound.startMusic();
		}
	}
	
	// 조각 맞추기 완성 체크
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
	
	
	
	// 게임 클리어
	public void End_Game(){
		int width = ori_img[stageCheck].getWidth(this)/3;		// 가로분할길이
		int height = ori_img[stageCheck].getHeight(this)/3;	// 세로분할길이
		sub_img[2][2] = ori_img[stageCheck].getSubimage(width * 2,  height * 2, width, height);
		repaint();		// 그림 완성
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
	
	// 마우스 클릭 이벤트 처리
	@Override
	public void mousePressed(MouseEvent e) {
		int inX = e.getX();		// 마우스 x 좌표
		int inY = e.getY();		// 마우스 y 좌표
		if (inX > 180 && inX < 380 && inY > 50 && inY < 250){		// [0][0]
			Change_Image(0,0,1);// 조각 이미지 교환 처리 함수 
		}
		else if(inX > 380 && inX < 580 && inY > 50 && inY < 250){	// [0][1]
			Change_Image(0,1,2);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 580 && inX < 780 && inY > 50 && inY < 250){	// [0][2]
			Change_Image(0,2,3);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 180 && inX < 380 && inY > 250 && inY < 450){	// [1][0]
			Change_Image(1,0,4);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 380 && inX < 580 && inY > 250 && inY < 450){	// [1][1]
			Change_Image(1,1,5);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 580 && inX < 780 && inY > 250 && inY < 450){	// [1][2]
			Change_Image(1,2,6);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 180 && inX < 380 && inY > 350 && inY < 650){	// [2][0]
			Change_Image(2,0,7);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 380 && inX < 580 && inY > 350 && inY < 650){	// [2][1]
			Change_Image(2,1,8);	// 조각 이미지 교환 처리 함수
		}
		else if(inX > 580 && inX < 780 && inY > 350 && inY < 650){	// [2][2]
			Change_Image(2,2,9);	// 조각 이미지 교환 처리 함수
		}
		repaint();			// paint()메소드 실행
		
		if (Check_Image()){	// 조각 이미지 완성 체크
			 music.stopMusic();
			if(stageCheck == 0) { //1탄 클리어시
				End_Game();
				result = JOptionPane.showConfirmDialog(this, "클리어 하셨습니다. 다음 스테이지로 이동하시겠습니까?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.CLOSED_OPTION) { // x 버튼 누른 경우
					 System.exit(0);
				}
				else if(result==JOptionPane.YES_OPTION) { // 예 를 누른 경우
					stageCheck++;
					dispose(); //현재 프레임만 종료
					Create_Frame();
				}
				else { // 아니오 를 누른 경우 
					 System.exit(0);
				}
				
			}
			else if(stageCheck == 1){ //2탄까지 클리어시
				clear.startMusic();
				End_Game();
				//결과창 출력 및 카테고리 선택창으로 이동 여부 확인 
				long E_Timer = System.currentTimeMillis();
				Timer = (E_Timer - S_Timer) / 1000;
				result = JOptionPane.showConfirmDialog(this, "최종 클릭 수 : " + M_count + "번, 걸린 시간 : " + Timer + "초" +"\r\n" + "카테고리 선택창으로 이동하겠습니까?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.CLOSED_OPTION) { // x 버튼 누른 경우
					 JOptionPane.showMessageDialog(null, "종료합니다");
					 System.exit(0);
				}
				else if(result==JOptionPane.YES_OPTION) { // 예 를 누른 경우
					dispose();
					P_Title p = new P_Title();
					p.P_title();
				}
				else { // 아니오 를 누른 경우 
					 JOptionPane.showMessageDialog(null, "종료합니다");
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
