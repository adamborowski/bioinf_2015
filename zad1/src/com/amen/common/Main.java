/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

import com.amen.algorithm.NeedlemanWunsch;
import com.amen.algorithm.SmithWaterman;

/**
 *
 * @author AmeN
 */
public class Main {

    private final static Integer random_rows = 6;
    private final static Integer random_cols = 6;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppRuntime.parseParameters(args);

        String seqNotParsed1 = AppRuntime.getInputA();
        String seqNotParsed2 = AppRuntime.getInputB();

        Double[][] similarityMatrix = AppRuntime.getInputSimilarityMatrix();

        SmithWaterman runner = new SmithWaterman(seqNotParsed1, seqNotParsed2, similarityMatrix);
        runner.run();

        NeedlemanWunsch runner2 = new NeedlemanWunsch(seqNotParsed1, seqNotParsed2, similarityMatrix);
        runner2.run();
    }

}
