package com.jss.sdmp.simulation;

import com.jss.sdmp.data.BinDataRepository;
import com.jss.sdmp.data.model.BinData;
import com.jss.sdmp.management.bin.BinRepository;
import com.jss.sdmp.management.bin.model.Bin;
import com.jss.sdmp.management.bin.register.BinRegisterDto;
import com.jss.sdmp.management.bin.register.BinRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/simulate")
public class BinSimulator {

    private final BinRegistrationService binRegistrationService;

    private final BinRepository binRepository;

    private final BinDataRepository binDataRepository;

    @Autowired
    public BinSimulator(BinRegistrationService binRegistrationService,
                        BinRepository binRepository, BinDataRepository binDataRepository) {
        this.binRegistrationService = binRegistrationService;
        this.binRepository = binRepository;
        this.binDataRepository = binDataRepository;
    }

    @PostMapping("/bin/activate")
    public void activateDustbin(@RequestParam String bin) {
        binRegistrationService.activate(bin);
    }


    @PostMapping("/bin/create_new_dustbins")
    public void createDustbins(Principal principal, @RequestParam int count, @RequestParam double lat,
                               @RequestParam double lng, @RequestParam String wardId) {

        RandomStringGenerator generator = new RandomStringGenerator(10);

        Random radiusGenerator = new Random();

        for (int i = 0; i < count; i++) {
            BinRegisterDto binRegisterDto = new BinRegisterDto();
            binRegisterDto.setBin(generator.nextString());
            binRegisterDto.setWardId(wardId);

            double[] location = RandomLocationGenerator.getLocation(lat, lng,
                radiusGenerator.nextInt(1000) + 100);
            binRegisterDto.setLat(location[0]);
            binRegisterDto.setLng(location[1]);

            binRegistrationService.register(principal.getName(), binRegisterDto);
        }

    }


    @GetMapping("/bin/activate_all_dustbins")
    public void activateDustbins(Principal principal) {
        List<Bin> bins = binRepository.findAll();
        for (Bin bin : bins) {
            binRegistrationService.activate(bin.getBin());
        }
    }

    @PostMapping("/bin/create_logs_single_dustbin")
    public void createLogsSingleDustbin(Principal principal, @RequestParam int count, @RequestParam String bin) {

        int prev = 10;

        Random random = new Random();

        Instant instant = Instant.now();

        for (int i = 0; i < count; i++) {
            BinData data = new BinData();
            data.setBin(bin);
            data.setPercentage((random.nextInt(prev * 10))/10.0);
            data.setInstant(instant);
            instant = instant.plusMillis(10 * 60 * 1000);
            binDataRepository.save(data);
            prev += 10;
            if (prev >= 100) {
                prev = 10;
            }
        }

    }


}
