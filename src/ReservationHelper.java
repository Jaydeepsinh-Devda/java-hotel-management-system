import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReservationHelper {

    void newReservation(Connection con, Scanner sc) {
        System.out.print("Enter Name of the Guest: ");
        String guestName = sc.next();

        System.out.print("Enter the room Number: ");
        int roomNumber = sc.nextInt();

        System.out.print("Enter the Contact Number: ");
        String contactNumber = sc.next();

        String addQuery = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(addQuery);
            preparedStatement.setString(1, guestName);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.setString(3, contactNumber);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Data Added Successfully");
            } else {
                System.out.println("Failed to add Data into DB");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

    void viewReservations(Connection con) {
        String viewQuery = "SELECT * from reservations";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(viewQuery);
            ResultSet result = preparedStatement.executeQuery();

            System.out.println("Reservations Data:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest Name      | Room Number   | Contact Number       | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (result.next()) {
                int reservationId = result.getInt("reservation_id");
                String guestName = result.getString("guest_name");
                int roomNumber = result.getInt("room_number");
                String contactNumber = result.getString("contact_number");
                String reservationDate = result.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);

            }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateReservation(Connection con, Scanner sc) {
        System.out.print("Enter Reservation ID: ");
        int reservationId = sc.nextInt();

        try {

            if(isReservationExits(con, reservationId)){
                System.out.print("Enter Guest Name: ");
                String guestName = sc.next();

                System.out.print("Enter the room Number: ");
                int roomNumber = sc.nextInt();

                System.out.print("Enter the Contact Number: ");
                String contactNumber = sc.next();

                String updateQuery = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
                preparedStatement.setString(1, guestName);
                preparedStatement.setInt(2, roomNumber);
                preparedStatement.setString(3, contactNumber);
                preparedStatement.setInt(4, reservationId);

                int res = preparedStatement.executeUpdate();
                if(res > 0){
                    System.out.println("Data Updated Successfully!!");
                }else {
                    System.out.println("Failed to Update Data");
                }
            } else {
                System.out.println("No reservations found for this id");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    void deleteReservation(Connection con, Scanner sc) {
        System.out.print("Enter the reservation id: ");
        int reservationId = sc.nextInt();

        if(isReservationExits(con, reservationId)){
            String deleteQuery = "DELETE FROM reservations WHERE reservation_id = ?";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, reservationId);

                int result = preparedStatement.executeUpdate();
                if(result > 0) {
                    System.out.println("Data is Successfully Deleted");
                } else {
                    System.out.println("Failed to Delete Data");
                }

            } catch (SQLException e) {
                System.out.println("Failed to Delete Data");
            }

        }else {
            System.out.println("No reservations found for this id");
        }
    }

    boolean isReservationExits(Connection con, int reservationId) {
        String findReservationIdQuery = "SELECT * FROM reservations WHERE reservation_id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(findReservationIdQuery);
            preparedStatement.setInt(1, reservationId);

            ResultSet result = preparedStatement.executeQuery();
            return result.next();

        } catch (SQLException e){
            return false;
        }
    }
}
