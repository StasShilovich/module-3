package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
import com.epam.esm.model.service.Page;
import com.epam.esm.model.service.exception.IncorrectArgumentException;
import com.epam.esm.model.service.exception.NotExistEntityException;
import com.epam.esm.model.service.GiftCertificateService;
import com.epam.esm.model.service.converter.impl.GiftCertificateDTOMapper;
import com.epam.esm.model.service.dto.CertificateDTO;
import com.epam.esm.model.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final Logger logger = Logger.getLogger(GiftCertificateServiceImpl.class);
    private final GiftCertificateDao certificateDao;
    private final GiftCertificateDTOMapper dtoMapper;

    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, GiftCertificateDTOMapper dtoMapper) {
        this.certificateDao = certificateDao;
        this.dtoMapper = dtoMapper;
    }

    @Override
    @Transactional
    public CertificateDTO find(Long id) throws ServiceException, NotExistEntityException {
        try {
            Optional<GiftCertificate> certificate = certificateDao.findById(id);
            return certificate.map(dtoMapper::toDTO)
                    .orElseThrow(() -> new NotExistEntityException("Gift certificate with id=" + id + " not exist!"));
        } catch (DataAccessException e) {
            logger.error("Find certificate service exception", e);
            throw new ServiceException("Find certificate service exception", e);
        }
    }

    @Override
    @Transactional
    public CertificateDTO add(CertificateDTO certificateDTO) throws ServiceException {
        try {
            GiftCertificate certificate = dtoMapper.fromDTO(certificateDTO);
            GiftCertificate giftCertificate = certificateDao.create(certificate);
            return dtoMapper.toDTO(giftCertificate);
        } catch (DataAccessException e) {
            logger.error("Add certificate service exception", e);
            throw new ServiceException("Add certificate service exception", e);
        }
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) throws ServiceException {
        try {
            GiftCertificate certificate = dtoMapper.fromDTO(certificateDTO);
            GiftCertificate updated = certificateDao.update(certificate);
            return dtoMapper.toDTO(updated);
        } catch (DataAccessException e) {
            logger.error("Update certificate service exception", e);
            throw new ServiceException("Update certificate service exception", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        try {
            certificateDao.delete(id);
        } catch (DataAccessException e) {
            logger.error("Delete certificate service exception", e);
            throw new ServiceException("Delete certificate service exception", e);
        }
    }

    @Override
    @Transactional
    public List<CertificateDTO> filterByParameters(
            String tag, String part, String sortBy, SortType type, int page, int size)
            throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page certificatePage = new Page(page, size, count);
            List<GiftCertificate> certificates = certificateDao.filterByParameters(tag, part, sortBy, type,
                    certificatePage.getOffset(), certificatePage.getLimit());
            return certificates.stream().map(dtoMapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Filter by parameters exception", e);
            throw new ServiceException("Filter by parameters exception", e);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return certificateDao.getCountOfEntities();
        } catch (DataAccessException e) {
            logger.error("Count certificates service exception", e);
            throw new ServiceException("Count certificates service exception", e);
        }
    }
}
