package com.mycompany.core.framework.reports;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestInvokeListener
  implements IInvokedMethodListener
{
  public static IInvokedMethod invokedMethod;

  @Override
public void afterInvocation(IInvokedMethod paramIInvokedMethod, ITestResult paramITestResult)
  {
  }

  @Override
public void beforeInvocation(IInvokedMethod paramIInvokedMethod, ITestResult paramITestResult)
  {
    invokedMethod = paramIInvokedMethod;
    if (paramIInvokedMethod.getTestMethod().isTest())
      paramITestResult.setAttribute("invokeCountAtttrib", Integer.valueOf(paramIInvokedMethod.getTestMethod().getCurrentInvocationCount() + 1));
  }
}