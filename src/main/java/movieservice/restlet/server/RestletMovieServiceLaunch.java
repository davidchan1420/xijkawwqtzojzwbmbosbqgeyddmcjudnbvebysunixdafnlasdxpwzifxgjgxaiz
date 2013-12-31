package movieservice.restlet.server;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class RestletMovieServiceLaunch {

	public static void main(String[] args) throws Exception {
		createService();
	}

	private static void createService() throws Exception {
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 1201);

		component.getDefaultHost().attach(new RestletMovieServiceJaxRsApplication(null));
		component.start();

	}

}
