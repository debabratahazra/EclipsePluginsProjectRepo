package com.odcgroup.workbench.memoryanalyzer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.memoryanalyzer.agent.VMAgent;

public class MemoryAnalyzerCore implements BundleActivator {

	public static final Logger logger = LoggerFactory
			.getLogger(MemoryAnalyzerCore.class);

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		MemoryAnalyzerCore.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		MemoryAnalyzerCore.context = null;
	}

	public static Instrumentation getInstrumentation() {
		return (Instrumentation) System.getProperties().get(VMAgent.KEY);
	}

	public static long getMemoryUseOf(Object obj) {
		return getMemoryUseOf(new Object[] { obj });
	}

	public static long getMemoryUseOf(Object[] objects) {
		if (getInstrumentation() == null)
			return -1;
		long size = 0;
		for (Object obj : objects) {
			size += getInstrumentation().getObjectSize(obj);
		}
		return size;
	}

	public static long getMemoryUseOf(Iterator<? extends Object> it) {
		if (getInstrumentation() == null)
			return -1;
		long size = 0;
		while (it.hasNext()) {
			size += getInstrumentation().getObjectSize(it.next());
		}
		return size;
	}

	public static String[] getAnalysis(ResourceSet resourceSet) {
		if (resourceSet == null)
			return new String[0];

		List<String> lines = new ArrayList<String>();

		lines.add("Total no. of models    : "
				+ resourceSet.getResources().size());
		int noOfLoadedModels = 0;
		long biggestModelSize = 0;
		String biggestModelName = null;
		long size = 0;
		for (Resource res : resourceSet.getResources()) {
			if (res.isLoaded()) {
				noOfLoadedModels++;
				long modelSize = deepMemoryUsageOf(res.getAllContents());
				size += modelSize;
				if (modelSize > biggestModelSize) {
					biggestModelSize = modelSize;
					biggestModelName = res.getURI().lastSegment();
				}
			}
		}
		lines.add("No. of models in memory: " + noOfLoadedModels);
		lines.add("Used memory (total)    : " + size);
		if (noOfLoadedModels > 0)
			lines.add("Used memory (avg/model): " + size / noOfLoadedModels);
		if (biggestModelName != null)
			lines.add("Biggest model is " + biggestModelName + ": "
					+ biggestModelSize);

		return lines.toArray(new String[lines.size()]);
	}

	public static long deepMemoryUsageOf(Object obj) {
		return deepMemoryUsageOfAll(Collections.singleton(obj));
	}

	public static long deepMemoryUsageOf(Iterator<? extends Object> it) {
		Set<Object> objs = new HashSet<Object>();
		while(it.hasNext()) objs.add(it.next());
		return deepMemoryUsageOfAll(objs);
	}

	public static long deepMemoryUsageOfAll(Collection<? extends Object> objs) {
		Instrumentation instr = getInstrumentation();
		long total = 0L;

		Set<Integer> counted = new HashSet<Integer>(objs.size() * 4);
		for (Iterator<? extends Object> localIterator = objs.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			total += deepMemoryUsageOf(instr, counted, o);
		}
		return total;
	}

	private static long deepMemoryUsageOf(Instrumentation instrumentation,
			Set<Integer> counted, Object obj) throws SecurityException {
		Stack<Object> st = new Stack<Object>();
		st.push(obj);
		long total = 0L;
		while (!st.isEmpty()) {
			Object o = st.pop();
			if (counted.add(Integer.valueOf(System.identityHashCode(o)))) {
				long sz = instrumentation.getObjectSize(o);
				total += sz;

				Class<? extends Object> clz = o.getClass();

				Class<? extends Object> compType = clz.getComponentType();
				int localObject1;
				int localObject2;
				Object el;
				if ((compType != null) && (!compType.isPrimitive())) {
					Object[] arr = (Object[]) o;
					Object[] arrayOfObject1 = arr;
					localObject1 = 0;
					for (localObject2 = arrayOfObject1.length; localObject1 < localObject2; localObject1++) {
						el = arrayOfObject1[localObject1];
						if (el != null) {
							st.push(el);
						}
					}

				}

				while (clz != null) {
					for (Field fld : clz.getDeclaredFields()) {
						int mod = fld.getModifiers();

						if (((mod & 0x8) == 0)) {
							Class<? extends Object> fieldClass = fld.getType();
							if (!fieldClass.isPrimitive() && fieldClass.equals(String.class)) {
								if (!fld.isAccessible())
									fld.setAccessible(true);
								try {
									Object subObj = fld.get(o);
									if (subObj != null)
										st.push(subObj);
								} catch (IllegalAccessException illAcc) {
									throw new InternalError("Couldn't read "
											+ fld);
								}
							}
						}
					}
					clz = clz.getSuperclass();
				}
			}
		}
		return total;
	}
}
