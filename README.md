# simpleGRPC
Simple gRPC app build with Java (8) with h2 in memory db, no spring


### How to use this gRPC project

- run mvn clean install
- this will install dependencies
- Open with intellij and build, this will generate gRPC proto java classes. 
Then run main RoomReservationServer (right click on the file in project explorer) followed by
RoomReservationClient. Output is to the console


#### Hotel Object

Hotel object; used in requests and responses
```
message Hotel {
  int64 hotel_id = 1;
  string name = 2;
  string location = 3;
  int32 room_count = 4;
  int32 max_capacity = 5;
}
```

RoomReservationService service
- The following api functions are available when the server application is running
```
service RoomReservationService {
  rpc getAllHotelsAvailability(EmptyRequest) returns (AllHotelsAvailabilityResponse);
  rpc checkAvailability(CheckAvailabilityRequest) returns (CheckAvailabilityResponse);
  rpc reserveRoom(ReservationRequest) returns (ReservationResponse);
  rpc cancelRoomReservation(ReservationRequest) returns (ReservationResponse);
}
```

AllHotelsAvailabilityResponse
- Contains a ListOfHotels, which can be converted to List<Hotel> in java
```
message AllHotelsAvailabilityResponse {
ListOfHotels hotelList = 1;
}
```
ListOfHotels
```
message ListOfHotels {
repeated Hotel hotels=1;
}
```

CheckAvailabilityRequest
- Check availability at hotel for given hotel id
- Currently only supports hotel id, but could possibly be changed to accept hotel name too. Otherwise change parameter
type to long for just hotel id
```
message CheckAvailabilityRequest {
Hotel hotel = 1;
}
```

CheckAvailabilityResponse
- Contains the number of rooms available at the requested hotel
```
message CheckAvailabilityResponse {
int32 availableRooms = 1;
}
```

ReservationRequest
- Used to make or cancel a reservation at hotel for given hotel id
```
message ReservationRequest {
Hotel hotel = 1;
}
```

ReservationResponse
- Currently returns a boolean to indicate success or failure but can be expanded to an enum
```
message ReservationResponse {
bool result = 1;
}
```

#### ToDo
- Add TLS authentication
- Add caching
- Mobile app to test


#### Miscellaneous
- Tried using new versions of Java (11, 18) however due to building on m1, dependecies either didnt exist. Tried
several workarounds but nothing worked.
- Tried with spring however couldnt get the SpringApplication part running together with the gRPC part
- Tried using hibernate however possibly due to Java 8 restrictions and not using spring, database connection/ population issues