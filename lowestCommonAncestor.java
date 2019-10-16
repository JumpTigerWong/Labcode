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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null){
            return null;
        }
        int rNum=root.val;
        int pNum=p.val;
        int qNum=q.val;
        if(pNum>rNum&&qNum>rNum){
            return lowestCommonAncestor(root.right,p,q);
        }
        if(pNum<rNum&&qNum<rNum){
            return lowestCommonAncestor(root.left,p,q);
        }
        else{
            return root;
        }
    }
}
