########################################################################
#
# File:   testutils.py
#
# Copyright (c) 2008 by Enea Embedded Technology AB. All rights reserved
#
########################################################################

########################################################################
# Imports
########################################################################

# Python library imports
import time
import cPickle
import os
import string
import re
import sys
import traceback
from shutil import copy

# TEFO imports
from ose.tefo.scripted import ScriptedTest
import ose.tefo.channels
from ose.tefo.utilities import RegisterCleanupCommand
from ose.tefo.utilities import GetTefoHost
from ose.tefo.utilities import CopyToTmpFile
from ose.tefo.channels import WaitforTimeoutException
from ose.tefo.scripted import WaitforPrompt

########################################################################
# Classes
########################################################################

class TestFailedError(StandardError):
    """Exception raised if test condition fails."""
    def __init__(self, message, annotation = {}):
        self.errmsg = message
        self.annotation = annotation
        StandardError.__init__(self)
    # End of __init__

    def __str__(self):
        return self.errmsg
    # End of __str__
# End of TestFailedError

class OseCommandError(StandardError):
    """Exception raised if ose command fails."""
    def __init__(self, command, message):
        self.errmsg = message
        self.command = command
        StandardError.__init__(self)
    # End of __init__

    def __str__(self):
        return self.errmsg
    # End of __str__
# End of OseCommandError

class OseCommandProxy:
    ose_prompt = "> "
    result = None
    console = None

    def __init__(self, result, console):
        self.result = result
        self.console = console
    # End of __init__

    def SyncWithPrompt(self, timeout=10.0):
        """Wait for prompt to appear in console.

        'tmo' -- Wait timeout value."""
        time.sleep(1)
        self.console.ReadAll()
        self.console.Write(self.console.end_of_line_seq)
        WaitforPrompt(re.escape(self.ose_prompt), self.result,
                      self.console, timeout)
    # End of SyncWithPrompt

    def GetInetAddress(self):
        """Get IP address for target.

        Returns -- The target IP address."""
        self.console.WriteEchoedLine("ifconfig -a", self.result, 5.0)
        regexp = r"inet (?P<inetaddr>((172\.(?:24|25)\.\d+\.\d+)|(192\.168\.168\.\d+))) "
        mobj = self.console.Waitfor(regexp, None, self.result, 5.0)
        ipaddr = mobj.group("inetaddr")
        self.SyncWithPrompt()
        return ipaddr
    # End of GetInetAddress

class SystemModelTest(ose.tefo.scripted.ScriptedTest):
   """Base class for System Model tests."""
   def __init__(self, statemachine):
      ose.tefo.scripted.ScriptedTest.__init__(self, statemachine)
      self.port = "21768"
      self.java = "/rdtools/linux/jdk1.6.0_31/bin/java"
      self.eclipse = "/vobs/ose5/tools/eclipse"
      self.jars = {"system_tests": "/com.ose.system.tests/bin",
                   "system": "/com.ose.system/bin", 
                   "gateway": "/com.ose.gateway/bin",
                   "equinox": "/eclipse/plugins/org.eclipse.equinox.common_3.6.100.v20120522-1841.jar",
                   "junit": "/eclipse/plugins/org.junit_3.8.2.v3_8_2_v20100427-1100/junit.jar"}
   # End of __init__

   def SplitCluster(self, clustertarget, result):
      """Get host/target from cluster target."""
      members = clustertarget.GetMembers()

      if self.IsTargetHost(members[0]):
         host = members[0]
         target = members[1]
      else:
         host = members[1]
         target = members[0]

      return (host, target)
   # End of SplitCluster

   def IsTargetHost(self, target):
      if (target.GetArch() == "windows") or \
         (target.GetArch() == "solaris") or \
         (target.GetArch() == "linux"):
        return True
      return False
   # End of IsTargetHost

   def SplitString(self, seq, length):
       """Split a string into several fixed length substrings."""
       return [seq[i:i+length] for i in range(0, len(seq), length)]
   # End of SplitString

   def ExecuteCommand(self, cmd, host, result, tmo=20.0):
      """Execute the shell command.
         On Solaris the maximum command line length is ~257
         characters. So, we need to break the shell command
         in several pieces."""

      # Split the command into several commands
      # of fixed length (250 characters)
      cmds = self.SplitString(cmd, 250)

      # Execute the commands
      for i in range(0, len(cmds)-1):
          host.Write(cmds[i] + "\\\n")
          WaitforPrompt("> ", result, host, 10.0)
      host.WriteEchoedLine(cmds[len(cmds) - 1], result, tmo)

      # Wait for prompt
      out = self.WaitforPrompt(host, result, tmo)
      return out
   # End of ExecuteCommand

   def WaitforPrompt(self, host, result=None, tmo=20.0):
       """Wait until we get a unix style prompt (ending with '$ ', '# ',
       or '> ').
       'result' -- the QMTest result class, filled in if failed (optional).
       'tmo' -- floating point value of time out in seconds (default 20s)
       """
       while 1:
           # Wait for a "normal" Unix shell prompt
           out = host.Waitfor("(\$ )|(# )|(> )", None, result, tmo)
           try:
               # Check that the sequence is not in the middle of some
               # general output by looking for more characters
               host.Waitfor(".", tmo=0.1)

               # No exception, so there were more characters, keep
               # looking for a prompt
               continue

           except WaitforTimeoutException:
               # Ok, so there was no character available within 100ms
               # I guess we have a prompt!
               break
       # End while
       return out
   # End of WaitforUnixPrompt

   def JavaOnHost(self, target):
      """Find the location of the java executable."""

      if (target.GetArch() == "windows"):
          arch = "win32"
      elif (target.GetArch() == "linux"):
          arch = "linux"
      elif (target.GetArch() == "solaris"):
          arch = "solaris2"
      else:
          arch = "unknown"
      return arch
   # End of JavaOnHost

   def Run(self, clustertarget, context, result):
      """Run system model test.

      'clustertarget' -- Cluster target to run test on.

      'context' -- Test context object.

      'result' -- Test result object."""

      # Enter Test Precondition state
      self.TestPreCond()

      (host, target) = self.SplitCluster(clustertarget, result)

      # Create proxy object for sending command to OSE
      ose = OseCommandProxy(result, target.GetTerminalChannel())

      # Write the name of the test class as a shell comment
      self.SyncWithShell(result, host.GetTerminalChannel(), host.GetArch())
      host.GetTerminalChannel().WriteEchoedLine("# TEST CLASS: " + \
                                                self.__class__.__name__,
                                                result, 5.0)

      # Setup host for interaction
      self.SyncWithShell(result, host.GetTerminalChannel(), host.GetArch())
      self.SetupShell(result, host)
      self.SyncWithShell(result, host.GetTerminalChannel(), host.GetArch())

      # Setup target for interaction
      ose.SyncWithPrompt()

      # Start the view
      self.StartView(result, host.GetTerminalChannel())
      viewpath = self.GetViewPath()
      self.SyncWithShell(result, host.GetTerminalChannel(), host.GetArch())

      # Find the location of the java executable, it depends on the architecture of the host
      self.java = "/rdtools/" + self.JavaOnHost(host) + "/jdk1.6.0_31/bin/java"
      try:
         try:
            # Run actual test code
            self.RunTestCase(viewpath, result, host, target, ose)

         finally:
            ose.SyncWithPrompt()
            target.GetTerminalChannel().WriteEchoedLine("rld -p")
            ose.SyncWithPrompt()

      except (TestFailedError), e:
         # Notify TEFO that test failed.
         result.Fail(e.errmsg, e.annotation)

         # Attempt to restore an acceptable state for
         # the next test by power cycling the target.
         try:
             target.PowerCycle()
         except:
             # not available for soft kernels
             pass
         return

      except WaitforTimeoutException, e:
         # Check if the target may have rebooted.
         if re.search("(" + target.bootloader.prompt + "|CFE)",
                      e.annotation["target_output"]) != None:

            # Dump the ramlog.
            target.GetTerminalChannel().WriteEchoedLine("rld -p")

            # Attempt to restore an acceptable state for
            # the next test by power cycling the target.
            target.PowerCycle()

         # Give up and re-raise exception.
         raise

         self.SyncWithShell(result, host.GetTerminalChannel(), host.GetArch())
         ose.SyncWithPrompt()

         # Set Test Postcondition state
         self.TestPostCond()
   # End of Run

   def RunTestCase(self, viewpath, result, host, target, ose):
      """Run the test case specific code.

      'host' -- Host target that will run the System Model jUnit tests.

      'target' -- OSE target.

      'ose' -- A proxy object for communicating with OSE."""
      raise "Override this method."
   # End of RunActualTest
