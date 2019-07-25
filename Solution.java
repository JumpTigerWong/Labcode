package leetcode;

public class Solution {
	 public int reverse(int x) {
		 int intmax=Integer.MAX_VALUE;
		 int intmin=Integer.MIN_VALUE;
		 int result=0;
	        while(x!=0){
	            int temp=result*10+x%10;
	            if(result>intmax/10||result<intmin/10){
	                return 0;
	            }
	            result=temp;
	            x/=10;
	        }
	        return result;    
	 }
	 
	 
}
