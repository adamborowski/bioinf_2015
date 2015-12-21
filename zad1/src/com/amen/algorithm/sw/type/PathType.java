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
public enum PathType {

    TYPE_A('A', 0),
    TYPE_G('G', 1),
    TYPE_T('T', 2),
    TYPE_C('C', 3),
    TYPE_W('W', 4),
    TYPE_R('R', 5),
    TYPE_I('I', 6),
    TYPE_E('E', 7),
    TYPE_S('S', 8),
    TYPE_N('N', 9),
    TYPE_V('V', 10),
    TYPE_NOT('-', 14),
    TYPE_IDD('|', 15),
    TYPE_GAP(' ', 16),
    TYPE_MIM(':', 17);

    private final char _type;
    private final int _val;

    private PathType(char p_type, int p_val) {
        _type = p_type;
        _val = p_val;
    }

    private PathType() {
        _type = 'A';
        _val = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(_type);
    }

    public static PathType create(ProteinType t_protein) {
        if (t_protein == null) {
            return TYPE_NOT;
        }
        for (PathType t : values()) {
            if (t._val == t_protein.val()) {
                return t;
            }
        }
        return TYPE_NOT;
    }
}
