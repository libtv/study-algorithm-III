import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Five_Algorithms extends JFrame {
	//create button
	JButton btnDda = new JButton("DDA");
	JButton btnBres = new JButton("Bresenham");
	//create canvas for drawing area
	DrawArea2 area = new DrawArea2();
	//declare panel
	JPanel pnlUp;
	JPanel pnlDown;

	public Five_Algorithms() {

		//create panel
		pnlUp = new JPanel(new BorderLayout());
		pnlDown = new JPanel(new GridLayout(1, 2));

		//attach panel to the window
		//Frame, Window의 default 배치관리자가 BorderLay 이다.)
		add(pnlUp, BorderLayout.CENTER);
		add(pnlDown, BorderLayout.SOUTH);

		//attach canvas to the panel
		pnlUp.add(area);
	}
}

class DrawArea2 extends Canvas {
	private int fir_X, fir_Y, las_X, las_Y = 0; // 첫번째 두번째 좌표
	private int chk_point = 0; // 첫번째 점, 두번쨰 점인지 확인하기 위함
	static int Left=50, Right=700, Bottom=50, Top=500; // 사각형 좌표 ( 이 외의 좌표들은 클립할 예정 )
	Point P0,P1; // 현재 점을 기억할 2개의 변수를 저장
	
	DrawArea2() { 
		setBackground(Color.BLACK);
		this.addMouseListener(new MouseAdapter(){ // 마우스리스너 생성
			public void mousePressed(MouseEvent evt) {
				if(chk_point == 0) { // chk_point가 첫번째이면 
					fir_X=evt.getX(); fir_Y=evt.getY(); chk_point++; // fir변수에 좌표 저장 
				} else if (chk_point == 1) {// chk_point가 두번째이면 
					las_X=evt.getX(); las_Y=evt.getY(); chk_point = 0;  // las변수에 좌표 저장 후 chk_point 초기화
					P0 = new Point(fir_X, fir_Y); 	// 포인트 하나 생성
					P1 = new Point(las_X, las_Y);	// 포인트 하나 생성
					paint(getGraphics());}
			}});
	}
	
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int)Left, (int)Bottom, (int)(Right-Left), (int)(Top-Bottom));
		g.setColor(Color.YELLOW);
		try {
			if(CohenSutherland(P0,P1)) // 라인그리기
				g.drawLine(P0.x, (int)P0.y, (int)P1.x,(int)P1.y); 
		} catch(Exception e) {
			
		}

	}

	static boolean CohenSutherland(Point P0, Point P1) {
		int outCode0,outCode1; 
		
		while(true) {
			outCode0 = outLine(P0); // 8421로 외부 점을 표현
			outCode1 = outLine(P1); // 8421로 외부 점을 표현
			
			if ((outCode0 == 0) && (outCode1 == 0)) { // 두 점 다 다각형 내부에 있을 경우  or 처리를 다 했을 경우
				return true;						  // 그 점을 그대로 사용
			}
			
			if(outCode0 == 0) { // outCode0과 outCode1 교환 + P0와 P1 교환
				int tempCoord;
				int tempCode;
				tempCoord = P0.x; 
				P0.x= P1.x; 
				P1.x = tempCoord;
				tempCoord = P0.y; 
				P0.y= P1.y; 
				P1.y = tempCoord;
				tempCode = outCode0; 
				outCode0 = outCode1; 
				outCode1 = tempCode;
			} 
			// 교차점을 찾아서 공식 대입
			// x = x1 + (1 / slope) * (y - y1) 
			// y = y1 + slope * (x - x1)
			if ((outCode0 & 1) != 0 ) {  // top이 초과할 경우
				P0.x += (P1.x - P0.x)*(Top - P0.y)/(P1.y - P0.y); // x = x1 + (1 / slope) * (y - y1) 
				P0.y = Top;
			} else if((outCode0 & 2) != 0 ) { 
				P0.x += (P1.x - P0.x)*(Bottom - P0.y)/(P1.y - P0.y); // x = x1 + (1 / slope) * (y - y1) 
				P0.y = Bottom;
			} else if((outCode0 & 4) != 0 ) { 
				P0.y += (P1.y - P0.y)*(Right - P0.x)/(P1.x - P0.x); // y = y1 + slope * (x - x1)
				P0.x = Right;
			} else if((outCode0 & 8) != 0 ) { 
				P0.y += (P1.y - P0.y)*(Left - P0.x)/(P1.x - P0.x); // y = y1 + slope * (x - x1)
				P0.x = Left;
			}
		} 
	} 
	
	private static int outLine(Point P) { /* 8421로 표현하여 초과하는 라인을 영역으로 표시함   */
		int Code = 0; 
		try {
			if(P.y > Top) Code += 1;			 // 1 is top
			else if(P.y < Bottom) Code += 2; 	// 2 is bottom

			if(P.x > Right) Code += 4;			 // 4 is right
			else if(P.x < Left) Code += 8; 	// 8 is left 
		} catch(Exception e) {
			
		}
		return Code;
	}
}
