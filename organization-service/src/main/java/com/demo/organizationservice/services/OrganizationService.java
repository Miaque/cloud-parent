package com.demo.organizationservice.services;

import com.demo.organizationservice.model.Organization;
import com.demo.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    public Organization getOrg(String organizationId) {
        return orgRepository.findOrganizationById(organizationId);
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        orgRepository.save(org);

    }

    public void updateOrg(Organization org) {
        orgRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        orgRepository.delete(org);
    }
}
