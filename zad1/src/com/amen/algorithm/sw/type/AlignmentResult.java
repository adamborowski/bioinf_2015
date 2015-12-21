/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw.type;

import java.util.ArrayList;

/**
 *
 * @author AmeN
 */
public class AlignmentResult {

    private final ArrayList<PathType> lineAligned1;
    private final ArrayList<PathType> lineAligned2;

    private final ArrayList<PathType> line;
    private final int tIden;
    private final int tGaps;
    private final int tMiss;

    public AlignmentResult(ArrayList<PathType> line, int tIden, int tGaps, int tMiss, ArrayList<PathType> lineA1, ArrayList<PathType> lineA2) {
        this.line = line;
        this.tIden = tIden;
        this.tGaps = tGaps;
        this.tMiss = tMiss;

        this.lineAligned1 = lineA1;
        this.lineAligned2 = lineA2;
    }

    public ArrayList<PathType> getLine() {
        return line;
    }

    public int gettIden() {
        return tIden;
    }

    public int gettGaps() {
        return tGaps;
    }

    public int gettMiss() {
        return tMiss;
    }
    private final static int line_length = 80;

    public void print() {
        System.out.println(" IDENTITIES  => " + tIden);
        System.out.println(" GAPS        => " + tGaps);
        System.out.println(" MISSMATCHES => " + tMiss);
        System.out.println("");
        for (int counter = 0; counter < Math.ceil(((double) line.size() / (double) line_length)); counter++) {
            int j;
            System.out.print("Query:    ");
            for (j = 0; ((counter * line_length) + j) < lineAligned1.size() && j < line_length; j++) {
                System.out.print(lineAligned1.get(((counter * line_length) + j)));
            }
            System.out.println("");
            System.out.print("          ");
            for (j = 0; ((counter * line_length) + j) < line.size() && j < line_length; j++) {
                System.out.print(line.get(((counter * line_length) + j)));
            }
            System.out.println("");
            System.out.print("Alignment:");
            for (j = 0; ((counter * line_length) + j) < lineAligned2.size() && j < line_length; j++) {
                System.out.print(lineAligned2.get(((counter * line_length) + j)));
            }
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
        System.out.println("");
    }
}
