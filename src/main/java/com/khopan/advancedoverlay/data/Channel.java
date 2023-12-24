package com.khopan.advancedoverlay.data;

import java.util.ArrayList;
import java.util.List;

import com.khopan.advancedoverlay.Text;
import com.khopan.advancedoverlay.api.IModule;

public class Channel {
	private final List<Module> moduleList;

	private String channelName;
	private Location location;
	private double verticalSpacing;
	private double horizontalSpacing;
	private IModule[] moduleInstanceList;

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

	public IModule[] getModuleInstanceList() {
		return this.moduleInstanceList;
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

	public void makeInstance() {
		this.moduleInstanceList = new IModule[this.moduleList.size()];

		for(int i = 0; i < this.moduleInstanceList.length; i++) {
			this.moduleInstanceList[i] = this.moduleList.get(i).newModule();
		}
	}

	public static Channel getInstance() {
		return new Channel(Text.NEW_CHANNEL.getString(), Location.DEFAULT, 0.05d, 0.05d, new ArrayList<>());
	}
}
