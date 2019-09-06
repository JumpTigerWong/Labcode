public int maxProfit(int[] prices) {
       
		int ans=0;
		int res=0;
		int fastIndex=1;
		int slowIndex=0;
		for(;fastIndex<prices.length;fastIndex++) {
			if(prices[fastIndex]<prices[fastIndex-1]) {
				ans+=res;
                res=0;
				slowIndex=fastIndex;
			}else {
				res=Math.max(res, prices[fastIndex]-prices[slowIndex]);
			}
		}
		return ans+res;
	 
    }
    
   优质代码
   class Solution {
    public int maxProfit(int[] prices) {
        if (prices==null||prices.length==0)return 0;
        int pre=prices[0],maxProfit=0;
        for (int i:prices){
            if (pre<i){
                maxProfit+=i-pre;
            }
            pre=i;
        }
        return maxProfit;
    }
 }
