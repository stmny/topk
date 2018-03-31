# Top K

## BluePrint

##  SalesOrder RESTful service :
   
   
### Tech stack :
      Spring boot Restful,
      DynamoDB,
      EC2, 
      Elastic_Beanstalk
      DynamoDB stream 
##  DynamoDB Stream Lambda :
### work flow :  
         .dynamo db change  --> trigger ddb stream --> 
         kinesis firehose handle event --> lambda function --> 
         store data to  S3 -->AWS Glue category crawler --->athena  table
### Tech stack :
            ddb stream,
            kinesis firehose
            lambda
            s3
            athena
## 3. query  api :
   
   ### url : https://e8ju0509wj.execute-api.us-west-2.amazonaws.com/demo/qurey
   
   ### method type : post 
   ### request body : 
            {
                "startDate": "2018-02-05",
                 "endDate": "2018-04-29",
                      "top": "20"
         }
   
   ### result :
   
    {
       "status": "success",
       "result": [
           {
               "product": "Nike Shoes",
               "total": 1636
           },
           {
               "product": "Adidas Shoes",
               "total": 76
           }
       ]
   }
   
   ###Tech stack
   Api Gateway 
   Lambda,
   Athena
   S3
