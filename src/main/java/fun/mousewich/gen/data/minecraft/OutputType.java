package fun.mousewich.gen.data.minecraft;

public enum OutputType {
	DATA_PACK("data"),
	RESOURCE_PACK("assets"),
	REPORTS("reports");
	final String path;
	private OutputType(String path) { this.path = path; }
}