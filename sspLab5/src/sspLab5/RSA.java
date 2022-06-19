package sspLab5;

import java.io.PrintStream;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSA {
	private static final BigInteger TWO = BigInteger.valueOf(2);
	private static final BigInteger ONE = BigInteger.valueOf(1);
	private static final BigInteger ZERO = BigInteger.valueOf(0);
	static double LOG_2 = Math.log(2.0);
	private static final int MAX_DIGITS_2 = 977;
	private static final int MAX_DIGITS_E = 677;
	public static void  main(String[] args) {
		

		
		long Pmax = 1500L;
		long a = 3L;
		long max = 30000L;
		
		
		BigInteger bp = BigInteger.valueOf(293);
		BigInteger bq = BigInteger.valueOf(149);
		BigInteger bn = bp.multiply(bq);
		BigInteger beiler = (bp.subtract(ONE)).multiply(bq.subtract(ONE));
		BigInteger bopen = findOpenKey(beiler);
		BigInteger bclose = findCloseKey(bopen,bn,beiler);
		BigInteger number = BigInteger.valueOf(4314);
		BigInteger bzashifrowan = fastPotencial(number,bopen,bn);
		System.out.println("zashifrovane" +" "+ bzashifrowan);
		BigInteger brozshifrowan = fastPotencial(bzashifrowan,bclose,bn);
		System.out.println("rozshifrovane" +" "+brozshifrowan);

	
	}
	static BigInteger  findCloseKey(BigInteger  Iopen, BigInteger  n, BigInteger  Ieiler) {
		BigDecimal  open = new BigDecimal(Iopen);
		BigDecimal eiler = new BigDecimal(Ieiler);
		BigDecimal  z= BigDecimal.valueOf(1);
		BigDecimal  closeKey = BigDecimal.valueOf(1);
		BigInteger  cheak = BigInteger.valueOf(1);
		BigInteger returnCloseKey;
		
		do {
			closeKey = ((z.multiply(eiler)).add(BigDecimal.valueOf(1))).divide(open,50, RoundingMode.HALF_UP);
			cheak = closeKey.toBigInteger();
			if(closeKey.subtract(new BigDecimal(cheak)).compareTo(BigDecimal.valueOf(0)) == 0) {
				BigInteger temp = BigInteger.valueOf(open.longValue());
				BigInteger temp2 = BigInteger.valueOf(closeKey.longValue());
//				System.out.println("CLose KEY= "+closeKey);
//				System.out.println("CLose KEY= "+ temp.multiply(temp2).mod(n));
				if((((temp.multiply(temp2)).mod(n)).compareTo(BigInteger.valueOf(1)) == 0)) {
//					System.out.println(closeKey);
					
					returnCloseKey = convertBigDecimalIntoBigInteger(closeKey);
					System.out.println("CLose KEY= "+returnCloseKey);
					break;
				}
			}
			z = z.add(BigDecimal.valueOf(1));
		   } while(true);
			
			
		return returnCloseKey;
	}
	
	static BigInteger convertBigDecimalIntoBigInteger(BigDecimal dec) {
		BigInteger result = ZERO;
		String str = dec.toString();
		String arrStr[] = str.split("\\.");
		result = new BigInteger(arrStr[0]);
		return result;
	}
	
	
	static BigInteger findOpenKey(BigInteger eiler) {
		BigInteger Pmax = BigInteger.valueOf(13);
		BigInteger a = BigInteger.valueOf(3L);
		ArrayList<BigInteger> listPrimal = new ArrayList<BigInteger>();
		
		listPrimal = finderPrimalNumber(a , Pmax, listPrimal);
		for(BigInteger l : listPrimal) {
//			System.out.println(l);
		}
		for (BigInteger i : listPrimal) {
//			BigDecimal minimum = BigDecimal.valueOf(logBigInteger(Pmax.divide(TWO))/logBigInteger(TWO));
//			BigInteger minimumI = convertBigDecimalIntoBigInteger(minimum);
			if(gcdByEuclidsAlgorithm(eiler,i).compareTo(ONE) == 0 && i.compareTo(eiler) == -1) {
				System.out.println("OPEN KEY = " + i);
				return i;
		    }
		}
		System.out.println("OPEN KEY = " + BigInteger.valueOf(17));
		return BigInteger.valueOf(17);
	}
	
	static BigInteger  gcdByEuclidsAlgorithm(BigInteger n1, BigInteger  n2) {
	    if (n2.compareTo(ZERO) == 0) {
	        return n1;
	    }
	    return gcdByEuclidsAlgorithm(n2, n1.mod(n2));
	
	}
	public static boolean ferma(BigInteger p, BigInteger a) {
		BigInteger res = fastPotencial(a,p.subtract(BigInteger.valueOf(1)),p);
		if(res.compareTo(BigInteger.valueOf(1)) == 0) {
			return true;
		} else {
			return false;
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
	
	static long findOpenKeyInt(ArrayList<Long> listPrimal,long n, long eiler) {

		for (Long i : listPrimal) {
			double minimum =  (Math.log(n)/Math.log(2));	
			if(gcdByEuclidsAlgorithmInt(eiler,i) == 1 && i > minimum) {
		    	return i;
		    }
		}
		return 17;
	}
	
	
	static long gcdByEuclidsAlgorithmInt(long n1, long n2) {
	    if (n2 == 0) {
	        return n1;
	    }
	    return gcdByEuclidsAlgorithmInt(n2, n1 % n2);
	
	}	
	
	public static ArrayList<Long> finderPrimalNumberInt(long a, long Pmax,long max, ArrayList<Long>  listPrimal) {
		long k = (long) Math.round(Math.log(Pmax/2)/Math.log(a));
		long p1 = (long) (2* Math.pow(a, k) +1);
		long p2 = (long) (2* Math.pow(a, k) -1);
		if(p2 == 3 || p1 > max || listPrimal.size() > 5) {
			return listPrimal;
		} else {
			while(!(fermaInt(p1,a) || fermaInt(p2,a))) {
				p1 += 2;
				p2 -= 2;
			}
			 if(fermaInt(p1,a)){
				 listPrimal.add(p1);
				finderPrimalNumberInt(p1,Pmax,max,listPrimal);
			}  
			 if(fermaInt(p2,a)) {
				listPrimal.add(p2);
				finderPrimalNumberInt(p2,Pmax,max,listPrimal);
			}
		}
		return listPrimal;
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
		long res = 1	;
		if(n == 0) {
			res = 1;
		}else if(n % 2 ==0){
			res = (long) Math.pow(fastPotencialInt(a,n/2,k),2);
		} else {
			res = a * fastPotencialInt(a,n-1,k);
		}
		
		return res%k;
	}
	
	static double findCloseKeyInt(double open, double n, double eiler) {
		long z = 1;
		double closeKey = 1;
		do{
			closeKey = (z*eiler+1)/open;
			
			z+=1;
			if (!(closeKey % 1 == 0)) {

				continue;
			}
			
		} while(!((open*closeKey%n) == 1));
		System.out.println((!((open*closeKey%n) == 1)));
		System.out.println(open+" "+ closeKey +" " +" "+n);
		return closeKey;
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
