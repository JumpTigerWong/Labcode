public int maxSubArray(int[] nums) {
		int ans=nums[0];
		int num=0;
		for(int i=0;i<nums.length;i++) {
			if(num>=0) {
				num+=nums[i];
			}else {
				num=nums[i];
			}
			ans=Math.max(num, ans);
		}
		return ans;
	}
