package com.khopan.mods.advancedoverlay;

public class Version {
	public final int majorVersion;
	public final int minorVersion;
	public final int patchVersion;

	public Version(int majorVersion, int minorVersion, int patchVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.patchVersion = patchVersion;
	}

	public Version(String version) {
		try {
			String[] split = version.split(".");
			this.majorVersion = Integer.parseInt(split[0]);
			this.minorVersion = Integer.parseInt(split[1]);
			this.patchVersion = Integer.parseInt(split[2]);
		} catch(Throwable Errors) {
			throw new IllegalArgumentException("Version format must be 'MAJORVERSION.MINORVERSION.PATCHVERSION'");
		}
	}
}
