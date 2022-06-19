package sspLab4_1;

import java.math.BigInteger;
import java.util.Random;

public class FastMult {
	public static void  main(String[] args) {
		
		String sa = "4382948329483294312431243124123431241234431243124483";
		String sn = "3234414443124123412541431312432143124351342512431243124";
		String sk = "4382948329483294839248928423943214132431243124312431243124312832948392489328493284932849328492384982394832948392184918349184398214912843294893158905498130591";
		BigInteger a = new BigInteger(sa);
		BigInteger n = new BigInteger(sn);
		BigInteger k = new BigInteger(sk);
//		BigInteger z = a.multiply(n).divide(BigInteger.valueOf(5435));
//		BigInteger aa =  a.multiply(n);
//		BigInteger kk = a.multiply(n).multiply(k);
		System.out.println(fastPotencial(a,n,k));
	}
	
	public static BigInteger fastPotencial(BigInteger  a, BigInteger  n, BigInteger  k) {
		BigInteger res;
		if(n.compareTo(BigInteger.valueOf(0)) != 1) {
			res = BigInteger.valueOf(1);
		} else if ((n.mod(BigInteger.valueOf(2))).compareTo(BigInteger.valueOf(0)) != 1) {
			res = fastPotencial(a,n.divide(BigInteger.valueOf(2)),k).pow(2);
		}else {
			res = a.multiply(fastPotencial(a,n.subtract(BigInteger.valueOf(1)),k))  ;
		}
		
		return res.mod(k);
	}
	
	public static int fastPotencialInt(int  a, int  n, int  k) {
		int res;
		if(n == 0) {
			res = 1;
		}else if(n % 2 ==0){
			res = (int) (Math.pow(fastPotencialInt(a,n/2,k),2));
		} else {
			res = a * fastPotencialInt(a,n-1,k);
		}
		
		return res%k;
	}
}

