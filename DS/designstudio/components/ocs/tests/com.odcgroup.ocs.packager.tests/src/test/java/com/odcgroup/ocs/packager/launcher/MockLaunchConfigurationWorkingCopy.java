package com.odcgroup.ocs.packager.launcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;

public class MockLaunchConfigurationWorkingCopy implements
		ILaunchConfigurationWorkingCopy {
	
	Map<String, String> stringAttributes = new HashMap<String, String>();
	Map<String, Boolean> booleanAttributes = new HashMap<String, Boolean>();
	@SuppressWarnings("rawtypes")
	Map<String, List> listAttributes = new HashMap<String, List>();

	@Override
	public boolean contentsEqual(ILaunchConfiguration configuration) {

		return false;
	}

	@Override
	public ILaunchConfigurationWorkingCopy copy(String name)
			throws CoreException {

		return null;
	}

	@Override
	public void delete() throws CoreException {


	}

	@Override
	public boolean exists() {

		return false;
	}

	@Override
	public boolean getAttribute(String attributeName, boolean defaultValue)
			throws CoreException {

		return false;
	}

	@Override
	public int getAttribute(String attributeName, int defaultValue)
			throws CoreException {

		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAttribute(String attributeName, List defaultValue)
			throws CoreException {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set getAttribute(String attributeName, Set defaultValue)
			throws CoreException {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getAttribute(String attributeName, Map defaultValue)
			throws CoreException {

		return null;
	}

	@Override
	public String getAttribute(String attributeName, String defaultValue)
			throws CoreException {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getAttributes() throws CoreException {

		return null;
	}

	@Override
	public String getCategory() throws CoreException {

		return null;
	}

	@Override
	public IFile getFile() {

		return null;
	}

	@Override
	public IPath getLocation() {

		return null;
	}

	@Override
	public IResource[] getMappedResources() throws CoreException {

		return null;
	}

	@Override
	public String getMemento() throws CoreException {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Set getModes() throws CoreException {

		return null;
	}

	@Override
	public String getName() {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ILaunchDelegate getPreferredDelegate(Set modes) throws CoreException {

		return null;
	}

	@Override
	public ILaunchConfigurationType getType() throws CoreException {

		return null;
	}

	@Override
	public ILaunchConfigurationWorkingCopy getWorkingCopy()
			throws CoreException {

		return null;
	}

	@Override
	public boolean hasAttribute(String attributeName) throws CoreException {

		return false;
	}

	@Override
	public boolean isLocal() {

		return false;
	}

	@Override
	public boolean isMigrationCandidate() throws CoreException {

		return false;
	}

	@Override
	public boolean isReadOnly() {

		return false;
	}

	@Override
	public boolean isWorkingCopy() {

		return false;
	}

	@Override
	public ILaunch launch(String mode, IProgressMonitor monitor)
			throws CoreException {

		return null;
	}

	@Override
	public ILaunch launch(String mode, IProgressMonitor monitor, boolean build)
			throws CoreException {

		return null;
	}

	@Override
	public ILaunch launch(String mode, IProgressMonitor monitor, boolean build,
			boolean register) throws CoreException {

		return null;
	}

	@Override
	public void migrate() throws CoreException {


	}

	@Override
	public boolean supportsMode(String mode) throws CoreException {

		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addModes(Set modes) {


	}

	@Override
	public ILaunchConfiguration doSave() throws CoreException {

		return null;
	}

	@Override
	public ILaunchConfiguration getOriginal() {

		return null;
	}

	@Override
	public ILaunchConfigurationWorkingCopy getParent() {

		return null;
	}

	@Override
	public boolean isDirty() {

		return false;
	}

	@Override
	public Object removeAttribute(String attributeName) {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void removeModes(Set modes) {


	}

	@Override
	public void rename(String name) {


	}

	@Override
	public void setAttribute(String attributeName, int value) {


	}

	@Override
	public void setAttribute(String attributeName, String value) {
		stringAttributes.put(attributeName, value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setAttribute(String attributeName, List value) {
		listAttributes.put(attributeName, value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setAttribute(String attributeName, Map value) {


	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setAttribute(String attributeName, Set value) {


	}

	@Override
	public void setAttribute(String attributeName, boolean value) {
		booleanAttributes.put(attributeName, value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setAttributes(Map attributes) {


	}

	@Override
	public void setContainer(IContainer container) {


	}

	@Override
	public void setMappedResources(IResource[] resources) {


	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setModes(Set modes) {


	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setPreferredLaunchDelegate(Set modes, String delegateId) {


	}

}
