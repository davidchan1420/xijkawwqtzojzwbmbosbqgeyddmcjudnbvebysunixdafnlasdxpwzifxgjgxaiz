package movieservice.restlet.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import movieservice.restlet.resource.MovieResource;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class RestletMovieServiceJaxRsApplication extends JaxRsApplication {

	public RestletMovieServiceJaxRsApplication(Context context) {
		super(context);
		this.add(new RestletApplication());
	}

	private class RestletApplication extends Application {

		@Override
		public Set<Class<?>> getClasses() {

			Set<Class<?>> rrcs = new HashSet<Class<?>>();
			rrcs.add(MovieResource.class);
			return rrcs;

		}
	}
}
