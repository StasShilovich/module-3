package com.epam.esm.controller.rest;

import com.epam.esm.controller.CommonController;
import com.epam.esm.model.service.exception.IncorrectArgumentException;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.TagService;
import com.epam.esm.model.service.dto.TagDTO;
import com.epam.esm.model.service.exception.ServiceException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tag RestAPI.
 */
@RestController
@RequestMapping("/tags")
public class TagController implements CommonController<TagDTO> {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> find(@PathVariable(name = "id") Long id) throws ServiceException, NotExistEntityException {
        TagDTO tag = tagService.find(id);
        return ResponseEntity.ok(tag);
    }

    /**
     * Add tag.
     *
     * @param tag tag
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<TagDTO> add(@RequestBody TagDTO tag) throws ServiceException {
        TagDTO result = tagService.add(tag);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete tag by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) throws ServiceException, NotExistEntityException {
        tagService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<PagedModel<TagDTO>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<TagDTO> tags = tagService.findAll(page, size);
        long count = tagService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(TagController.class, page, size, pageMetadata.getTotalPages());
        PagedModel<TagDTO> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }
}