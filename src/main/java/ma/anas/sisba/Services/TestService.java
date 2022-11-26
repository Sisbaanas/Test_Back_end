package ma.anas.sisba.Services;

import ma.anas.sisba.Dto.ApiResponse;
import ma.anas.sisba.Dto.ChartDto;
import ma.anas.sisba.Dto.Table;
import ma.anas.sisba.Dto.TestObjectDto;
import ma.anas.sisba.Entities.enume.ChartType;
import ma.anas.sisba.Req.TestObjectReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TestService {

    List<TestObjectDto> getTestObjectList();

    ApiResponse addTestObject(TestObjectReq req);

    TestObjectDto addRandomCountry();

    ChartDto getChart(ChartType chartType);

    Table getTable(int size, int page, String label);

    void delete(String id);

    void edit(String id, TestObjectReq req);

}
