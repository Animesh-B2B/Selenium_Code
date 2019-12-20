package guice_Dependency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.inject.Guice;
import com.google.inject.Injector;

import cta_OcePortal.OcePortal_Parent;
import cta_e2e.AppModule;



public class CurrentTextContext {
	
	  private static final Logger logger = LoggerFactory.getLogger(CurrentTextContext.class);
	  private final static ThreadLocal<Injector> context = new ThreadLocal<Injector>();
	  
	  public static Injector getContext() {
			Injector injector = context.get();
			if(injector == null) {
				Injector newContext = Guice.createInjector(new AppModule());
				if(logger.isDebugEnabled()) {
					logger.debug("------------Creating new Context-----------------");
					logger.debug("billingService "+ newContext.getInstance(OcePortal_Parent.class));
				}
				context.set(newContext);
			}
			return context.get();
		}

		
		public static Injector createNewContext() {
			Injector newContext = Guice.createInjector(new AppModule());
			context.set(newContext);
			if(logger.isDebugEnabled()) {
				logger.debug("------------ CreateNewContext : "+ newContext.getInstance(OcePortal_Parent.class));
			}
			return context.get();
		}

}
