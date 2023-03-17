import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражиние (например: -1/3 - 4/5 + 33/7) или quit: ");
        String input = in.nextLine();
        while (!input.equals("quit")){
            try{
                double answer = Manipulations.razdelitel(input);
                if (answer != 999999999){
                    Fraction frac = Manipulations.make_frac(answer);
                    System.out.println("Ответ: " + frac + " = " + answer);
                }
            } catch(Exception ex){
                System.out.println("Попробуйте снова");
            }
            System.out.println();
            System.out.print("Введите новое выражение или quit, если хотите закончить: ");
            input = in.nextLine();
        }
    }
}