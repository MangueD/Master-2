package application.main;

import application.main.exceptions.LogInException;
import application.main.exceptions.NoSuchDisplayException;
import application.main.exceptions.OptionNotFoundException;
import application.products.Product;
import application.products.ProductRepository;
import application.products.exceptions.ProductAlreadyExistsException;
import application.products.exceptions.ProductDeleteException;
import application.products.exceptions.ProductNotFoundException;
import application.users.User;
import application.users.UserRepository;
import application.users.exceptions.UserAlreadyExistsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class Main {
    /**
     * Macro pour les options
     */
    static final int OPTION_LOGIN = 100;
    static final int OPTION_SIGN = 101;
    static final int OPTION_DISPLAY_ALL_PRODUCT = 102;
    static final int OPTION_SEARCH_PRODUCT = 103;
    static final int OPTION_ADD_PRODUCT = 104;
    static final int OPTION_DELETE_PRODUCT = 105;
    static final int OPTION_EDIT_PRODUCT_MENU = 106;
    static final int OPTION_PRODUCT_MENU_BACK = 107;
    static final int OPTION_EDIT_PRODUCT_NAME = 108;
    static final int OPTION_EDIT_PRODUCT_PRICE = 109;
    static final int OPTION_EDIT_PRODUCT_DATE = 110;
    static final int OPTION_EDIT_PRODUCT_BACK = 111;
    static final int OPTION_EXIT = -1;

    /**
     * State du cli, en 3 états
     */
    static final int CLI_STATE_ROOT = 0;
    static final int CLI_STATE_PRODUCT_MENU = 1;
    static final int CLI_STATE_PRODUCT_EDIT = 2;
    static int currentCLiState;

    /**
     * Extra et ses clés
     */
    static Map<Integer,Object> extra;
    static final int EXTRA_EDIT_ID = 10; //la clé pour récuperer un produit extra pour l'éditer

    /**
     * Les données des utilisateurs et des produits
     */
    static final String usersFilePath = "data/users.json";
    static final String productsFilePath = "data/products.json";
    static UserRepository userRepository;
    static User currentUser;// l'utilisateur connecté actuellement, null sinon
    static ProductRepository productRepository;

    static final String separator = "----------------------------------";//spératateur pour le UI
    static boolean exit;//pour quitter l'application


    /**
     * fonction permettant d'afficher sur le terminal une liste d'option et d'en récupere la valeur de
     * l'option que l'utilisateur a choisi
     * @param state : l'état actuel du cli
     * @return : l'option choisit
     * @throws OptionNotFoundException : lorsque l'utilisateur n'a pas input la bonne valeur sur le CLI
     * @throws NoSuchDisplayException : lorsque l'état n'est pas reconnu
     */
    static int displayMenu(int state) throws IOException, OptionNotFoundException, NoSuchDisplayException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        switch (state) {
            case CLI_STATE_ROOT : {
                System.out.println(separator + "\n" +
                        "1. Connexion\n" +
                        "2. S'enregistrer\n" +
                        "3. Quitter\n" +
                        separator);
                String line = reader.readLine();

                switch (line) {
                    case "1":
                        return OPTION_LOGIN;
                    case "2":
                        return OPTION_SIGN;
                    case "3":
                        return OPTION_EXIT;
                }
            }break;
            case CLI_STATE_PRODUCT_MENU : {
                System.out.println(separator + "\n" +
                        "1. Afficher les produits\n" +
                        "2. Rechercher un produit\n" +
                        "3. Ajouter un produit\n" +
                        "4. Supprimer un produit\n" +
                        "5. Modifier un produit\n" +
                        "6. Retour");
                String line = reader.readLine();

                switch (line) {
                    case "1":
                        return OPTION_DISPLAY_ALL_PRODUCT;
                    case "2":
                        return OPTION_SEARCH_PRODUCT;
                    case "3":
                        return OPTION_ADD_PRODUCT;
                    case "4":
                        return OPTION_DELETE_PRODUCT;
                    case "5":
                        return OPTION_EDIT_PRODUCT_MENU;
                    case "6":
                        return OPTION_PRODUCT_MENU_BACK;
                }
            }break;
            case CLI_STATE_PRODUCT_EDIT : {
                System.out.println(separator + "\n" +
                        "1. Modifier le nom\n" +
                        "2. Modifier le prix\n" +
                        "3. Modifier la date d'expiration\n" +
                        "4. Retour");
                String line = reader.readLine();

                switch (line) {
                    case "1":
                        return OPTION_EDIT_PRODUCT_NAME;
                    case "2":
                        return OPTION_EDIT_PRODUCT_PRICE;
                    case "3":
                        return OPTION_EDIT_PRODUCT_DATE;
                    case "4":
                        return OPTION_EDIT_PRODUCT_BACK;
                }
            }break;
            default : throw new NoSuchDisplayException();
        }
        throw new OptionNotFoundException();
    }

    /**
     * Fonction qui s'occupe de la logique des options extraites par la foncton display()
     * @param option : l'option choisit par l'utilisateur
     */
    static void optionsHandler(int option){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        switch (option) {
            case OPTION_LOGIN : {//se connecter
                try {
                    System.out.print("id ");
                    String id = reader.readLine();

                    System.out.print("mot de passe ");
                    String password = reader.readLine();

                    if (!userRepository.logUser(id, password)) {
                        throw new LogInException();
                    }

                    currentCLiState = CLI_STATE_PRODUCT_MENU;
                    currentUser = userRepository.getUser(id);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (LogInException e) {
                    System.out.println("Utilisateur ou mot de passe incorrect");
                }
            }break;
            case OPTION_SIGN : {//s'inscrire
                try {
                    System.out.print("id ");
                    String id = reader.readLine();

                    System.out.print("nom ");
                    String name = reader.readLine();

                    System.out.print("age ");
                    int age = Integer.parseInt(reader.readLine());

                    System.out.print("email ");
                    String email = reader.readLine();

                    System.out.print("mot de passe ");
                    String password = reader.readLine();

                    currentUser = userRepository.createUser(id, name, age, email, password);
                    currentCLiState = CLI_STATE_PRODUCT_MENU;
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (UserAlreadyExistsException e) {
                    System.out.println("Utilisateur déjà existant");
                }
                catch (NumberFormatException e) {
                    System.out.println("Un nombre est attendu");
                }
            }break;
            case OPTION_DISPLAY_ALL_PRODUCT : {//afficher tous les produits
                for (Product p : productRepository.getAllProduct()) {
                    System.out.println(p);
                }
            }break;
            case OPTION_SEARCH_PRODUCT : {// rechercher un produit par ID
                try {
                    System.out.print("id ");
                    String id = reader.readLine();

                    Product product = productRepository.getProduct(id);
                    System.out.println(product);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (ProductNotFoundException e) {
                    System.out.println("Aucun produit correspondant");
                }
            }break;
            case OPTION_ADD_PRODUCT : {//ajouter un produit
                try {
                    System.out.print("id ");
                    String id = reader.readLine();

                    System.out.print("name ");
                    String name = reader.readLine();

                    System.out.print("price ");
                    double price = Double.parseDouble(reader.readLine());

                    System.out.print("date ");
                    SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = parseDate.parse(reader.readLine());

                    productRepository.addProduct(id, name, price, date);
                    System.out.println("Produit ajouté");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (ProductAlreadyExistsException e) {
                    System.out.println("Produit existant");
                }
                catch (ParseException e) {
                    System.out.println("Format de date incorrecte : dd/mm/yyyy");
                }
                catch (NumberFormatException e) {
                    System.out.println("Un nombre est attendu");
                }
            }break;
            case OPTION_DELETE_PRODUCT : {//supprimer un produit par ID
                try {
                    System.out.print("id ");
                    String id = reader.readLine();

                    productRepository.deleteProduct(id);
                    System.out.println("Produit supprimé");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (ProductDeleteException e) {
                    System.out.println("Erreur, produit à supprimer inexistant");
                }
            }break;
            case OPTION_EDIT_PRODUCT_MENU : {//pour aller au menu d'édition si le produit existe déjà
                try {
                    System.out.print("id ");
                    String id = reader.readLine();

                    //On garde le produit à modifier en tant qu'extra pour le sous menu d'édition
                    extra.put(EXTRA_EDIT_ID, productRepository.getProduct(id));

                    currentCLiState = CLI_STATE_PRODUCT_EDIT;
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (ProductNotFoundException e) {
                    System.out.println("Produit introuvable");
                }
            }break;
            case OPTION_PRODUCT_MENU_BACK : {//retourner au menu des produits
                currentCLiState = CLI_STATE_ROOT;
                currentUser = null;
            }break;
            case OPTION_EDIT_PRODUCT_NAME : {//modifier le nom du produit
                try {
                    System.out.print("nom ");
                    String name = reader.readLine();

                    Product product = (Product) extra.get(EXTRA_EDIT_ID);
                    product.setName(name);
                    productRepository.editProduct(product);

                    System.out.println("Nom modifié");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }
            }break;
            case OPTION_EDIT_PRODUCT_PRICE : {//modifier le prix d'un produit
                try {
                    System.out.print("prix ");
                    double price = Double.parseDouble(reader.readLine());

                    Product product = (Product)extra.get(EXTRA_EDIT_ID);
                    product.setPrice(price);
                    productRepository.editProduct(product);

                    System.out.println("Prix modifié");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (NumberFormatException e) {
                    System.out.println("Un nombre est attendu");
                }
                catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }
            }break;
            case OPTION_EDIT_PRODUCT_DATE : {//modifier la date de péremption d'un produit
                try {
                    System.out.print("Date ");
                    SimpleDateFormat parseDate = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = parseDate.parse(reader.readLine());

                    Product product = (Product) extra.get(EXTRA_EDIT_ID);
                    product.setExpirationDate(date);
                    productRepository.editProduct(product);

                    System.out.println("Date modifié");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                catch (ParseException e) {
                    System.out.println("Date incorrecte : dd/mm/yyyy");
                }
                catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }
            }break;
            case OPTION_EDIT_PRODUCT_BACK : {//retour au menu des produits
                currentCLiState = CLI_STATE_PRODUCT_MENU;
                extra.remove(EXTRA_EDIT_ID);
            }break;

            case OPTION_EXIT : {//quitter l'application
                exit = true;
            }break;
        }
    }

    static String macroToString(int macro){
        switch (macro) {
            case OPTION_LOGIN : {
                return "user_logging";
            }
            case OPTION_SIGN : {
                return "user_sign";
            }
            case OPTION_DISPLAY_ALL_PRODUCT : {
                return "display_product";
            }
            case OPTION_SEARCH_PRODUCT : {
                return "search_product";
            }
            case OPTION_ADD_PRODUCT : {
                return "add_product";
            }
            case OPTION_DELETE_PRODUCT : {
                return "delete_product";
            }
            case OPTION_EDIT_PRODUCT_MENU : {
                return "edit_product";
            }
            case OPTION_PRODUCT_MENU_BACK : {
                return "back_to_root";
            }
            case OPTION_EDIT_PRODUCT_NAME : {
                return "edit_product_name";
            }
            case OPTION_EDIT_PRODUCT_PRICE : {
                return "edit_product_price";
            }
            case OPTION_EDIT_PRODUCT_DATE : {
                return "edit_product_date";
            }
            case OPTION_EDIT_PRODUCT_BACK : {
                return "back_to_product_menu";
            }
            case OPTION_EXIT : {
                return "exit_app";
            }
            case CLI_STATE_ROOT : {
                return "root_menu";
            }
            case CLI_STATE_PRODUCT_MENU : {
                return "product_menu";
            }
            case CLI_STATE_PRODUCT_EDIT : {
                return "edit_menu";
            }
        }
        return "not supposed to show this";
    }

    /**
     * Initialisation des attributs de la classe
     */
    static public void init() throws IOException {
        currentCLiState = CLI_STATE_ROOT;
        userRepository = new UserRepository(usersFilePath);
        productRepository = new ProductRepository(productsFilePath);
        exit = false;
        extra = new Hashtable<>();
        currentUser = null;
    }

    public static void main(String[] args) throws IOException {
        init();

        while(!exit){
            int option;
            try {
                option = displayMenu(currentCLiState);
                optionsHandler(option);
            }
            catch (IOException | NoSuchDisplayException e) {
                throw new RuntimeException(e);
            }
            catch (OptionNotFoundException e) {
                System.out.println("Option non reconnue");

            }
        }
    }
}
