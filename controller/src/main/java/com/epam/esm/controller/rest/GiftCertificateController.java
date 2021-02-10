package com.epam.esm.controller.rest;

import com.epam.esm.model.dao.entity.SortType;
import com.epam.esm.model.service.exception.IncorrectArgumentException;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.GiftCertificateService;
import com.epam.esm.model.service.dto.CertificateDTO;
import com.epam.esm.model.service.exception.ServiceException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Gift certificate RestAPI.
 */

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private final GiftCertificateService certificateService;

    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Find certificate by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> find(@PathVariable(name = "id") Long id)
            throws ServiceException, NotExistEntityException {
        CertificateDTO result = certificateService.find(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Add certificate.
     *
     * @param certificateDTO the certificate dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PostMapping
    public ResponseEntity<CertificateDTO> add(@RequestBody CertificateDTO certificateDTO) throws ServiceException {
        CertificateDTO result = certificateService.add(certificateDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * Update certificate. Mark the fields that are not specified for updating null.
     *
     * @param certificateDTO the certificate dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PutMapping(consumes = "application/json")
    public ResponseEntity<CertificateDTO> update(@RequestBody CertificateDTO certificateDTO) throws ServiceException {
        CertificateDTO result = certificateService.update(certificateDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete certificate by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id)
            throws ServiceException, NotExistEntityException {
        certificateService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Filter gift certificates by parameters
     *
     * @param tag
     * @param part
     * @param sortBy
     * @param type
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    @GetMapping
    public ResponseEntity<PagedModel<CertificateDTO>> filterByParameter(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "part", required = false) String part,
            @RequestParam(value = "sort_by", required = false) String sortBy,
            @RequestParam(value = "type", required = false) SortType type,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<CertificateDTO> list = certificateService.filterByParameters(tag, part, sortBy, type, page, size);
        long count = certificateService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = new ArrayList<>();
        Link self = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(GiftCertificateController.class)
                        .filterByParameter(tag, part, sortBy, type, page, size)
        ).withRel("self");
        linkList.add(self);
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(GiftCertificateController.class)
                            .filterByParameter(tag, part, sortBy, type, page - 1, size)
            ).withRel("previous");
            linkList.add(previous);
        }
        if (pageMetadata.getTotalPages() > page) {
            Link next = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(GiftCertificateController.class)
                            .filterByParameter(tag, part, sortBy, type, page + 1, size)
            ).withRel("next");
            linkList.add(next);
        }
        PagedModel<CertificateDTO> pagedModel = PagedModel.of(list, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }
}
