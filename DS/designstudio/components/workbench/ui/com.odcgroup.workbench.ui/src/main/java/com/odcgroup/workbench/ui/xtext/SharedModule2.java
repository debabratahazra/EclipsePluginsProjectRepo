package com.odcgroup.workbench.ui.xtext;

import org.eclipse.xtext.builder.clustering.ClusteringBuilderState;
import org.eclipse.xtext.builder.clustering.DynamicResourceClusteringPolicy;
import org.eclipse.xtext.builder.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.builder.resourceloader.ResourceLoaderProviders;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.odcgroup.workbench.core.helper.FeatureSwitches;

/**
 * Xtext SharedModule extension which binds
 * the DynamicResourceClusteringPolicy,
 * the ParallelResourceLoader,
 * and ResourceLoaderSorter.
 *
 * @author Michael Vorburger
 */
public class SharedModule2 extends AbstractModule
{
	@Override
	protected void configure() {
		bind(IResourceLoader.Sorter.class).to(ByResourceExtensionLoaderSorter.class);

		if (FeatureSwitches.xtextBuilderFreeMemory.get()) {
			bind(IResourceClusteringPolicy.class).to(DynamicResourceClusteringPolicy.class);
		}

		if (FeatureSwitches.xtextBuilderParallel.get()) {
			// copy/paste from SharedModule boolean parallel = false's if (parallel), which is hard-coded to be always false :-(
			bind(IResourceLoader.class)
						.toProvider(ResourceLoaderProviders.getParallelLoader());
			bind(IResourceLoader.class).annotatedWith(
					Names.named(ClusteringBuilderState.RESOURCELOADER_GLOBAL_INDEX))
						.toProvider(ResourceLoaderProviders.getParallelLoader());
			bind(IResourceLoader.class).annotatedWith(
					Names.named(ClusteringBuilderState.RESOURCELOADER_CROSS_LINKING))
						.toProvider(ResourceLoaderProviders.getParallelLoader());
		}
	}

	// TODO org.eclipse.xtext.builder.resourceloader.IResourceLoader.Sorter.NoSorting

}
