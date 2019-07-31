public int removeDuplicates(int[] nums) {   
		if(nums.length==0) {
			return 0;
		}
		int i=0;
		int j=1;
		while(j<nums.length){
			if(nums[i]!=nums[j]) {
       i++;
				nums[i]=nums[j];
				j++;
			}else if(nums[i]==nums[j]) {
				j++;
			}
		}
		return i+1;
}
