package com.algorithms;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class KaratsubaAlgorithmTest {

    @Test
    void karatsubaAlgorithm() {
//        long start1 = System.currentTimeMillis();
//        System.out.println("\nResult = " + karatsuba(1234, 5678));
//        System.out.println("duration ms = " + (start1 - System.currentTimeMillis()));

        Instant start = Instant.now();
        BigInteger res = KaratsubaAlgorithm.karatsuba(new BigInteger("1685287499328328297814655639278583667919355849391453456921116729"),
                new BigInteger("7114192848577754587969744626558571536728983167954552999895348492"));
        assertThat(res).isEqualTo(new BigInteger("11989460275519080564894036768322865785999566885539505969749975204962718118914971586072960191064507745920086993438529097266122668"));

        Duration duration = Duration.between(start, Instant.now());
        System.out.println("duration ms = " + duration.toMillis());
    }
}