package sspLab4_2;

import java.math.BigInteger;
import java.util.ArrayList;

public class CreatePrimalNumber {
	static double LOG_2 = Math.log(2.0);
	private static final int MAX_DIGITS_2 = 977;
	 private static final int MAX_DIGITS_E = 677;
	private static final BigInteger TWO = BigInteger.valueOf(2);
	private static final BigInteger ONE = BigInteger.valueOf(1);
	private static final BigInteger ZERO = BigInteger.valueOf(0);
	
	public static void  main(String[] args) {
		
		BigInteger bPmax = BigInteger.valueOf(32321);
		BigInteger ba = BigInteger.valueOf(17);
		long Pmax = 1500;
		long a = 3;
		ArrayList<BigInteger> listPrimal = new ArrayList<BigInteger>();
		for(BigInteger i : finderPrimalNumber(ba,bPmax, listPrimal)) {
			System.out.println(i);
		}
		
		
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
	
	public static boolean ferma(BigInteger p, BigInteger a) {
		BigInteger res = fastPotencial(a,p.subtract(BigInteger.valueOf(1)),p);
		if(res.compareTo(BigInteger.valueOf(1)) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public static ArrayList<BigInteger> finderPrimalNumber(BigInteger a, BigInteger Pmax, ArrayList<BigInteger> listPrimal) {
		BigInteger k1 = BigInteger.valueOf(Math.round(logBigInteger(Pmax.divide(TWO))/logBigInteger(a)));
		BigInteger p11 = ZERO;
		BigInteger p22 = ZERO;
		BigInteger i = ZERO;
		BigInteger a1 = ONE;
		while(!(i.compareTo(k1) == 0)) {
			a1 = a1.multiply(a);
			
			i = i.add(ONE);
		}
		
		p11 = a1;
		p22 = a1;
		p11 = p11.multiply(TWO).add(ONE);
		p22 = p22.multiply(TWO).subtract(ONE);
		if( p22.compareTo(BigInteger.valueOf(3)) == -1 ||  listPrimal.size() > 20) {
			return listPrimal;
		} else {
			while(!(ferma(p11,a) || ferma(p22,a))){
				p11 = p11.add(TWO);
				p22 = p22.subtract(TWO);

			}
			if(ferma(p11,a)){
				listPrimal = finderPrimalNumber(p11,Pmax,listPrimal);
				listPrimal.add(p11);
			}
			if(ferma(p22,a) ) {
				listPrimal = finderPrimalNumber(p22,Pmax,listPrimal);
				listPrimal.add(p22);
			}
		}
		
		return listPrimal;
	}
	public static boolean fermaInt(long p, long a) {
		long res = fastPotencialInt(a,p-1,p);
		if(res == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static long fastPotencialInt(long  a, long  n, long  k) {
		long res;
		if(n == 0) {
			res = 1;
		}else if(n % 2 ==0){
			res = (long) (Math.pow(fastPotencialInt(a,n/2,k),2));
		} else {
			res = a * fastPotencialInt(a,n-1,k);
		}
		
		return res%k;
	}
	
	public static double logBigInteger(BigInteger val) {
		
        if (val.signum() < 1)
            return val.signum() < 0 ? Double.NaN : Double.NEGATIVE_INFINITY;
        int blex = val.bitLength() - MAX_DIGITS_2; // any value in 60..1023 works here
        if (blex > 0)
            val = val.shiftRight(blex);
        double res = Math.log(val.doubleValue());
        
        return blex > 0 ? res + blex * LOG_2 : res;
    }
}
