package com.khopan.advancedoverlay.common.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
	private final List<Byte> list;

	public FileWriter() {
		this.list = new ArrayList<>();
	}

	public void writeByte(byte data) {
		this.list.add(data);
	}

	public void writeShort(short data) {
		this.writeByte((byte) (data & 0xFF));
		this.writeByte((byte) ((data >> 8) & 0xFF));
	}

	public void writeInt(int data) {
		this.writeByte((byte) (data & 0xFF));
		this.writeByte((byte) ((data >> 8) & 0xFF));
		this.writeByte((byte) ((data >> 16) & 0xFF));
		this.writeByte((byte) ((data >> 24) & 0xFF));
	}

	public void writeLong(long data) {
		this.writeByte((byte) (data & 0xFF));
		this.writeByte((byte) ((data >> 8) & 0xFF));
		this.writeByte((byte) ((data >> 16) & 0xFF));
		this.writeByte((byte) ((data >> 24) & 0xFF));
		this.writeByte((byte) ((data >> 32) & 0xFF));
		this.writeByte((byte) ((data >> 40) & 0xFF));
		this.writeByte((byte) ((data >> 48) & 0xFF));
		this.writeByte((byte) ((data >> 56) & 0xFF));
	}

	public void writeFloat(float value) {
		this.writeInt(Float.floatToIntBits(value));
	}

	public void writeDouble(double value) {
		this.writeLong(Double.doubleToLongBits(value));
	}

	public void writeBoolean(boolean value) {
		this.writeByte((byte) (value ? 0x01 : 0x00));
	}

	public void writeChar(char data) {
		this.writeByte((byte) (data & 0xFF));
		this.writeByte((byte) ((data >> 8) & 0xFF));
	}

	public void writeByteArray(byte[] data) {
		this.writeInt(data.length);

		for(int i = 0; i < data.length; i++) {
			this.writeByte(data[i]);
		}
	}

	public void writeString(String value) {
		byte flag = 0x00;

		if(value == null) {
			value = "";
			flag = 0x01;
		} else if(value.isEmpty()) {
			flag = 0x02;
		}

		byte[] byteArray = value.getBytes(StandardCharsets.UTF_8);
		this.writeInt(byteArray.length);
		this.writeByte(flag);

		for(int i = 0; i < byteArray.length; i++) {
			this.writeByte(byteArray[i]);
		}
	}

	public void writeClass(Class<?> data) {
		String name = null;

		if(data != null) {
			name = data.getName();
		}

		this.writeString(name);
	}

	public void reset() {
		this.list.clear();
	}

	public void write(File file) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);

		for(int i = 0; i < this.list.size(); i++) {
			stream.write(this.list.get(i));
		}

		stream.close();
	}
}
