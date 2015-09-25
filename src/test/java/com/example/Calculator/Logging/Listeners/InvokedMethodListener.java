package com.example.Calculator.Logging.Listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Created by ivaa on 9/23/2015.
 */
public class InvokedMethodListener  implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult)
    {
        if (method.isTestMethod())
        {
            System.out.println("before invocation of " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult)
    {
        if (method.isTestMethod())
            System.out.println("after invocation " + method.getTestMethod().getMethodName());
    }
}
