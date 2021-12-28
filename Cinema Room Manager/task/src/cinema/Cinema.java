package cinema;

import java.util.Scanner;

public class Cinema {

    private static int purchasedTickets = 0;
    private static int currentIncome = 0;

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();
        char[][] schema = createSchema(rows, seatsPerRow);
        displayMenu(scanner, schema);

        /*
        int profit = calculatesProfit(rows, seatsPerRow);
        System.out.printf("Total income:%n$%d%n", profit);

         */

    }

    private static void displayMenu(Scanner scanner, char[][] schema) {
        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            String option = scanner.next();
            switch (option) {
                case "1":
                    showSchema(schema);
                    break;
                case "2":
                    buyTicketForASeat(scanner, schema);
                    break;
                case "3":
                    showStatistics(schema);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void showStatistics(char[][] schema) {
        int rows = schema.length;
        int seatsPerRow = schema[0].length;
        int totalSeats = rows * seatsPerRow;
        float percentage =  ((float) purchasedTickets / (float) totalSeats) * 100;
        char percentageSign = '%';
        int totalIncome = calculatesProfit(rows, seatsPerRow);
        System.out.printf("Number of purchased tickets: %d%n" , purchasedTickets);
        System.out.printf("Percentage: %.2f%c%n", percentage, percentageSign);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", totalIncome);
    }

    private static char[][] createSchema(int rows, int seatsPerRow) {
        char[][] schema = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                schema[i][j] = 'S';
            }
        }
        return schema;
    }

    private static void showSchema(char[][] schema) {
        System.out.println("Cinema:");
        int aux;
        System.out.print("  ");
        for (int i = 0; i < schema[0].length; i++) {
            aux = i + 1;
            System.out.print(aux + " ");
        }
        System.out.println();
        for (int i = 0; i < schema.length; i++) {
            aux = i + 1;
            System.out.print(aux + " ");
            for (int j = 0; j < schema[i].length; j++) {
                System.out.print(schema[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void buyTicketForASeat(Scanner scanner, char[][] schema) {
        while (true) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();
            try {
                char chosenSeat = schema[row - 1][seat - 1];
                if (chosenSeat == 'B') {
                    System.out.println("That ticket has already been purchased!");
                    continue;
                } else {
                    markChosenSeat(schema, row, seat);
                    calculatesTicketPrice(schema, row, seat);
                    purchasedTickets++;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException aibe) {
                System.out.println("Wrong input!");
                continue;
            }
        }
    }

    private static void calculatesTicketPrice(char[][] schema, int row, int seat) {
        int ticketPrice;
        int rows = schema.length;
        int columns = schema[0].length;
        int totalSeats = rows * columns;
        int half;
        if (totalSeats <= 60) {
            ticketPrice = 10;
        } else {
            half = rows % 2 == 0 ? rows / 2 : ((rows - 1) / 2);
            ticketPrice = row > half ? 8 : 10;
        }
        System.out.printf("Ticket price: $%d%n", ticketPrice);
        currentIncome += ticketPrice;
    }

    private static void markChosenSeat(char[][] schema, int row, int seat) {
        schema[row - 1][seat - 1] = 'B';
    }

    private static int calculatesProfit(int rows, int seatsPerRow) {
        int profit;
        int totalSeats = rows * seatsPerRow;
        int pricePerTicket;
        if (totalSeats <= 60) {
            pricePerTicket = 10;
            profit = totalSeats * pricePerTicket;
        } else {
            int frontHalf;
            int backHalf;
            if (rows % 2 == 0) {
                frontHalf = (rows / 2) * seatsPerRow;
                backHalf = frontHalf;
            } else {
                frontHalf = ((rows - 1) / 2);
                backHalf = (rows - frontHalf) * seatsPerRow;
                frontHalf *= seatsPerRow;
            }
            pricePerTicket = 10;
            int priceFrontHalf = frontHalf * pricePerTicket;
            pricePerTicket = 8;
            int priceBackHalf = backHalf * pricePerTicket;
            profit = priceFrontHalf + priceBackHalf;
        }
        return profit;
    }
}