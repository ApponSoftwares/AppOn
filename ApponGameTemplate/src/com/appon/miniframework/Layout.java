/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appon.miniframework;

import com.appon.util.Serilizable;



/**
 *
 * @author swaroop_kumar
 */
public interface Layout extends Serilizable{
    public void applyLayout(Container parent,Control currentControl);
    public void port();
    public int getPreferedWidth(Container parent);
    public int getPreferedHeight(Container parent);
}
