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

public class Two_Algorithms extends JFrame implements ActionListener {
	//create button
	JButton btnPoly = new JButton("XY_Polygon");
	//create canvas for drawing area
	DrawArea3 area = new DrawArea3();
	//declare panel
	JPanel pnlUp;
	JPanel pnlDown;

	public Two_Algorithms() {

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
		pnlDown.add(btnPoly);

		//register action of button
		btnPoly.addActionListener(this);
		
	}	

	public void actionPerformed(ActionEvent evt) {
		String str = evt.getActionCommand();

		if (str.equals("XY_Polygon")) {
			area.draw("polygon");
		}; 
	}
}

class DrawArea3 extends Canvas {
	BufferedImage mImage = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
	String shape = "";
	
	public DrawArea3() {
		draw("first");
		// TODO Auto-generated constructor stub
	}

	/* Polygon 페인팅  */
	public void paint(Graphics g) {
		ArrayList<Point> mVertices = new ArrayList<>(); /* 점의 리스트들을 ArrayList로 구현   */
		ArrayList<EdgeList> list = new ArrayList<EdgeList>(); /* EdgeList 생성 */
		g.setColor(Color.green);
	    Polygon p = new Polygon(); 
	    for (int i = 0; i < 6; i++) { /* 다각형을 그리기 위한 점을 리스트에 추가함  */
	    	p.addPoint((int) (100 + 100 * Math.cos(i * 2 * Math.PI / 6)), (int) (100 + 100 * Math.sin(i * 2 * Math.PI / 6)));
	    	mVertices.add(new Point((int) (100 + 100 * Math.cos(i * 2 * Math.PI / 6)), (int) (100 + 100 * Math.sin(i * 2 * Math.PI / 6))));
	    }
        for(int i = 0 ; i < mVertices.size() - 1; ++i) { /* 각 선분들을 EdgeList의 추가함  */
            Point current = mVertices.get(i);
            Point next = mVertices.get(i+1);
            list.add(new EdgeList(current.x,current.y,next.x,next.y));
        }
        Point first = mVertices.get(0); /* 마지막 선분 추가(1)  */
        Point last = mVertices.get(mVertices.size() -1); /* 마지막 선분 추가(2)  */
        list.add(new EdgeList(first.x,first.y,last.x,last.y)); /* 마지막 선분 추가(3)  */
        g.drawPolygon(p); /* 다각형 그리고  */
        
		if (shape.equals("polygon")) {
		    floodFill(g, list, mVertices); /* 다각형을 채우는 함수  */
		} 
	}
	
	/* 다각형을 채우는 함수  */
    private void floodFill(Graphics g, ArrayList<EdgeList> list, ArrayList<Point> mVertices) { 
        Collections.sort(list); /* y축으로 정렬  */

        int shortY = list.get(0).getYa();
        int longY = list.get(list.size()-1).getYa();
        
        Comparator<EdgeList> myComparator = new Comparator<EdgeList>() {
			@Override
			public int compare(EdgeList o1, EdgeList o2) {
				if (o1.getXa() > o2.getXa()) {
					return 1;
				}
				return -1;
			}
        	};
        	
        Collections.sort(list, myComparator);  /* x축으로 정렬  */

        int shortX = list.get(0).getXa();
        int longX = list.get(list.size()-1).getXa();
        
        for(int i = shortX; i<=longX; i++) { /* 해당 픽셀이 다각형 안에 있을 때 픽셀채우기  */
        	for(int j = shortY; j<=longY; j++) {
        		if(isInside(i, j, mVertices)) { 
        			putPixel(g, i, j);
        		}
        	}
        }
    }
    
    private boolean isInside(int x, int y, ArrayList<Point> mVertices) { /* 홀짝규칙으로 선분에 안에 있는지 확인 */
        boolean result = false;
        int i,j;
        for (i = 0, j = mVertices.size()- 1; i < mVertices.size(); j = i++) {
            if ((mVertices.get(i).y > y) != (mVertices.get(j).y > y) &&
                    (x < (mVertices.get(j).x - mVertices.get(i).x) * (y - mVertices.get(i).y) /
                            (mVertices.get(j).y-mVertices.get(i).y) + mVertices.get(i).x)) {
            	
                result = !result;
            }
        }
        return result;
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
	
	void putPixel(Graphics g, int x, int y) {
		g.setColor(new Color(random(256), random(256), random(256)));
		g.fillRect(x, y, 1, 1);
	}
}
