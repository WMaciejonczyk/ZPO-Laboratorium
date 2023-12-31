import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.round;

/**
 * Klasa podrzędna tworząca i operująca na obiektach reprezentujących wektory zespolone na płaszczyźnie. <p>
 * Autor: Wojciech Maciejończyk 268337
 */
public class ComplexNumber extends Vector2D {
    /**
     * Konstruktor przyjmujący dwa parametry
     * @param a część rzeczywista wektora
     * @param b część urojona wektora
     */
    public ComplexNumber (double a, double b) {
        super(a, b);
    }

    /**
     * Metoda umożliwiająca pobranie liczby zespolonej od użytkownika w postaci x+iy, x+i*y, x-iy, x-i*y, x + i y,
     * x + i + y, x - i y, x - i * y, r*exp*(i*phi), r * exp * (i * phi), r*exp(iphi), rexp(iphi).
     * @return liczba zespolona podana przez użytkownika
     * @throws InvalidInputException gdy wprowadzone przez użytkownika dane nie są reprezentacją liczby zespolonej
     */
    public static ComplexNumber Input () {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().toLowerCase();
        sc.close();
        double rzecz;
        double uroj;
        input = input.replaceAll(" ", "").replaceAll("i", "").replaceAll("j", "").replaceAll("\\)", "").replaceAll("\\*", "");
        // wszelkie znaki, które nie są szukanymi liczbami x, y (lub r, phi), są usuwane poza znakami dodawania, odejmowania
        // oraz nawias ) dla formy r*exp*(i*phi), gdzie nawias ( stanowi miejsce splitu stringa
        if (input.contains("exp")) { // formy typu r*exp*(i*phi)
            input = input.replaceAll("exp", "");
            String[] substring = input.split("\\(");
            if (substring.length != 2) {
                throw new InvalidInputException("Nie udało się podzielić danych wejściowych na dwie liczby.");
            }
            else {
                double modul = Double.parseDouble(substring[0]);
                double argument = Double.parseDouble(substring[1]);
                rzecz = modul * Math.cos(argument);
                uroj = modul * Math.sin(argument);
            }
        }
        else if (input.contains("+")) { // formy x+y
            String[] substring = input.split("\\+");
            if (substring.length != 2) {
                throw new InvalidInputException("Nie udało się podzielić danych wejściowych na dwie liczby.");
            }
            else {
                rzecz = Double.parseDouble(substring[0]);
                uroj = Double.parseDouble(substring[1]);
            }
        }
        else if (input.replaceFirst("-", "").contains("-")) { // formy x-y
            String[] substring = input.replaceFirst("-", "").split("-");
            String updatedSubstring = "-" + substring[0];
            if (substring.length != 2) {
                throw new InvalidInputException("Nie udało się podzielić danych wejściowych na dwie liczby.");
            }
            else {
                rzecz = Double.parseDouble(updatedSubstring);
                uroj = Double.parseDouble("-" + substring[1]);
            }
        }
        else {
            throw new InvalidInputException("Podane dane wejściowe nie spełniają warunków liczby zespolonej.");
        }
        return new ComplexNumber(rzecz, uroj);
    }
    /**
     * Metoda obliczająca moduł liczby zespolonej.
     * @return moduł liczby zespolonej
     */
    public double modul () {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * Metoda obliczająca argument liczby zespolonej.
     * @return argument liczby zespolonej
     * @throws IllegalArgumentException gdy liczba zespolona jest wektorem zerowym
     */
    public double argument () {
        if (x == 0 && y == 0) {
            throw new IllegalArgumentException("Argument liczby jest nieoznaczony.");
        }
        return Math.atan2(y, x);
    }

    /**
     * Metoda dodająca liczbę zespoloną do danej instancji liczby zespolonej.
     * @param v obiekt klasy Vector2D jako liczba zespolona
     * @return nowa liczba zespolona będąca suma dwóch innych
     */
    @Override
    public Vector2D dodawanie (Vector2D v) {
        return new ComplexNumber(x + v.getX(), y + v.getY());
    }

    /**
     * Metoda obliczająca sumę dwóch zadanych liczb zespolonych.
     * @param n1 pierwsza liczba zespolona
     * @param n2 druga liczba zespolona
     * @return nowa liczba zespolona będąca sumą dwóch innych
     */
    public static ComplexNumber dodawanieStat (ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber(n1.getX() + n2.getX(), n1.getY() + n2.getY());
    }

    /**
     * Metoda odejmująca liczbę zespoloną od danej instancji liczby zespolonej.
     * @param v obiekt klasy Vector2D jako liczba zespolona (odjemnik)
     * @return nowa liczba zespolona będąca różnicą dwóch innych
     */
    @Override
    public Vector2D odejmowanie (Vector2D v) {
        return new ComplexNumber(x - v.getX(), y - v.getY());
    }

    /**
     * Metoda obliczająca różnicę dwóch zadanych liczb zespolonych.
     * @param n1 pierwsza liczba zespolona
     * @param n2 druga liczba zespolona
     * @return nowa liczba zespolona będąca różnicą dwóch innych
     */
    public static ComplexNumber odejmowanieStat (ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber(n1.getX() - n2.getX(), n1.getY() - n2.getY());
    }

    /**
     * Metoda obliczająca iloczyn liczby zespolonej i danej instancji liczby zespolonej.
     * @param n obiekt klasy Vector2D jako liczba zespolona
     * @return nowa liczba zespolona będąca iloczynem dwóch innych
     */

    public ComplexNumber mnozenie (ComplexNumber n) {
        return new ComplexNumber(x * n.getX() - y * n.getY(),y * n.getX() + x * n.getY());
    }

    /**
     * Metoda obliczająca iloczyn dwóch zadanych liczb zespolonych.
     * @param n1 pierwsza liczba zespolona
     * @param n2 druga liczba zespolona
     * @return nowa liczba zespolona będąca iloczynem dwóch innych
     */
    public static ComplexNumber mnozenieStat (ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber(n1.getX() * n2.getX() - n1.getY() * n2.getY(), n1.getY() * n2.getX() + n1.getX() * n2.getY());
    }

    /**
     * Metoda dzieląca daną instancję liczby zespolonej przez inną liczbę zespoloną.
     * @param n liczba zespolona (dzielnik)
     * @return nowa liczba zespolona będąca ilorazem dwóch innych
     * @throws IllegalArgumentException gdy dzielnik jest zerem
     */
    public ComplexNumber dzielenie (ComplexNumber n) {
        if (n.getX() == 0 && n.getY() == 0) {
            throw new IllegalArgumentException("Mianownik nie może być zerowy.");
        }
        double mianownik = Math.pow(n.getX(), 2) + Math.pow(n.getY(), 2);
        return new ComplexNumber ((x * n.getX() + y * n.getY()) / mianownik, (y * n.getX() - x * n.getY()) / mianownik);
    }

    /**
     * Metoda obliczająca iloraz dwóch liczb zespolonych.
     * @param n1 pierwsza liczba zespolona (dzielna)
     * @param n2 druga liczba zespolona (dzielnik)
     * @return nowa liczba zespolona będąca ilorazem dwóch innych
     * @throws IllegalArgumentException gdy dzielnik jest zerem
     */
    public static ComplexNumber dzielenieStat (ComplexNumber n1, ComplexNumber n2) {
        if (n2.getX() == 0 && n2.getY() == 0) {
            throw new IllegalArgumentException("Mianownik nie może być zerowy.");
        }
        double mianownik = Math.pow(n2.getX(), 2) + Math.pow(n2.getY(), 2);
        return new ComplexNumber ((n1.getX() * n2.getX() + n1.getY() * n2.getY()) / mianownik,
                (n1.getY() * n2.getX() - n1.getX() * n2.getY()) / mianownik);
    }

    /**
     * Metoda obliczająca daną potęgę danej instancji liczby zespolonej.
     * @param p wykładnik
     * @return nowa liczba zespolona będąca wynikiem potęgowania innej
     * @throws IllegalArgumentException gdy wykładnik = 0 i moduł = 0
     */
    public ComplexNumber potegowanie (double p) {
        if (p == 0 && modul() == 0) {
            throw new IllegalArgumentException("Podstawa i wykładnik nie mogą być równocześnie zerem.");
        }
        double modul = Math.pow(modul(), p);
        double rzecz = modul * Math.cos(p * argument());
        double uroj = modul * Math.sin(p * argument());
        return new ComplexNumber(rzecz, uroj);
    }

    /**
     * Metoda obliczająca daną potęgę zadanej liczby zespolonej.
     * @param n liczba zespolona
     * @param p wykładnik
     * @return nowa liczba zespolona będąca wynikiem potęgowania innej
     * @throws IllegalArgumentException gdy wykładnik = 0 i moduł = 0
     */
    public static ComplexNumber potegowanieStat (ComplexNumber n, double p) {
        if (p == 0 && n.modul() == 0) {
            throw new IllegalArgumentException("Podstawa i wykładnik nie mogą być równocześnie zerem.");
        }
        double modul = Math.pow(n.modul(), p);
        double rzecz = modul * Math.cos(p * n.argument());
        double uroj = modul * Math.sin(p * n.argument());
        return new ComplexNumber(rzecz, uroj);
    }

    /**
     * Metoda zwracająca reprezentację biegunową liczby zespolonej.
     * @return reprezentacja biegunowa po zaokrągleniu do dwóch miejsc po przecinku
     */
    public String repBieg () {
        return "z = (" + round(modul() * 100.0) / 100.0 + ", " + round(argument() * 100.0) / 100.0 + ")";
    }

    /**
     * Metoda porównujaca instancję liczby zespolonej z zadanym obiektem.
     * @param o zadany obiekt
     * @return true/false w zależności od zgodności tych dwóch obiektów
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber number = (ComplexNumber) o;
        return Double.compare(number.getX(), getX()) == 0 && Double.compare(number.getY(), getY()) == 0;
    }

    /**
     * Metoda zwracająca reprezentację liczby zespolonej jako ciąg znaków.
     * @return postać wykładnicza i kanoniczna liczby zespolonej
     */
    @Override
    public String toString () {
        String exp;
        String kan;
        if (y < 0) {
            exp = round(modul() * 100.0) / 100.0 + "exp(-i" + (-round(argument() * 100.0) / 100.0) + ")";
            kan = "z = " + round(x * 100.0) / 100.0 + " - i" + round(-y * 100.0) / 100.0;
        }
        else {
            exp = round(modul() * 100.0) / 100.0 + "exp(i" + round(argument() * 100.0) / 100.0 + ")";
            kan = "z = " + round(x * 100.0) / 100.0 + " + i" + round(y * 100.0) / 100.0;
        }
        return exp + "\n" + kan;
    }

    /**
     * Metoda odpowiedzialna za odczytywanie danych z pliku i zwracająca tablicę, która je zawiera
     * @param fileDirectory ścieżka pliku
     * @return tablica z danymi związanymi z liczbami zespolonymi
     */
    public static ArrayList<String> readComplexData(String fileDirectory) {
        ArrayList<String> array = new ArrayList<>();
        File myFile = new File(fileDirectory);
        try (BufferedReader br = new BufferedReader(new FileReader(myFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] fragments = line.split("[\\s, +]");
                    String t;
                    double Re;
                    double Im;
                    double mod;
                    double arg;
                    if (fragments.length == 3) {
                        t = fragments[0];
                        Re = Double.parseDouble(fragments[1]);
                        Im = Double.parseDouble(fragments[2].replaceAll("i", ""));
                        ComplexNumber number = new ComplexNumber(Re, Im);
                        mod = round(number.modul() * 100000.0) / 100000.0;
                        arg = round(number.argument() * 100000.0) / 100000.0;
                        array.add(t);
                        array.add(String.valueOf(mod));
                        array.add(String.valueOf(arg));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * Metoda wpisująca do pliku dane związane z liczbami zespolonymi
     * @param data dane do wpisania
     * @param overwrite decyzja, czy dopisać dane do już istniejącego pliku, czy nadpisać go
     */
    public static void writeToFile(ArrayList<String> data, boolean overwrite) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("out_data.out", !overwrite))) {
            bw.write("# t mod arg");
            bw.newLine();
            for (int i = 0; i < data.size(); i += 3) {
                bw.write(data.get(i) + " " + data.get(i + 1) + " " + data.get(i + 2));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
