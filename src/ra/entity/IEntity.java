package ra.entity;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public interface IEntity<T> {
    void inputData(Scanner scanner, Connection conn);
    void displayData(List<T> listData);
}
