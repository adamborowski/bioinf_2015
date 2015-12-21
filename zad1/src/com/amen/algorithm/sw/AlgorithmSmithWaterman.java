/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw;

import com.amen.algorithm.sw.type.AlignmentLines;
import com.amen.algorithm.sw.type.AlignmentResult;
import com.amen.algorithm.sw.type.MoveType;
import com.amen.algorithm.sw.type.PathType;
import static com.amen.common.log.Log.Error;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author AmeN
 */
public class AlgorithmSmithWaterman {

    public static AlignmentResult establishAlignmentString(AlignmentLines lines) {
        ArrayList<PathType> alignment = new ArrayList<>();
        ArrayList<PathType> seq1 = lines.getAlignedProteinLine1();
        ArrayList<PathType> seq2 = lines.getAlignedProteinLine2();
        int tGaps = 0, tIden = 0, tMiss = 0;

        for (PathType val_s1 : seq1) {
            for (PathType val_s2 : seq2) {
                if (val_s1 == val_s2) {
                    alignment.add(PathType.TYPE_IDD);
                    tIden++;
                } else if (val_s1 == PathType.TYPE_NOT || val_s2 == PathType.TYPE_NOT) {
                    alignment.add(PathType.TYPE_GAP);
                    tGaps++;
                } else {
                    alignment.add(PathType.TYPE_MIM);
                    tMiss++;
                }
            }
        }

        return new AlignmentResult(alignment, tIden, tGaps, tMiss, seq1, seq2);
    }

    public static AlignmentLines traceback(SWScoreMatrix pMatrix) {
        ArrayList<PathType> line1 = new ArrayList<>();
        ArrayList<PathType> line2 = new ArrayList<>();

        Point lPosition = pMatrix.getScorePos();
        MoveType lMove = evaluateMove(pMatrix, lPosition);
        while (lMove != MoveType.MOVE_NOT) {
            if (lMove == MoveType.MOVE_DIAG) {
                line1.add(PathType.create(pMatrix.getSeq1().get(lPosition.x - 1)));
                line2.add(PathType.create(pMatrix.getSeq2().get(lPosition.y - 1)));
                lPosition.setLocation((lPosition.x - 1), (lPosition.y - 1));
            } else if (lMove == MoveType.MOVE_UP) {
                line1.add(PathType.create(pMatrix.getSeq1().get(lPosition.x - 1)));
                line2.add(PathType.create(null));
                lPosition.setLocation((lPosition.x - 1), (lPosition.y));
            } else {
                line1.add(PathType.create(null));
                line2.add(PathType.create(pMatrix.getSeq2().get(lPosition.y - 1)));
                lPosition.setLocation((lPosition.x), (lPosition.y - 1));
            }
            lMove = evaluateMove(pMatrix, lPosition);
        }
        line1.add(PathType.create(pMatrix.getSeq1().get(lPosition.x - 1)));
        line2.add(PathType.create(pMatrix.getSeq2().get(lPosition.y - 1)));

        return new AlignmentLines(reversed(line1), reversed(line2));
    }

    private static ArrayList<PathType> reversed(ArrayList<PathType> list) {
        System.out.println("Seq: ");
        PathType[] array = new PathType[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[list.size() - i - 1] = list.get(i);
            System.out.print(list.get(i).toString());
        }
        System.out.println("");
        return new ArrayList<>(Arrays.asList(array));
    }

    private static MoveType evaluateMove(SWScoreMatrix pMatrix, Point lPosition) {
        Integer scoreDiag, scoreUp, scoreLeft;

        scoreDiag = pMatrix.getScoreMatrix()[lPosition.x - 1][lPosition.y - 1];
        scoreLeft = pMatrix.getScoreMatrix()[lPosition.x][lPosition.y - 1];
        scoreUp = pMatrix.getScoreMatrix()[lPosition.x - 1][lPosition.y];

        if (scoreDiag >= scoreUp && scoreDiag >= scoreLeft) {
            return scoreDiag != 0 ? MoveType.MOVE_DIAG : MoveType.MOVE_NOT;
        } else if (scoreUp > scoreDiag && scoreUp >= scoreLeft) {
            return scoreUp != 0 ? MoveType.MOVE_UP : MoveType.MOVE_NOT;
        } else if (scoreLeft > scoreDiag && scoreLeft > scoreUp) {
            return scoreLeft != 0 ? MoveType.MOVE_LEFT : MoveType.MOVE_NOT;
        }

        Error(AlgorithmSmithWaterman.class, "Unexpected state...");
        System.exit(5);

        return MoveType.MOVE_NOT;
    }
}
