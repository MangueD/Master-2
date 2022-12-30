package application.products;

import application.products.exceptions.ProductAlreadyExistsException;
import application.products.exceptions.ProductDeleteException;
import application.products.exceptions.ProductNotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class ProductRepository {
    private Map<String, Product> products; //liste des produits
    private String path;//chemin pour accéder aux données des produits stockés dans un fichier
    private JSONArray jsonArray;

    /**
     * Création d'un repertoire de produit à partir d'un fichier json
     */
    public ProductRepository(String path) throws IOException {
        this.products = new Hashtable<>();
        this.path = path;
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(path);
            Object o = jsonParser.parse(reader);
            jsonArray = (JSONArray)o;
            for(Object object : jsonArray){
                JSONObject productObject = (JSONObject)object;

                String name = (String)productObject.get("name");
                String id = (String)productObject.get("id");
                double price = Double.parseDouble((String)productObject.get("price"));
                SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
                Date date = parseDate.parse((String)productObject.get("date"));

                products.put(id, new Product(id, name, price, date));
            }
        }
        catch (FileNotFoundException e) {
            File file = new File(path);
            file.createNewFile();
            jsonArray = new JSONArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ParseException e) {
            File file = new File(path);
            file.createNewFile();
            jsonArray = new JSONArray();
        }
        catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Product> getAllProduct(){
        return products.values();
    }

    public Product getProduct(String id)throws ProductNotFoundException {
         if(!products.containsKey(id)){
             throw new ProductNotFoundException();
         }
         return products.get(id).copy();
    }

    /**
     * Ajout d'un produit dans le repertoire et aussi le mettre dans le fichier json
     */
    public void addProduct(String id, String name, double price, Date date) throws ProductAlreadyExistsException {
        if(products.containsKey(id)){
            throw new ProductAlreadyExistsException();
        }

        products.put(id, new Product(id, name, price, date));

        /*
         * ecriture dans le fichier
         */
        JSONObject nProduct = new JSONObject();
        nProduct.put("id", id);
        nProduct.put("name", name);
        nProduct.put("price", Double.toString(price));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        nProduct.put("date", strDate);
        jsonArray.add(nProduct);
        try{
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supression d'un produit dans le repertoire et dans le fichier json
     */
    public void deleteProduct(String id) throws ProductDeleteException {
        if(!products.containsKey(id)){
            throw new ProductDeleteException();
        }

        /*
         * suppression du produit dans le fichier
         */
        for(Object object : jsonArray){
            JSONObject productObject = (JSONObject) object;
            if(((String)productObject.get("id")).equals(id)){
                jsonArray.remove(productObject);
                break;
            }
        }
        try{
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        products.remove(id);
    }

    /**
     * Edition d'un produit dans le repertoire et dans le fichier json
     */
    public void editProduct(Product product) throws ProductNotFoundException {
        String id = product.getId();
        if(!products.containsKey(id)){
            throw new ProductNotFoundException();
        }

        products.put(id, product);

        /*
         * edition du produit dans le fichier
         */
        JSONObject nProduct = new JSONObject();
        for(Object object : jsonArray){
            JSONObject productObject = (JSONObject) object;
            if(((String)productObject.get("id")).equals(id)){
                nProduct = productObject;
                break;
            }
        }
        nProduct.put("id", id);
        nProduct.put("name", product.getName());
        nProduct.put("price", Double.toString(product.getPrice()));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(product.getExpirationDate());
        nProduct.put("date", strDate);
        try{
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
