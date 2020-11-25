import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Six_Algorithms extends JFrame {
	//create button
	JButton btnDda = new JButton("DDA");
	JButton btnBres = new JButton("Bresenham");
	//create canvas for drawing area
	DrawArea5 area = new DrawArea5();
	//declare panel
	JPanel pnlUp;
	JPanel pnlDown;
	
	public Six_Algorithms() {

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

class DrawArea5 extends Canvas {
	private int fir_X, fir_Y, las_X, las_Y = 0; // 첫번째 두번째 좌표
	private int chk_point = 0; // 첫번째 점, 두번쨰 점인지 확인하기 위함
	static int xLeft=50, xRight=700, yBottom=50, yTop=500; // 사각형 좌표 ( 이 외의 좌표들은 클립할 예정 )
	Point P0,P1; // 현재 점을 기억할 2개의 변수를 저장
	
	DrawArea5() { 
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
		g.drawRect((int)xLeft, (int)yBottom, (int)(xRight-xLeft), (int)(yTop-yBottom));
		g.setColor(Color.YELLOW);
		try {
			if(LiangBarsky(P0, P1)) // 라인그리기
				g.drawLine(P0.x, P0.y, P1.x,(int)P1.y); 
		} catch(Exception e) {
			
		}

	}
	

	static boolean LiangBarsky(Point P0, Point P1) {
		/* LiangBarsky 알고리즘
		 * 참고문헌 https://www.cs.helsinki.fi/group/goa/viewing/leikkaus/intro.html
		 * http://www.pracspedia.com/CG/liangbarsky.html
		 * https://www.geeksforgeeks.org/liang-barsky-algorithm/
		 */
		double t_min = 0, t_max = 1; // 처음 min값과 max값 설정
		int dx = P1.x - P0.x, dy = P1.y - P0.y; // deltaX와 deltaY 계산
		int p[] = {-dx, dx, -dy, dy}; // 
		int q[] = {P0.x - xLeft, xRight - P0.x, P0.y - yBottom, yTop - P0.y};
		
		for (int i = 0; i < 4; i++) {
		    if (p[i] == 0) { // 클리핑에 가장자리에 평행한 선 일 경우
		        if (q[i] < 0) { // 선이 완전히 밖에있으므로 제거됨 
		            return false;
		        }
		    } else { // 아닐시에
		        double u = (double) q[i] / p[i];
		        if (p[i] < 0) { //선이 외부에서 내부로 이동함 pk < 0, maximum(0, qk/pk) is taken.
		            t_min = Math.max(u, t_min);
		        } else { //선이 내부에서 외부로 이동함 pk > 0, minimum(1, qk/pk) is taken.
		            t_max = Math.min(u, t_max);
		        }
		    }
		}
		
        if (t_min > t_max) { //선이 외부에 있는 경우
            return false;
        }
		
        int new_x0, new_y0, new_x1, new_y1; // 해당 알고리즘을 사용하여 xy축 변경
        new_x0 = (int) (P0.x + t_min * dx); // (x1 + tmin(x2-x1)
        new_y0 = (int) (P0.y + t_min * dy); // y1 + tmin(y2-y1))
        new_x1 = (int) (P0.x + t_max * dx); // (x1 + tmax(x2-x1)
        new_y1 = (int) (P0.y + t_max * dy); // y1 + tmax(y2-y1))

        P0.x = new_x0;
        P0.y = new_y0;
        P1.x = new_x1;
        P1.y = new_y1;
        
		return true;
	} 
	
}
