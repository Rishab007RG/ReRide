package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.repository.InspectionRepo;

@Service
public class InspectionService {

    @Autowired
    InspectionRepo inspectionRepo;

    public Inspection addInspection(Inspection inspection){
        return inspectionRepo.save(inspection);
    }
}

