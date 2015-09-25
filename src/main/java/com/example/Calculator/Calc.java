package com.example.Calculator;

import com.example.Calculator.Logging.Listeners.MyApplicationEventListener;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ivaa on 9/22/2015.
 */

@Path("")
@Singleton
public class Calc extends MyApplicationEventListener
{
    //This method illustrates usage of @GET request
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response Info()
    {
        String out = "This is calculator.";
        return Response.status(200).entity(out).build();
    }

    //This method illustrates usage of @GET request with parameter
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/constants/{name}")
    public Response ReadConstant(@PathParam("name") String name)
    {
        Double value = Constants.Get(name);
        if (value == null)
        {
            String out = "Constant is not exist.";
            return Response.status(400).entity(out).build();
        }

        return Response.status(200).entity(value).build();
    }

    //This method illustrates usage of @DELETE request with parameter
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/constants/{name}")
    public Response DeleteConstant(@PathParam("name") String name)
    {
        if (!Constants.IsExist(name))
        {
            String out = "Constant is not exist.";
            return Response.status(400).entity(out).build();
        }

        Constants.Delete(name);
        String out = "Deleted successfully.";
        return Response.status(200).entity(out).build();
    }

    //This method illustrates usage of @PUT request with parameter
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/constants/{name}")
    public Response AddConstant(Object value, @PathParam("name") String name)
    {
        double valueDouble;
        if (Constants.IsExist(name))
        {
            String out = "Constant is already exist.";
            return Response.status(400).entity(out).build();
        }

        try
        {
            valueDouble = (double)value;
        }
        catch (Exception e)
        {
            String out = "Value is wrong.";
            return Response.status(409).entity(out).build();
        }

        if (Constants.Add(name, valueDouble))
        {
            String out = "Constant was added successfully.";
            return Response.status(200).entity(out).build();
        }
        else
        {
            String out = "Something was wrong.";
            return Response.status(500).entity(out).build();
        }

    }

    //This method illustrates usage of @POST request with JSON in/out data
    @POST
    @Path("/bin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response convertToBinary(Object[] array)
    {
        long[] numbers = new long[array.length];

        try
        {
            int i=0;
            for (Object num : array)
            {
                numbers[i] = (long) num;
                if (numbers[i++] < 0)
                    throw new Exception();
            }
        }
        catch (Exception e)
        {
            String out = "Wrong input data.";
            return Response.status(400).entity(out).build();
        }

        long[] results = new long[numbers.length];
        int i=0;
        for (long num : numbers)
            results[i++] = ToBinary(0, num);

        return Response.status(200).entity(results).build();
    }

    //This two methods illustrates usage of @POST request with JSON in / Text out data
    @POST
    @Path("/multiply")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response multipliation(Object[] array)
    {
        double[] numbers = ConvertInputData(array);

        if (numbers == null)
        {
            String out = "Wrong input data.";
            return Response.status(400).entity(out).build();
        }

        double result = 1;
        for (double num : numbers)
            result *= num;

        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response add(Object[] array)
    {
        double[] numbers = ConvertInputData(array);

        if (numbers == null)
        {
            String out = "Wrong input data.";
            return Response.status(400).entity(out).build();
        }

        double result = 0;
        for (double num : numbers)
            result += num;

        return Response.status(200).entity(result).build();
    }


    private double[] ConvertInputData(Object[] data)
    {
        double [] result = new double[data.length];

        try
        {
            int i=0;
            for (Object num : data)
                result[i++] = (double) num;
        }
        catch (Exception e)
        {
            result = null;
        }

        return result;
    }

    private long ToBinary(long result, long number) {
        long remainder;

        if (number <= 1)
        {
            result = result*10 + number;
            return result;
        }

        remainder = number % 2;
        result = ToBinary(result, number >> 1);
        result = result*10 + remainder;

        return result;
    }
}
