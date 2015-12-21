/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.algorithm.sw.type;

import static com.amen.common.log.Log.Error;

/**
 *
 * @author AmeN
 */
public enum ProteinType {

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
    TYPE_X('-', 11);

    private final Character _type;
    private final int _val;

    private ProteinType(char p_type, int p_val) {
        _type = p_type;
        _val = p_val;
    }

    private ProteinType() {
        _type = 'A';
        _val = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(_type);
    }

    public static ProteinType create(Character t_chr) {
        for (ProteinType t : values()) {
            if (t_chr.compareTo(t._type) == 0) {
                return t;
            }
        }
        Error(ProteinType.class, "Wrong input. Error - no protein with sign [" + t_chr + "]");
        System.exit(7);
        return TYPE_A;
    }

    public static ProteinType create(Integer t_generated) {
        for (ProteinType t : values()) {
            if (t._val == t_generated) {
                return t;
            }
        }
        return TYPE_A;
    }

    public int val() {
        return _val;
    }
}
