public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> res=new ArrayList<>();
		DFS(root,0,res);
		return res;
	}
	
	public void DFS(TreeNode root,int level,List<List<Integer>> res) {
		if(root==null) {
			return ;
		}
		if(res.size()<=level) {
			res.add(0,new ArrayList<>());
		}
		res.get(res.size()-1-level).add(root.val);
		DFS(root.left,level+1,res);
		DFS(root.right,level+1,res);
	}
