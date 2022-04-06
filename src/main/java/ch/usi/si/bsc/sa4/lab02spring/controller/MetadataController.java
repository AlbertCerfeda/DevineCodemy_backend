package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.AddDataDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.MetadataDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.usi.si.bsc.sa4.lab02spring.service.MetadataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController

@RequestMapping("/metadata")
public class MetadataController {
    private final MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService){ this.metadataService = metadataService;}

    /**
     * GET  /metadata
     */
    @GetMapping
    public ResponseEntity<List<MetadataDTO>> getAll() {
        ArrayList<MetadataDTO> allData = new ArrayList<MetadataDTO>();

        for (Metadata metadata : metadataService.getAll()) {
            allData.add(metadata.toMetadataDTO());
        }

        return ResponseEntity.ok(allData);
    }

    //TODO GET /username - all data for a specific user

    //TODO GET /username?levelname - all data for a specific user, for a specific level

    /**
     * POST /metadata
     */
    @PostMapping
    public ResponseEntity<MetadataDTO> addUser(@RequestBody AddDataDTO data) {
        Metadata newData = metadataService.createData(data);
        return ResponseEntity.ok(newData.toMetadataDTO());
    }

    /**
     * PUT /metadata/addData
     */
    @PutMapping("/addData")
    public ResponseEntity<MetadataDTO> addData(@RequestBody AddDataDTO data) {

        Optional<Metadata> optionalUser = metadataService.getByUsername(data.getUsername());
        if (optionalUser.isPresent()) {
            Metadata savedData = optionalUser.get();
            metadataService.updateData(savedData,data);
            return ResponseEntity.ok(savedData.toMetadataDTO());
        }
        return ResponseEntity.notFound().build();
    }
}

