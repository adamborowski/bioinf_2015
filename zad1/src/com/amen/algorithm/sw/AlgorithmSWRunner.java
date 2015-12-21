/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw;

import com.amen.algorithm.sw.type.ProteinType;
import com.amen.common.ProgramRandom;
import com.amen.common.ProgramRuntime;
import static com.amen.common.log.Log.Error;
import static com.amen.common.log.Log.Info;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author AmeN
 */
public class AlgorithmSWRunner {

    private SWScoreMatrix _matrix;
    private final int _match = 2;
    private final int _notMatch = -1;
    private final int _gap = -1;

    private final ArrayList<ProteinType> sequence_1;
    private final ArrayList<ProteinType> sequence_2;

    public AlgorithmSWRunner() {
        String seqNotParsed1 = ProgramRuntime.getParameterValue(ProgramRuntime.PARAM_SEQ1);
        String seqNotParsed2 = ProgramRuntime.getParameterValue(ProgramRuntime.PARAM_SEQ2);

        this.sequence_1 = parseSeq(seqNotParsed1);
        this.sequence_2 = parseSeq(seqNotParsed2);
    }

    public AlgorithmSWRunner(int p_seqLength) {
        this(ProgramRandom.generateProteinSequence(p_seqLength), ProgramRandom.generateProteinSequence(p_seqLength));
    }

    public AlgorithmSWRunner(ArrayList<ProteinType> p_sequence_1, ArrayList<ProteinType> p_sequence_2) {
        this.sequence_1 = p_sequence_1;
        this.sequence_2 = p_sequence_2;
    }

    public void createMatrix() {
        _matrix = new SWScoreMatrix(sequence_1, sequence_2);
        _matrix.evaluateScoreMatrix(_match, _notMatch, _gap);
    }

    public SWScoreMatrix getMatrix() {
        return _matrix;
    }

    private ArrayList<ProteinType> parseSeq(String seq) {
        ArrayList<ProteinType> lst = new ArrayList<>();

        for (Character c : seq.toCharArray()) {
            lst.add(ProteinType.create(c));
        }

        return lst;
    }
}
