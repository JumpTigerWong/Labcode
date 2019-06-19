package gameUi;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class GameUi {
	public int length = 800;
	public int width = 600;
	public String path = "images/pieces1/";
	public static JButton[][] jb = new JButton[16][10];
	public JFrame game = new JFrame("连连看游戏");
	public JButton gs = new JButton("游戏开始");
	public JButton resort = new JButton("重新排列");
	public JButton gamestop = new JButton("游戏暂停");
	public JButton tips = new JButton("提示");
	public JPanel jp = (JPanel) game.getContentPane(); // 从JFrame 里面创建一个JPanel
	public ImageIcon[] pic = new ImageIcon[20];
	public Border originBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	public boolean matched = false;// 是否消除
	public int lastchoose[] = new int[4];
	public boolean lastclick = false;
	public int clickcount = 0;
	public static JProgressBar jpb = new JProgressBar(0, 300);
	public static long timestart;
	public static int barvalue;
	Graphics g;

	public class baseEvents implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "游戏开始":
				gamestart();
				break;
			case "游戏暂停":
				stopgame();
				break;
			case "继续游戏":
				gamecontinue();
				break;
			case "提示":
				Tips();
				break;
			case "重新排列":
				reSort();
				break;
			}
		}
	}

	GameUi() {
		initialize();
		game.setAlwaysOnTop(true);
		game.setVisible(true);
	}

	public class MouseEvent implements MouseListener {
		int row, col;
		boolean color = false;

		MouseEvent(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			if (lastclick && lastchoose[0] != -1) {
				jb[lastchoose[0]][lastchoose[1]].setBackground(Color.blue);
				color = false;
			}
			if (color == false) {// color false 表示蓝色
				jb[row][col].setBackground(Color.red);
				color = !color;
				lastclick = true;
			} else {
				jb[row][col].setBackground(Color.blue);
				color = !color;
				lastclick = false;
			}
			if (lastchoose[0] != -1) {
				lastchoose[2] = row;
				lastchoose[3] = col;
			} else {
				lastchoose[0] = row;
				lastchoose[1] = col;
			}
			if (lastchoose[3] != -1
					&& Control.match(lastchoose[0] + 1, lastchoose[1] + 1, lastchoose[2] + 1, lastchoose[3] + 1) != 0) {
				jb[lastchoose[0]][lastchoose[1]].setVisible(false);
				jb[lastchoose[2]][lastchoose[3]].setVisible(false);
				Control.database[lastchoose[0] + 1][lastchoose[1] + 1] = -1;
				Control.database[lastchoose[2] + 1][lastchoose[3] + 1] = -1;
			}
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			game.setVisible(true);
			int ret;
			ret = Control.match(lastchoose[0] + 1, lastchoose[1] + 1, lastchoose[2] + 1, lastchoose[3] + 1);
			if (lastchoose[0] != -1 && lastchoose[2] != -1) {
				if (ret == 1) {
					draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
							jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
				} else if (ret == 2) {
					int index_X = Control.index[2] - 1;
					int index_Y = Control.index[3] - 1;
					draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
							jb[index_X][index_Y].getX(), jb[index_X][index_Y].getY());
					draw_line(jb[index_X][index_Y].getX(), jb[index_X][index_Y].getY(),
							jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
				} else if (ret == 3) {
					int index_X1 = Control.index[0] - 1;
					int index_Y1 = Control.index[1] - 1;
					int index_X2 = Control.index[2] - 1;
					int index_Y2 = Control.index[3] - 1;
					if (Control.index[0] < 1 && Control.index[2] < 1) {
						index_X1++;
						index_X2++;
						draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
								jb[index_X2][index_Y2].getX() - 40, jb[index_X2][index_Y2].getY());
						draw_line(jb[index_X2][index_Y2].getX() - 40, jb[index_X2][index_Y2].getY(),
								jb[index_X1][index_Y1].getX() - 40, jb[index_X1][index_Y1].getY());
						draw_line(jb[index_X1][index_Y1].getX() - 40, jb[index_X1][index_Y1].getY(),
								jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
					} else if (Control.index[0] > 16 && Control.index[2] > 16) {
						index_X1--;
						index_X2--;
						draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
								jb[index_X2][index_Y2].getX() + 40, jb[index_X2][index_Y2].getY());
						draw_line(jb[index_X2][index_Y2].getX() + 40, jb[index_X2][index_Y2].getY(),
								jb[index_X1][index_Y1].getX() + 40, jb[index_X1][index_Y1].getY());
						draw_line(jb[index_X1][index_Y1].getX() + 40, jb[index_X1][index_Y1].getY(),
								jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
					} else if (Control.index[1] < 1 && Control.index[3] < 1) {
						index_Y1++;
						index_Y2++;
						draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
								jb[index_X2][index_Y2].getX(), jb[index_X2][index_Y2].getY() - 40);
						draw_line(jb[index_X2][index_Y2].getX(), jb[index_X2][index_Y2].getY() - 40,
								jb[index_X1][index_Y1].getX(), jb[index_X1][index_Y1].getY() - 40);
						draw_line(jb[index_X1][index_Y1].getX(), jb[index_X1][index_Y1].getY() - 40,
								jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
					} else if (Control.index[1] > 10 && Control.index[3] > 10) {
						index_Y1--;
						index_Y2--;
						draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
								jb[index_X2][index_Y2].getX(), jb[index_X2][index_Y2].getY() + 40);
						draw_line(jb[index_X2][index_Y2].getX(), jb[index_X2][index_Y2].getY() + 40,
								jb[index_X1][index_Y1].getX(), jb[index_X1][index_Y1].getY() + 40);
						draw_line(jb[index_X1][index_Y1].getX(), jb[index_X1][index_Y1].getY() + 40,
								jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
					} else {
						draw_line(jb[lastchoose[0]][lastchoose[1]].getX(), jb[lastchoose[0]][lastchoose[1]].getY(),
								jb[index_X2][index_Y2].getX(), jb[index_X2][index_Y2].getY());
						draw_line(jb[index_X2][index_Y2].getX(), jb[index_X2][index_Y2].getY(),
								jb[index_X1][index_Y1].getX(), jb[index_X1][index_Y1].getY());
						draw_line(jb[index_X1][index_Y1].getX(), jb[index_X1][index_Y1].getY(),
								jb[lastchoose[2]][lastchoose[3]].getX(), jb[lastchoose[2]][lastchoose[3]].getY());
					}
				}else {
					initlastchoose();
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ImageIcon img = new ImageIcon("images/bg_image1.jpg");
				JLabel background = new JLabel(img);
				game.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
				background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
				initlastchoose();
			}
			//initlastchoose();
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	void initialize() {
		game.setBounds(305, 150, length, width);
		game.setResizable(false);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp.setOpaque(false); // JPanel 透明模式
		ImageIcon img = new ImageIcon("images/bg_image1.jpg"); // 创建一个图片路径
		JLabel background = new JLabel(img); // 创建个带背景图片的JLabel
		game.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		jp.setLayout(null);
		gs.setBounds(20, 20, 80, 40);
		resort.setBounds(20, 80, 80, 40);
		gamestop.setBounds(20, 140, 80, 40);
		tips.setBounds(20, 200, 80, 40);
		gs.addActionListener(new baseEvents());
		resort.addActionListener(new baseEvents());
		gamestop.addActionListener(new baseEvents());
		tips.addActionListener(new baseEvents());
		jp.add(gs);
		jp.add(gamestop);
		jp.add(resort);
		jp.add(tips);
		jp.add(jpb);
		jp.setBackground(Color.GREEN);
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 10; j++) {
				jb[i][j] = new JButton();
				jb[i][j].setBounds(180 + i * 36, 60 + j * 36, 36, 36);
				jb[i][j].setBackground(Color.ORANGE);
				jp.add(jb[i][j]);
				jb[i][j].setBackground(Color.BLUE);
				jb[i][j].setOpaque(true);
				jb[i][j].setBorder(originBorder);
				jb[i][j].addMouseListener(new MouseEvent(i, j));
				jb[i][j].setVisible(false);
			}
		}

	}

	void initpic() {
		for (int i = 0; i < 20; i++) {
			String str = path + (i + 1) + ".png";
			pic[i] = new ImageIcon(str);
		}
	}

	void draw_line(int prex, int prey, int curx, int cury) {// 连线操作,button的getX()函数和getY()函数是按钮的左上角的点的坐标
		g = game.getGraphics();
		Graphics2D dg = ((Graphics2D) g);
		dg.setStroke(new BasicStroke(3.0F));
		dg.setColor(Color.red);
		dg.drawLine(prex + 18, prey + 36, curx + 18, cury + 36);
		// game.setVisible(true);

	}

	void initGamePic() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 10; j++) {
				jb[i][j].setVisible(true);
				jb[i][j].setIcon(pic[Control.database[i + 1][j + 1]]);
				jb[i][j].setBackground(Color.blue);
			}
		}
		jpb.setBounds(180, 450, 550, 30);
	}

	void initlastchoose() {
		for (int i = 0; i < 4; i++) {
			lastchoose[i] = -1;
		}
	}

	void gamestart() {
		initlastchoose();
		initpic();
		Control.randomnum();
		initGamePic();
		timestart = System.currentTimeMillis();
		Control.tocount_time();
		game.setVisible(true);
	}

	void stopgame() {
		for(int i=0;i<16;i++) {
			for(int j=0;j<10;j++) {
				jb[i][j].setVisible(false);
			}
		}
		gamestop.setText("继续游戏");
		Control.time=Control.timer();
		Control.stop=true;
	}
	
	void gamecontinue() {
		for(int i=0;i<16;i++) {
			for(int j=0;j<10;j++) {
				if(Control.database[i+1][j+1]!=-1) {
					jb[i][j].setVisible(true);
				}
			}
		}
		gamestop.setText("游戏暂停");
		timestart=System.currentTimeMillis();
		Control.stop=false;
		Control.tocount_time();
	}

	void Tips() {
		Control.get_Tips();
		jb[Control.tip[0]][Control.tip[1]].setBackground(Color.GREEN);
		jb[Control.tip[2]][Control.tip[3]].setBackground(Color.GREEN);
	}

	void reSort() {
		Control.resort();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 10; j++) {
				if (Control.database[i + 1][j + 1] == -1) {
					continue;
				}
				jb[i][j].setVisible(true);
				jb[i][j].setIcon(pic[Control.database[i + 1][j + 1]]);
				jb[i][j].setBackground(Color.blue);
			}
		}

	}
}
