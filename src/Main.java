import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true){
            Connection con = DBConnection.startConnection();

            if(con != null){
                ReservationHelper reservationHelper = new ReservationHelper();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. New Reservation");
                System.out.println("2. View Reservation");
                System.out.println("3. Update Reservation");
                System.out.println("4. Delete Reservation");
                System.out.println("0. Exit");

                Scanner sc = new Scanner(System.in);

                System.out.print("Enter your choice: ");
                int userInput = sc.nextInt();

                switch (userInput){
                    case 1:
                        // Add New Reservation Method Call
                        reservationHelper.newReservation(con, sc);
                        break;
                    case 2:
                        // View Reservation Method Call
                        reservationHelper.viewReservations(con);
                        break;
                    case 3:
                        // Update Reservation Method Call
                        reservationHelper.updateReservation(con, sc);
                        break;
                    case 4:
                        // Delete Reservation Call
                        reservationHelper.deleteReservation(con, sc);
                        break;
                    case 0:
                        // Exit the Program
                        DBConnection.stopConnection(con);
                        sc.close();
                        return;
                    default:
                        System.out.println("Please enter the correct choice which are given!!");
                }
            } else {
                System.out.println("Problem connecting with DB");
            }
        }
    }
}