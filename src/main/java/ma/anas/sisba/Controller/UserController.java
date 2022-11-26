package ma.anas.sisba.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.anas.sisba.Dto.ApiResponse;
import ma.anas.sisba.Dto.ChartDto;
import ma.anas.sisba.Dto.Table;
import ma.anas.sisba.Dto.TestObjectDto;
import ma.anas.sisba.Entities.enume.ChartType;
import ma.anas.sisba.Req.TestObjectReq;
import ma.anas.sisba.Services.FilesStorageService;
import ma.anas.sisba.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private TestService testService;

    @Autowired
    private FilesStorageService filesStorageService;

    @GetMapping("/chart/testObject")
    @Operation(summary = "Get list of testObject")
    public ResponseEntity<ChartDto> addCountry() {
        return new ResponseEntity<>(testService.getChart(null), HttpStatus.OK);
    }

    @GetMapping("/table/testObject")
    @Operation(summary = "Get a table of testObject")
    public ResponseEntity<Table> addCountry(@RequestParam("size") @Min(1) int size,
                                            @RequestParam("page") int page,
                                            @RequestParam(value = "label" , required = false) String label) {
        return new ResponseEntity<>(testService.getTable(size,page,label), HttpStatus.OK);
    }

    @PostMapping("/testObject")
    @Operation(summary = "add a testObject")
    public ResponseEntity<ApiResponse>  addCountry(@Valid @RequestBody TestObjectReq req) {
        return new ResponseEntity<>(testService.addTestObject(req), HttpStatus.OK);
    }

    @DeleteMapping("/testObject/{id}")
    @Operation(summary = "add a random testObject")
    public void  addRandomCountry(@PathVariable("id") String id) {
        testService.delete(id);
    }


    @PatchMapping("/testObject/{id}")
    @Operation(summary = "edit an existing testObject")
    public void  addRandomCountry(@PathVariable("id") String id,
                                  @Valid @RequestBody TestObjectReq req) {
        testService.edit(id,req);
    }

    @PostMapping(value = "/upload",
                 consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "upload a file")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            filesStorageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message(message).build());
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.builder().message(message).build());
        }
    }

}
