/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

import com.amen.algorithm.sw.AlgorithmSWRunner;
import com.amen.algorithm.sw.AlgorithmSmithWaterman;
import com.amen.algorithm.sw.type.AlignmentLines;
import com.amen.algorithm.sw.type.AlignmentResult;
import com.amen.common.ProgramRuntime;

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
        ProgramRuntime.parseParameters(args);

        
        AlgorithmSWRunner runner = new AlgorithmSWRunner();
        runner.createMatrix();
        AlignmentLines lines = AlgorithmSmithWaterman.traceback(runner.getMatrix());
        AlignmentResult result = AlgorithmSmithWaterman.establishAlignmentString(lines);

        runner.getMatrix().printScoreMatrix();
        result.print();
    }

}
