public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> triangle=new ArrayList<List<Integer>>();
		if(numRows==0) {
			return triangle;
		}
		triangle.add(new ArrayList<>());
		triangle.get(0).add(1);
		for(int i=1;i<numRows;i++) {
			List<Integer> row=new ArrayList<>();
			List<Integer> prerow=triangle.get(i-1);
			row.add(1);
			for(int j=1;j<i;j++) {
				row.add(prerow.get(j-1)+prerow.get(j));
			}
			row.add(1);
		}
		return triangle;
	}
