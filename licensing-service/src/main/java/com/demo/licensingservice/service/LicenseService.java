package com.demo.licensingservice.service;

import com.demo.licensingservice.clients.OrganizationDiscoveryClient;
import com.demo.licensingservice.clients.OrganizationFeignClient;
import com.demo.licensingservice.clients.OrganizationRestTemplateClient;
import com.demo.licensingservice.config.ServiceConfig;
import com.demo.licensingservice.model.License;
import com.demo.licensingservice.model.Organization;
import com.demo.licensingservice.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Log
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);
        license.setOrganizationName(org.getName());
        license.setContactName(org.getContactName());
        license.setContactEmail(org.getContactEmail());
        license.setContactPhone(org.getContactPhone());
        license.setComment(config.getExampleProperty());

        return license;
    }

    private void randomlyRunLong() {
        Random random = new Random();

        int randomNum = random.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*// 后备处理
    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();

        return licenseRepository.findByOrganizationId(organizationId);
    }*/

    /*@HystrixCommand(
            commandProperties = {
                    // 定制断路器超时时间
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "12000")
            }
    )
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();

        return licenseRepository.findByOrganizationId(organizationId);
    }*/

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList", threadPoolKey = "licenseByOrgThreadPool",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")}
    )
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();

        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
    }

    public void saveLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license);
    }
}
