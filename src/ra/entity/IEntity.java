package ra.entity;

import java.util.List;

public interface IEntity<T> {
    void inputData();
    void displayData(List<T> listData);
}
