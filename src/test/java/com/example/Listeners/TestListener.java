package com.example.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by ivaa on 9/23/2015.
 */
public class TestListener implements ITestListener {


    public void onTestStart(ITestResult var1)
    {
        String out = String.format("Test %s (%s) was started.", var1.getName(), var1.getMethod().getDescription());
        //System.out.println(out);
    }

    public void onTestSuccess(ITestResult var1)
    {
        String out = String.format("Test %s (%s) was passed.", var1.getName(), var1.getMethod().getDescription());
        System.out.println(out);
    }

    public void onTestFailure(ITestResult var1)
    {
        String out = String.format("Test %s (%s) was failed.", var1.getName(), var1.getMethod().getDescription());
        System.out.println(out);
    }

    public void onTestSkipped(ITestResult var1)
    {
        String out = String.format("Test %s (%s) was skipped.", var1.getName(), var1.getMethod().getDescription());
        System.out.println(out);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult var1)
    {

    }

    public void onStart(ITestContext var1)
    {

    }

    public void onFinish(ITestContext var1)
    {

    }
}
