public boolean isPalindrome(int x) {
		String str=String.valueOf(x);
		int len=str.length();
		for(int i=0;i<len/2;i++) {
			if(str.charAt(i)!=str.charAt(len-1-i)) {
				return false;
			}
		}
		return true;
	}
