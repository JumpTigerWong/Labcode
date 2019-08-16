public TreeNode sortedArrayToBST(int[] nums) {
		return sortedArrayToBST(nums,0,nums.length);
	}
	
	public TreeNode sortedArrayToBST(int []nums,int start,int end) {
		if(end==start) {
			return null;
		}
		int mid=(start+end)/2;
		TreeNode root=new TreeNode(nums[mid]);
		root.left=sortedArrayToBST(nums,start,mid);
		root.right=sortedArrayToBST(nums,mid+1,end);
		return root;
	}
