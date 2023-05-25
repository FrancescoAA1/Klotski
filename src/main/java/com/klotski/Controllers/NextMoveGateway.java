package com.klotski.Controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klotski.Model.Disposition;
import com.klotski.Model.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    private static final String RESPONSE_KEY = "ans";
    private static final String API_ERROR_VALUE = "0";
    private static final int HTTP_OK = 200;

    // I don't need a Constructor -> I use the default one

    /**
     * This method is reserved to obtain the next Move to solve the current disposition
     * This method invoke an HTTP API REST (GET method) located on AWS
     * @param dispositionNumber: the identifier of the initial configuration from which you started
     * @param actualDisposition: the actual string representation of the disposition
     * @return the next suggested move to solve the passed disposition if the current disposition is present
     * otherwise returns null
     */
    public Move GetNextMove(int dispositionNumber, Disposition actualDisposition)
    {
        // I want to connect to the API located on AWS
        try
        {
            // encode in the correct format the string disposition representation
            String schema = URLEncoder.encode(actualDisposition.getTextDisposition(), StandardCharsets.UTF_8);
            //prepare the API endpoint with all params
            String formattedUrl = API_URL + PARAM_STARTER +
                    FIRST_PARAM_NAME+KEY_VALUE_SEP+
                    dispositionNumber+PARAM_SEPARATOR+
                    SECOND_PARAM_NAME+KEY_VALUE_SEP+schema;
            URL url = new URL(formattedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set the REST Method
            connection.setRequestMethod("GET");
            // exec the query and save the HTTP response code
            int responseCode = connection.getResponseCode();
            // test the response code: if 502 error -> I return null
            // if it's 200 the query was successful then I fetch the data

            if(responseCode == HTTP_OK)
            {
                // obtain data from Body response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                }
                in.close();

                // the response is a JSON and I want to convert it with the related library

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.toString());

                String textNextMove = jsonNode.get(RESPONSE_KEY).asText();

                if(!textNextMove.equals(API_ERROR_VALUE))
                {
                    return Move.convertToMove(textNextMove);
                }
                else return null;
            }
            else return null;
        }
        catch(IOException e)
        {
            throw new RuntimeException("Unable to connect to API Socket or invalid params");
        }
    }

}
