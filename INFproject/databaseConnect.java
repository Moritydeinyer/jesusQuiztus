import java.sql.*; 
import java.util.Random;

public class databaseConnect
{
    Connection connection;
    double ping;
    
    public databaseConnect()
    {
        System.out.println("debug11");
        try {
            connection = createConnection();
        } catch (SQLException e) {System.out.println(e);}
        //unitTest17052024();
    }
     
    public Connection createConnection() throws SQLException
        {
            //create connection to database
            
            
            
            
            long startTime = System.currentTimeMillis();
            String dbUrl = "jdbc:postgresql://iab-access.ddns.net:5431/infprojectdb";
            String user = "admin";
            String password = "X4a9Bg3!";
            Connection connection = DriverManager.getConnection(dbUrl, user, password);
            long endTime = System.currentTimeMillis();
            ping = (double) (endTime - startTime);
            return connection;
        }
        
    public void unitTest17052024() {
        // 1 - Delete; 2 - Update; 3 - Insert
        try {
            //user[] users = { new user(14), new user(12) };
            //map maps = new map(23);
            //game tempGame = new game(7, 100, 808, users, maps);
        
            System.out.println(getNewJoinNr());
            //mapdesign tempMD = new mapdesign(56, 4f, 4f, 5);
            //mapdesignSerializerDB(tempMD, 3);
            //System.out.println(tempMD.id);
        
            //effect ef = new effect(2, 1, 1, 1, "");
            //effectSerializerDB(ef, 1);
            //effect[] effects = { ef };
            //user tempUser = new user(3, 4f, 4f, 3, 3, 3, 340, "", "", effects, true);
            //userSerializerDB(tempUser, 1);
            //userSerializerJava(tempUser.id, "", "");
            //userSerializerJavaAll();
            
            //effect tempEffect = new effect(1, 1, 67, 1, "fg");
            //effectSerializerDB(tempEffect, 1);
            //effectSerializerJava(1);
            //effectSerializerJavaAll();
        
            //mapdesign tempMD = new mapdesign(1, 3.45674f, 5.65653f, 2);
            //mapdesignSerializerDB(tempMD, 1);
            //mapdesignSerializerJava(tempMD.id);
            //mapdesignSerializerJavaAll();
            
            //question tempQ = new question(3, 1, "d", "F", "df", "hjK", "L", "edE");
            //questionSerializerDB(tempQ, 3);
            //tempQ.points = 9456;
            //questionSerializerDB(tempQ, 2);
            //question tempQ = questionSerializerJava(21);
            //System.out.println(tempQ.data);
            //questionSerializerJavaAll();
        
            //mapdesign[] tempMD = { new mapdesign(0), new mapdesign(5) };
            //mapdesign[] tempMMD = { new mapdesign(05), new mapdesign(45) };
            //map tempMap = new map(2, tempMD);
            //mapSerializerDB(tempMap, 3);  //WORKS
            //mapSerializerDB(tempMap, 1);  //WORKS
            //tempMap.mapdesigns = tempMMD;
            //mapSerializerDB(tempMap, 2);  //WORKS
            //mapSerializerJavaAll();
            //mapSerializerJava(1);
            
            //gameSerializerDB(tempGame, 2);  //WORKS
            //gameSerializerDB(tempGame, 1);  //WORKS
            //gameSerializerDB(tempGame, 3);  //WORKS
            //game[] games = gameSerializerJavaAll(); //WORKS
            //tempGame = gameSerializerJava(6787); //WORKS
            System.out.println("debug1");
        } catch (SQLException e) {System.out.println(e);}
    }
        
    public int getNewJoinNr() throws SQLException { // 0-9 5chars example 54238
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM game";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM game";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        int[] tempJoinNrs = new int[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            int joinNr = resultSet.getInt("join_nr");
            tempJoinNrs[i] = joinNr;
            i++;
        }
        resultSet.close();
        statement.close();
        //int[] tempJoinNrs = { 45128, 45127, 56568, 21474 };
        Random random = new Random();
        String randomNumber = null;
        do {
            StringBuilder nb = new StringBuilder();
            for (int iii = 0; iii < 5; iii++) {
                nb.append(random.nextInt(10));
            }
            randomNumber = nb.toString();
        } while (contains(tempJoinNrs, randomNumber));
        return Integer.parseInt(randomNumber);
    } 
    
    public boolean contains(int[] arr, String number) {
        for (int n : arr) {
            if (String.valueOf(n).equals(number)) {
                return true;
            }
        }
        return false;
    }
    
    public game gameSerializerJava(int joinNumber) throws SQLException {
        //getting entry by joinNumber
        String sql = "SELECT * FROM game WHERE join_nr = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, joinNumber);
        long startTime = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          //get attributes
          String[] strArray = resultSet.getString("users").split(","); // "1,2,4,5"
          user[] users;
          if (strArray != null) {
              users = new user[strArray.length];
              for (int i=0; i<strArray.length; i++) {
                users[i] = userSerializerJava((Integer.parseInt(strArray[i])), "", "");
              }
          } else {users = new user[0];}
          map tempMap = mapSerializerJava(resultSet.getInt("map"));
          int joinNr = resultSet.getInt("join_nr");
          int id = resultSet.getInt("id");
          int time = resultSet.getInt("time");
          int round = resultSet.getInt("round");
          int phase = resultSet.getInt("phase");
          int server_id = resultSet.getInt("server_id");
          boolean pblc = resultSet.getBoolean("public");
          game tempGame = new game(id, time, joinNr,users, tempMap, round, phase, server_id, pblc);
          return tempGame;
        }
        long endTime = System.currentTimeMillis();
        ping = (double) (endTime - startTime);
        resultSet.close();
        preparedStatement.close();
        throw new SQLException();
    } //returning JAVA object from Database get
    
    public void gameSerializerDB(game tempGame, int method) throws SQLException {
        String sql = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempGame.users.length; i++) {
            sb.append(tempGame.users[i].id).append(",");
        }
        String userStr = sb.toString();
        userStr = userStr.substring(0, userStr.length() - 1);
        if (method == 1) {
            sql = "DELETE FROM game WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempGame.id);
            preparedStatement.executeUpdate();
        } else if (method == 2) {
            sql = "UPDATE game SET users = ?, map = ?, join_nr = ?, time = ?, round = ?, phase = ?, server_id = ?, public = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userStr);          
            preparedStatement.setInt(2, tempGame.current_map.id);
            preparedStatement.setInt(3, tempGame.join_nr);
            preparedStatement.setInt(4, tempGame.time);
            preparedStatement.setInt(5, tempGame.round);
            preparedStatement.setInt(6, tempGame.phase);
            preparedStatement.setInt(7, tempGame.server_id);
            preparedStatement.setBoolean(8, tempGame.pblc);
            preparedStatement.setInt(9, tempGame.id);
            preparedStatement.executeUpdate();
        } else { 
            sql = "INSERT INTO game (users, map, join_nr, time, round, phase, server_id, public) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userStr);          
            preparedStatement.setInt(2, tempGame.current_map.id);
            preparedStatement.setInt(3, tempGame.join_nr);
            preparedStatement.setInt(4, tempGame.time);
            preparedStatement.setInt(5, tempGame.round);
            preparedStatement.setInt(6, tempGame.phase);
            preparedStatement.setInt(7, tempGame.server_id);
            preparedStatement.setBoolean(8, tempGame.pblc);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newID = generatedKeys.getInt(1);
                tempGame.id = newID;
            }
        }
        preparedStatement.close();
    }   //returning Database object from JAVA delete, update, create
    
    public game[] gameSerializerJavaAll() throws SQLException {
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM game";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM game";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        game[] tempGame = new game[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            String[] strArray = resultSet.getString("users").split(","); // "1,2,4,5"
            user[] users;
            if (strArray != null) {
                users = new user[strArray.length];
                for (int ii=0; ii<strArray.length; ii++) {
                    users[ii] = userSerializerJava((Integer.parseInt(strArray[ii])), "", "");
                }
            } else {users = new user[0];}
            map tempMap = mapSerializerJava(resultSet.getInt("map"));
            int joinNr = resultSet.getInt("join_nr");
            int id = resultSet.getInt("id");
            int time = resultSet.getInt("time");
            int round = resultSet.getInt("round");
            int phase = resultSet.getInt("phase");
            int server_id = resultSet.getInt("server_id");
            boolean pblc = resultSet.getBoolean("public");
            tempGame[i] = new game(id, time, joinNr,users, tempMap, round, phase, server_id, pblc);
            i++;
        }
        resultSet.close();
        statement.close();
        return tempGame;
    }
    
    
    
    public user userSerializerJava(int userID, String password, String email) throws SQLException {
        //getting entry by userID
        String sql = "SELECT * FROM players WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        if (email != "") { //getting entry by user email
            sql = "SELECT * FROM players WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          //get attributes
          String[] strArray = resultSet.getString("effects").split(","); // "1,2,4,5"
          effect[] effects;
          if (strArray != null && strArray[0] != "") {
              effects = new effect[strArray.length];
              for (int i=0; i<strArray.length; i++) {
                effects[i] = effectSerializerJava((Integer.parseInt(strArray[i])));
              }
          } else {effects = new effect[0];}
          int id = resultSet.getInt("id");
          float x = resultSet.getFloat("x");
          float y = resultSet.getFloat("y");
          int speed = resultSet.getInt("speed");
          int health = resultSet.getInt("health");
          int damage = resultSet.getInt("damage");
          int points = resultSet.getInt("points");
          String tempEmail = resultSet.getString("email");
          String tempPassword = resultSet.getString("password");
          boolean visibility = resultSet.getBoolean("visibility");
          String img = resultSet.getString("img");
          user tempUser = new user(id, x, y, speed, health, damage, points, tempEmail, tempPassword, visibility, img, effects);
          return tempUser;
        }
        resultSet.close();
        preparedStatement.close();
        throw new SQLException();
    }   //entweder, oder
    
    public void userSerializerDB(user tempUser, int method) throws SQLException {
        String sql = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        String userStr = "";
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tempUser.effects.length; i++) {
                sb.append(tempUser.effects[i].id).append(",");
            }
            userStr = sb.toString();
            userStr = userStr.substring(0, userStr.length() - 1);
        } catch (java.lang.NullPointerException e) {}
        
        if (method == 1) {
            sql = "DELETE FROM players WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempUser.id);
            preparedStatement.executeUpdate();
        } else if (method == 2) {
            sql = "UPDATE players SET effects = ?, health = ?, x = ?, y = ?, points = ?, speed = ?, visibility = ?, damage = ?, email = ?, password = ?, img = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userStr);    
            preparedStatement.setInt(2, tempUser.health);
            preparedStatement.setFloat(3, tempUser.xx);
            preparedStatement.setFloat(4, tempUser.yy);
            preparedStatement.setInt(5, tempUser.points);
            preparedStatement.setInt(6, tempUser.speed);
            preparedStatement.setBoolean(7, tempUser.visibility);
            preparedStatement.setInt(8, tempUser.damage);
            preparedStatement.setString(9, tempUser.email);
            preparedStatement.setString(10, tempUser.password);
            preparedStatement.setString(11, tempUser.img);
            preparedStatement.setInt(12, tempUser.id);
            preparedStatement.executeUpdate();
        } else { 
            sql = "INSERT INTO players (effects, health, x, y, points, speed, visibility, damage, email, password, img) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userStr);    
            preparedStatement.setInt(2, tempUser.health);
            preparedStatement.setFloat(3, tempUser.xx);
            preparedStatement.setFloat(4, tempUser.yy);
            preparedStatement.setInt(5, tempUser.points);
            preparedStatement.setInt(6, tempUser.speed);
            preparedStatement.setBoolean(7, tempUser.visibility);
            preparedStatement.setInt(8, tempUser.damage);
            preparedStatement.setString(9, tempUser.email);
            preparedStatement.setString(10, tempUser.password);
            preparedStatement.setString(11, tempUser.img);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newID = generatedKeys.getInt(1);
                tempUser.id = newID;
            }
        }
        preparedStatement.close();
    }
    
    public user[] userSerializerJavaAll() throws SQLException {
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM players";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM players";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        user[] tempUsers = new user[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            String[] strArray = resultSet.getString("effects").split(","); // "1,2,4,5"
            effect[] effects;
            if (strArray != null && strArray[0] != "") {
                effects = new effect[strArray.length];
                for (int ii=0; ii<strArray.length; ii++) {
                    effects[ii] = effectSerializerJava((Integer.parseInt(strArray[ii])));
                }
            } else {effects = new effect[0];}
            int id = resultSet.getInt("id");
            float x = resultSet.getFloat("x");
            float y = resultSet.getFloat("y");
            int speed = resultSet.getInt("speed");
            int health = resultSet.getInt("health");
            int damage = resultSet.getInt("damage");
            int points = resultSet.getInt("points");
            String tempEmail = resultSet.getString("email");
            String tempPassword = resultSet.getString("password");
            boolean visibility = resultSet.getBoolean("visibility");
            String img = resultSet.getString("img");
            tempUsers[i] = new user(id, x, y, speed, health, damage, points, tempEmail, tempPassword, visibility, img, effects);
            i++;
        }
        resultSet.close();
        statement.close();
        return tempUsers;
    }
    
    
    
    public map mapSerializerJava(int mapID) throws SQLException {
        //getting entry by mapID
        String sql = "SELECT * FROM map WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, mapID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          //get attributes
          String[] strArray = resultSet.getString("mapdesign").split(","); // "1,2,4,5"
          mapdesign[] mapdes;
          if (strArray != null) {
              mapdes = new mapdesign[strArray.length];
              for (int i=0; i<strArray.length; i++) {
                mapdes[i] = mapdesignSerializerJava((Integer.parseInt(strArray[i])));
              }
          } else {mapdes = new mapdesign[0];}
          map tempMap = new map(resultSet.getInt("id"), mapdes);
          return tempMap;
        }
        resultSet.close();
        preparedStatement.close();
        throw new SQLException();
    }
    
    public void mapSerializerDB(map tempMap, int method) throws SQLException {
        String sql = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempMap.mapdesigns.length; i++) {
            sb.append(tempMap.mapdesigns[i].id).append(",");
        }
        String userStr = sb.toString();
        userStr = userStr.substring(0, userStr.length() - 1);
        if (method == 1) {
            sql = "DELETE FROM map WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempMap.id);
            preparedStatement.executeUpdate();
        } else if (method == 2) {
            sql = "UPDATE map SET mapdesign = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userStr);          
            preparedStatement.setInt(2, tempMap.id);
            preparedStatement.executeUpdate();
        } else { 
            sql = "INSERT INTO map (mapdesign) VALUES (?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userStr); 
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newID = generatedKeys.getInt(1);
                tempMap.id = newID;
            }
        }
        preparedStatement.close();
    }
    
    public map[] mapSerializerJavaAll() throws SQLException{
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM map";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM map";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        map[] tempMap = new map[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            String[] strArray = resultSet.getString("mapdesign").split(","); // "1,2,4,5"
            mapdesign[] mapdesigns;
            if (strArray != null) {
                mapdesigns = new mapdesign[strArray.length];
                for (int ii=0; ii<strArray.length; ii++) {
                    mapdesigns[ii] = mapdesignSerializerJava((Integer.parseInt(strArray[ii])));
                }
            } else {mapdesigns = new mapdesign[0];}
            int id = resultSet.getInt("id");
            tempMap[i] = new map(id, mapdesigns);
            i++;
        }
        resultSet.close();
        statement.close();
        return tempMap;
    }
    
    
    
    public effect effectSerializerJava(int effectID) throws SQLException {
        //getting entry by effectID
        String sql = "SELECT * FROM effect WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, effectID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          //get attributes
          int id = resultSet.getInt("id");
          int lasting_time = resultSet.getInt("lasting_time");
          int intensity = resultSet.getInt("intensity");
          int function = resultSet.getInt("function");
          int activated = resultSet.getInt("activated");
          String description = resultSet.getString("description");
          effect tempEffect = new effect(id, lasting_time, intensity, function, description, activated);
          return tempEffect;
        }
        resultSet.close();
        preparedStatement.close();
        throw new SQLException();
    }
    
    public void effectSerializerDB(effect tempEffect, int method) throws SQLException {
        String sql = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (method == 1) {
            sql = "DELETE FROM effect WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempEffect.id);
            preparedStatement.executeUpdate();
        } else if (method == 2) {
            sql = "UPDATE effect SET lasting_time = ?, intensity = ?, function = ?, description = ?, activated = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempEffect.lasting_time); 
            preparedStatement.setInt(2, tempEffect.intensity); 
            preparedStatement.setInt(3, tempEffect.function); 
            preparedStatement.setString(4, tempEffect.description); 
            preparedStatement.setInt(5, tempEffect.activated); 
            preparedStatement.setInt(6, tempEffect.id);
            preparedStatement.executeUpdate();
        } else { 
            sql = "INSERT INTO effect (lasting_time, intensity, function, description, activated) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, tempEffect.lasting_time); 
            preparedStatement.setInt(2, tempEffect.intensity); 
            preparedStatement.setInt(3, tempEffect.function);  
            preparedStatement.setString(4, tempEffect.description);
            preparedStatement.setInt(5, tempEffect.activated);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newID = generatedKeys.getInt(1);
                System.out.println(newID);
                tempEffect.id = newID;
            }
        }
        preparedStatement.close();
    }
    
    public effect[] effectSerializerJavaAll() throws SQLException {
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM effect";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM effect";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        effect[] tempEffects = new effect[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            int id = resultSet.getInt("id");
            int lasting_time = resultSet.getInt("lasting_time");
            int intensity = resultSet.getInt("intensity");
            int function = resultSet.getInt("function");
            int activated = resultSet.getInt("activated");
            String description = resultSet.getString("description");
            tempEffects[i] = new effect(id, lasting_time, intensity, function, description, activated);
            i++;
        }
        resultSet.close();
        statement.close();
        return tempEffects; 
    }
    
    
    
    public question questionSerializerJava(int questionID) throws SQLException {
        //getting entry by questionID
        String sql = "SELECT * FROM question WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, questionID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          //get attributes
          int id = resultSet.getInt("id");
          int points = resultSet.getInt("points");
          String data = resultSet.getString("data");
          String answer = resultSet.getString("answer");
          String a = resultSet.getString("a");
          String b = resultSet.getString("b");
          String c = resultSet.getString("c");
          String d = resultSet.getString("d");
          question tempQuestion = new question(id, points, data, a, b, c, d, answer);
          return tempQuestion;
        }
        resultSet.close();
        preparedStatement.close();
        throw new SQLException();
    }
    
    public void questionSerializerDB(question tempQuestion, int method) throws SQLException {
        String sql = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (method == 1) {
            sql = "DELETE FROM question WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempQuestion.id);
            preparedStatement.executeUpdate();
        } else if (method == 2) {
            sql = "UPDATE question SET data = ?, answer = ?, points = ?, a = ?, b = ?, c = ?, d = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tempQuestion.data); 
            preparedStatement.setString(2, tempQuestion.answer); 
            preparedStatement.setInt(3, tempQuestion.points); 
            preparedStatement.setString(4, tempQuestion.answer_a); 
            preparedStatement.setString(5, tempQuestion.answer_b); 
            preparedStatement.setString(6, tempQuestion.answer_c); 
            preparedStatement.setString(7, tempQuestion.answer_d); 
            preparedStatement.setInt(8, tempQuestion.id);
            preparedStatement.executeUpdate();
        } else { 
            sql = "INSERT INTO question (data, answer, points, a, b, c, d) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tempQuestion.data); 
            preparedStatement.setString(2, tempQuestion.answer); 
            preparedStatement.setInt(3, tempQuestion.points); 
            preparedStatement.setString(4, tempQuestion.answer_a); 
            preparedStatement.setString(5, tempQuestion.answer_b); 
            preparedStatement.setString(6, tempQuestion.answer_c); 
            preparedStatement.setString(7, tempQuestion.answer_d);   
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newID = generatedKeys.getInt(1);
                tempQuestion.id = newID;
            }
        }
        preparedStatement.close();
    }
    
    public question[] questionSerializerJavaAll() throws SQLException {
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM question";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM question";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        question[] tempQuestions = new question[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            int id = resultSet.getInt("id");
            int points = resultSet.getInt("points");
            String data = resultSet.getString("data");
            String answer = resultSet.getString("answer");
            String a = resultSet.getString("a");
            String b = resultSet.getString("b");
            String c = resultSet.getString("c");
            String d = resultSet.getString("d");
            tempQuestions[i] = new question(id, points, data, a, b, c, d, answer);
            i++;
        }
        resultSet.close();
        statement.close();
        return tempQuestions;
    }
    
    
    
    public mapdesign mapdesignSerializerJava(int mapdesignID) throws SQLException {
        //getting entry by mapdesignID
        String sql = "SELECT * FROM mapdesign WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, mapdesignID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          //get attributes
          int id = resultSet.getInt("id");
          float x = resultSet.getFloat("x");
          float y = resultSet.getFloat("y");
          String source = resultSet.getString("source");
          String data = resultSet.getString("data");
          int function = resultSet.getInt("function");
          mapdesign tempMapdesign = new mapdesign(id, y, x, function, source, data);
          return tempMapdesign;
        }
        resultSet.close();
        preparedStatement.close();
        throw new SQLException();
    }
    
    public void mapdesignSerializerDB(mapdesign tempMapdesign, int method) throws SQLException {
        String sql = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (method == 1) {
            sql = "DELETE FROM mapdesign WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempMapdesign.id);
            preparedStatement.executeUpdate();
        } else if (method == 2) {
            sql = "UPDATE mapdesign SET x = ?, y = ?, function = ?, source = ?, data = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, tempMapdesign.xx); 
            preparedStatement.setFloat(2, tempMapdesign.yy); 
            preparedStatement.setInt(3, tempMapdesign.function); 
            preparedStatement.setString(4, tempMapdesign.source); 
            preparedStatement.setString(5, tempMapdesign.data); 
            preparedStatement.setInt(6, tempMapdesign.id);
            preparedStatement.executeUpdate();
        } else { 
            sql = "INSERT INTO mapdesign (x, y, function, source, data) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setFloat(1, tempMapdesign.xx); 
            preparedStatement.setFloat(2, tempMapdesign.yy); 
            preparedStatement.setInt(3, tempMapdesign.function);  
            preparedStatement.setString(4, tempMapdesign.source);
            preparedStatement.setString(5, tempMapdesign.data);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newID = generatedKeys.getInt(1);
                tempMapdesign.id = newID;
            }
        }
        preparedStatement.close();
    }
    
    public mapdesign[] mapdesignSerializerJavaAll() throws SQLException {
        //getting current number of entries
        String sql = "SELECT COUNT(*) FROM mapdesign";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int tempCount = 0;
        if (resultSet.next()) {
            tempCount = resultSet.getInt("count");
        }
        resultSet.close();
        statement.close();
        //getting the data from every entry
        sql = "SELECT * FROM mapdesign";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        mapdesign[] tempMapdesign = new mapdesign[tempCount];
        int i = 0;
        while (resultSet.next()) {
            //get attributes
            int id = resultSet.getInt("id");
            float x = resultSet.getFloat("x");
            float y = resultSet.getFloat("y");
            int function = resultSet.getInt("function");
            String source = resultSet.getString("source");
            String data = resultSet.getString("data");
            tempMapdesign[i] = new mapdesign(id, x, y, function, source, data);
            i++;
        }
        resultSet.close();
        statement.close();
        return tempMapdesign;
    }
    }

