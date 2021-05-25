## Web application for booking cinema seats

### Get started
* Install [java 11](https://openjdk.java.net/install/)
* Install [docker](https://docs.docker.com/engine/install/)
* Install [maven](https://maven.apache.org/install.html)
* Run following commands
```
$ mvn package
$ docker-compose up
```

### API

#### 1. Get list of seats with statuses

* 200:
  * request
    ```
    curl --request GET \
      --url 'http://localhost:8081/seats?movieSessionId=1' \
      --header 'Content-Type: application/json;'
    ```
  * response
    ```json
    {
      "success": true,
      "data": [
        {
          "bookingId": null,
          "movieSessionId": 5,
          "number": 1,
          "status": "FREE"
        },
        ...,
        {
          "bookingId": 8,
          "movieSessionId": 5,
          "number": 5,
          "status": "BOOKED"
        },
        ...
      ],
      "errorMessage": null
    }
    ```

* 404:
  * request
    ```
    curl --request GET \
      --url 'http://localhost:8081/seats?movieSessionId=0' \
      --header 'Content-Type: application/json;'
    ```
  * response
    ```json
    {
      "success": false,
      "data": null,
      "errorMessage": "Movie session with id = '0' doesn't exist"
    }
    ```

#### 2. Book seats

* 200:
  * request
    ```
    curl --request POST \
      --url http://localhost:8081/seats \
      --header 'Content-Type: application/json' \
      --data '{
    	"movieSessionId": 1,
    	"seats": [-1, 3, 4, 5, 5, 6, 7, 11, 1, 11, 11, 11, 11, 2]
    }'
    ```
  * response
    ```json
    {
      "success": true,
      "data": {
        "movieSessionId": 1,
        "bookingStatus": {
          "-1": "FAILED",
          "3": "FAILED",
          "4": "FAILED",
          "5": "FAILED",
          "6": "BOOKED",
          "7": "BOOKED",
          "11": "FAILED",
          "1": "FAILED",
          "2": "FAILED"
        }
      },
      "errorMessage": null
    }
    ```

* 404:
  * request
    ```
    curl --request POST \
      --url http://localhost:8081/seats \
      --header 'Content-Type: application/json' \
      --data '{
    	"movieSessionId": 0,
    	"seats": [-1, 3, 4, 5, 5, 6, 7, 11, 1, 11, 11, 11, 11, 2]
    }'
    ```
  * response
    ```json
    {
      "success": false,
      "data": null,
      "errorMessage": "Movie session with id = '0' doesn't exist"
    }
    ```
