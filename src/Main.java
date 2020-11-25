import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("::Computer Graphics Term Project::");
		System.out.println("::HBUN 20177115 KIM-JUN-HO::");
		
		
		while(true){
			System.out.println("");
			System.out.println("Enter value 1,		DDA, Bresenham Algorithms");
			System.out.println("Enter value 2,		X-Y polygonal scanline Algorithms");
			System.out.println("Enter value 3,		동차좌표계를 이용한 이동, 신축, 회전");
			System.out.println("Enter value 4,		동차좌표계를 이용한 이동, 신축, 회전 [임의의 점에 대한 합성변환]");
			System.out.println("Enter value 5,		Cohen-Sutherland Algorithms");
			System.out.println("Enter value 6,		LiangBarsky Algorithms");
			System.out.println("Enter value 9,		Exit System");
			
			System.out.print("Enter your choice -> ");
			int choice = scan.nextInt();

			switch(choice){
			case 1: 
				One_Algorithms myWin = new One_Algorithms();
				myWin.setTitle("DDA, Bersenham Algorithms");

				myWin.setSize(800, 600);
				myWin.setVisible(true);
			break;
			case 2:
				Two_Algorithms myWin2 = new Two_Algorithms();
				myWin2.setTitle("X-Y polygonal scanline Algorithms");

				myWin2.setSize(800, 600);
				myWin2.setVisible(true);
			break;
			case 3:
				//해당 알고리즘은 이동, 신축, 회전할 때 사용하는 (x, y) 는 동일하다고 가정
				Three_Algorithms myWin3 = new Three_Algorithms();
				myWin3.setTitle("동차좌표계");

				myWin3.setSize(800, 600);
				myWin3.setVisible(true);
			break;
			case 4:
				//해당 알고리즘은 이동, 신축, 회전할 때 사용하는 (x, y) 는 동일하다고 가정
				Four_Algorithms myWin4 = new Four_Algorithms();
				myWin4.setTitle("임의의 점에 대한 합성변환");

				myWin4.setSize(800, 600);
				myWin4.setVisible(true);
			break;
			case 5:
				Five_Algorithms myWin5 = new Five_Algorithms();
				myWin5.setTitle("Cohen-Sutherland Algorithms");

				myWin5.setSize(800, 600);
				myWin5.setVisible(true);
			break;
			case 6:
				Six_Algorithms myWin6 = new Six_Algorithms();
				myWin6.setTitle("LiangBarsky Algorithms");

				myWin6.setSize(800, 600);
				myWin6.setVisible(true);
			break;
			case 9: System.out.println("Exiting the application");
			System.exit(0);
			default: System.out.println("Incorrect input!!! Please re-enter choice from our menu");
			}
		}

	}

}