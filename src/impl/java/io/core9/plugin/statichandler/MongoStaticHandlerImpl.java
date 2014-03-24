package io.core9.plugin.statichandler;

import io.core9.plugin.filesmanager.FileRepository;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import com.google.common.io.ByteStreams;

@PluginImplementation
public class MongoStaticHandlerImpl implements StaticHandler {

	@InjectPlugin
	private VertxServer server;
	
	@InjectPlugin
	private FileRepository repository;

	@Override
	public void execute() {
		server.use("/static/.*", new Middleware() {

			@Override
			public void handle(Request request) {
				String filePath = request.getPath().replaceFirst("/static", "");
				try {
					Map<String,Object> file = repository.getFileContentsByName(request.getVirtualHost(), filePath); 
					request.getResponse().sendBinary(ByteStreams.toByteArray((InputStream) file.get("stream")));
					request.getResponse().putHeader("Content-Type", (String) file.get("ContentType"));
				} catch (IOException e) {
					request.getResponse().setStatusCode(404);
					request.getResponse().setStatusMessage("File not found");
				}

			}
		});
	}
}
