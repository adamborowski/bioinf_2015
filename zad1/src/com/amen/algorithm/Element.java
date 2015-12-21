/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm;

/**
 *
 * @author AmeN
 */
public class Element {

    double value;
    boolean up;
    boolean left;
    boolean diagonal;

    public Element() {
        this.up = false;
        this.left = false;
        this.diagonal = false;
        this.value = 0.0;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    public void setDiagonal(boolean diagonal) {
        this.diagonal = diagonal;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
