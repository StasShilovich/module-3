package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.GenericDao;
import com.epam.esm.model.dao.entity.GiftCertificate;
import com.epam.esm.model.dao.entity.SortType;
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

    private final static Logger logger = Logger.getLogger(GiftCertificateServiceImpl.class);
    private final GenericDao<GiftCertificate> certificateDao;
    private final GiftCertificateDTOMapper dtoMapper;

    public GiftCertificateServiceImpl(GenericDao<GiftCertificate> certificateDao, GiftCertificateDTOMapper dtoMapper) {
        this.certificateDao = certificateDao;
        this.certificateDao.setClazz(GiftCertificate.class);
        this.dtoMapper = dtoMapper;
    }

    @Override
    @Transactional
    public CertificateDTO find(Long id) throws ServiceException, NotExistEntityException {
        try {
            Optional<GiftCertificate> certificate = certificateDao.findById(id);
            certificate.orElseThrow(() ->
                    new NotExistEntityException("Gift certificate with id=" + id + " not exist!"));
            return dtoMapper.toDTO(certificate.get());
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
            Optional<GiftCertificate> giftCertificate = null;
            giftCertificate.orElseThrow(() -> new ServiceException("Invalid gift certificate " + certificateDTO));
            return dtoMapper.toDTO(giftCertificate.get());
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

            Optional<GiftCertificate> giftCertificate = certificateDao.findById(certificateDTO.getId());
            giftCertificate.orElseThrow(
                    () -> new ServiceException("Update certificate exception with id=" + certificateDTO.getId()));
            return dtoMapper.toDTO(giftCertificate.get());
        } catch (DataAccessException e) {
            logger.error("Update certificate service exception", e);
            throw new ServiceException("Update certificate service exception", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException, NotExistEntityException {
        try {
            certificateDao.delete(id);
        } catch (DataAccessException e) {
            logger.error("Delete certificate service exception", e);
            throw new ServiceException("Delete certificate service exception", e);
        }
    }

    @Override
    @Transactional
    public List<CertificateDTO> filterByParameters(String tag, String part, String sortBy, SortType type)
            throws ServiceException {
        try {
//            List<GiftCertificate> certificates = certificateDao.filterByParameters(tag, part, sortBy, type);
//            return certificates.stream().map(dtoMapper::toDTO).collect(Collectors.toList());
            return null;
        } catch (DataAccessException e) {
            logger.error("Filter by parameters exception", e);
            throw new ServiceException("Filter by parameters exception", e);
        }
    }
}
