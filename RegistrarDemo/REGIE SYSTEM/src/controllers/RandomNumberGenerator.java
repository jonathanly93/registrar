package controllers;

import java.util.Random;

/**
 * Generates a random i-digit number
 */
public class RandomNumberGenerator {

    /**
     * @return random i digit integer
     */
    public int getRandomNumber(int i){

        Random rand = new Random();
        int k = 1;
        for(int j = 0; j < i; j++){
            k *= 10;
        }
        k = k -1;
        return rand.nextInt(k) + 1;
    }

}


