package movieservice.restlet.server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import movieservice.restlet.resource.MovieResource;
import movieservice.runnable.MovieSchedule;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.service.TaskService;

public class RestletMovieServiceJaxRsApplication extends JaxRsApplication {

	public RestletMovieServiceJaxRsApplication(Context context) {
		super(context);
		this.add(new RestletApplication());
		
		startScheduleJob();		
		
	}
	
	
	private void startScheduleJob(){
		
		TaskService taskService = new TaskService();
		//taskService.scheduleWithFixedDelay(new MovieSchedule(), 0, 5, TimeUnit.SECONDS);
		taskService.scheduleWithFixedDelay(new MovieSchedule(), 0, 5, TimeUnit.MINUTES);
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
