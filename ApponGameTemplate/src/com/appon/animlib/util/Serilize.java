/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appon.animlib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public abstract class Serilize {

	private static final byte TYPE_INT = 0;
	private static final byte TYPE_STRING = 1;
	private static final byte TYPE_VECTOR = 2;
	private static final byte TYPE_HASHTABLE = 3;
	private static final byte TYPE_SERILIZABLE = 4;
	private static final byte TYPE_BYTE_ARRY = 5;
	private static final byte TYPE_LONG = 6;
	private static final byte TYPE_BOOLEAN = 7;
	private static final byte TYPE_INT_ARRY = 8;
	private static final byte TYPE_2DINT_ARRY = 9;
	private static final byte TYPE_BOOLEAN_ARRY = 10;
	private static final byte TYPE_DOUBLE_ARRY = 11;
	public static void serialize(Object obj, ByteArrayOutputStream bos) throws Exception {

		DataOutputStream dos = new DataOutputStream(bos);

		if (obj instanceof Serilizable) {
			dos.writeByte(TYPE_SERILIZABLE);
			// String className = obj.getClass().getName();
			// dos.writeUTF(className);
			int classcode = ((Serilizable) obj).getClassCode();
			dos.writeInt(classcode);
			byte data[] = ((Serilizable) obj).serialize();
			dos.writeInt(data.length);
			dos.write(data);
		} else if (obj instanceof String) {
			dos.writeByte(TYPE_STRING);
			dos.writeUTF((String) obj);
		} else if (obj instanceof Integer) {
			dos.writeByte(TYPE_INT);
			dos.writeInt(((Integer) obj).intValue());
		} else if (obj instanceof Long) {
			dos.writeByte(TYPE_LONG);
			dos.writeLong(((Long) obj).longValue());
		} else if (obj instanceof Vector) {
			Vector vct = (Vector) obj;
			dos.writeByte(TYPE_VECTOR);
			dos.writeInt(vct.size());
			for (int i = 0; i < vct.size(); i++) {
				serialize(vct.elementAt(i), bos);
			}
		} else if (obj instanceof Hashtable) {
			dos.writeByte(TYPE_HASHTABLE);
			Hashtable hashtable = (Hashtable) obj;
			dos.writeInt(hashtable.size());
			Enumeration enm = hashtable.keys();
			while (enm.hasMoreElements()) {
				Object key = enm.nextElement();
				Object value = hashtable.get(key);
				serialize(key, bos);
				serialize(value, bos);
			}
		} else if (obj instanceof byte[]) {
			byte data[] = (byte[]) obj;
			dos.writeByte(TYPE_BYTE_ARRY);
			dos.writeInt(data.length);
			dos.write(data, 0, data.length);
		} else if (obj instanceof int[]) {
			int data[] = (int[]) obj;
			dos.writeByte(TYPE_INT_ARRY);
			dos.writeInt(data.length);
			for (int i = 0; i < data.length; i++) {
				dos.writeInt(data[i]);
			}
		} else if (obj instanceof Boolean) {
			dos.writeByte(TYPE_BOOLEAN);
			dos.writeBoolean(((Boolean) obj).booleanValue());
		} else if (obj instanceof int[][]) {
			int data[][] = (int[][]) obj;
			dos.writeByte(TYPE_2DINT_ARRY);
			dos.writeInt(data.length);
			for (int i = 0; i < data.length; i++) {
				serialize(data[i], bos);
			}
		} else if (obj instanceof boolean[]) {
			boolean data[] = (boolean[]) obj;
			dos.writeByte(TYPE_BOOLEAN_ARRY);
			dos.writeInt(data.length);
			for (int i = 0; i < data.length; i++) {
				dos.writeBoolean(data[i]);
			}
		} else if (obj instanceof double[]) {
			double data[] = (double[]) obj;
			dos.writeByte(TYPE_DOUBLE_ARRY);
			dos.writeInt(data.length);
			for (int i = 0; i < data.length; i++) {
				dos.writeDouble(data[i]);
			}
		}
	}

	public static Object deserialize(ByteArrayInputStream bis, Serilize serilize) throws Exception {
		int type, size;
		Object obj = new Object();
		DataInputStream dis = new DataInputStream(bis);
		// if(dis.readBoolean())
		{
			type = dis.readByte();
			switch (type) {
				case TYPE_SERILIZABLE :
					int classcode = dis.readInt();
					size = dis.readInt();
					obj = serilize.getClassObject(classcode, -1, -1);
					((Serilizable) obj).deserialize(bis);
					break;
				case TYPE_INT :
					obj = new Integer(dis.readInt());
					break;
				case TYPE_LONG :
					obj = new Long(dis.readLong());
					break;
				case TYPE_STRING :
					obj = dis.readUTF();
					break;
				case TYPE_VECTOR :
					size = dis.readInt();
					Vector vct = new Vector(size);
					for (int i = 0; i < size; i++) {
						vct.addElement(deserialize(bis, serilize));
					}
					obj = vct;
					break;
				case TYPE_HASHTABLE :
					size = dis.readInt();
					Hashtable hashtable = new Hashtable(size);
					for (int i = 0; i < size; i++) {
						hashtable.put(deserialize(bis, serilize), deserialize(bis, serilize));
					}
					obj = hashtable;
					break;
				case TYPE_BYTE_ARRY :
					size = dis.readInt();
					obj = new byte[size];
					dis.read((byte[]) obj);
					break;

				case TYPE_INT_ARRY :
					size = dis.readInt();
					obj = new int[size];
					for (int i = 0; i < size; i++) {
						((int[]) obj)[i] = dis.readInt();
					}
					break;
				case TYPE_2DINT_ARRY :
					size = dis.readInt();
					obj = new int[size][];
					for (int i = 0; i < size; i++) {
						((int[][]) obj)[i] = (int[]) deserialize(bis, serilize);
					}
					break;
				case TYPE_BOOLEAN :
					obj = new Boolean(dis.readBoolean());
					break;
				case TYPE_BOOLEAN_ARRY :
					size = dis.readInt();
					obj = new boolean[size];
					for (int i = 0; i < size; i++) {
						((boolean[]) obj)[i] = dis.readBoolean();
					}
					break;
				case TYPE_DOUBLE_ARRY :
					size = dis.readInt();
					obj = new double[size];
					for (int i = 0; i < size; i++) {
						((double[]) obj)[i] = dis.readDouble();
					}
					break;
			}
		}
		return obj;
	}
	public abstract Serilizable getClassObject(int classcode, int id, int additionalData);

}
