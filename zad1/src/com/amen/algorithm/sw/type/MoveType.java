/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw.type;

/**
 *
 * @author AmeN
 */
public enum MoveType {

    MOVE_NOT(0),
    MOVE_DIAG(1),
    MOVE_UP(2),
    MOVE_LEFT(3);

    private final int value;

    private MoveType(int val) {
        this.value = val;
    }

    private MoveType() {
        this(0);
    }
}
