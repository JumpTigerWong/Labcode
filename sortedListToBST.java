/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
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
    public ListNode findMiddleNode(ListNode head) {
		ListNode fast=head;
		ListNode slow=head;
		ListNode pre=null;
		while(fast!=null&&fast.next!=null) {
			pre=slow;
			slow=slow.next;
			fast=fast.next.next;
		}
		if(pre!=null) {
			pre.next=null;
		}
		return slow;
	}

	public TreeNode sortedListToBST(ListNode head) {
		if(head==null) {
			return null;
		}
		ListNode midNode=findMiddleNode(head);
		TreeNode node=new TreeNode(midNode.val);
		if(head==midNode) {
			return node;
		}
		node.left=sortedListToBST(head);
		node.right=sortedListToBST(midNode.next);
		return node;
	}
}
