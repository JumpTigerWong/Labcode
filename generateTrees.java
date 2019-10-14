/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<TreeNode> generateTrees(int n) {
		if(n==0) {
			return new LinkedList<TreeNode>();
		}
		return generate_Tree(1,n);
	}
	
	public LinkedList<TreeNode> generate_Tree(int start,int end){
		LinkedList<TreeNode> all_tree=new LinkedList<TreeNode>();
		if(start>end) {
			all_tree.add(null);
			return all_tree;
		}
		
		for(int i=start;i<=end;i++) {
			LinkedList<TreeNode> left_tree=generate_Tree(start,i-1);
			LinkedList<TreeNode> right_tree=generate_Tree(i+1,end);
			for(TreeNode l:left_tree) {
				for(TreeNode r:right_tree) {
					TreeNode current_tree=new TreeNode(i);
					current_tree.left=l;
					current_tree.right=r;
					all_tree.add(current_tree);
				}
			}
		}
		return all_tree;
	}
}
