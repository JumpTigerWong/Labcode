package gameUi;

import java.util.*;

public class Control {
	public static int database[][] = new int[18][12];
	public static int index[]=new int[4];//用来存中转点坐标
	public static void initdatabase() {
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < 12; j++) {
				database[i][j] = -1;
			}
		}
	}

	public static void randomnum() {//生成随机图标的序号
		initdatabase();
		Random rd = new Random();
		for (int i = 0; i < 20; i++) {
			int count = 0;
			while (count < 8) {
				int x = 1 + rd.nextInt(16);
				int y = 1 + rd.nextInt(10);
				if (database[x][y] == -1) {
					database[x][y] = i;
					count++;
				}
			}
		}
	}

	public static void printIt() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println(database[i][j]);
			}
		}
	}

	static int max(int num1, int num2) {//最大值
		if (num1 > num2) {
			return num1;
		} else {
			return num2;
		}
	}

	static int min(int num1, int num2) {//最小值
		if (num1 > num2) {
			return num2;
		} else {
			return num1;
		}
	}

	public static boolean issame(int x1, int y1, int x2, int y2) {
		if(database[x1][y1]==database[x2][y2]) {
			return true;
		}
		return false;
	}
	
	public static boolean horizon(int x1, int y1, int x2, int y2) {//水平y相等
		if(x1==x2&&y1==y2) {
			return false;
		}
		if(y1!=y2) {
			return false;
		}
		
		for (int i = min(x1, x2)+1; i < max(x1, x2); i++) {
			if (database[i][y1] != -1)
				return false;
		}
		return true;
	}

	public static boolean vertical(int x1, int y1, int x2, int y2) {//竖直x相等
		if(y1==y2&&x1==x2) {
			return false;
		}
		if(x1!=x2) {
			return false;
		}
		for (int j = min(y1, y2)+1; j < max(y1, y2); j++) {
			if (database[x1][j] != -1) {
				return false;
			}
		}
		return true;
	}

	public static boolean one_turn(int x1, int y1, int x2, int y2) {//存在一次转折
		if (horizon(x1, y1, x2, y1) && vertical(x2, y1, x2, y2)) {
			if(database[x2][y1]!=-1) {//中间点不是有效的
				return false;
			}else {
				index[2]=x2;
				index[3]=y1;
				return true;
			}
		} else if (horizon(x1, y2, x2, y2) && vertical(x1, y1, x1, y2)) {
			if(database[x1][y2]!=-1) {//中间点不是有效的
				return false;
			}else {
				index[2]=x1;
				index[3]=y2;
				return true;
			}
		}
		return false;
	}
	public static boolean two_turn(int x1,int y1,int x2,int y2) {//存在两次转折
		int i=0,j=0;
		for(i=0;i<18;i++) {
			for(j=0;j<12;j++) {
				if((i==x1&&j==y1)||(i==x2&&j==y2)) {
					continue;
				}
				if(database[i][j]!=-1) {
					continue;
				}
				if(one_turn(x1,y1,i,j)&&(horizon(i,j,x2,y2)||vertical(i,j,x2,y2))) {
					index[0]=i;
					index[1]=j;
					return true;
				}
				if(one_turn(i,j,x2,y2)&&(horizon(x1,y1,i,j)||vertical(x1,y1,i,j))) {
					index[0]=index[2];
					index[1]=index[3];
					index[2]=i;
					index[3]=j;
					return true;
				}
			}
		}
		return false;
	}

	public static int match(int x1, int y1, int x2, int y2) {
		
		if(horizon(x1,y1,x2,y2)&&issame(x1,y1,x2,y2)||vertical(x1,y1,x2,y2)&&issame(x1,y1,x2,y2)) {
			return 1;
		}
		if(one_turn(x1,y1,x2,y2)&&issame(x1,y1,x2,y2)) {
			return 2;
		}
		if(two_turn(x1,y1,x2,y2)&&issame(x1,y1,x2,y2)) {
			return 3;
		}
		return 0;
	}
	
	public static boolean judgeWin() {//判断游戏是否胜利
		for(int i=1;i<17;i++) {
			for(int j=1;j<11;j++) {
				if(database[i][j]!=-1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static int tip[]=new int [4];
	
	public static void get_Tips() {//得到提示的坐标
		for(int i=1;i<17;i++) {
			for(int j=1;j<11;j++) {
				if(database[i][j]==-1) {
					continue;
				}
				for(int m=1;m<17;m++) {
					for(int n=1;n<11;n++) {
						if(database[m][n]==-1) {
							continue;
						}
						if(match(i,j,m,n)!=0) {
							tip[0]=i-1;
							tip[1]=j-1;
							tip[2]=m-1;
							tip[3]=n-1;
						}
					}
				}
			}
		}
	}
	
	public static void swap(int x1,int y1,int x2,int y2) {
		int temp;
		temp=database[x1][y1];
		database[x1][y1]=database[x2][y2];
		database[x2][y2]=temp;
	}
	
	public static void resort() {
		Random rd1 = new Random();
		int count=10+rd1.nextInt(30);
		while(count>0) {
			int x1 = 1 + rd1.nextInt(16);
			int y1 = 1 + rd1.nextInt(10);
			int x2= 1 + rd1.nextInt(16);
			int y2= 1 + rd1.nextInt(10);
			if(database[x1][y1]==-1||database[x2][y2]==-1) {
				continue;
			}else {
				swap(x1,y1,x2,y2);
				count--;
			}
		}
	}
	
	static boolean stop;
	static class Time extends Thread{
		public void run() {
			int x=timer();
			while(x>0&&!stop) {
				x=timer();
				GameUi.jpb.setValue(x);
			}
		}
	}
	
	public static void tocount_time() {
		Time t=new Time();
		t.start();
	}
	
	static int time=300;
	public static int timer() {	
		int usetime;
		long nowtime=System.currentTimeMillis();
		usetime=time-(int)((nowtime-GameUi.timestart)/1000);
		return usetime;
	}
}
