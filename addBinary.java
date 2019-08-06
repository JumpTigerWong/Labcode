public String addBinary(String a, String b) {
		String res="";
		int i=a.length()-1;
		int j=b.length()-1;
		int count=Math.max(i, j);
		int carry=0;
		for(int x=count;x>=0;x--) {
			int intAt=0;
			if(i>=0&&j>=0) {
				intAt=a.charAt(i)-'0'+b.charAt(j)-'0'+carry;
			}else if(i<0&&j>=0) {
				intAt=b.charAt(j)-'0'+carry;
			}else if(i>=0&&j<0){
				intAt=a.charAt(i)-'0'+carry;
			}

			if(intAt==3) {
				res="1"+res;
				carry=1;
			}else if(intAt==2) {
				res="0"+res;
				carry=1;
			}else if(intAt==1) {
				res="1"+res;
				carry=0;
			}else {
				res="0"+res;
				carry=0;
			}
			i--;
			j--;
		}
        if(carry==1)
        res="1"+res;
		return res;
	}
