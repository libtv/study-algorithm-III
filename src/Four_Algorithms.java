import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Four_Algorithms extends JFrame implements ActionListener {
	//declare panel
	JPanel pnlUp;
	JPanel pnlDown;
	JLabel txtTrans = new JLabel("연속적인 이동"); JLabel txtScale = new JLabel("임의의 점 신축"); JLabel txtRotation = new JLabel("임의의 점 회전");
	JTextArea txtT = new JTextArea("5,2"); JTextArea txtS = new JTextArea("5"); JTextArea txtR = new JTextArea("180");
	JButton btn1 = new JButton("Translation"); JButton btn2 = new JButton("Scale"); JButton btn3 = new JButton("Rotation");
	CanvasArea2 area = new CanvasArea2();

	Four_Algorithms() {
		pnlUp = new JPanel(new BorderLayout());
		pnlDown = new JPanel(new GridLayout(1, 2));
		add(pnlUp, BorderLayout.CENTER);
		add(pnlDown, BorderLayout.SOUTH);
		
		pnlUp.add(area);
		pnlDown.add(txtTrans);
		pnlDown.add(txtT);
		pnlDown.add(btn1);
		
		pnlDown.add(txtScale);
		pnlDown.add(txtS);
		pnlDown.add(btn2);
		
		pnlDown.add(txtRotation);
		pnlDown.add(txtR);
		pnlDown.add(btn3);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();

		if (str.equals("Translation")) {
			String a[] = txtT.getText().split(",");
			int result = Integer.parseInt(a[0]) + Integer.parseInt(a[1]);
			area.draw("Translation", result);
		} else if (str.equals("Scale")) {
			area.draw("Scale", Double.parseDouble(txtS.getText()));
		}  else if (str.equals("Rotation")) {
			area.draw("Rotation", Integer.parseInt(txtR.getText()));
		}; 
	}
}

class CanvasArea2 extends Canvas {
	private static final long serialVersionUID = 4012295323526936729L;
	String shape = "";
	double number;
	int centerX, centerY, pixelSize;

	void draw(String str, double number) {
		shape = str;
		this.number = number;
		repaint();
	}
	
	void initgr() {
		pixelSize = 10;
		centerX = pixelSize * ((getWidth() / pixelSize) / 2);
		centerY = pixelSize * ((getHeight() / pixelSize) / 2);
	}

	void putPixel(Graphics g, int x, int y) {
		g.setColor(Color.GREEN);
		g.fillRect((x * pixelSize) - (pixelSize / 2), (y * pixelSize)
				- (pixelSize / 2), pixelSize, pixelSize);
	}

	void drawPolygon_O(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
		
		int x[] = {x1*pixelSize, x2*pixelSize, x3*pixelSize};
		int y[] = {y1*pixelSize, y2*pixelSize, y3*pixelSize};
		
		g.setColor(Color.BLACK);
		g.fillPolygon(x, y, 3);
	}

	public void paint(Graphics g) {
		initgr();
		paintCoordinateSystem(g);
		drawPolygon_O(g, 0, 0, 0, 5, 5, 0);
		if (shape.equals("Translation")) {
			drawPolygon_T(g, 0, 0, 0, 5, 5, 0, number);
		} else if (shape.equals("Scale")) {
			drawPolygon_S(g, 0, 0, 0, 5, 5, 0, number);
		} else	if (shape.equals("Rotation")) {
			drawPolygon_R(g, 0, 0, 0, 5, 5, 0, number);
		} 		
	}
	
	/* Translation 함수 */
	void drawPolygon_T(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, double Translation) {
		//		[ 1 0 t ]	[ x ]
 		//P` = 	  0 1 t		  y 	= T(tx, ty)*P
		//		[ 0 0 1 ]	[ 1 ]
		double[][] a = {{1, 0, Translation}, {0, 1, Translation}, { 0, 0, 1}};
		int result_first[]; int result_second[]; int result_last[];
		int[] xy_first = {x1, y1, 1}; int[] xy_second = {x2, y2, 1}; int[] xy_last = {x3, y3, 1};
		
		result_first = productMatrix(a, xy_first);
		result_second = productMatrix(a, xy_second);
		result_last = productMatrix(a, xy_last);
		
		int x[] = {result_first[0]*pixelSize, result_second[0]*pixelSize, result_last[0]*pixelSize};
		int y[] = {result_first[1]*pixelSize, result_second[1]*pixelSize, result_last[1]*pixelSize};
		
		g.setColor(Color.BLACK);
		g.fillPolygon(x, y, 3);
		
	}
	
	private int random(int r) {
		return (int)Math.floor(Math.random() * r);
	}
	
	/* Scale 함수 */
	void drawPolygon_S(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, double Scale) {
		//이동 + 신축 + (역)이동
		int random = random(3) + 1;  // 랜덤함수로 임의의 점 선택
		int randomX = 0, randomY = 0;
		if (random == 1) {
			randomX=x1; 
			randomY=y1;
		}
		if (random == 2) {
			randomX=x2; 
			randomY=y2;
		}
		if (random == 3) {
			randomX=x3; 
			randomY=y3;
		}
		double[][] a = {{Scale, 0, randomX*(1-Scale)}, {0, Scale, randomY*(1-Scale)}, { 0, 0, 1}};
		int result_first[]; int result_second[]; int result_last[];
		int[] xy_first = {x1, y1, 1}; int[] xy_second = {x2, y2, 1}; int[] xy_last = {x3, y3, 1};
		
		result_first = productMatrix(a, xy_first); 		//이동
		result_second = productMatrix(a, xy_second); 	//신축
		result_last = productMatrix(a, xy_last); 		//역이동
		
		int x[] = {result_first[0]*pixelSize, result_second[0]*pixelSize, result_last[0]*pixelSize};
		int y[] = {result_first[1]*pixelSize, result_second[1]*pixelSize, result_last[1]*pixelSize};
		
		g.setColor(Color.YELLOW);
		g.fillPolygon(x, y, 3);
		
	}
	
	/* Rotation 함수 */
	void drawPolygon_R(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, double Rotation) {
		int random = random(3) + 1;
		int randomX = 0, randomY = 0; //랜덤
		if (random == 1) {
			randomX=x1; 
			randomY=y1;
		}
		if (random == 2) {
			randomX=x2; 
			randomY=y2;
		}
		if (random == 3) {
			randomX=x3; 
			randomY=y3;
		}
		
		double[][] first_a = {{1, 0, randomX}, {0, 1, randomY}, {0, 0, 1}};
		double[][] a = {{Math.cos(Math.toRadians(Rotation)),- Math.sin(Math.toRadians(Rotation)), 0}, {Math.sin(Math.toRadians(Rotation)), Math.cos(Math.toRadians(Rotation)), 0}, {0, 0, 1}};
		int result_first[]; int result_second[]; int result_last[];
		int[] xy_first = {x1, y1, 1}; int[] xy_second = {x2, y2, 1}; int[] xy_last = {x3, y3, 1};
		
		double[][] s = productMatrix2(first_a, a);
		
		result_first = productMatrix(s, xy_first); 		//이동
		result_second = productMatrix(s, xy_second);	//회전
		result_last = productMatrix(s, xy_last);		//역이동
		
		int x[] = {result_first[0]*pixelSize, result_second[0]*pixelSize, result_last[0]*pixelSize};
		int y[] = {result_first[1]*pixelSize, result_second[1]*pixelSize, result_last[1]*pixelSize};
		
		g.setColor(Color.GREEN);
		g.fillPolygon(x, y, 3);
		
	}
	
	/* (3*3)*(3*3) 매트릭스 연산 함수 */
    public double[][] productMatrix2(double[][] A, double[][] B) {
        double[][] answer = new double[A.length][B[0].length];
         
        for(int i=0; i<answer.length;i++){
            for(int j=0; j<answer[0].length;j++){  
                for(int k=0; k<A[0].length;k++){
                    answer[i][j] += A[i][k]*B[k][j];
                }     
            }
        } 
        return answer;
    }
	
	/* (3*3)*(3*1) 매트릭스 연산 함수 */
	public int[] productMatrix(double[][] A, int[] B) {
		int[] answer = new int[A.length];

		for(int i=0; i<answer.length; i++) {
			for(int j=0; j<B.length; j++ ) {
				answer[i] += A[i][j] * B[j];
			}
		}
		
		return answer;
	}

	// gird 그리는 함수
	private void paintCoordinateSystem(Graphics g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		// coordinate grid
		g.setColor(Color.BLACK);

		for (int i = 0; i <= getWidth(); i += pixelSize) {
			g.drawLine(i, 0, i, getHeight());
			g.drawLine(0, i, getWidth(), i);
		}

		g.setColor(Color.RED);
		g.drawLine(centerX, 0, centerX, getHeight());
		g.drawLine(0, centerY, getWidth(), centerY);
		g.drawLine(centerX - 5, 10, centerX, 0);
		g.drawLine(centerX + 5, 10, centerX, 0);
		g.drawLine(getWidth() - 10, centerY - 5, getWidth(), centerY);
		g.drawLine(getWidth() - 10, centerY + 5, getWidth(), centerY);
		g.drawString("x", getWidth() - 20, centerY - 10);
		g.drawString("y", centerX + 10, 20);
		for (int i = -170; i <= 190; i += 10) {
			g.drawLine(centerX - 4, centerY + i, centerX + 4, centerY + i);
		}
		for (int i = -240; i <= 220; i += 10) {
			g.drawLine(centerX + i, centerY - 4, centerX + i, centerY + 4);
		}

		for (int i = -150; i <= 150; i += 50) {
			g.drawLine(centerX - 10, centerY + i, centerX + 10, centerY + i);
		}
		for (int i = -200; i <= 200; i += 50) {
			g.drawLine(centerX + i, centerY - 10, centerX + i, centerY + 10);
		}
		g.drawString("5", centerX - 25, centerY - 45);
		g.drawString("5", centerX + 45, centerY - 15);

		// translate origin and orientation
		g.translate(centerX, centerY);
		((Graphics2D) g).scale(1.0, -1.0);

		// center
		g.fillOval(-pixelSize / 2, -pixelSize / 2, pixelSize, pixelSize);
	}
}
