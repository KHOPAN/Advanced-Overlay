package com.khopan.advancedoverlay.data;

import java.util.ArrayList;
import java.util.List;

import com.khopan.advancedoverlay.Text;

public class Channel {
	private final List<Module> moduleList;

	private String channelName;
	private Location location;
	private double verticalSpacing;
	private double horizontalSpacing;

	public Channel(String channelName, Location location, double verticalSpacing, double horizontalSpacing, List<Module> moduleList) {
		this.channelName = channelName;
		this.location = location;
		this.verticalSpacing = verticalSpacing;
		this.horizontalSpacing = horizontalSpacing;
		this.moduleList = moduleList;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public Location getLocation() {
		return this.location;
	}

	public double getVerticalSpacing() {
		return this.verticalSpacing;
	}

	public double getHorizontalSpacing() {
		return this.horizontalSpacing;
	}

	public List<Module> getModuleList() {
		return this.moduleList;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setVerticalSpacing(double verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}

	public void setHorizontalSpacing(double horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
	}

	public static Channel getInstance() {
		return new Channel(Text.NEW_CHANNEL.getString(), Location.DEFAULT, 0.05d, 0.05d, new ArrayList<>());
	}
}
