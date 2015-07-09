package com.odcgroup.integrationfwk.ui.decorators.core;

import org.eclipse.core.runtime.Status;

public class DecoratorStatus extends Status
{
  public DecoratorStatus(
    int type,
    int code,
    String message,
    Throwable exception)
  {
    super(type, "hello", code, message, exception);
  }
  
  public DecoratorStatus(int code, String message)
  {
    this(code, code, message, null);
  }
  
  public DecoratorStatus(int code, String message, Throwable exception)
  {
    this(code, code, message, exception);
  }
}
