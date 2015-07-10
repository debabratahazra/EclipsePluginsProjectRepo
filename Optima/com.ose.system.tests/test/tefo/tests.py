########################################################################
#
# File:   tests.py
#
# Copyright (c) 2008 by Enea Embedded Technology AB. All rights reserved
#
########################################################################

########################################################################
# Imports
########################################################################

# Python library imports
import sys
import time
import cPickle
import os
import string
import re
import traceback

# TEFO imports
from ose.tefo.scripted import ScriptedTest
import ose.tefo.channels
from ose.tefo.channels import WaitforTimeoutException
from ose.tefo.utilities import Quote

# Test lib imports
from testutils import TestFailedError
from testutils import SystemModelTest

########################################################################
# Classes
########################################################################

class OseSystemModel(SystemModelTest):
    """Test OSE System Model."""
    def __init__(self, statemachine):
        SystemModelTest.__init__(self, statemachine)
    # End of __init__

    def RunTestCase(self, viewpath, result, host, target, ose):

        # Enter Test Action state
        self.TestAction()

        # Get ip address from target
        ip = ose.GetInetAddress()

        cmd  = self.java + " -classpath "
        cmd += viewpath + self.eclipse + self.jars["system_tests"] + ":"
        cmd += viewpath + self.eclipse + self.jars["system"]  + ":"
        cmd += viewpath + self.eclipse + self.jars["gateway"]  + ":"
        cmd += viewpath + self.eclipse + self.jars["equinox"]  + ":"
        cmd += viewpath + self.eclipse + self.jars["junit"]
	cmd += " -Xmx1500m "
        cmd += " -Dcom.ose.system.tests.gate.address=" + ip
        cmd += " -Dcom.ose.system.tests.gate.port=" + self.port
        cmd += " junit.textui.TestRunner com.ose.system.tests.AllTests"

        out = self.ExecuteCommand(cmd, host.GetTerminalChannel(), result, tmo=900.0)

        # Enter Test Response state
        self.TestResponse()

        out_pass = re.compile("OK +\(\d+ tests\)", re.M).search(out.string)

        if out_pass == None:
            out_res = re.compile("Tests run: (\d+), +Failures: (\d+), +Errors: (\d+)", re.M).search(out.string)
            if out_res == None:
                annotation = {
                    "fail_reason" : " jUnit tests results not found.",
                    "parsed_output" : Quote(out.string)
                    }
                raise TestFailedError("jUnit tests results not found.", annotation)

            if int(out_res.group(2)) != 0:
                annotation = {
                    "fail_reason" : out_res.group(2) + " jUnit tests failed.",
                    "parsed_output" : Quote(out.string)
                    }
                raise TestFailedError(out_res.group(2) + " jUnit tests failed.", annotation)

            if int(out_res.group(3)) != 0:
                annotation = {
                    "fail_reason" : out_res.group(3) + " jUnit tests have errors.",
                    "parsed_output" : Quote(out.string)
                    }
                raise TestFailedError(out_res.group(3) + " jUnit tests have errors.", annotation)
            
        
    # End of RunTestCase

# End of OseSystemModel
