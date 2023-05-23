package com.klotski.Controllers;

import com.klotski.Model.Disposition;
import com.klotski.Model.Move;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents the object used to obtain the next move based on the actual config
 * This class works calling an API RESTFULL on AWS
 */
public class NextMoveGateway
{
    // The URL endpoint + GET resources => searchnextklotskidisposition
    private static final String API_URL = "https://6kwyyq6sec.execute-api.eu-south-1.amazonaws.com/dev/searchnextklotskidisposition";
    // The first parameter for the HTTP query
    private static final String FIRST_PARAM_NAME = "disp_number";
    // The second parameter for the HTTP query
    private static final String SECOND_PARAM_NAME = "schema";

    private static final String PARAM_STARTER = "?";
    private static final String PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEP = "=";

    // I don't need a Constructor -> I use the default one

    /**
     * This method is reserved to obtain the next Move to solve the current disposition
     * This method invoke an HTTP API REST (GET method) located on AWS
     * @return the next suggested move to solve the passed disposition
     */
    public Move GetNextMove(int dispositionNumber, Disposition actualDisposition)
    {
        try
        {
            String formattedUrl = API_URL + PARAM_STARTER +
                    FIRST_PARAM_NAME+KEY_VALUE_SEP+
                    dispositionNumber+PARAM_SEPARATOR+actualDisposition.getTextDisposition();
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        }
        catch(IOException e)
        {

        }


        return null;
    }


}
