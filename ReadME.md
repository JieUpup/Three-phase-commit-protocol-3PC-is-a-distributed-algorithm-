# Key-Value service
A simple key value store service. The service will only receive string key and string value without spaces.

## Operation format
```
# Sample key-value store operation
# PUT <key1> <value1>
# GET <key1>
# DELETE <key1>
```

## Run the server
```
cd CS6650HW4/src
docker build -f ./dockerfile .
./deploy.sh
./deploy_server2.sh
./deploy_server3.sh




```

## Run the  Client
```
# server2 will not respond to any request to mock failure
./run_client.sh myclient my-server 4080
./run_client.sh myclient2 my-server-3 4080

# Then you could write key value store command in the terminal 

```