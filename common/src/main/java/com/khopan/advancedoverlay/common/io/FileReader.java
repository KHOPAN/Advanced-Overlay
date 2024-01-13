package com.khopan.advancedoverlay.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileReader {
	private final byte[] data;

	private int pointer;

	public FileReader(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		this.data = stream.readAllBytes();
		stream.close();
		this.pointer = 0;
	}

	public byte readByte() {
		if(this.pointer > this.data.length - 1) {
			throw new IndexOutOfBoundsException("readByte() overflow!");
		}

		byte result = this.data[this.pointer];
		this.pointer++;
		return result;
	}

	public short readShort() {
		int first = this.readByte();
		int second = this.readByte();
		return (short) (
				((first & 0xFF) << 8) |
				(second & 0xFF)
				);
	}

	public int readInt() {
		int first = this.readByte();
		int second = this.readByte();
		int third = this.readByte();
		int forth = this.readByte();
		return ((first & 0xFF) << 24) |
				((second & 0xFF) << 16) |
				((third & 0xFF) << 8) |
				(forth & 0xFF);
	}

	public long readLong() {
		int first = this.readByte();
		int second = this.readByte();
		int third = this.readByte();
		int forth = this.readByte();
		int fifth = this.readByte();
		int sixth = this.readByte();
		int seventh = this.readByte();
		int eighth = this.readByte();
		return (((long) first & 0xFF) << 56) |
				(((long) second & 0xFF) << 48) |
				(((long) third & 0xFF) << 40) |
				(((long) forth & 0xFF) << 32) |
				(((long) fifth & 0xFF) << 24) |
				(((long) sixth & 0xFF) << 16) |
				(((long) seventh & 0xFF) << 8) |
				((long) eighth & 0xFF);
	}

	public float readFloat() {
		return Float.intBitsToFloat(this.readInt());
	}

	public double readDouble() {
		return Double.longBitsToDouble(this.readLong());
	}

	public boolean readBoolean() {
		return this.readByte() != 0x00;
	}

	public char readChar() {
		return (char) this.readShort();
	}

	public byte[] readByteArray() {
		int length = this.readInt();
		byte[] array = new byte[length];

		for(int i = 0; i < length; i++) {
			array[i] = this.readByte();
		}

		return array;
	}

	public String readString() {
		int length = this.readInt();
		byte flag = this.readByte();

		if(flag == 0x01) {
			return "";
		} else if(flag == 0x02) {
			return null;
		}

		byte[] array = new byte[length];

		for(int i = 0; i < length; i++) {
			array[i] = this.readByte();
		}

		return new String(array, StandardCharsets.UTF_8);
	}

	public Class<?> readClass() throws ClassNotFoundException {
		String name = this.readString();
		return FileReader.class.getClassLoader().loadClass(name);
	}

	public void reset() {
		this.pointer = 0;
	}
}
