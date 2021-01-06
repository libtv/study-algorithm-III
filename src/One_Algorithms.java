/*****************************************************************/
/* 	       DDA, Bresenham 알고리즘 구현을 추가하면 바로 		*/
/* 	  실행가능한 프로그램을 작성할 수 있는 Reference Code임 	*/
/*	   	    swing 클래스로 업그레이드가 필요함			*/
/*****************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;

public class One_Algorithms extends JFrame implements ActionListener {
	//create button
	JButton btnDda = new JButton("DDA");
	JButton btnDda_circle = new JButton("DDA_circle");
	JButton btnBres_line = new JButton("Bresenham_line");
	JButton btnbres_eclipse = new JButton("Bresenham_eclipse");
	DrawArea area = new DrawArea();
	//declare panel
	JPanel pnlUp;
	JPanel pnlDown;

	public One_Algorithms() {

		//create panel
		pnlUp = new JPanel(new BorderLayout());
		pnlDown = new JPanel(new GridLayout(1, 2));

		//attach panel to the window
		//Frame, Window의 default 배치관리자가 BorderLay 이다.)
		add(pnlUp, BorderLayout.CENTER);
		add(pnlDown, BorderLayout.SOUTH);

		//attach canvas to the panel
		pnlUp.add(area);
		//attach buttons to the panel
		pnlDown.add(btnDda);
		pnlDown.add(btnDda_circle);
		pnlDown.add(btnBres_line);
		pnlDown.add(btnbres_eclipse);
		

		//register action of button
		btnDda.addActionListener(this);
		btnDda_circle.addActionListener(this);
		btnBres_line.addActionListener(this);
		btnbres_eclipse.addActionListener(this);
	}	

	public void actionPerformed(ActionEvent evt) {
		String str = evt.getActionCommand();

		if (str.equals("DDA")) { 
			//draw dda 
			area.draw("dda");
		}else if (str.equals("DDA_circle")) { 
			//draw dda 
			area.draw("dda_circle");
		} else if (str.equals("Bresenham_line")) {
			//draw bresenham
			area.draw("bres");
		}  else if (str.equals("Bresenham_eclipse")) {
			area.draw("bres_eclipse");
		}; 
	}
}

class DrawArea extends Canvas {
	ArrayList<Point> mVertices = new ArrayList<>();
	BufferedImage mImage = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
	//for shape
	String shape = "";

	public void paint(Graphics g) 
	{
		int x1=10, y1=getSize().height-20, x2=getSize().width-20, y2=10;
		if (shape.equals("dda")) {
			g.setColor(new Color(random(256), random(256), random(256)));
			dda(g, x1, y1, x2, y2);
		} 
		else if (shape.equals("bres")) {
			g.setColor(new Color(random(256), random(256), random(256)));
			Bresenham(g, x1, y1, x2, y2);
		} 
		else if (shape.equals("bres_eclipse")) {
			Bresenham_eclipse(g, x1, y1, x2, y2);
		} 
		else if (shape.equals("dda_circle")) {
			dda_circle(g, getSize().width / 2, getSize().height / 2, 250);
		}
	}
	private void dda_circle(Graphics g, int x1, int y1, int r) {
		/* 참고 문헌
		/* https://code.cheraus.com/codes/cgraphics/dda_circle.php
		*/
		
	   double xc1,xc2,yc1,yc2,d,sx,sy;
	   int val,i;
	   
	   /* 초기값 (xc, yc), (x, y) 설정 */
	   xc1 = r;
	   yc1 = 0;
	   sx = xc1; 
	   sy = yc1;
	   
	   i=0;
	   
	   do {
		   val = (int) Math.pow(2, i);
		   i++;
	   } while (val < r);
	   
	   /* 판별식 d = 1/2^r */
	   d = 1/Math.pow(2,i-1);
	   
	   /* 알고리즘 xc2 = xc1+yc1*d */
	   /* 알고리즘 yc2 = yc2-xc2*d */
	   do {
	      xc2 = xc1 + yc1*d;
	      yc2 = yc1 - xc2*d;

	      putPixel(g, (int)(x1+xc2), (int)(y1-yc2));
	      xc1=xc2;
	      yc1=yc2;
	     } while((yc1-sy)<d || (sx-xc1)>d); /* 판별식 d가 이 범위에 있을때까지 반복 */
		
	}
	private void Bresenham_eclipse(Graphics g, int x0, int y0, int x1, int y1) {
		
		int delta_x = Math.abs(x1-x0), delta_y = Math.abs(y1-y0), b1 = delta_y&1; /* 지름 값 */
		long dx = 4*(1-delta_x)*delta_y*delta_y, dy = 4*(b1+1)*delta_x*delta_x; /* deltaX와 deltaY */
		long err = dx+dy+b1*delta_x*delta_x, e2; /* 1단계 오류 */

		/* 교환된 점으로 호출할 경우 */
		if (x0 > x1) { x0 = x1; x1 += delta_x; } 
		if (y0 > y1) y0 = y1;
		y0 += (delta_y+1)/2; y1 = y0-b1;   // 시작픽셀
		delta_x *= 8*delta_x; b1 = 8*delta_y*delta_y;

		do {
			putPixel(g, x1, y0); /* 1 사분면 */
			putPixel(g, x0, y0); /* 2 사분면 */
			putPixel(g, x0, y1); /* 3 사분면 */
			putPixel(g, x1, y1); /* 4 사분면 */
		    e2 = 2*err;
		    if (e2 <= dy) { y0++; y1--; err += dy += delta_x; } // y 단계
		    if (e2 >= dx || 2*err > dy) { x0++; x1--; err += dx += b1; } // x 단계
		} while (x0 <= x1);
		   
		while (y0-y1 < delta_y) {  /* 타원이 끝내려고 하는 조건문 */
			putPixel(g, x0-1, y0); /* -> 타원 끝내기 */
			putPixel(g, x1+1, y0++); 
			putPixel(g, x0-1, y1);
			putPixel(g, x1+1, y1--); 
		}
	}

	void draw(String str) 
	{
		shape = str;
		repaint();
	}

	private int random(int r) 
	{
		return (int)Math.floor(Math.random() * r);
	}

	public void dda(Graphics g, int xa, int ya, int xb, int yb)
	{
		// DDA 알고리즘 구현
		// 좌표 (x1, y1) 인 점을 그리기 위해서는 
		// Graphics g; g.drawLine(x1, y1, x1, y1);
		
		// (1)초기 값을 구한다.
		int delta_x = xb - xa; // deltaX 
		int delta_y = yb - ya; // deltaY
		float m = (float) delta_y / delta_x; // 기울기 계산
		float temp_x = xa; // x의 증가로 인한 저장 변수
		float temp_y = ya; // y의 증가로 인한 저장 변수
		
		// (2)알고리즘
		if (m <= 1.0  && m >= -1.0) { // 기울기 lml <= 1
			// x = x + 1
			// y = y + m
			for (int i=xa; i<xb; i++) { //x가 1증가 했을 때의 y의 값이 변하므로 x를 최대변수 만큼 증가 시킨다.
				temp_y += m; //y는 round 함수를 적용시키기 전에 기울기를 더해준다.
				g.drawLine((int)i, (int)(temp_y+0.5), (int)i, (int)(temp_y+0.5)); //round 함수를 이용하여 y의 값을 변화시켜준다.
			}
			
		} else { // 기울기 lml > 1
			// y = y + 1
			// x = x + 1 / m
			for (int i=ya; i<yb; i++) { //y가 1증가 했을 때의 y의 값이 변하므로 y를 최대변수 만큼 증가 시킨다.
				temp_x += 1/m; //x는 round 함수를 적용시키기 전에 기울기를 더해준다.
				g.drawLine((int)(temp_x+0.5), i, (int)(temp_y+0.5), i); //round 함수를 이용하여 x의 값을 변화시켜준다.
			}
		}
		
	}

	public void Bresenham(Graphics g, int xa, int ya, int xb, int yb)
	{
		// 브레젠함 알고리즘 구현
		// 좌표 (x1, y1) 인 점을 그리기 위해서는 
		// Graphics g; g.drawLine(x1, y1, x1, y1);
		
		// 기울기 0 <= m <= 1로 가정
		// (1)초기 값을 구한다.
		int delta_x = Math.abs(xb - xa); // deltaX 
		int delta_y = Math.abs(yb - ya); // deltaY
		int temp_y = ya; // y의 증가로 인한 저장 변수
		
		int p1 = 2 * delta_y - delta_x; // 처음 판별식
		int c1 = 2 * delta_y; // Pk < 0 인 경우 y의 픽셀은 증가 하지 않는 식
		int c2 = 2 * (delta_y - delta_x); // Pk > 0 인 경우 y의 픽셀 증가 식
		
		// (2)알고리즘
		for(int i=xa; i<xb; i++) { //x의 최대 변수만큼 증가 시킨다.
			g.drawLine(i, temp_y, i, temp_y);
			
			if (p1 < 0) { // 판별식(1)
				p1 += c1;
			} else {
				--temp_y; // 판별식(2)
				p1 += c2;
			}
		}
	}
	
	void putPixel(Graphics g, int x, int y) {
		g.setColor(new Color(random(256), random(256), random(256)));
		g.fillRect(x, y, 1, 1);
	}
}
