 public void merge(int[] nums1, int m, int[] nums2, int n) {
		if(m==0) {
			for(int i=0;i<n-1;i++) {
				nums1[i]=nums2[i];
			}
		}
		int point1_1=m-1;
		int point1_2=m+n-1;
		int point2_1=n-1;
		while(point1_1>=0&&point2_1>=0) {
			if(nums2[point2_1]>nums1[point1_1]) {
				nums1[point1_2]=nums2[point2_1];
				point1_2--;
				point2_1--;
			}else {
				nums1[point1_2]=nums1[point1_1];
				point1_2--;
				point1_1--;
			}
		}
		System.arraycopy(nums2, 0, nums1, 0, point2_1 + 1);
	
    }
