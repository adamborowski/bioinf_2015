/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw.type;

import static com.amen.common.log.Log.Error;
import java.util.ArrayList;

/**
 *
 * @author AmeN
 */
public class AlignmentLines {

    private final ArrayList<PathType> alignedProteinLine1;
    private final ArrayList<PathType> alignedProteinLine2;

    public AlignmentLines(ArrayList<PathType> alignedProteinLine1, ArrayList<PathType> alignedProteinLine2) {
        this.alignedProteinLine1 = alignedProteinLine1;
        this.alignedProteinLine2 = alignedProteinLine2;

        if (alignedProteinLine1.size() != alignedProteinLine2.size()) {
            Error(getClass(), "Alignment lines are not the same size.");
            System.exit(6);
        }
    }

    public ArrayList<PathType> getAlignedProteinLine1() {
        return alignedProteinLine1;
    }

    public ArrayList<PathType> getAlignedProteinLine2() {
        return alignedProteinLine2;
    }
}
