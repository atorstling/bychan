import com.torstling.tdop.CalculatorParser;
import org.junit.Test;

public class CompleteTest {

    @Test
    public void test() {
       new CalculatorParser().parse("((1-2)-3)-4");
    }
}
