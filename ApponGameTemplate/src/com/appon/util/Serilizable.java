/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.util;

import java.io.ByteArrayInputStream;


public interface Serilizable {
    public byte[] serialize() throws Exception;
    public ByteArrayInputStream deserialize(ByteArrayInputStream bis)throws Exception;
    public int getClassCode();
}
