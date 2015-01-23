package com.voodoodyne.gwizard.spi.example;

import com.codahale.metrics.health.HealthCheck;
import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.voodoodyne.gwizard.healthchecks.HealthChecks;
import com.voodoodyne.gwizard.healthchecks.PeriodicHealthCheckConfig;
import com.voodoodyne.gwizard.healthchecks.PeriodicHealthCheckModule;
import com.voodoodyne.gwizard.metrics.MetricsModule;
import com.voodoodyne.gwizard.services.Run;
import com.voodoodyne.gwizard.services.ServicesModule;
import com.voodoodyne.gwizard.spi.GwizardModuleLoader;
import io.dropwizard.util.Duration;
import lombok.EqualsAndHashCode;

public class SpiModuleExample {

	/**
	 * dumb example that's always unhealthy
	 */
	public static class ChronicallyUnhealthy extends HealthCheck {
		@Inject
		public ChronicallyUnhealthy(HealthChecks healthChecks) {
			healthChecks.add("chronicfailure", this);
		}

		@Override
		protected Result check() throws Exception {
			return Result.unhealthy("this health check is a terrible disappointment");
		}
	}

	@AutoService(Module.class)  // generates ref to this class in META-INF/services/com.google.inject.Module
	@EqualsAndHashCode(of={}, callSuper = false)	// makes installation of this module idempotent
	public static class ExampleModule extends AbstractModule {
		@Override
		protected void configure() {
			// TODO: if add google-auto-service to other gwizard modules, don't need the following lines.

			install(new ServicesModule());
			install(new MetricsModule());
			install(new PeriodicHealthCheckModule());

			bind(ChronicallyUnhealthy.class).asEagerSingleton();
		}

		@Provides
		public PeriodicHealthCheckConfig periodicHealthCheckConfig() {
			PeriodicHealthCheckConfig cfg = new PeriodicHealthCheckConfig();
			cfg.setInterval(Duration.seconds(5));
			return cfg;
		}
	}

	public static void main(String[] args) throws Exception {
		final Injector injector = Guice.createInjector(
				GwizardModuleLoader.loadModules() //
		);

		// start services
		injector.getInstance(Run.class).start();
	}

}
