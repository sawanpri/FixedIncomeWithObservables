import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.domain.*;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.nio.file.Paths;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        String[] paths ={"src/main/resources/MockTradeData/trade1.json",
                        "src/main/resources/MockTradeData/trade2.json",
                        "src/main/resources/MockTradeData/trade3.json"};

        Observable<List<Trade>> publisher = new Publisher().getPublisher(paths);

        try {
            new Subscriber(publisher,"instance 1");
//            new Subscriber(publisher,"instance 2");
//            new Subscriber(publisher,"instance 3");

            publisher.blockingLast();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }



}
