import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TestLocalDate {
    public static void main(String[] args) {
        Long s = LocalDate.now().atTime(23,59,59,999).toInstant(ZoneOffset.of("+8")).toEpochMilli() ;
        Long a = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long b = System.currentTimeMillis();
        System.out.println("a="+a);
        System.out.println("b="+b);
        System.out.println("s="+s);
    }
}

