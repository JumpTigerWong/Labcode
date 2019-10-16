public TreeNode invertTree(TreeNode root) {
		if(root==null) {
			return null;
		}
		TreeNode left=invertTree(root.left);
		TreeNode right=invertTree(root.right);
		root.right=left;
		root.left=right;
		return root;
	}
	//直接换节点，比换节点的值来的更快。
