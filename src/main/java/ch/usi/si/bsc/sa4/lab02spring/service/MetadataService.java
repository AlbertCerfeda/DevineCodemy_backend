package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.AddDataDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Metadata.Metadata;
import ch.usi.si.bsc.sa4.lab02spring.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetadataService {
    private final MetadataRepository metadataRepository;

    @Autowired
    public MetadataService(MetadataRepository metadataRepository) {this.metadataRepository = metadataRepository;}


    public Optional<Metadata> getByUsername(String username) {return metadataRepository.findByUsernameContaining(username);}

    public Metadata createData(AddDataDTO metadata) {
        var data = new Metadata(metadata.getUsername());
        data.addData(metadata.getLevel_name(), metadata.getData());
        return metadataRepository.save(data);
    }


    public void updateData(Metadata metadata, AddDataDTO data) {
        Metadata.updateData(metadata,data);
        metadataRepository.save(metadata);
    }

    public List<Metadata> getAll() {return metadataRepository.findAll();}

}