/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null) {
			return head;
		}
		ListNode node1=head;
		ListNode node2=head;
		while(node2.next!=null) {
			if(node1.val==node2.next.val) {
				node2=node2.next;
			}else {
				node1.next=node2.next;
				node1=node1.next;
			}
		}
        node1.next=null;
		return head;
	
    }
}
