package org.tokyoalex.server;

import io.grpc.stub.StreamObserver;
import org.tokyoalex.*;
import org.tokyoalex.server.database.HotelEntity;
import org.tokyoalex.server.database.HotelRepository;

import java.util.ArrayList;
import java.util.List;


public class RoomReservationServiceImpl extends RoomReservationServiceGrpc.RoomReservationServiceImplBase {

    @Override
    public void getAllHotelsAvailability(EmptyRequest e, StreamObserver<AllHotelsAvailabilityResponse> responseObserver) {

        List<HotelEntity> hotels = HotelRepository.getAllHotelsAvailability();

        System.out.println("Number of hotels available: " + hotels.size());

        List<Hotel> hotelList = new ArrayList<>();
        for(HotelEntity h : hotels) {
            Hotel temp = Hotel.newBuilder().setHotelId(1).setName(h.getName()).setLocation(h.getLocation())
                    .setRoomCount(h.getRooms_available()).setMaxCapacity(h.getMax_capacity()).build();
            hotelList.add(temp);
        }
        ListOfHotels listOfHotels = ListOfHotels.newBuilder().addAllHotels(hotelList).build();

        AllHotelsAvailabilityResponse availabilityResponse = AllHotelsAvailabilityResponse
                .newBuilder()
                .setHotelList(listOfHotels)
                .build();

        // Send the response to the client.
        responseObserver.onNext(availabilityResponse);

        // Notifies the customer that the call is completed.
        responseObserver.onCompleted();
    }

    @Override
    public void checkAvailability(CheckAvailabilityRequest request, StreamObserver<CheckAvailabilityResponse> responseObserver) {
        Long hotelId = request.getHotel().getHotelId();

        HotelEntity h = HotelRepository.getHotelAvailability(hotelId);

        System.out.println("Availability of hotel id " + hotelId + " name: " +h.getName() + " is " + h.getRooms_available());
        String resultMsg = "Availability of hotel id " + hotelId + " name: " +h.getName() + " is " + h.getRooms_available();

        CheckAvailabilityResponse availabilityResponse = CheckAvailabilityResponse
                .newBuilder()
                .setAvailableRooms(h.getRooms_available())
                .build();

        // Send the response to the client.
        responseObserver.onNext(availabilityResponse);

        // Notifies the customer that the call is completed.
        responseObserver.onCompleted();
    }

    @Override
    public void reserveRoom(ReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {

        Long hotelId = request.getHotel().getHotelId();

        System.out.println("Attempting to reserve hotel id " + hotelId);
        String resultMsg = "Attempting to reserve hotel id " + hotelId;

        Boolean res = HotelRepository.makeReservation(hotelId);

        ReservationResponse reservationResponse = ReservationResponse
                .newBuilder()
                .setResult(res)
                .build();

        // Send the response to the client.
        responseObserver.onNext(reservationResponse);

        // Notifies that the call is completed.
        responseObserver.onCompleted();
        System.out.println(resultMsg);
    }

    @Override
    public void cancelRoomReservation(ReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {

        Long hotelId = request.getHotel().getHotelId();

        System.out.println("Attempting to cancel reservation at hotel id " + hotelId);
        String resultMsg = "Attempting to cancel reservation at hotel id " + hotelId;

        Boolean res = HotelRepository.cancelReservation(hotelId);

        ReservationResponse reservationResponse = ReservationResponse
                .newBuilder()
                .setResult(res)
                .build();

        // Send the response to the client.
        responseObserver.onNext(reservationResponse);

        // Notifies that the call is completed.
        responseObserver.onCompleted();
        System.out.println(resultMsg);
    }

}
