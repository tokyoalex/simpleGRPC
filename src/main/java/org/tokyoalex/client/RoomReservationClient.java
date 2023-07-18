package org.tokyoalex.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.tokyoalex.*;

import java.util.List;

public class RoomReservationClient {
    public static void main(String[] args) {

        // Channel is used by the client to communicate with the server using the domain localhost and port 5004.
        // This is the port where our server is starting.
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5004)
                .usePlaintext()
                .build();

        // Auto generated stub class with the constructor wrapping the channel.
        RoomReservationServiceGrpc.RoomReservationServiceBlockingStub stub = RoomReservationServiceGrpc.newBlockingStub(channel);

        // get all hotels availability
        EmptyRequest emptyReq = EmptyRequest.newBuilder().build();
        AllHotelsAvailabilityResponse allAvailabilityResponse = stub.getAllHotelsAvailability(emptyReq);
        ListOfHotels hotelsList = allAvailabilityResponse.getHotelList();
        List<Hotel> hotels = hotelsList.getHotelsList();
        System.out.println("Number of hotels available: " + hotels.size());

        for(Hotel h : hotels)   {
            System.out.println("Hotel name: '" + h.getName() + "', location: " + h.getLocation() + ", rooms available: " + h.getRoomCount() + ", max capacity: " + h.getMaxCapacity());
        }

        // Check individual hotel availability
        CheckAvailabilityRequest availabilityRequest = CheckAvailabilityRequest.newBuilder()
                .setHotel(Hotel.newBuilder()
                        .setHotelId(1)
                        .build())
                .build();
        CheckAvailabilityResponse availabilityResponse = stub.checkAvailability(availabilityRequest);

        System.out.println("Number of rooms available: " + availabilityResponse.getAvailableRooms());

        // make a reservation
        ReservationRequest reservationRequest = ReservationRequest.newBuilder()
                .setHotel(Hotel.newBuilder()
                        .setHotelId(1)
                        .build())
                .build();

        ReservationResponse resvResponse = stub.reserveRoom(reservationRequest);
        System.out.println("Reservation success: " + resvResponse.getResult());


//        System.out.println("### Pause before cancelling reservation ###");
//        try {
//            Thread.sleep(5000);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // cancel reservation
        reservationRequest = ReservationRequest.newBuilder()
                .setHotel(Hotel.newBuilder()
                        .setHotelId(1)
                        .setName("Park Hyatt")
                        .build())
                .build();

        resvResponse = stub.cancelRoomReservation(reservationRequest);
        System.out.println("Reservation cancellation success: " + resvResponse.getResult());
    }
}
