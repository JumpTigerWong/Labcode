public int climbStairs(int n) {
		int []meno=new int[n+1];
		return wayOfClimb(0,n,meno);
	}
  public int wayOfClimb(int i,int n,int meno[]) {
		if(i>n) {
			return 0;
		}
		if(i==n) {
			return 1;
		}
		if(meno[i]>0) {
			return meno[i];
		}
		return wayOfClimb(i+1,n,meno)+wayOfClimb(i+2,n,meno);
	}

//斐波那契数法
public class Solution {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }
}
//作者：LeetCode
//链接：https://leetcode-cn.com/problems/two-sum/solution/pa-lou-ti-by-leetcode/
//来源：力扣（LeetCode）
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

//表达式法，通过斐波那契公式计算出结果
public class Solution {
    public int climbStairs(int n) {
        double sqrt5=Math.sqrt(5);
        double fibn=Math.pow((1+sqrt5)/2,n+1)-Math.pow((1-sqrt5)/2,n+1);
        return (int)(fibn/sqrt5);
    }
}

//作者：LeetCode
//链接：https://leetcode-cn.com/problems/two-sum/solution/pa-lou-ti-by-leetcode/
//来源：力扣（LeetCode）
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
