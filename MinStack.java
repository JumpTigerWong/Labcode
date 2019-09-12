
public class MinStack {
	
	private Stack<Integer> data;
	
	private Stack<Integer> helper;
	
	public MinStack() {
		data=new Stack();
		helper=new Stack();
	}

	public void push(int x) {
		data.add(x);
		if(helper.isEmpty()||helper.peek()>=x) {
			helper.add(x);
		}
		helper.add(helper.peek());
		
	}

	public void pop() {
		if(!data.isEmpty()) {
			data.pop();
			helper.pop();
		}
	}

	public int top() {
		if(!data.isEmpty()) {
			return data.peek();
		}
		throw new RuntimeException("栈中元素为空，此操作非法");
	}

	public int getMin() {
		if(!helper.isEmpty()) {
			return helper.peek();
		}
		throw new RuntimeException("栈中元素为空，此操作非法");
	}
}


******************************************************************************************************************************
class MinStack {

    private int size;
    private int top;
    private int list[];
    private int min[];

    public MinStack() {
        size=100;
        list = new int[size];
        min = new int[size];
        top=0;
    }


    public void push(int x) {
        if(top==size){
            int temp1[]=new int[size+size/2];
            int temp2[]=new int[size+size/2];
            System.arraycopy(list,0,temp1,0,size);
            System.arraycopy(min,0,temp2,0,size);
            size=size+size/2;
            list=temp1;
            min=temp2;
        }
        list[top]=x;
        if(top==0)
            min[top]=x;
        else 
            min[top]=Math.min(min[top-1],x);
        top++;
    }

    public void pop() {
        if(top>=0){
            top--;
        }
    }

    public int top() {
        return list[top-1];
    }

    public int getMin() {
        return min[top-1];
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
