package com.temenos.ds.t24.data.rps;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.temenos.soa.services.data.ResponseDetails;

/**
 * Checked Exception for anything that can go wrong during a remote component service invocation.
 *
 * @author Michael Vorburger
 */
public class RemoteServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	static final String[][] ERROR_MESSAGES_AND_HINTS =
		{
			/* 0*/{"LIVE RECORD NOT CHANGED", "The record sent was exacly the same on the database, therefore no record were changed."},
			/* 1*/{"etag", "The etag field is the optimistic locking mechanism of T24. This problem could occur in case of concurrent modification or if the context used is not reused (recreated) in call belonging to the same change."},
			/* 2*/{"NO LEADING MINUS WITH SWIFT", "No field can start with a minus (-). This is linked to the fact the underlying protocol used is SWIFT and forbit having minus as a first character."},
			/* 3*/{"Response Type = OVERRIDE", "Some validations are run when a record is written to T24. When confirmation are required, T24 issues a OVERRIDE confirmation."},
			/* 4*/{"NO CHANGE TO LIVE (NAU-REC. EXISTS)", "The record is already waiting for authorisation, hence it is not possible to change it right now."},
			/* 5*/{"EB-USER.CONTEXT.MISSING", "The credentials might be wrong."},
			/* 6*/{"UNAUTH. RECORD MISSING", "The record is already authorized."},
			/* 7*/{"SECURITY VIOLATION", "The credentials might be wrong."},
		};

	public RemoteServiceException(String message) {
		super(addHint(message));
	}

	public RemoteServiceException(String message, Throwable cause) {
		super(addHint(message), cause);
	}
	
	public RemoteServiceException(ResponseDetails outResponseDetails) {
		this(outResponseDetails.toString());
	}

	public RemoteServiceException(ResponseDetails responseDetails, Throwable cause) {
		this(responseDetails.toString(), cause);
	}

	public RemoteServiceException(com.temenos.soa.services.data.xsd.ResponseDetails jaxbResponseDetails) {
		this(jaxbResponseDetails.toString());
	}

	public RemoteServiceException(com.temenos.soa.services.data.xsd.ResponseDetails jaxbResponseDetails, Throwable cause) {
		this(jaxbResponseDetails.toString());
	}

	private static String addHint(String message) {
		return addHint(message, null);
	}

	private static String addHint(String message, Throwable cause) {
		List<String> hints = new ArrayList<String>();
		gatherHints(message, cause, hints);
		if (hints.size() == 0) {
			return message;
		} else if (hints.size() == 1) {
			return message + "\nHint: " + hints.get(0);
		} else {
			return message + "\nHints:\n" + StringUtils.join(hints, "\n");
		}
	}
	
	private static void gatherHints(String message, Throwable cause, List<String> hints) {
		if (message == null && cause == null) {
			return;
		}
		
		if (message != null) {
			for (String[] messageAndHint: ERROR_MESSAGES_AND_HINTS) {
				if (message.contains(messageAndHint[0]) && !hints.contains(messageAndHint[1])) {
					hints.add(messageAndHint[1]);
				}
			}
		}

		if (cause != null) {
			gatherHints(cause.getMessage(), cause.getCause(), hints);
		}
	}

}
