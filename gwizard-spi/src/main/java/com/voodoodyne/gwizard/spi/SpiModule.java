package com.voodoodyne.gwizard.spi;

import com.google.auto.service.AutoService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import lombok.EqualsAndHashCode;

@AutoService(Module.class)  // generates ref to this class in META-INF/services/com.google.inject.Module
@EqualsAndHashCode(of={}, callSuper = false)	// makes installation of this module idempotent
public class SpiModule extends AbstractModule {
	@Override
	protected void configure() {
	}
}
