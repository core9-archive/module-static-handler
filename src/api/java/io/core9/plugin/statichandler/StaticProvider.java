package io.core9.plugin.statichandler;

import java.io.InputStream;
import java.util.Map;

public interface StaticProvider {
	Map<String, InputStream> getStaticFiles();
}
