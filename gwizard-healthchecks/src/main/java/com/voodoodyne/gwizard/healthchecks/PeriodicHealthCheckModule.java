package com.voodoodyne.gwizard.healthchecks;

import com.google.inject.AbstractModule;
import com.voodoodyne.gwizard.services.ServicesModule;
import lombok.EqualsAndHashCode;

/**
 * binding for a periodic service that runs health checks and logs results
 *
 * depends on gwizard-services
 */
@EqualsAndHashCode(of={}, callSuper = false)	// makes installation of this module idempotent
public class PeriodicHealthCheckModule extends AbstractModule {

	@Override
	protected void configure() {
		// requirements on other modules
		install(new ServicesModule());
		install(new HealthChecksModule());

		bind(PeriodicHealthCheckService.class).asEagerSingleton();
	}
}
