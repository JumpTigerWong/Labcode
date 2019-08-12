public int maxDepth(TreeNode root) {
		if(root==null) {
			return 0;
		}
		int depth=1;
		return figureDepth(root,depth);
	}
	
	public int figureDepth(TreeNode root,int depth) {
		if(root.left==null&&root.right==null) {
			return depth;
		}
		if(root.left==null&&root.right!=null) {
			return figureDepth(root.right, ++depth);
		}
		if(root.left!=null&&root.right==null) {
			return figureDepth(root.left, ++depth);
		}
		return Math.max(figureDepth(root.left, ++depth),figureDepth(root.right, depth++));
	}
*******************************************************************************************************************************  
  class Solution {
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    } else {
      int left_height = maxDepth(root.left);
      int right_height = maxDepth(root.right);
      return java.lang.Math.max(left_height, right_height) + 1;
    }
  }
}

作者：LeetCode
链接：https://leetcode-cn.com/problems/two-sum/solution/er-cha-shu-de-zui-da-shen-du-by-leetcode/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
