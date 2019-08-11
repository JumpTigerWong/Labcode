class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return isMirror(root.left,root.right);
    }
    
    public boolean isMirror(TreeNode leftNode,TreeNode rightNode) {
		if(leftNode==null&&rightNode==null) {
			return true;
		}
		if(leftNode==null||rightNode==null) {
			return false;
		}
		if(leftNode.val==rightNode.val) {
			return isMirror(leftNode.left,rightNode.right)&&isMirror(leftNode.right,rightNode.left);
		}
		return false;
	}
}
