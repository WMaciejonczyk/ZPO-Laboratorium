import java.util.Scanner;

/**
 * Zadanie 3.
 */
public class Ex3 {
    /**
     * Program do konwersji szestnastkowego kodu RGB na dziesiętny
     * @param args kod szestnastkowy RGB
     * @throws IllegalArgumentException gdy kod szestnastkowy podany przez użytkownika nie składa się z 6 znaków
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj kod HEX: ");
        String k = scanner.nextLine();

        scanner.close();

        if (k.length() != 6) {
            throw new IllegalArgumentException("Podany kod powinien składać się z sześciu znaków.");
        }

        try {
            String s1 = k.substring(0, 2);
            String s2 = k.substring(2, 4);
            String s3 = k.substring(4, 6);

            int r = Integer.parseInt(s1, 16);
            int g = Integer.parseInt(s2, 16);
            int b = Integer.parseInt(s3, 16);
            System.out.printf("(" + r + "," + b + "," + g + ")");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
