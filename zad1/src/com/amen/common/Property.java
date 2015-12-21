/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

import java.util.Map;

/**
 *
 * @author AmeN
 */
public class Property implements Map.Entry<Object, Object> {

    private Object _key;
    private Object _value;

    public Property() {
        _key = null;
        _value = null;
    }

    public Property(Object key, Object value) {
        this._key = key;
        this._value = value;
    }

    @Override
    public Object getValue() {
        return _value;
    }

    @Override
    public Object getKey() {
        return _key;
    }

    @Override
    public Object setValue(Object v) {
        _value = v;
        return _value;
    }
}
