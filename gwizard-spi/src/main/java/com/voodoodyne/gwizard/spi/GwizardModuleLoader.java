package com.voodoodyne.gwizard.spi;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import java.util.ServiceLoader;

/**
 * static methods for loading Guice Modules
 *
 * <p/>Similar:
 * <li>Onami SPI (http://onami.apache.org/spi/index.html)
 * which was (https://github.com/99soft/gspi)</li>
 */
public class GwizardModuleLoader {
	/**
	 * loads any modules in META-INF/services/com.google.inject.Module
	 * @return combined Module of all discovered Modules from META-INF
	 */
	public static Module loadModules() {
		final ServiceLoader<Module> moduleServiceLoader = ServiceLoader.load(Module.class);
		final ImmutableSet<Module> modulesSet = ImmutableSet.copyOf(moduleServiceLoader);
		return Modules.combine(modulesSet);
	}
}
