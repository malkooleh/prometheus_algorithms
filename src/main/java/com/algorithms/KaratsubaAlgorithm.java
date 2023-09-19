package com.algorithms;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
class KaratsubaAlgorithm {

    private KaratsubaAlgorithm() {
    }

    // multiplying numbers by one operation is less/faster than the standard approach
    public static Long karatsuba(long x, long y) {
//        log.debug("Arguments: x=" + x + ", y=" + y);
        String xStr = String.valueOf(Math.max(x, y));
        if (xStr.length() == 1) {
            long x_y = x * y;
//            log.debug("x_y = " + x_y);
            return x_y;

        } else {
            int m = xStr.length() / 2;
            long a = x / (int) Math.pow(10, m);
            long b = x % (int) Math.pow(10, m);
            long c = y / (int) Math.pow(10, m);
            long d = y % (int) Math.pow(10, m);
//            log.debug("middle={}, a={}, b={}, c={}, d={}", m, a, b, c, d);

            long a_c = karatsuba(a, c);
            long b_d = karatsuba(b, d);
            long ad_bc = karatsuba(a + b, c + d) - a_c - b_d;
//            log.debug("a_c={}, b_d={}, ad_bc={}", a_c, b_d, ad_bc);

            return (long) (Math.pow(10, (2 * m)) * a_c + Math.pow(10, m) * ad_bc + b_d);
        }
    }

    public static BigInteger karatsuba(BigInteger x, BigInteger y) {
//        log.debug("Arguments: x=" + x + ", y=" + y);
        int n = Math.max(x.toString().length(), y.toString().length());
        if (n <= 1) {
            BigInteger x_y = x.multiply(y);
//            log.debug("x_y = " + x_y);
            return x_y;
        }

        n = n / 2;

        BigInteger a = x.divide(BigInteger.valueOf(10L).pow(n));
        BigInteger b = x.remainder(BigInteger.valueOf(10L).pow(n));
        BigInteger c = y.divide(BigInteger.valueOf(10L).pow(n));
        BigInteger d = y.remainder(BigInteger.valueOf(10L).pow(n));
//        log.debug("middle={}, a={}, b={}, c={}, d={}", n, a, b, c, d);

        // compute sub-expressions
        BigInteger a_c = karatsuba(a, c);
        BigInteger b_d = karatsuba(b, d);
        BigInteger ab_cd = karatsuba(a.add(b), c.add(d));
//        log.debug("a_c={}, b_d={}, ab_cd={}", a_c, b_d, ab_cd);

        BigInteger subtract = ab_cd.subtract(a_c).subtract(b_d);
//        log.debug("subtract=" + subtract);

        return BigInteger.valueOf(10L).pow(2 * n).multiply(a_c)
                .add(BigInteger.valueOf(10L).pow(n).multiply(subtract))
                .add(b_d);
    }
}
