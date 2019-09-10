public int numPrimeArrangements(int n) {
       if(n==1||n==2){
           return 1;
       }
		int numOfPrimerNumber=0;
		for(int i=2;i<=n;i++) {
			numOfPrimerNumber+=judgePrimeNumber(i);
		}
		long temp=fullPermutation(numOfPrimerNumber)*fullPermutation(n-numOfPrimerNumber);
		long restemp=temp%1000000007;
       int res=(int)restemp;
		return res;
		
		
	}
	
	public long fullPermutation(int num) {
		long res=1;
		while(num>0) {
			res*=num;
            res%=1000000007;
			num--;
		}
		return res%1000000007;
	}
	
	public int judgePrimeNumber(int n) {
		if(n==2||n==3) {
			return 1;
		}
		for(int i=2;i<=(n/2);i++) {
			if(n%i==0) {
				return 0;
			}
		}
		return 1;
	}
