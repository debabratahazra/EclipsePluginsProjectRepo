package com.odcgroup.ocs.server.embedded.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.nature.EmbeddedServerNature;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerManager;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.server.ui.IServerExternalChangeListener;
import com.odcgroup.server.ui.UnableToStartServerException;
import com.odcgroup.server.ui.UnableToStopServerException;

public class EmbeddedServerContributionsTest extends AbstractServerTest {
	
	class ServerExternalChangeListenerSpy implements IServerExternalChangeListener {
		int serverAddedExternallyCalled = 0;
		int serverRemovedExternallyCalled = 0;
		
		public void serverAddedExternally(IDSServer server) {
			serverAddedExternallyCalled++;
		}
		public void serverRemovedExternally(IDSServer server) {
			serverRemovedExternallyCalled++;
		}
	}
	
	IJavaProject serverProject;
	@Before
	public void setUp() {
		try {
			serverProject = createJavaProject("serverProject", EmbeddedServerNature.NATURE_ID);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		EmbeddedServerManager.getInstance().reset();
	}
	
	@After
	public void tearDown() {
		try {
			serverProject.getProject().delete(true, null);
		} catch (CoreException e) {
			// Ignore
		}
		EmbeddedServerManager.getInstance().reset();
	}
	
	@Test
	public void testServerAddedExternally() {
		// Given
		EmbeddedServerContributions contrib = new EmbeddedServerContributions();
		ServerExternalChangeListenerSpy spyListener1 = new ServerExternalChangeListenerSpy();
		contrib.addListenerServerAddedRemovedExternally(spyListener1);
		ServerExternalChangeListenerSpy spyListener2 = new ServerExternalChangeListenerSpy();
		contrib.addListenerServerAddedRemovedExternally(spyListener2);
		
		// When
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions.notifyServerAddedExternally(server);
		
		// Then
		Assert.assertEquals(1, spyListener1.serverAddedExternallyCalled);
		Assert.assertEquals(0, spyListener1.serverRemovedExternallyCalled);

		Assert.assertEquals(1, spyListener2.serverAddedExternallyCalled);
		Assert.assertEquals(0, spyListener2.serverRemovedExternallyCalled);
	}

	@Test
	public void testServerRemovedExternally() {
		// Given
		EmbeddedServerContributions contrib = new EmbeddedServerContributions();
		ServerExternalChangeListenerSpy spyListener1 = new ServerExternalChangeListenerSpy();
		contrib.addListenerServerAddedRemovedExternally(spyListener1);
		ServerExternalChangeListenerSpy spyListener2 = new ServerExternalChangeListenerSpy();
		contrib.addListenerServerAddedRemovedExternally(spyListener2);
		
		// When
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions.notifyServerRemovedExternally(server);
		
		// Then
		Assert.assertEquals(0, spyListener1.serverAddedExternallyCalled);
		Assert.assertEquals(1, spyListener1.serverRemovedExternallyCalled);

		Assert.assertEquals(0, spyListener2.serverAddedExternallyCalled);
		Assert.assertEquals(1, spyListener2.serverRemovedExternallyCalled);
	}
	
	@Test
	public void testStart() throws UnableToStartServerException {
		// Given
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions contributions = new EmbeddedServerContributions() {
			@Override
			protected EmbeddedServerLauncherHelper getHelper() {
				return new EmbeddedServerLauncherHelper() {
					@Override
					public void start(IEmbeddedServer embeddedServer,
							boolean debug) throws UnableToStartServerException {
						if (embeddedServer.getState() != IDSServerStates.STATE_STARTING) {
							throw new IllegalStateException("Should be in starting mode");
						}
					}
				};
			}
		};
		
		// When
		contributions.start(server, false);
		
		// Then
		Assert.assertEquals(IDSServerStates.STATE_STARTING, server.getState());
	}
	
	@Test
	public void testStartFailure() {
		// Given
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions contributions = new EmbeddedServerContributions() {
			@Override
			protected EmbeddedServerLauncherHelper getHelper() {
				return new EmbeddedServerLauncherHelper() {
					@Override
					public void start(IEmbeddedServer embeddedServer,
							boolean debug) throws UnableToStartServerException {
						throw new UnableToStartServerException("fake unable to start server exception");
					}
				};
			}
		};
		
		// When
		boolean unableToStart = false;
		try {
			contributions.start(server, false);
		} catch (UnableToStartServerException e) {
			unableToStart = true;
		}
		
		// Then
		Assert.assertTrue(unableToStart);
		Assert.assertEquals(IDSServerStates.STATE_STOPPED, server.getState());
	}
	
	@Test
	public void testStartInDebug() throws UnableToStartServerException {
		// Given
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions contributions = new EmbeddedServerContributions() {
			@Override
			protected EmbeddedServerLauncherHelper getHelper() {
				return new EmbeddedServerLauncherHelper() {
					@Override
					public void start(IEmbeddedServer embeddedServer,
							boolean debug) throws UnableToStartServerException {
						if (embeddedServer.getState() != IDSServerStates.STATE_STARTING_DEBUG) {
							throw new IllegalStateException("Should be in starting mode");
						}
					}
				};
			}
		};
		
		// When
		contributions.start(server, true);
		
		// Then
		Assert.assertEquals(IDSServerStates.STATE_STARTING_DEBUG, server.getState());
	}
	
	@Test
	public void testStartInDebugFailure() {
		// Given
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions contributions = new EmbeddedServerContributions() {
			@Override
			protected EmbeddedServerLauncherHelper getHelper() {
				return new EmbeddedServerLauncherHelper() {
					@Override
					public void start(IEmbeddedServer embeddedServer,
							boolean debug) throws UnableToStartServerException {
						throw new UnableToStartServerException("fake unable to start server exception");
					}
				};
			}
		};
		
		// When
		boolean unableToStart = false;
		try {
			contributions.start(server, true);
		} catch (UnableToStartServerException e) {
			unableToStart = true;
		}
		
		// Then
		Assert.assertTrue(unableToStart);
		Assert.assertEquals(IDSServerStates.STATE_STOPPED, server.getState());
	}
	
	@Test
	public void testStop() throws UnableToStopServerException {
		// Given
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		server.setState(IDSServerStates.STATE_STARTED);
		EmbeddedServerContributions contributions = new EmbeddedServerContributions() {
			@Override
			protected EmbeddedServerLauncherHelper getHelper() {
				return new EmbeddedServerLauncherHelper() {
					@Override
					public void stop(IDSServer server) throws UnableToStopServerException {
					}
				};
			}
		};
		
		// When
		contributions.stop(server);
		
		// Then
		Assert.assertEquals(IDSServerStates.STATE_STOPPED, server.getState());
	}

	@Test
	public void testStopFailure() throws UnableToStopServerException {
		// Given
		IEmbeddedServer server = EmbeddedServerManager.getInstance().getEmbeddedServers().get(0);
		EmbeddedServerContributions contributions = new EmbeddedServerContributions() {
			@Override
			protected EmbeddedServerLauncherHelper getHelper() {
				return new EmbeddedServerLauncherHelper() {
					@Override
					public void stop(IDSServer server) throws UnableToStopServerException {
						throw new UnableToStopServerException("fake unable to start server exception");
					}
				};
			}
		};
		

		
		// When
		boolean unableToStop = false;
		try {
			contributions.stop(server);
		} catch (UnableToStopServerException e) {
			unableToStop = true;
		}
		
		// Then
		Assert.assertTrue(unableToStop);
	}

}
