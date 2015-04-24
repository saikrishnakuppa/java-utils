package org.javautils.web;

public class AppInfo {

	private static final AppInfo INSTANCE = new AppInfo();
	private String version;
	private String builtBy;
	private String buildTimestamp;
	private String buildJDK;
	private AppInfo() {}
	public static AppInfo getInstance() {
		return INSTANCE;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBuiltBy() {
		return builtBy;
	}
	public void setBuiltBy(String builtBy) {
		this.builtBy = builtBy;
	}
	public String getBuildTimestamp() {
		return buildTimestamp;
	}
	public void setBuildTimestamp(String buildTimestamp) {
		this.buildTimestamp = buildTimestamp;
	}
	public String getBuildJDK() {
		return buildJDK;
	}
	public void setBuildJDK(String buildJDK) {
		this.buildJDK = buildJDK;
	}
	@Override
	public String toString() {
		return "AppInfo [version=" + version + ", builtBy=" + builtBy
				+ ", buildTimestamp=" + buildTimestamp + ", buildJDK="
				+ buildJDK + "]";
	}
}
