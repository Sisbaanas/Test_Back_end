package ma.anas.sisba.Services.implementation;

import ma.anas.sisba.Dto.*;
import ma.anas.sisba.Entities.File;
import ma.anas.sisba.Entities.TestObject;
import ma.anas.sisba.Entities.User;
import ma.anas.sisba.Entities.enume.ChartType;
import ma.anas.sisba.Repositories.TestObjectRepo;
import ma.anas.sisba.Req.TestObjectReq;
import ma.anas.sisba.Services.TestService;
import ma.anas.sisba.exception.ExceptionApi;
import ma.anas.sisba.exception.PayLoadExceptionItem;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TestImpl implements TestService {

    @Autowired
    private TestObjectRepo testObjectRepo;

    @Override
    public List<TestObjectDto> getTestObjectList() {

        List<TestObject> list = testObjectRepo.findAll();
        System.out.println(list.size());
        List<TestObjectDto> Dtolist = new ArrayList<>();
        list.forEach(e->{
            Dtolist.add(new TestObjectDto(e.getId().toString(),e.getName(),e.getPrice().toString(),e.getCountry(),e.getCity()));
        });
        return Dtolist;
    }

    @Override
    public ApiResponse addTestObject(TestObjectReq req) {
        TestObject testObject = new TestObject(UUID.randomUUID().toString(),req.getName(),req.getValue(),req.getCountry(),req.getCity(),false);
        testObjectRepo.save(testObject);
        return ApiResponse.builder().id(testObject.getId().toString()).build();
    }

    @Override
    public TestObjectDto addRandomCountry() {
        RandomString generatedName = new RandomString(8, ThreadLocalRandom.current());
        Random r = new Random();
        double randomValue = 1 + (99) * r.nextDouble();
        TestObject testObject = new TestObject(UUID.randomUUID().toString(),generatedName.nextString(),randomValue,null,null,false);
        testObjectRepo.save(testObject);
        return new TestObjectDto(testObject.getId().toString(),generatedName.nextString(),randomValue+"",null,null);
    }

    @Override
    public ChartDto getChart(ChartType chartType) {

        ChartDto chart = new ChartDto();

        chart.setLabels(testObjectRepo.getLabels());
        chart.setData(new ArrayList<>());


        chart.getLabels().forEach(e->{
            chart.getData().add(testObjectRepo.getTotalPrice(e));
        });

        return chart;
    }

    @Override
    public Table getTable(int size, int page, String label) {
        if(label == null)
            label = "";

        Page<TestObject> p = testObjectRepo.getPage(label, PageRequest.of(page,size));
        List<TestObjectDto> l = new ArrayList<>();

        p.getContent().forEach(e->{
            TestObjectDto elem = new TestObjectDto();
            elem.setId(e.getId().toString());
            elem.setName(e.getName());
            elem.setValue((e.getPrice() != null ) ? e.getPrice().toString() : null);
            elem.setCountry(e.getCountry());
            elem.setCity(e.getCity());
            l.add(elem);
        });

        Table table = new Table();
        table.setList(l);
        table.setPageNumber(page);
        table.setTotalElements((int) p.getTotalElements());
        table.setTotalPages(p.getTotalPages());

        return table;
    }

    @Override
    public void delete(String id) {
        TestObject testObject = testObjectRepo.findById(id).orElseThrow(
                () ->{
                    throw new ExceptionApi(PayLoadExceptionItem.INVALID_INPUT);
                }
        );

        testObject.setDeleted(true);
        testObjectRepo.save(testObject);
    }

    @Override
    public void edit(String id, TestObjectReq req) {
        TestObject testObject = testObjectRepo.findById(id).orElseThrow(
                () ->{
                    throw new ExceptionApi(PayLoadExceptionItem.INVALID_INPUT);
                }
        );

        testObject.setName(req.getName());
        testObject.setPrice(req.getValue());
        testObject.setCountry(req.getCountry());
        testObject.setCity(req.getCity());
        System.out.println(req.getValue());
        testObjectRepo.save(testObject);
    }

}
