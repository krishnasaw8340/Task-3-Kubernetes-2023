package com.krishna.app.kaiburrr.Controller;
import com.krishna.app.kaiburrr.Model.dataNew;
import com.krishna.app.kaiburrr.Repo.dataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {
    @Autowired
    private  dataRepo dataRepo;
    @GetMapping(value="/")
    public List<dataNew> getData(){
        return dataRepo.findAll();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<dataNew> getDataById(@PathVariable long id) {
        Optional<dataNew> data = dataRepo.findById(id);
        if (!data.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data.get());
    }

    @GetMapping("/servers/{name}")
    public ResponseEntity<List<dataNew>> getServersByName(@PathVariable String name) {
        List<dataNew> servers = dataRepo.findAllByNameContaining(name);

        if (servers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servers);
    }
    @PostMapping(value ="/send")
    public String saveData(@RequestBody dataNew d)
    {
        dataRepo.save(d);
        return "Data saved to database...";
    }
//    @PutMapping(value ="/update/{id}")
//    public String updateData(@PathVariable long id, @RequestBody @NotNull Data d)
//    {
//        Data updateData = dataRepo.findById(id).get();
//        updateData.setName(d.getName());
//        updateData.setLanguage(d.getLanguage());
//        updateData.setFramework(d.getFramework());
//        return "Data is updated";
//    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updateData(@PathVariable long id, @RequestBody dataNew d) {
        Optional<dataNew> optionalData = dataRepo.findById(id);

        if (optionalData.isPresent()) {
            dataNew updateData = optionalData.get();
            updateData.setName(d.getName());
            updateData.setLanguage(d.getLanguage());
            updateData.setFramework(d.getFramework());

            dataRepo.save(updateData); // Save the updated data

            return ResponseEntity.ok("Data is updated");
        } else {
            return ResponseEntity.notFound().build(); // Handle the case where data with the given ID is not found
        }
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteUser(@PathVariable long id)
    {
        dataNew deleteUser = dataRepo.findById(id).get();
        dataRepo.delete(deleteUser);
        return "Delete user with the id..."+id;
    }
}