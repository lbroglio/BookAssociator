package com.lukebroglio.BookAssociation;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightedListTests {
    @Test
    public void testAdd(){
        WeightedList<Integer> testList = new WeightedList<Integer>();

        testList.add(1);
        assertEquals(1,testList.get(0));

    }

    @Test
    public void testWeightedAdd(){
        WeightedList<Integer> testList = new WeightedList<Integer>();

        testList.addWithWeight(1,1);
        assertEquals(1,testList.get(0));

        testList.addWithWeight(2,10);
        assertEquals(2,testList.get(0));

        testList.addWithWeight(3,5);
        assertEquals(3,testList.get(1));
        assertEquals(1,testList.get(2));

    }

    @Test
    public void testRandomizedAdd(){
        WeightedList<Integer> testList = new WeightedList<Integer>();
        Random rand = new Random();
        HashMap<Integer,Integer> weightStorage = new HashMap<>();

        for(int i =0; i < 1000; i++){
            int randWeight = rand.nextInt(0,100);
            weightStorage.put(i,randWeight);
            testList.addWithWeight(i,randWeight);
        }

        boolean ordered = true;
        int prevWeight = weightStorage.get(testList.get(0));
        for(int i=1; i < testList.size(); i++){
            if(prevWeight < weightStorage.get(testList.get(i))){
                ordered = false;
            }
        }

        assertEquals(true,ordered);
    }


}
