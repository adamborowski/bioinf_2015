/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

import com.amen.algorithm.sw.type.ProteinType;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author AmeN
 */
public class ProgramRandom {

    private final static Random _randomGenerator;

    static {
        _randomGenerator = new Random();
        _randomGenerator.setSeed(System.currentTimeMillis());
    }

    public static String generateStringSequence(ArrayList<Character> p_allowedChars, int length) {
        StringBuilder t_string = new StringBuilder();
        Integer t_charRange = p_allowedChars.size();

        Integer t_generated;
        for (int i = 0; i < length; i++) {
            t_generated = _randomGenerator.nextInt(t_charRange);
            t_string.append(p_allowedChars.get(t_generated));
        }

        return t_string.toString();
    }

    public static ArrayList<ProteinType> generateProteinSequence(int length) {
        ArrayList<ProteinType> t_list = new ArrayList<>();
        ProteinType[] allowedValues = ProteinType.values();
        Integer t_range = allowedValues.length;

        Integer t_generated = 0;
        for (int i = 0; i < length; i++) {
            t_generated = _randomGenerator.nextInt(t_range);
            t_list.add(ProteinType.create(t_generated));
        }

        return t_list;
    }

}
