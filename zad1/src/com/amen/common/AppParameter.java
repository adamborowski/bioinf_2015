/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

/**
 *
 * @author AmeN
 */
public class AppParameter extends Property {

    private final String _default;

    public AppParameter(String p_key) {
        this(p_key, null);
    }

    public AppParameter(String p_key, String p_default) {
        super(p_key, null);

        _default = p_default;
    }

    public boolean isForKey(String p_keyGiven) {
        return this.getKey().equals(p_keyGiven);
    }

    @Override
    public String getKey() {
        try {
            return super.getKey().toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getValue() {
        try {
            return super.getValue() == null ? _default : super.getValue().toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Object setValue(String v) {
        return super.setValue(v); //To change body of generated methods, choose Tools | Templates.
    }
}
