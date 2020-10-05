package P5.DAO;

import P5.Domein.OVChipkaart;
import P5.Domein.Product;
import P5.Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{

    private Connection connection;
    private OVChipkaartDAO odao;

    public ProductDAOPsql(Connection connection){
        this.connection = connection;
    }

    public void setOdao(OVChipkaartDAO odao){
        this.odao = odao;
    }
    @Override
    public boolean save(Product product) {
        boolean result = false;
        String query = "INSERT INTO product (product_nummer,naam,beschrijving,prijs) VALUES (?,?,?,?)";
        String queryOv = "INSERT INTO ov_chipkaart_product (kaart_nummer,product_nummer) VALUES (?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1,product.getProduct_nummer());
            pst.setString(2,product.getNaam());
            pst.setString(3,product.getBeschrijving());
            pst.setDouble(4,product.getPrijs());

            result = pst.execute();

            for (OVChipkaart ov : product.getOvChipkaarten()) {
                PreparedStatement pstOV = connection.prepareStatement(queryOv);
                pstOV.setInt(1,ov.getKaartnummer());
                pstOV.setInt(2,product.getProduct_nummer());
                result = pstOV.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public boolean update(Product product) {
        boolean result = false;
        String query = "UPDATE product SET product_nummer = ?, naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";

        String deletequery = "DELETE FROM ov_chipkaart_product where product_nummer = ?";
        String insertQuery = "INSERT INTO ov_chipkaart_product (kaart_nummer,product_nummer) VALUES (?,?)";

        try {
            PreparedStatement pstDelete = connection.prepareStatement(deletequery);
            pstDelete.setInt(1,product.getProduct_nummer());
            result = pstDelete.execute();

            for (OVChipkaart ov : product.getOvChipkaarten()) {
                PreparedStatement pstOV = connection.prepareStatement(insertQuery);
                pstOV.setInt(1,ov.getKaartnummer());
                pstOV.setInt(2,product.getProduct_nummer());
                result = pstOV.execute();
            }

            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1,product.getProduct_nummer());
            pst.setString(2,product.getNaam());
            pst.setString(3,product.getBeschrijving());
            pst.setDouble(4,product.getPrijs());
            pst.setInt(5,product.getProduct_nummer());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Product product) {
        boolean result = false;
        String query = "DELETE FROM product WHERE product_nummer = ?";
        String queryOV = "DELETE FROM ov_chipkaart_product where product_nummer = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            PreparedStatement pstOv = connection.prepareStatement(queryOV);
            pstOv.setInt(1,product.getProduct_nummer());
            result = pstOv.execute();
            pst.setInt(1,product.getProduct_nummer());
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        String query = "SELECT p.product_nummer,naam,beschrijving,prijs FROM ov_chipkaart o INNER JOIN ov_chipkaart_product ocp ON o.kaart_nummer = ocp.kaart_nummer INNER JOIN product p ON p.product_nummer = ocp.product_nummer WHERE o.kaart_nummer = ?";
        List<Product> products = new ArrayList<>();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1,ovChipkaart.getKaartnummer());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Product temp = new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4));
                products.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Product temp = new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4));
                temp.setOvChipkaarten(odao.findByProduct(temp));
                products.add(temp);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findByNummer(int id) {
        String query = "SELECT * From product where product_nummer=?";
        Product temp = null;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                temp = new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4));
            }
        } catch (SQLException e) {
            System.out.println("Het vinden van een reiziger met het gegeven ID is mislukt");
        }
        return temp;
    }

}
