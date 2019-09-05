class Solution {
    public int maxProfit(int[] prices) {
        
		int res=0;
		int fastIndex=0;
		int slowIndex=0;
		for(;fastIndex<prices.length;fastIndex++) {
			if(prices[fastIndex]<prices[slowIndex]) {
				slowIndex=fastIndex;
			}else {
				res=Math.max(res, prices[fastIndex]-prices[slowIndex]);
			}
		}
		return res;
	
    }
}
