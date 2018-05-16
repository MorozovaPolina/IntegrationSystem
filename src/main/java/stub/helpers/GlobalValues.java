package stub.helpers;

import stub.session.AbstractSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalValues {
    public static Map<Integer, AbstractSession> Sessions = new HashMap(); /*список идентификаторов всех открытых на
    момент запуска программы транзакций и всех транзакций,
    созданых во время данной сессии (открытых, упавших и успешно завершенных). */
    public static int SessionNumber=1; /* идентификатор транзакции. У каждой транзакции уникальный номер.
    Чтобы не лезть и не проверять, какой следующий номер свободен,
    будем хранить следующий возможный номер в данной переменной*/
    public static ArrayList<String> NewMessagesLog = new ArrayList<>();
    public static ArrayList<String> Log = new ArrayList<>();

}
