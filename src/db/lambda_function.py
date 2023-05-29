import boto3
import json
s3 = boto3.client('s3')

def lambda_handler(event, context):
    # get the query string params
    # the API REST method GET gives me 2 parameters: 
    # the start disposition numeber that represents the file name in my s3 bucket
    # the move counter to search the next line in the file 
    # if it's 0 i need to search it 
    file_name = event['queryStringParameters']['disp_number']
    schema = event['queryStringParameters']['schema']
    
    #prepare the response body 
    res_body = {}
    res_body['disp_number'] = file_name
    res_body['schema'] = schema
    tmp = searchInS3bucket(file_name, schema)
    
    code = 200
    
    if tmp == 502:
        code = 502
        res_body['ans'] = 'Internal Server Error'
    else: 
        res_body['ans'] = tmp
        
    # prepare the http response 
    http_res = {}
    http_res['statusCode'] = code
    http_res['headers'] = {}
    http_res['headers']['Content-Type'] = 'application/json'
    http_res['body'] = json.dumps(res_body)
    
    return http_res

def searchInS3bucket(file_name, schema):
    # the S3 bucket name
    bucket_name = 'klotskibucket'
    # add to the file name given by GET query my extension format 
    file_key = 'disp_'+ file_name + '.ksl'

    try:
        # get the file from s3 bucket using boto API 
        file_obj = s3.get_object(Bucket=bucket_name, Key=file_key)

        # I use the get_object method to obtain the file object from S3. 
        # This returns a dictionary that includes various metadata about the object, 
        # including the content of the file itself, which can be accessed via the 'Body' key.
        # To read the content, I read it (read()) and then decode it (decode('utf-8')) to convert the bytes into a string."
        file_content = file_obj['Body'].read().decode('utf-8')
        # split the content of the file on each line
        lines = file_content.split('\n')
        # now i need to split alla lines in 2 parts the move component and the schema component
        # if I found my schema I return the next move
        next_move = 0
        found = False
        
        
        for line in lines:
            move_schema = line.split('|')
            
            if found==False: 
                clean = move_schema[1].replace("\r", "") # removing \r if si present
                if clean == schema:
                    found = True
            else:
                next_move = move_schema[0]
                break
        

        return next_move
    except Exception as e:
        return str(e)