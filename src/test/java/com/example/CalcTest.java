package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import com.example.Listeners.TestListener;
import org.glassfish.grizzly.http.server.HttpServer;

import org.testng.Assert;

import org.testng.annotations.*;

@Listeners(TestListener.class)
public class CalcTest {

    private HttpServer server;
    private WebTarget target;

    @BeforeSuite
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @AfterSuite
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @DataProvider(name = "multiplication")
    public static Object[][] multiplicationArray() {
        return new Object[][] {
                {new Object[]{2.4, 1.2}, 2.88 },
                {new Object[]{-2.0, 5.6}, -11.2},
                {new Object[]{3.3, 0.0}, 0.0},
                {new Object[]{3.3, "as"}, null},
                {new Object[]{3.3, true}, null}};
    }

    @DataProvider(name = "add")
    public static Object[][] addArray() {
        return new Object[][] {
                {new Object[]{2.4, 1.2}, 3.6 },
                {new Object[]{-2.0, 5.6}, 3.6},
                {new Object[]{3.3, "as"}, null},
                {new Object[]{3.3, true}, null}};
    }

    @DataProvider(name = "bin")
    public static Object[][] binArray() {
        return new Object[][] {
                {new Object[]{1, 2, 3, 4, 5}, new long[]{1, 10, 11, 100, 101}},
                {new Object[]{1, "as", 3, 4, 5}, null},
                {new Object[]{1, true, 3, 4, 5}, null}};
    }



    @Test(dataProvider = "multiplication"
            , description = "Scenarios for multiplication method.")
    public void C01(Object []array, Double expectedResult)
    {
        String path = "multiply";

        Response response = target.path(path).request().post(
                Entity.entity(array, MediaType.APPLICATION_JSON));

        if (expectedResult == null)
            Assert.assertEquals(response.getStatus(), 400);
        else
        {
            Assert.assertEquals(response.getStatus(), 200);
            double result = response.readEntity(double.class);
            Assert.assertEquals(expectedResult, result, 0.000001);
        }
    }

    @Test(dataProvider = "add"
            , description = "Scenarios for add method.")
    public void C02(Object []array, Double expectedResult)
    {
        String path = "add";

        Response response = target.path(path).request().post(
                Entity.entity(array, MediaType.APPLICATION_JSON));

        if (expectedResult == null)
            Assert.assertEquals(response.getStatus(), 400);
        else
        {
            Assert.assertEquals(response.getStatus(), 200);
            double result = response.readEntity(double.class);
            Assert.assertEquals(expectedResult, result, 0.000001);
        }
    }

    @Test(dataProvider = "bin"
            , description = "Scenarios for convertion to bnary method.")
    public void C03(Object []array, long []expectedResults)
    {
        String path = "bin";

        Response response = target.path(path).request().post(
                Entity.entity(array, MediaType.APPLICATION_JSON));

        if (expectedResults == null)
            Assert.assertEquals(response.getStatus(), 400);
        else
        {
            Assert.assertEquals(response.getStatus(), 200);

            long[] results = response.readEntity(long[].class);
            int i=0;
            for (long result : results)
                Assert.assertEquals(expectedResults[i++], result);
        }
    }

    @Test(description = "Scenarios for constants")
    //public void C04(String name, Object value)
    public void C04()
    {
        String name = "t";
        double value = 3.3;
        String path = String.format("constants/%s", name);
        String wrongPath = String.format("constants/%s", "fail");

        //add new constants
        Response response = target.path(path).request().put(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), 200);

        //try to add existing constant
        response = target.path(path).request().put(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), 400);

        //try to read non existing constant
        response = target.path(wrongPath).request().get();
        Assert.assertEquals(response.getStatus(), 400);

        //read new constant
        response = target.path(path).request().get();
        double result = response.readEntity(double.class);
        Assert.assertEquals(value, result, 0.000001);
        Assert.assertEquals(response.getStatus(), 200);

        //try to delete non existing constant
        response = target.path(wrongPath).request().delete();
        Assert.assertEquals(response.getStatus(), 400);

        //delete new constant
        response = target.path(path).request().delete();
        Assert.assertEquals(response.getStatus(), 200);
    }
}
