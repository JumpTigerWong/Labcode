public boolean isValid(String s) {
		if (s.length()==0) {
			return true;
		}
		Stack st=new Stack();
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)=='('||s.charAt(i)=='['||s.charAt(i)=='{') {
				st.push(s.charAt(i));
			}else if(!st.isEmpty()&&s.charAt(i)==')'&&(char)st.peek()=='(') {
				st.pop();
				continue;
			}else if(!st.isEmpty()&&s.charAt(i)==']'&&(char)st.peek()=='[') {
				st.pop();
				continue;
			}else if(!st.isEmpty()&&s.charAt(i)=='}'&&(char)st.peek()=='{') {
				st.pop();
				continue;
			}else {
				return false;
			}
		}
		return st.isEmpty();
	}
