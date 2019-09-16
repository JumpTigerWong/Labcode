package linkedList;

import java.util.HashMap;
import java.util.Hashtable;

import linkedList.Solution.ListNode;

public class Solution {
	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null||headB==null){
            return null;
        }
		ListNode flagA=headA;
		do {
			ListNode flagB=headB;
			do {
				if(flagA==flagB) {
					return flagA;
				}else {
					flagB=flagB.next;
				}
				
			}while(flagB!=null);
            flagA=flagA.next;
		}while(flagA!=null);
		return null;
    }//暴力法
	
	public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
		if(headA==null||headB==null) {
			return null;
		}
        HashMap<ListNode, Integer> nodeOfHeadA=new HashMap<ListNode, Integer>();
        ListNode pA=headA;
        while(pA!=null) {
        	if(!nodeOfHeadA.containsKey(pA)) {
        		nodeOfHeadA.put(pA,pA.val);
        	}
        	pA=pA.next;
        }
        ListNode pB=headB;
        while(pB!=null) {
        	if(nodeOfHeadA.containsKey(pB)) {
        		return pB;
        	}
        }
        return null;
    }//哈希表法		

	public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
		if(headA==null||headB==null) {
			return null;
		}
		ListNode pA=headA,pB=headB;
		while(pA!=pB) {
			if(pA==null) {
				pA=headB;
			}else {
				pA=pA.next;
			}
			if(pB==null) {
				pB=headA;
			}else {
				pB=pB.next;
			}
		}
		return pA;
    }//双指针巧解法
	
}
