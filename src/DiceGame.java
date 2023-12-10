import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DiceGame {
    public static void main(String[] args) {
        Scanner input1 = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();

        System.out.println("Welcome to DiceGame!");
        System.out.print("How many sides will your dice have? ");
        int numSides = input1.nextInt();

        System.out.print("Excellent! And how many people are playing? ");
        int numPlayers = input1.nextInt();
        System.out.println("Lovely!");
        input1.nextLine();

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Player " + i + ", what's your name? ");
            players.add(new Player(
                    input1.nextLine(),
                    new Die(numSides)
            ));
        }
        System.out.println("Wonderful! Let's play!");
        System.out.println();

        try (FileWriter fileWriter = new FileWriter("DiceGameOutput.txt")) {
            fileWriter.write("");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (Player player : players) {
            player.getDie().roll();

////          Print to screen version
//            System.out.println("Player " + player.getName() +
//                    " rolled a " + player.getDie().getValue() +
//                    " on a " + player.getDie().getNumSides() +
//                    "-sided die!");

//            file output version
            try (FileWriter fileWriter = new FileWriter("DiceGameOutput.txt", true)) {
                fileWriter.write("Player " + player.getName() +
                        " rolled a " + player.getDie().getValue() +
                        " on a " + player.getDie().getNumSides() +
                        "-sided die!\n");
            } catch (IOException e) {
                System.out.println(player.getName() + "'s roll could not be written");
            }
        }

        int winner = decideWinner(players);

        if (winner > -1) {

////          Print to screen version
//            System.out.println(players.get(winner).getName() + " won the game!");

//            file output version
            try (FileWriter fileWriter = new FileWriter("DiceGameOutput.txt", true)) {
                fileWriter.write(players.get(winner).getName() + " won the game!");
                System.out.println("Your game has printed to DiceGameOutput.txt.");
            } catch (IOException e) {
                System.out.println("The winner could not be written.");
            }
        } else { //Tie game
            int highRoll = 0;
            for (Player player : players) {
                highRoll = Math.max(highRoll, player.getDie().getValue());
            }

////          Print to screen version
//            System.out.println("It was a tie! These players all rolled a " + highRoll + ":");
//            for (Player player : players) {
//                if (highRoll == player.getDie().getValue()) {
//                    System.out.println(player.getName());
//                }
//            }

//            file output version
            try (FileWriter fileWriter = new FileWriter("DiceGameOutput.txt", true)) {
                fileWriter.write("It was a tie! These players all rolled a " + highRoll + ":\n");
                for (Player player : players) {
                    if (highRoll == player.getDie().getValue()) {
                        fileWriter.write(player.getName() + "\n");
                    }
                }
                System.out.println("Your game has printed to DiceGameOutput.txt.");
            } catch (IOException e) {
                System.out.println("There was a tie but it threw an error in writing.");
            }

        }
    }

    public static int decideWinner(ArrayList<Player> players) {
        int highRoll = 0;
        int winnerCount = 0;
        int winner = 0;
        for (Player player : players) {
            if (player.getDie().getValue() > highRoll) {
                winnerCount = 1;
            } else if (player.getDie().getValue() == highRoll) {
                winnerCount++;
            }
            highRoll = Math.max(highRoll, player.getDie().getValue());
            if (player.getDie().getValue() == highRoll) {
                winner = players.indexOf(player);
            }
        }
        if (winnerCount == 1) {
            return winner;
        } else { //Tie game
            return -1;
        }
    }
}
