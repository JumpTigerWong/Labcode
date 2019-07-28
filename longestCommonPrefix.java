public String longestCommonPrefix(String[] strs) {
		if(strs.length==0) {
			return "";
		}else {
			String result=strs[0];
			for(int i=1;i<strs.length;i++) {
				int j=0;
				for(;j<strs[i].length()&&j<result.length();j++) {
					if(result.charAt(j)==strs[i].charAt(j)) {
						continue;
					}else {
						break;
					}
				}
				result=result.substring(0,j);
			}
			return result;
		}
	}
