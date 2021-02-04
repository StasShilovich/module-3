package com.epam.esm.controller;

import com.epam.esm.controller.rest.TagController;
import com.epam.esm.model.service.exception.IncorrectArgumentException;
import com.epam.esm.model.service.exception.ServiceException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface CommonController<T> {
    ResponseEntity<PagedModel<T>> findAll(int page, int size) throws ServiceException, IncorrectArgumentException;

    default List<Link> buildLink(Class<? extends CommonController<T>> clazz, int page, int size, long maxPage)
            throws ServiceException, IncorrectArgumentException {
        List<Link> linkList = new ArrayList<>();
        Link self = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(clazz)
                        .findAll(page, size)
        ).withRel("self");
        linkList.add(self);
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(clazz)
                            .findAll(page - 1, size)
            ).withRel("previous");
            linkList.add(previous);
        }
        if (maxPage > page) {
            Link next = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(TagController.class)
                            .findAll(page + 1, size)
            ).withRel("next");
            linkList.add(next);
        }
        return linkList;
    }
}
