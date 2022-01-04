package one.primedoc.backend.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

public class RandomGenerator {

    private static final SecureRandom random = new SecureRandom();


    public static String code() {
        return String.format("%s", random.nextInt(99999));
    }


}
