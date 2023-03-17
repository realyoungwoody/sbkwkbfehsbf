import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Fraction {

    private final String a;

    public Fraction (String input) throws Exception {
        Pattern no_extra_sign = Pattern.compile("^-?" + "[0-9]+" + "/" + "-?" + "[0-9]+$");
        Matcher ok = no_extra_sign.matcher(input);
        if (!ok.find()) {
            System.out.println("Это не дробь!");
            throw new Exception();
        }
        Pattern slash_zero = Pattern.compile("/-?0");
        Matcher zero = slash_zero.matcher(input);
        if (zero.find()){
            System.out.println("На ноль делить нельзя!");
            throw new Exception();
        }
        this.a = input;
    }

    public double make_double() {
        String f = String.valueOf(a);
        int slash = f.indexOf("/");
        int length = f.length();
        StringBuilder time = new StringBuilder(f);
        time.delete(slash, length);
        double first = Double.parseDouble(String.valueOf(time));
        time = new StringBuilder(f);
        time.delete(0, slash+1);
        double second = Double.parseDouble(String.valueOf(time));
        return (first/second);
    }

    public String toString() {
        return a;
    }

}

class Manipulations {

    static Fraction make_frac (double a) throws Exception {
        int b = (int) a;
        int zero = 0;
        while (b/a != 1){
            a = a * 10;
            b = (int) a;
            zero++;
            if (zero > 6){
                break;
            }
        }
        String one = "1";
        for (int i = 0; i < zero; i++){
            one = one + "0";
        }
        String time = b + "/" + one;
        return new Fraction(time);
    }

    public static double razdelitel (String input) throws Exception {
        double answer;
        String regex = "^-?" + "[0-9]+" + "/" + "-?" + "[0-9]+" + "\s" + "[-*/+]" + "\s" + "-?" + "[0-9]+" + "/" + "-?" + "[0-9]+";
        Pattern firs_frac = Pattern.compile(regex);
        Matcher get_first_frac = firs_frac.matcher(input);
        if (get_first_frac.find()){
            int end = get_first_frac.end();
            int length = input.length();

            StringBuilder time = new StringBuilder(input);
            StringBuilder another = new StringBuilder();
            if ((length - end) > 0){
                another = time.delete(0, end);
                time = new StringBuilder(input);
                time.delete(end, length);
            }

            answer = all_manipulations(String.valueOf(time));
            Fraction already_get = make_frac(answer);

            while (another.length() != 0){
                Pattern another_frac = Pattern.compile("^\s" + "[-*/+]" + "\s" + "-?" + "[0-9]+" + "/" + "-?" + "[0-9]+");
                Matcher get_next_frac = another_frac.matcher(String.valueOf(another));
                if (get_next_frac.find()){
                    end = get_next_frac.end();
                    length = String.valueOf(another).length();
                    time = another;
                    if ((length - end) > 0){
                        time.delete(end, length);
                    }
                    answer = all_manipulations(already_get + String.valueOf(time));
                    already_get = make_frac(answer);
                    another.delete(0, end);
                }
                else{
                    System.out.println("Введено не верное выражение!");
                    answer = 999999999;
                    break;
                }
            }
        }
        else{
            System.out.println("Введено не верное выражение!");
            answer = 999999999;
        }
        return answer;
    }

    private static double all_manipulations (String input) throws Exception {
        int something = input.indexOf(" ") + 1;
        int length = input.length();

        StringBuilder time = new StringBuilder(input);
        time.delete(something - 1, length);
        Fraction first = new Fraction(String.valueOf(time));

        time = new StringBuilder(input);
        time.delete(0, something + 2);
        Fraction second = new Fraction(String.valueOf(time));

        time = new StringBuilder(input);
        String what_do = String.valueOf(time.charAt(something));

        double result = 0;
        if (what_do.equals("+")){
            result = first.make_double()+second.make_double();
        }
        if (what_do.equals("*")){
            result = first.make_double()*second.make_double();
        }
        if (what_do.equals("-")){
            result = first.make_double()-second.make_double();
        }
        if (what_do.equals("/")){
            result = first.make_double()/second.make_double();
        }

        return result;
    }

}