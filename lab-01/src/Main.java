import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Locale.setDefault(Locale.forLanguageTag("uk-UA"));
        System.out.println("Лабораторна робота №1. Варіант 22");
        System.out.println("Виконавець: Мирослав Шевчук\n");

        // а) Обчислюємо точні значення для порівняння похибок.
        double x1 = 6.0 / 11.0, a1 = 0.545;
        double x2 = Math.sqrt(83.0), a2 = 9.11;
        // Абсолютна похибка - модуль різниці.
        double abs1 = Math.abs(x1 - a1);
        double abs2 = Math.abs(x2 - a2);
        // Відносна похибка порівнює точність різних чисел.
        double rel1 = abs1 / Math.abs(x1);
        double rel2 = abs2 / Math.abs(x2);
        System.out.println("а) Порівняння точності");
        System.out.printf("6/11: Δ = %.10f; δ = %.8f%%%n", abs1, rel1 * 100);
        System.out.printf("√83:  Δ = %.10f; δ = %.8f%%%n", abs2, rel2 * 100);
        System.out.println(rel2 < rel1
                ? "Точніша рівність: √83 ≈ 9,11\n"
                : "Точніша рівність: 6/11 ≈ 0,545\n");

        // б) Задане наближене число та межа його абсолютної похибки.
        BigDecimal a = new BigDecimal("3.7832");
        BigDecimal error = new BigDecimal("0.0043");
        // Соті початкового числа правильні: 0,0043 <= 0,005.
        BigDecimal rounded = a.setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundingError = a.subtract(rounded).abs();
        // Загальна межа враховує початкову похибку та округлення.
        BigDecimal resultError = error.add(roundingError);
        System.out.println("б) Округлення початкових правильних цифр");
        System.out.printf("Результат: %.2f%n", rounded);
        System.out.printf("Похибка округлення: %.4f%n", roundingError);
        System.out.printf("Гранична абсолютна похибка: %.4f%n", resultError);
        // Для 3,78 нова похибка 0,0075 > 0,005.
        // Округлення до десятих гарантує всі цифри у вузькому сенсі.
        BigDecimal strict = a.setScale(1, RoundingMode.HALF_UP);
        BigDecimal strictError = error.add(a.subtract(strict).abs());
        System.out.println("Усі цифри правильні у вузькому сенсі:");
        System.out.printf("%.1f ± %.4f%n%n", strict, strictError);

        // в) Половина одиниці останнього розряду - 0,0005.
        BigDecimal c = new BigDecimal("2.678");
        BigDecimal unit = new BigDecimal("0.001");
        BigDecimal limitAbs = unit.divide(new BigDecimal("2"));
        // Оцінка відносної похибки через наближене число.
        BigDecimal relApprox = limitAbs.divide(c, 16, RoundingMode.HALF_UP);
        // Строга верхня межа використовує найменше можливе точне x.
        BigDecimal relStrict = limitAbs.divide(
                c.subtract(limitAbs), 16, RoundingMode.CEILING);
        System.out.println("в) Правильні цифри у вузькому розумінні");
        System.out.printf("Гранична абсолютна похибка: %.4f%n", limitAbs);
        System.out.printf("Відносна похибка (Δ/a): %.8f%%%n",
                relApprox.doubleValue() * 100);
        System.out.printf("Строга межа (Δ/(a-Δ)): %.8f%%%n",
                relStrict.doubleValue() * 100);
        // Альтернатива: правильні цифри у широкому розумінні.
        System.out.printf("У широкому сенсі: Δ = %.3f; δ (Δ/a) = %.8f%%%n",
                unit, unit.doubleValue() / c.doubleValue() * 100);
    }
}
