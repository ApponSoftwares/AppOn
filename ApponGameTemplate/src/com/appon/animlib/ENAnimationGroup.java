/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

import com.appon.animlib.util.APSerilize;
import com.appon.animlib.util.Serilizable;

/**
 *
 * @author Swaroop1
 */
public class ENAnimationGroup extends AnimationGroupSupport implements Serilizable {
	private Vector<ENAnimation> animations = new Vector<ENAnimation>();
	private ImagePack imagePack;
	public ENAnimation getAnimation(int index) {
		if (index >= animations.size() || index < 0)
			return null;
		return animations.get(index);
	}
	public void port(int xper, int yper) {
		for (int i = 0; i < animations.size(); i++) {
			animations.get(i).port(xper, yper);
		}
	}
	public ImagePack getImagePack() {
		return imagePack;
	}

	public void setImagePack(ImagePack imagePack) {
		this.imagePack = imagePack;
	}

	public Vector<ENAnimation> getAnimations() {
		return animations;
	}

	public int size() {
		return animations.size();
	}

	@Override
	public byte[] serialize() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		APSerilize.serialize(animations, bos);
		bos.flush();
		byte data[] = bos.toByteArray();
		bos.close();
		bos = null;
		return data;
	}

	@Override
	public ByteArrayInputStream deserialize(ByteArrayInputStream bis) throws Exception {
		animations = (Vector<ENAnimation>) APSerilize.deserialize(bis, APSerilize.getInstance());
		return bis;
	}
	@Override
	public int getClassCode() {
		return APSerilize.ENANIMATION_GROUP_TYPE;
	}
}
