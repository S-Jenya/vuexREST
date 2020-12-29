package com.stp.demo.service;

import com.stp.demo.model.Institution;
import com.stp.demo.repository.InstitutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public List<Institution> selectInstFromCard(Long id){
        return institutionRepository.findInstFromCardCustomQuery(id);
    }

    public List<Institution> selectInstByIdCard(Long id){
        return institutionRepository.selectInstByIdCard(id);
    }

    public Institution selectInstByName(String name){
        return institutionRepository.selectInstByName(name);
    }

    public  Institution selectInstByName(Long id) {
        return institutionRepository.selectInstByNameCustomQuery(id);
    }

    public void deleteById(Long id) {
        institutionRepository.deleteById(id);
    }
}
