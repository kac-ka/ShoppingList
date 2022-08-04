package com.jsf.shoppingList.DataLayer;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import com.microsoft.sqlserver.jdbc.*;

public class DbConnect {

    public static Connection con;

    private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Resource(name = "java:com.microsoft.sqlserver.jdbc.SQLServerDataSource")
    private static SQLServerDataSource ds;

    public static boolean Connect(String dbName){


        String conStrUrl = "jdbc:sqlserver://SQL5107.site4now.net\\SQLEXPRESS:1433;user=;password=;databaseName=";

        try {

            ds = new SQLServerDataSource();
            ds.setURL(conStrUrl);
            con = ds.getConnection();

            return true;
        } catch (SQLException e) {
            System.out.println("Jejda, nastala SQL chyba: ");
            e.printStackTrace();
            return false;
        } catch (Exception ex){
            System.out.println("Jejda, nastala chyba: ");
            ex.printStackTrace();
            return false;
        }
    }

    public static List<AltUnitValue> getAltUnitValue(int ingredientRowId){
        try{
            String strSQL = "SELECT id, name_cz, name_eng, isShownG, isShowMl FROM unit WHERE NOT (isShownG = 0 AND isShowMl = 0);";
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery(strSQL);
            List<AltUnitValue> altUnitValueList = new LinkedList<>();

            while (rs.next()){
                AltUnitValue altUnitValue = new AltUnitValue();
                altUnitValue.setUnitID(rs.getInt("id"));
                altUnitValue.setIngredientRowId(ingredientRowId);
                altUnitValue.setUnitNameCZ(rs.getString("name_cz"));
                altUnitValue.setUnitNameEN(rs.getString("name_eng"));
                altUnitValue.setShownG(rs.getBoolean("isShownG"));
                altUnitValue.setShownMl(rs.getBoolean("isShowMl"));
                altUnitValueList.add(altUnitValue);
            }
            return altUnitValueList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public static Map<Integer, List<String>> getCategory(){
        try{
            String strSQL = "SELECT id, category_name_cz, category_name_eng FROM category;";
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery(strSQL);
            List<String> categoryNames;
            Map<Integer, List<String>> categoryMap = new HashMap<>();

            while (rs.next()){
                int categoryID = rs.getInt("id");
                categoryNames = new LinkedList<>();
                categoryNames.add(rs.getString("category_name_cz"));
                categoryNames.add(rs.getString("category_name_eng"));
                categoryMap.put(categoryID, categoryNames);
            }
            return categoryMap;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public static void insertIngredients(List<Ingredients> ingredientsList) {
        try {
            for (Ingredients i: ingredientsList) {
                int insertedID = 0;
                String sqlStr = String.format("INSERT INTO ingredient (name_eng, category_id, is_filtered, default_unit_id, created_by) VALUES ('%s',%s ,%s ,%s ,%s );",i.IngredientNameEN, i.getCategoryID(), i.isFiltered() ? 1 : 0, i.getDefaultUnitID(), 2);
                PreparedStatement statement = con.prepareStatement(sqlStr, Statement.RETURN_GENERATED_KEYS);
                statement.executeUpdate();
                ResultSet generatedKey = statement.getGeneratedKeys();
                if(generatedKey.next()){
                    insertedID = generatedKey.getInt(1);
                    System.out.println("inserted id =" + insertedID);
                }
                if (insertedID != 0 && !i.getAltUnitValueList().isEmpty()){
                    for(AltUnitValue u : i.getAltUnitValueList()) {
                        String sqlStr2 = String.format("INSERT INTO unit2ingredient (IngredientID, AmountToUnit, UnitID) VALUES (%s,%s,%s)",insertedID, u.getValue().toString().replace(',','.'), u.getUnitID());
                        Statement statementUnits = con.createStatement();
                        statementUnits.executeUpdate(sqlStr2);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public static List<Ingredients> getIngredientsFromDB(int recipesID){
        try{
            PreparedStatement query = con.prepareStatement("{call dbo.ingredientsSelect(?)}");
            query.setInt(1, recipesID);
            ResultSet rs = query.executeQuery();

            List<AltIngredients> altIngredientsList = getAltIngredientsFromDB(recipesID);
            List<Ingredients> resultIng = new LinkedList<>();
            int counter = 1;

            while (rs.next()){
                final Ingredients ingredients = new Ingredients();
                counter++;
                ingredients.RecipesID = rs.getInt("recipesID");
                ingredients.IngredientInRecipeID = rs.getInt("ingredientInRecipeID");
                ingredients.IngredientNameCZ = rs.getString("ingredientNameCZ");
                ingredients.IngredientNameEN = rs.getString("ingredientNameEN");
                ingredients.Amount = rs.getDouble("amount");
                ingredients.UnitNameCZ = rs.getString("unitNameCZ");
                ingredients.UnitNameEN = rs.getString("unitNameEN");
                ingredients.AlterAmount = rs.getDouble("alterAmount");
                ingredients.AlterUnitNameCZ = rs.getString("altUnitNameCZ");
                ingredients.AlterUnitNameEN = rs.getString("altUnitNameEN");
                ingredients.AltIngredientsList = altIngredientsList.stream().filter(i-> i.ingredients2RecipesID == ingredients.IngredientInRecipeID).toList();
                ingredients.setRowID(counter);
                resultIng.add(ingredients);
            }
            return resultIng;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new LinkedList<Ingredients>();
    }

    public static List<AltIngredients> getAltIngredientsFromDB(int recipesID){
        try{
            PreparedStatement query = con.prepareStatement("{call dbo.alterIng(?)}");
            query.setInt(1, recipesID);
            ResultSet rs = query.executeQuery();
            List<AltIngredients> resultAltIng = new LinkedList<>();

            while (rs.next()){
                AltIngredients altIngredients = new AltIngredients();
                altIngredients.ingredients2RecipesID = rs.getInt("ingredients2RecipesID");
                altIngredients.AltNameCZ = rs.getString("name_cz");
                altIngredients.AltNameEN = rs.getString("name_eng");
                resultAltIng.add(altIngredients);
            }

            return resultAltIng;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new LinkedList<AltIngredients>();
    }

    public static Recipes getRecipesFromDB (int recipesID){
        try{
            PreparedStatement query = con.prepareStatement("{call dbo.recipesSelect(?)}");
            query.setInt(1, recipesID);
            ResultSet rs = query.executeQuery();
            List<Ingredients> ingredientsList = getIngredientsFromDB(recipesID);
            Recipes recipes = null;

            while (rs.next()){
                recipes = new Recipes();
                recipes.setRecipesID(rs.getInt("recipesID"));
                recipes.setRecipeNameCZ(rs.getString("recipeName_cz"));
                recipes.setRecipeNameEN(rs.getString("recipeName_eng"));
                recipes.setPortion(rs.getInt("portion"));
                recipes.setPortionDefault(rs.getInt("portion"));
                recipes.setPathDirectionsCZ(rs.getString("directions_cz"));
                recipes.setPathDirectionsEN(rs.getString("directions_eng"));
                recipes.setPathIMG(rs.getString("fileLocation"));
                recipes.setNutritionValues(rs.getString("nutritionValues"));
                recipes.setIngredients(ingredientsList);
                recipes.setIngredientsAmoutPerPortion();
            }

            return recipes;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new Recipes();
    }

    public static List<Recipes> getRecipesFromDB (String recipeName, SQLServerDataTable listIngredientID) {
        try {
            PreparedStatement query = con.prepareStatement("{call dbo.recipesFilterSelect(?, ?)}");
            query.setString(1, recipeName);
            ((SQLServerPreparedStatement) query).setStructured(2, "dbo.selectedID", listIngredientID);
            ResultSet rs = query.executeQuery();
            List<Recipes> recipesList = new ArrayList<>();

            while (rs.next()){
                Recipes recipes = new Recipes();
                recipes.setRecipesID(rs.getInt("recipesID"));
                recipes.setRecipeNameCZ(rs.getString("recipeName_cz"));
                recipes.setRecipeNameEN(rs.getString("recipeName_eng"));
                recipes.setPathIMG(rs.getString("fileLocation"));
                recipesList.add(recipes);
            }
            return recipesList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<Ingredients> getIngredientFromDB() {
        List<Ingredients> result = new ArrayList<>();
        try {
            String strSQL = "SELECT id, name_cz, name_eng FROM ingredient;";
            Statement query = con.createStatement();
            ResultSet resultSet = query.executeQuery(strSQL);


            while (resultSet.next()){
                Ingredients ingredient = new Ingredients();
                ingredient.setIngredientID(resultSet.getInt("id"));
                ingredient.setIngredientNameCZ(resultSet.getString("name_cz"));
                ingredient.setIngredientNameEN(resultSet.getString("name_eng"));
                result.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Unit> getAllUnitFromDB() {
        List<Unit> result = new ArrayList<>();
        try {
            String strSQL = "SELECT id, name_cz, name_eng, isShowMl, isShownG FROM unit WHERE NOT (isShowMl = 0 AND isShownG = 0) OR id = 5 OR id = 7;";
            Statement query = con.createStatement();
            ResultSet resultSet = query.executeQuery(strSQL);


            while (resultSet.next()){
                Unit unit = new Unit();
                unit.setUnitID(resultSet.getInt("id"));
                unit.setUnitNameCZ(resultSet.getString("name_cz"));
                unit.setUnitNameEN(resultSet.getString("name_eng"));
                unit.setShownMl(resultSet.getBoolean("isShowMl"));
                unit.setShownG(resultSet.getBoolean("isShownG"));
                result.add(unit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Ingredients> getFilterIngFromDB() {
        List<Ingredients> result = new ArrayList<>();
        try {
            String strSQL = "SELECT id, name_cz, name_eng FROM ingredient WHERE is_filtered = 1;";
            Statement query = con.createStatement();
            ResultSet resultSet = query.executeQuery(strSQL);


            while (resultSet.next()){
                Ingredients ingredient = new Ingredients();
                ingredient.setIngredientID(resultSet.getInt("id"));
                ingredient.setIngredientNameCZ(resultSet.getString("name_cz"));
                ingredient.setIngredientNameEN(resultSet.getString("name_eng"));
                result.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Recipes> getAllRecipesNames(){
        try {
            String strSQL = "SELECT recipesID, recipeName_cz, recipeName_eng FROM recipes;";
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery(strSQL);
            List<Recipes> recipesList = new ArrayList<>();

            while (rs.next()){
                Recipes recipes = new Recipes();
                recipes.setRecipesID(rs.getInt("recipesID"));
                recipes.setRecipeNameCZ(rs.getString("recipeName_cz"));
                recipes.setRecipeNameEN(rs.getString("recipeName_eng"));
                recipesList.add(recipes);
            }
            return recipesList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
