package Phone.Book;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@Service
public class PhoneBookService {

    public void addEntry(Entry entry){
        JSONObject entryObject = new JSONObject();
        entryObject.put("First Name",entry.getFirstName());
        entryObject.put("Last Name",entry.getLastName());
        entryObject.put("Type",entry.getType());
        entryObject.put("Number",entry.getNumber());

        JSONArray entryList = getEntries();
        if(entryList==null){
            entryList = new JSONArray();
            entryList.add(entryObject);
        }
        else {
            entryList.add(entryObject);
        }

        try (FileWriter file = new FileWriter("phonebook.json")) {
            file.write(entryList.toJSONString());
            System.out.println("Entry added successfully");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void editEntry(String number,Entry entry){
        JSONArray entryList = getEntries();
        for(int i=0;i<entryList.size();i++){
            JSONObject entryObj = (JSONObject) entryList.get(i);
            if(entryObj.get("Number").equals(number)){
                entryList.remove(entryObj);
                JSONObject entryObject = new JSONObject();
                entryObject.put("First Name",entry.getFirstName());
                entryObject.put("Last Name",entry.getLastName());
                entryObject.put("Type",entry.getType());
                entryObject.put("Number",entry.getNumber());
                entryList.add(entryObject);
                try (FileWriter file = new FileWriter("phonebook.json")) {
                    file.write(entryList.toJSONString());
                    System.out.println("Entry Edited Successfully");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteEntry(String number){
        JSONArray entryList = getEntries();
        for(int i=0;i<entryList.size();i++){
            JSONObject entryObject = (JSONObject) entryList.get(i);
            if(entryObject.get("Number").equals(number)){
                entryList.remove(entryObject);
                try (FileWriter file = new FileWriter("phonebook.json")) {
                    file.write(entryList.toJSONString());
                    System.out.println("Entry Deleted Successfully");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public JSONArray getEntries(){
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("phonebook.json")){
            Object object = jsonParser.parse(reader);
            JSONArray entryList  = (JSONArray) object;
            return entryList;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(String number){
        JSONArray entryList = getEntries();
        if(entryList==null){
            return false;
        }
        for(int i=0;i<entryList.size();i++){
            JSONObject entryObj = (JSONObject) entryList.get(i);
            if(entryObj.get("Number").equals(number)){
                return true;
            }
        }
        return false;
    }

    public boolean checkForCorrectDetails(Entry entry){
        if(!entry.getFirstName().equals("") && !entry.getLastName().equals("") &&
                (entry.getType().equalsIgnoreCase("Work")
                        || entry.getType().equalsIgnoreCase("Cellphone")
                        || entry.getType().equalsIgnoreCase("Home"))
                && !entry.getNumber().equals("")){

            return true;
        }
        return false;
    }

    public JSONArray orderByFirstName(){
        JSONArray entryList = getEntries();
        JSONArray ordered = new JSONArray();
        ArrayList<JSONObject> list = new ArrayList<>();
        for(int i=0;i<entryList.size();i++){
            list.add((JSONObject) entryList.get(i));
        }
        Collections.sort(list, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "First Name";
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String valA = (String) o1.get(KEY_NAME);
                String valB = (String) o2.get(KEY_NAME);
                return valA.compareTo(valB);
            }
        });
        for(int i=0;i<entryList.size();i++){
            ordered.add(list.get(i));
        }
        return ordered;
    }

    public JSONArray orderByLastName(){
        JSONArray entryList = getEntries();
        JSONArray ordered = new JSONArray();
        ArrayList<JSONObject> list = new ArrayList<>();
        for(int i=0;i<entryList.size();i++){
            list.add((JSONObject) entryList.get(i));
        }
        Collections.sort(list, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "Last Name";
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String valA = (String) o1.get(KEY_NAME);
                String valB = (String) o2.get(KEY_NAME);
                return valA.compareTo(valB);
            }
        });
        for(int i=0;i<entryList.size();i++){
            ordered.add(list.get(i));
        }
        return ordered;
    }

}
