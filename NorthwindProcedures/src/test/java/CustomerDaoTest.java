import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class CustomerDaoTest {

    BasicDataSource dataSource = new BasicDataSource();
    CustomerDao customerDao = new CustomerDao(dataSource);

    @Test
    void getCustomerOrderHistory() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));

        String userInput = "BLONP";

        ArrayList<NorthwindData> historyList = customerDao.getCustomerOrderHistory(userInput);
        Order order = (Order) historyList.get(0);
        String actual = order.productName;

        Assertions.assertEquals("Alice Mutton", actual);
    }
}