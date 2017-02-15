package dispatchers;

import akka.dispatch.MessageDispatcher;
import play.libs.Akka;

/**
 * Created by Usuario on 27/08/2016.
 */
public class AkkaDispatcher {
    public static MessageDispatcher jdbcDispatcher =  Akka.system().dispatchers().lookup("contexts.jdbc-dispatcher");

}
