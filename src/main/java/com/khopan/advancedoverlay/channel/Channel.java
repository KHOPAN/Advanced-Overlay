package com.khopan.advancedoverlay.channel;

public class Channel {
	private String channelName;
	private Location location;

	public Channel(String channelName, Location location) {
		this.channelName = channelName;
		this.location = location;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
