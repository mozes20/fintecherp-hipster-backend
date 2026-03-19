package com.fintech.erp.service;

import com.fintech.erp.domain.AlvallalkozoiTigDokumentumok;
import com.fintech.erp.repository.AlvallalkozoiTigDokumentumokRepository;
import com.fintech.erp.service.dto.AlvallalkozoiTigDokumentumokDTO;
import com.fintech.erp.service.mapper.AlvallalkozoiTigDokumentumokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.AlvallalkozoiTigDokumentumok}.
 */
@Service
@Transactional
public class AlvallalkozoiTigDokumentumokService {

    private static final Logger LOG = LoggerFactory.getLogger(AlvallalkozoiTigDokumentumokService.class);

    private final AlvallalkozoiTigDokumentumokRepository repository;
    private final AlvallalkozoiTigDokumentumokMapper mapper;

    public AlvallalkozoiTigDokumentumokService(
        AlvallalkozoiTigDokumentumokRepository repository,
        AlvallalkozoiTigDokumentumokMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AlvallalkozoiTigDokumentumokDTO save(AlvallalkozoiTigDokumentumokDTO dto) {
        LOG.debug("Request to save AlvallalkozoiTigDokumentumok : {}", dto);
        AlvallalkozoiTigDokumentumok entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Optional<AlvallalkozoiTigDokumentumokDTO> findOne(Long id) {
        LOG.debug("Request to get AlvallalkozoiTigDokumentumok : {}", id);
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete AlvallalkozoiTigDokumentumok : {}", id);
        repository.deleteById(id);
    }
}
