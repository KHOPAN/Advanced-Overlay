package com.khopan.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.khopan.core.property.Property;
import com.khopan.core.property.SimpleProperty;

public class BinaryConfig {
	public BinaryConfig() {}

	private static final byte MAGIC_NUMBER = 0x3F;

	private static final ExceptionHandler DEFAULT_HANDLER = Error -> {
		throw new RuntimeException(Error);
	};

	private static ExceptionHandler ExceptionHandler = BinaryConfig.DEFAULT_HANDLER;

	public static Property<ExceptionHandler, Void> exceptionHandler() {
		return new SimpleProperty<ExceptionHandler, Void>(() -> BinaryConfig.ExceptionHandler, handler -> BinaryConfig.ExceptionHandler = handler, null).whenNull(BinaryConfig.DEFAULT_HANDLER);
	}

	public static void writeBinaryConfigFile(BinaryConfigElement element, File file) {
		if(element == null || file == null) {
			return;
		}

		try {
			FileOutputStream stream = new FileOutputStream(file);
			Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
			byte[] data = BinaryConfig.writeBinaryConfigByte(element);
			deflater.setInput(data);
			deflater.finish();
			byte[] output = new byte[data.length * 2];
			int size = deflater.deflate(output);
			byte[] result = new byte[size];

			for(int i = 0; i < size; i++) {
				result[i] = output[i];
			}

			stream.write(BinaryConfig.MAGIC_NUMBER);
			stream.write(result);
			deflater.end();
			stream.close();
		} catch(Throwable Errors) {
			BinaryConfig.ExceptionHandler.onThrowableThrows(Errors);
		}
	}

	public static BinaryConfigElement readBinaryConfigFile(File file) {
		if(file == null) {
			return null;
		}

		try {
			FileInputStream stream = new FileInputStream(file);

			if(stream.read() != BinaryConfig.MAGIC_NUMBER) {
				BinaryConfig.ExceptionHandler.onThrowableThrows(new IllegalArgumentException("Not a binary config file"));
				stream.close();
				return null;
			}

			Inflater inflater = new Inflater();
			byte[] input = stream.readAllBytes();
			inflater.setInput(input);
			byte[] output = new byte[input.length * 10];
			int size = inflater.inflate(output);
			byte[] result = new byte[size];

			for(int i = 0; i < size; i++) {
				result[i] = output[i];
			}

			inflater.end();
			stream.close();
			return BinaryConfig.readBinaryConfigByte(result);
		} catch(Throwable Errors) {
			BinaryConfig.ExceptionHandler.onThrowableThrows(Errors);
			return null;
		}
	}

	public static byte[] writeBinaryConfigByte(BinaryConfigElement element) {
		return InternalBinaryConfigWriter.write(element);
	}

	public static BinaryConfigElement readBinaryConfigByte(byte[] byteArray) {
		return InternalBinaryConfigReader.read(byteArray);
	}

	@FunctionalInterface
	public static interface ExceptionHandler {
		public void onThrowableThrows(Throwable Error);
	}
}
