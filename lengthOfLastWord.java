public int lengthOfLastWord(String s) {
		int i=s.length();
		if(i==0) {
			return 0;
		}
		i--;
		int res=0;
		boolean flag=false;
		for(;i>=0;i--) {
			if(s.charAt(i)==' '&&!flag) {
				continue;
			}if(s.charAt(i)==' '&&flag){
                break;
            }else {
				flag=true;
				res++;
			}
		}
		return res;
    }
