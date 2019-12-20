package Phone.Book;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PhoneBookController {
    @Autowired
    private PhoneBookService service;

    @PostMapping("/addEntry")
    public ResponseEntity addEntry(@RequestBody Entry entry){
        if(service.checkForCorrectDetails(entry)){
            service.addEntry(entry);
            return new ResponseEntity("Entry Addedd Successfully",HttpStatus.OK);
        }
        return new ResponseEntity("Entry Details Are Wrong",HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getEntries")
    public ResponseEntity getEntries(){
        JSONArray entryList = service.getEntries();
        return new ResponseEntity(entryList,HttpStatus.OK);
    }

    @DeleteMapping("/deleteEntry/{number}")
    public ResponseEntity deleteEntry(@PathVariable String number){
        if(service.exists(number)){
            service.deleteEntry(number);
            return new ResponseEntity("Entry Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity("Entry Not Exists",HttpStatus.NOT_FOUND);

    }

    @PutMapping("/editEntry/{number}")
    public ResponseEntity editEntry(@PathVariable String number,@RequestBody Entry entry){
        if(service.exists(number)){
            if(service.checkForCorrectDetails(entry)){
                service.editEntry(number,entry);
                return new ResponseEntity("Entry Edited Successfully",HttpStatus.OK);
            }
            else {
                return new ResponseEntity("Entry Details Are Wrong",HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Entry Not Exists",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getOrderedByFirstName")
    public ResponseEntity getOrderedByFirstName(){
        return new ResponseEntity(service.orderByFirstName(),HttpStatus.OK);
    }

    @GetMapping("/getOrderedByLastName")
    public ResponseEntity getOrderedByLastName(){
        return new ResponseEntity(service.orderByLastName(),HttpStatus.OK);
    }
}