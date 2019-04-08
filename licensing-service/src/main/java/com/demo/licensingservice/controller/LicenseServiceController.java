package com.demo.licensingservice.controller;

import com.demo.licensingservice.config.ServiceConfig;
import com.demo.licensingservice.model.License;
import com.demo.licensingservice.service.LicenseService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/license")
public class LicenseServiceController {

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private ServiceConfig serviceConfig;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {

        return licenseService.getLicensesByOrg(organizationId);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {

        return licenseService.getLicense(organizationId, licenseId, "");
    }

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId,
                                         @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
    public void updateLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
        licenseService.updateLicense(license);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
        licenseService.deleteLicense(license);
    }
}
