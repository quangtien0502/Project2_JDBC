package ra.business;

import ra.entity.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBusiness {
    public static List<Product> listProduct(Connection conn) {
        List<Product> listProduct = new ArrayList<>();
        try {
            CallableStatement callableStatement = conn.prepareCall("{CALL selectProduct()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("product_id"), rs.getString("product_name"), rs.getString("manufacturer"), rs.getDate("created"), rs.getShort("batch"), rs.getInt("quantity"), rs.getBoolean("product_status"));
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listProduct;
    }
}
