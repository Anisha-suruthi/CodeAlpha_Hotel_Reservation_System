package Reservation;
import java.util.*;

class Room {
    private int roomId;
    private String roomType;
    private double pricePerNight;
    private List<Integer> availableRoomNumbers;

    // Constructor
    public Room(int roomId, String roomType, double pricePerNight, List<Integer> roomNumbers) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.availableRoomNumbers = new ArrayList<>(roomNumbers);
    }

    // Getters
    public int getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public List<Integer> getAvailableRoomNumbers() {
        return availableRoomNumbers;
    }

    // Method to book a room
    public boolean bookRoom(int roomNumber) {
        if (availableRoomNumbers.contains(roomNumber)) {
            availableRoomNumbers.remove((Integer) roomNumber);
            return true;
        }
        return false;
    }

    // Method to release a room
    public void releaseRoom(int roomNumber) {
        availableRoomNumbers.add(roomNumber);
    }
}

class Reservation {
    private static int nextBookingId = 1;
    private int bookingId;
    private int roomId;
    private int roomNumber;
    private int noOfGuests;
    private String customerName;
    private double totalPrice;
    private int noOfNights;

    // Constructor
    public Reservation(int roomId, int roomNumber, int noOfGuests, String customerName, double totalPrice, int noOfNights) {
        this.bookingId = nextBookingId++;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.noOfGuests = noOfGuests;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.noOfNights = noOfNights;
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNoOfGuests() {
        return noOfGuests;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNoOfNights() {
        return noOfNights;
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize rooms with specific room numbers
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, "Single", 50.0, Arrays.asList(101, 102, 103)));
        rooms.add(new Room(2, "Double", 80.0, Arrays.asList(201, 202, 203)));
        rooms.add(new Room(3, "Suite", 120.0, Arrays.asList(301, 302, 303)));

        List<Reservation> reservations = new ArrayList<>();

        while (true) {
            System.out.println("1. Search available rooms");
            System.out.println("2. Make reservation");
            System.out.println("3. View booking details");
            System.out.println("4. Cancel reservation");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1,2,3,4,5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println();

            switch (choice) {
                case 1:
                    // Search available rooms
                    System.out.println("Available Rooms:");
                    for (Room room : rooms) {
                        System.out.println("Room ID: " + room.getRoomId() + ", Type: " + room.getRoomType() +
                                ", Price/Night: $" + room.getPricePerNight() + ", Available Room Numbers: " +
                                room.getAvailableRoomNumbers());
                    }
                    System.out.println();
                    break;
                case 2:
                    // Make reservation
                    System.out.println("Enter room ID for reservation: ");
                    int roomId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Room selectedRoom = null;
                    for (Room room : rooms) {
                        if (room.getRoomId() == roomId) {
                            selectedRoom = room;
                            break;
                        }
                    }

                    if (selectedRoom != null && !selectedRoom.getAvailableRoomNumbers().isEmpty()) {
                        System.out.println("Enter number of guests: ");
                        int noOfGuests = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Enter number of days: ");
                        int noOfNights = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Enter your name: ");
                        String customerName = scanner.nextLine();

                        System.out.println("Available Room Numbers: " + selectedRoom.getAvailableRoomNumbers());
                        System.out.println("Enter room number to book: ");
                        int roomNumber = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (selectedRoom.bookRoom(roomNumber)) {
                            double totalPrice = selectedRoom.getPricePerNight() * noOfNights * noOfGuests;
                            System.out.println("Total Price: $" + totalPrice);

                            // Process payment (placeholder)
                            System.out.println("Payment processed successfully!");

                            // Book the room
                            reservations.add(new Reservation(roomId, roomNumber, noOfGuests, customerName, totalPrice, noOfNights));
                            System.out.println("Reservation successful! Your booking ID is: " + reservations.get(reservations.size() - 1).getBookingId() + "\n");
                        } else {
                            System.out.println("Room number not available.");
                        }
                    } else {
                        System.out.println("Invalid room ID or no rooms available.");
                    }
                    break;
                case 3:
                    // View booking details
                    System.out.println("Enter booking ID: ");
                    int bookingId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    boolean found = false;
                    for (Reservation reservation : reservations) {
                        if (reservation.getBookingId() == bookingId) {
                            found = true;
                            System.out.println("Booking Details:");
                            System.out.println("Booking ID: " + reservation.getBookingId());
                            System.out.println("Room ID: " + reservation.getRoomId());
                            System.out.println("Room Number: " + reservation.getRoomNumber());
                            System.out.println("Number of Guests: " + reservation.getNoOfGuests());
                            System.out.println("Customer Name: " + reservation.getCustomerName());
                            System.out.println("Number of Days: " + reservation.getNoOfNights());
                            System.out.println("Total Price: $" + reservation.getTotalPrice());
                            System.out.println();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Booking not found.");
                    }
                    break;
                case 4:
                    // Cancel reservation
                    System.out.println("Enter booking ID to cancel: ");
                    bookingId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Iterator<Reservation> iterator = reservations.iterator();
                    found = false;
                    while (iterator.hasNext()) {
                        Reservation reservation = iterator.next();
                        if (reservation.getBookingId() == bookingId) {
                            found = true;
                            // Release the room
                            for (Room room : rooms) {
                                if (room.getRoomId() == reservation.getRoomId()) {
                                    room.releaseRoom(reservation.getRoomNumber());
                                    break;
                                }
                            }
                            iterator.remove();
                            System.out.println("Reservation cancelled successfully.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Booking not found.");
                    }
                    break;
                case 5:
                    System.out.println("THANK YOU");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}