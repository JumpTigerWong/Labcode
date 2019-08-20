public int minDepth(TreeNode root) {
        
		if(root==null) {
			return 0;
		}
		if(root.left==null&&root.right==null) {
			return 1;
		}
		
		int min_num=Integer.MAX_VALUE;
		if(root.left!=null) {
			min_num=Math.min(minDepth(root.left), min_num);
		}
		if(root.right!=null) {
			min_num=Math.min(minDepth(root.right), min_num);
		}
		
		return min_num+1;
	
    }
