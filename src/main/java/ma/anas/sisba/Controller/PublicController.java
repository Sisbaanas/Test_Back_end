package ma.anas.sisba.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ma.anas.sisba.Dto.ApiResponse;
import ma.anas.sisba.Dto.ChartDto;
import ma.anas.sisba.Dto.TestObjectDto;
import ma.anas.sisba.Entities.enume.ChartType;
import ma.anas.sisba.Req.TestObjectReq;
import ma.anas.sisba.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/public")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin
public class PublicController {

    @Autowired
    private TestService testService;

    @GetMapping("/list/testObject")
    @Operation(summary = "Get list of testObject")
    public ResponseEntity<List<TestObjectDto>> addCountry() {
        return new ResponseEntity<>(testService.getTestObjectList(), HttpStatus.OK);
    }

    @PostMapping("/testObject")
    @Operation(summary = "add a testObject")
    public ResponseEntity<ApiResponse>  addCountry(@Valid @RequestBody TestObjectReq req) {
        return new ResponseEntity<>(testService.addTestObject(req), HttpStatus.OK);
    }

    @PostMapping("/random/testObject")
    @Operation(summary = "add a random testObject")
    public ResponseEntity<TestObjectDto>  addRandomCountry() {
        return new ResponseEntity<>(testService.addRandomCountry(), HttpStatus.OK);
    }

}
