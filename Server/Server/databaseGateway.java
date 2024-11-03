import greenfoot.*;
import java.net.*;
import java.io.*;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class databaseGateway  
{

    int ping;
    
    public databaseGateway()
    {
        //unit_test
        try {
            //int joinNr = getNewJoinNr();
            //game tGame = gameSerializerJava(26101);
            //System.out.println(joinNr);
            //tGame.join_nr = joinNr;
            //gameSerializerDB(tGame, 3);
            //effect FX = effectSerializerJava(34);
            //effectSerializerDB(FX, 3);  
            //question Q = questionSerializerJava(2330);
            //questionSerializerDB(Q, 3);
            //effect FX = new effect(0, -10, 1, -10, "x", 0); // init effect
            //effectSerializerDB(FX, 3);   
            //System.out.println("d1");
            //effect[] effects = { FX }; //DEV get by predefined IDs
            //user user = new user(1, 0, 0, 5, 10, 1, 0, "", "1234", true, "juengerRechts.png", effects);
            //System.out.println("d2");
            //userSerializerDB(user, 3);
        } catch (Exception e) {System.out.println(e);}
    }

    public String apiCallPost(String jsonStr, String url) throws Exception {
        long startTime = System.currentTimeMillis();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(jsonStr.getBytes());
        os.flush();
        os.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        ping = (int) elapsedTime;
        return response.toString();
    }
    
    public String apiCallGet(String urlStr) throws Exception{
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        if (con.getResponseCode() != 200) {
            throw new IOException("Failed to connect: HTTP error code " + con.getResponseCode());
        }
        String contentType = con.getHeaderField("Content-Type");
        if (!contentType.equals("application/json")) {
            System.err.println("Warning: Expected JSON response, but received " + contentType);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        String jsonString = sb.toString().trim();
        return jsonString;
    }
    
    public int getNewJoinNr() throws Exception
    {
        String jsonString = apiCallGet("https://iab-services.ddns.net/api/jesusquiztus/get_join_nr");
        JSONObject jsonObject = new JSONObject(jsonString);
        String answer = jsonObject.getString("join_nr");
        return Integer.parseInt(answer);
    }
    
    public game gameSerializerJava(int joinNumber) throws Exception {
        String requestBody = "{\"join_nr\": \"" + joinNumber + "\"}";
        String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/get_game_obj");
        JSONObject jsonObject = new JSONObject(response);
        int id = Integer.parseInt(jsonObject.getString("id"));
        int time = Integer.parseInt(jsonObject.getString("time"));
        int join_nr = Integer.parseInt(jsonObject.getString("join_nr"));
        int round = Integer.parseInt(jsonObject.getString("round"));
        int phase = Integer.parseInt(jsonObject.getString("phase"));
        int server_id = Integer.parseInt(jsonObject.getString("server_id"));
        boolean publiC = Boolean.parseBoolean(jsonObject.getString("public"));        
        String[] strArray = jsonObject.getString("users").split(","); 
        user[] users;
        if (strArray != null) {
            users = new user[strArray.length];
            for (int i=0; i<strArray.length; i++) {
              users[i] = userSerializerJava((Integer.parseInt(strArray[i])), "", "");
            }
        } else {users = new user[0];}
        int map = Integer.parseInt(jsonObject.getString("map"));                                              
        return new game(id, time, join_nr, users, map, round, phase, server_id, publiC);
    }
    
    public void gameSerializerDB(game tempGame, int method) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempGame.users.length; i++) {
            sb.append(tempGame.users[i].id).append(",");
        }
        String userStr = sb.toString();
        userStr = userStr.substring(0, userStr.length() - 1);
        if (method == 1) {
            //DELETE
            String requestBody = "{\"method\": \"delete\", \"id\": \"" + tempGame.id + "\"}";
            String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/serializer_game_obj");
        }
        if (method == 2) {
            //UPDATE
            String requestBody = "{\"method\": \"update\", \"users\": \"" + userStr + "\", \"id\": \"" + tempGame.id + "\", \"map\": \"" + tempGame.current_map + "\", \"time\": \"" + tempGame.time + "\", \"round\": \"" + tempGame.round + "\", \"phase\": \"" + tempGame.phase + "\", \"server_id\": \"" + tempGame.server_id + "\", \"public\": \"" + tempGame.pblc + "\", \"join_nr\": \"" + tempGame.join_nr + "\"}";
            String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/serializer_game_obj");
        }
        if (method == 3) {
            //INSERT
            String requestBody = "{\"method\": \"create\", \"users\": \"" + userStr + "\", \"map\": \"" + tempGame.current_map + "\", \"time\": \"" + tempGame.time + "\", \"round\": \"" + tempGame.round + "\", \"phase\": \"" + tempGame.phase + "\", \"server_id\": \"" + tempGame.server_id + "\", \"public\": \"" + tempGame.pblc + "\", \"join_nr\": \"" + tempGame.join_nr + "\"}";
            String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/serializer_game_obj");
            JSONObject jsonObject = new JSONObject(response);
            int id = Integer.parseInt(jsonObject.getString("id"));
            tempGame.id = id;
        }
    }
    
    public game[] gameSerializerJavaAll() throws Exception {
        String response = apiCallGet("https://iab-services.ddns.net/api/jesusquiztus/get_all_game_obj");
        JSONObject jsonObject = new JSONObject(response);
        String data = jsonObject.getString("games");
        List<game> gameList = parseGameData(data);
        return gameList.toArray(new game[gameList.size()]);
    }
    
    public List<game> parseGameData(String dataString) throws Exception {
        String trimmedData = dataString.substring(1, dataString.length() - 1);
        String[] tuples = trimmedData.split("\\), \\(");
        List<game> dataPoints = new ArrayList<>();
        for (String tuple : tuples) {
            String[] elements = tuple.split(", ");
            String[] strArray = elements[1].split(",");
            user[] users;
            if (strArray != null) {
                users = new user[strArray.length];
                for (int ii=0; ii<strArray.length; ii++) {
                    users[ii] = userSerializerJava((Integer.parseInt(strArray[ii])), "", "");
                }
            } else {users = new user[0];}
            int tempCurrentMap = Integer.parseInt(elements[2]); 
            game dataPoint = new game(Integer.parseInt(elements[0]), Integer.parseInt(elements[4]), Integer.parseInt(elements[3]), users, tempCurrentMap, Integer.parseInt(elements[5]), Integer.parseInt(elements[7]), Integer.parseInt(elements[8]), Boolean.parseBoolean(elements[9]));
            dataPoints.add(dataPoint);
        }
        return dataPoints;
    }
    
    
    
    
    public user userSerializerJava(int userID, String password, String email) throws Exception {
        String requestBody = "{\"id\": \"" + userID + "\"}";
        String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/get_user_obj");
        JSONObject jsonObject = new JSONObject(response);
        int id = Integer.parseInt(jsonObject.getString("id"));
        float x = Float.parseFloat(jsonObject.getString("x"));
        float y = Float.parseFloat(jsonObject.getString("y"));
        int speed = Integer.parseInt(jsonObject.getString("speed"));
        int health = Integer.parseInt(jsonObject.getString("health"));
        int points = Integer.parseInt(jsonObject.getString("points"));
        int damage = Integer.parseInt(jsonObject.getString("damage"));
        String tempEmail = jsonObject.getString("email");
        String tempPwd = jsonObject.getString("password");
        boolean visibility = Boolean.parseBoolean(jsonObject.getString("visibility"));
        String img = jsonObject.getString("img");
        String[] strArray = jsonObject.getString("effects").split(",");
        int[] effects;
        if (strArray != null) {
            effects = new int[strArray.length];
            for (int ii=0; ii<strArray.length; ii++) {
                effects[ii] = (Integer.parseInt(strArray[ii]));
            }
        } else {effects = new int[0];}
        return new user(id, x, y, speed, health, damage, points, tempEmail, tempPwd, visibility, img, effects);
    }
    
    public void userSerializerDB(user tempUser, int method) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tempUser.effects.length; i++) {
            sb.append(tempUser.effects[i]).append(",");
        }
        String userStr = sb.toString();
        userStr = userStr.substring(0, userStr.length() - 1);
        if (method == 1) {
            //DELETE
            String requestBody = "{\"method\": \"delete\", \"id\": \"" + tempUser.id + "\"}";
            String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/serializer_user_obj");
        }
        if (method == 2) {
            //UPDATE
            String requestBody = "{\"method\": \"update\",\"id\": \"" + tempUser.id + "\", \"effects\": \"" + userStr + "\", \"health\": \"" + tempUser.health + "\", \"x\": \"" + tempUser.xx + "\", \"y\": \"" + tempUser.yy + "\", \"points\": \"" + tempUser.points + "\", \"speed\": \"" + tempUser.speed + "\", \"visibility\": \"" + tempUser.visibility + "\", \"damage\": \"" + tempUser.damage + "\", \"email\": \"" + tempUser.email + "\", \"password\": \"" + tempUser.password + "\", \"img\": \"" + tempUser.img + "\"}";
            String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/serializer_user_obj");
        }
        if (method == 3) {
            //INSERT
            String requestBody = "{\"method\": \"create\", \"effects\": \"" + userStr + "\", \"health\": \"" + tempUser.health + "\", \"x\": \"" + tempUser.xx + "\", \"y\": \"" + tempUser.yy + "\", \"points\": \"" + tempUser.points + "\", \"speed\": \"" + tempUser.speed + "\", \"visibility\": \"" + tempUser.visibility + "\", \"damage\": \"" + tempUser.damage + "\", \"email\": \"" + tempUser.email + "\", \"password\": \"" + tempUser.password + "\", \"img\": \"" + tempUser.img + "\"}";
            String response = apiCallPost(requestBody, "https://iab-services.ddns.net/api/jesusquiztus/serializer_user_obj");
            JSONObject jsonObject = new JSONObject(response);
            int id = Integer.parseInt(jsonObject.getString("id"));
            tempUser.id = id;
        }
    }
    
    public user[] userSerializerJavaAll() throws Exception {
        String response = apiCallGet("https://iab-services.ddns.net/api/jesusquiztus/get_all_user_obj");
        JSONObject jsonObject = new JSONObject(response);
        String data = jsonObject.getString("players");
        List<user> userList = parseUserData(data);
        return userList.toArray(new user[userList.size()]);
    }
    
    public List<user> parseUserData(String dataString) throws Exception {
        String trimmedData = dataString.substring(1, dataString.length() - 1);
        String[] tuples = trimmedData.split("\\), \\(");
        List<user> dataPoints = new ArrayList<>();
        for (String tuple : tuples) {
            String[] elements = tuple.split(", ");
            String[] strArray = elements[2].split(",");
            int[] effects;
            if (strArray != null) {
                effects = new int[strArray.length];
                for (int ii=0; ii<strArray.length; ii++) {
                    effects[ii] = (Integer.parseInt(strArray[ii]));
                }
            } else {effects = new int[0];}
            user dataPoint = new user(Integer.parseInt(elements[0]), Float.parseFloat(elements[3]), Float.parseFloat(elements[4]), Integer.parseInt(elements[6]), Integer.parseInt(elements[1]), Integer.parseInt(elements[8]), Integer.parseInt(elements[5]), elements[9], elements[10], Boolean.parseBoolean(elements[7]), elements[11], effects);
            dataPoints.add(dataPoint);
        }
        return dataPoints;
    }
}
