public boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        sum-=root.val;
        if(root.left==null&&root.right==null) {
			return sum==0;
		}
		return hasPathSum(root.left,sum)||hasPathSum(root.right,sum);
    }
   **************************************************************************************************************************** 
    public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null)
      return false;

    LinkedList<TreeNode> node_stack = new LinkedList();
    LinkedList<Integer> sum_stack = new LinkedList();
    node_stack.add(root);
    sum_stack.add(sum - root.val);

    TreeNode node;
    int curr_sum;
    while ( !node_stack.isEmpty() ) {
      node = node_stack.pollLast();
      curr_sum = sum_stack.pollLast();
      if ((node.right == null) && (node.left == null) && (curr_sum == 0))
        return true;

      if (node.right != null) {
        node_stack.add(node.right);
        sum_stack.add(curr_sum - node.right.val);
      }
      if (node.left != null) {
        node_stack.add(node.left);
        sum_stack.add(curr_sum - node.left.val);
      }
    }
    return false;
  }

作者：LeetCode
链接：https://leetcode-cn.com/problems/path-sum/solution/lu-jing-zong-he-by-leetcode/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
