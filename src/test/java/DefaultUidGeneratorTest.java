import com.ikki.tools.DefaultUidGenerator;
import com.ikki.tools.UidGenerater;
import org.junit.Test;

public class DefaultUidGeneratorTest {


    @Test
    public void Test() {
        UidGenerater defaultUidGenerator = new DefaultUidGenerator();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; i < 10; j++) {
                System.out.println(defaultUidGenerator.nextId());
            }

        }


    }
}
