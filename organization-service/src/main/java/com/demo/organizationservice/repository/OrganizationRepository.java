package com.demo.organizationservice.repository;

import com.demo.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

    public Organization findOrganizationById(String organizationId);

}
