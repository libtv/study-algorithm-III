import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main extends JFrame implements ActionListener {
	JPanel pnlUp;
	JPanel pnlDown = new JPanel(new GridLayout(1, 2));
	DrawArea area = new DrawArea();
	DrawArea3 area2 = new DrawArea3();
	DrawArea2 area5 = new DrawArea2();
	DrawArea5 area6 = new DrawArea5();
	boolean visible = false;
	
	Main() {
		setSize(800, 600);
		createMenu();
		setVisible(true);
		
	}
	
	void createMenu() {
		JMenuBar mb = new JMenuBar(); 
		JMenu screenMenu = new JMenu("Line/Circle");
		JMenu line = new JMenu("Line");
		JMenu circle = new JMenu("Circle");
		JMenu homogeneous = new JMenu("동치좌표계");
		JMenu clipping = new JMenu("Line clipping");
		JMenu polygon = new JMenu("X-Y polygon");
		
		JMenuItem dda_line = new JMenuItem("DDA_line");
		JMenuItem dda_circle = new JMenuItem("DDA_circle");
		JMenuItem bresenham_line = new JMenuItem("Bresenham_line");
		JMenuItem Bresenham_eclipse = new JMenuItem("Bresenham_eclipse");
		JMenuItem x_y_polygon = new JMenuItem("X-Y polygon");
		JMenuItem homogeneous_0 = new JMenuItem("동치좌표계를 이용한 이동, 신축, 회전");
		JMenuItem homogeneous_1 = new JMenuItem("[임의의점]동치좌표계를 이용한 이동, 신축, 회전");
		JMenuItem cohen = new JMenuItem("Cohen-Sutherland");
		JMenuItem liang = new JMenuItem("LiangBarsky");
		

		screenMenu.add(line);
		screenMenu.add(circle);
		
		line.add(dda_line);
		line.add(bresenham_line);
		
		circle.add(dda_circle);
		circle.add(Bresenham_eclipse);

		mb.add(screenMenu);
		mb.add(polygon);
		
		polygon.add(x_y_polygon);
		
		mb.add(homogeneous);
		
		homogeneous.add(homogeneous_0);
		homogeneous.add(homogeneous_1);
		
		mb.add(clipping);

		clipping.add(cohen);
		clipping.add(liang);
		
		setJMenuBar(mb);
		
		/* 클릭 리스너 */
		dda_line.addActionListener(this);
		dda_circle.addActionListener(this);
		bresenham_line.addActionListener(this);
		Bresenham_eclipse.addActionListener(this);
		x_y_polygon.addActionListener(this);
		homogeneous_0.addActionListener(this);
		homogeneous_1.addActionListener(this);
		cohen.addActionListener(this);
		liang.addActionListener(this);
		
		pnlUp = new JPanel(new BorderLayout());
		add(pnlUp, BorderLayout.CENTER);
		
		setTitle("컴퓨터그래픽스 프로젝트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Main();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		
		if (str.equals("DDA_line")) { 
			pnlUp.remove(area2);
			pnlUp.remove(area5);
			pnlUp.remove(area6);
			pnlUp.add(area);
			pnlUp.revalidate();
			pnlUp.repaint();
			area.draw("dda");
			
		} else if (str.equals("DDA_circle")) { 
			pnlUp.remove(area2);
			pnlUp.remove(area5);
			pnlUp.remove(area6);
			pnlUp.add(area);
			pnlUp.revalidate();
			pnlUp.repaint();
			area.draw("dda_circle");
			
		} else if (str.equals("Bresenham_line")) { 
			pnlUp.remove(area2);
			pnlUp.remove(area5);
			pnlUp.remove(area6);
			pnlUp.add(area);
			pnlUp.revalidate();
			pnlUp.repaint();
			area.draw("bres");
			
		} else if (str.equals("Bresenham_eclipse")) { 
			pnlUp.remove(area2);
			pnlUp.remove(area5);
			pnlUp.remove(area6);
			pnlUp.add(area);
			pnlUp.revalidate();
			pnlUp.repaint();
			area.draw("bres_eclipse");
			
		} else if (str.equals("X-Y polygon")) { 
			pnlUp.remove(area);
			pnlUp.remove(area5);
			pnlUp.remove(area6);
			pnlUp.add(area2);
			pnlUp.revalidate();
			pnlUp.repaint();
			
			if (!visible) {
				area2.draw("");
				visible = !visible;
			} else {
				area2.draw("polygon");
				visible = !visible;
			}
			
		} else if (str.equals("동치좌표계를 이용한 이동, 신축, 회전")) {
			Three_Algorithms myWin3 = new Three_Algorithms();
			myWin3.setTitle("동치좌표계");

			myWin3.setSize(800, 600);
			myWin3.setVisible(true);
			
		} else if (str.equals("[임의의점]동치좌표계를 이용한 이동, 신축, 회전")) {
			//해당 알고리즘은 이동, 신축, 회전할 때 사용하는 (x, y) 는 동일하다고 가정
			Four_Algorithms myWin4 = new Four_Algorithms();
			myWin4.setTitle("임의의 점에 대한 합성변환");

			myWin4.setSize(800, 600);
			myWin4.setVisible(true);
			
		} else if (str.equals("Cohen-Sutherland")) {
			pnlUp.remove(area);
			pnlUp.remove(area2);
			pnlUp.remove(area6);
			pnlUp.add(area5);
			pnlUp.revalidate();
			pnlUp.repaint();
			
		} else if (str.equals("LiangBarsky")) {
			pnlUp.remove(area);
			pnlUp.remove(area2);
			pnlUp.remove(area5);
			pnlUp.add(area6);
			pnlUp.revalidate();
			pnlUp.repaint();
			
		} 
	}
}



