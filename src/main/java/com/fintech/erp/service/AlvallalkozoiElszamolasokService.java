package com.fintech.erp.service;

import com.fintech.erp.domain.AlvallalkozoiElszamolasok;
import com.fintech.erp.repository.AlvallalkozoiElszamolasokRepository;
import com.fintech.erp.service.dto.AlvallalkozoiElszamolasokDTO;
import com.fintech.erp.service.mapper.AlvallalkozoiElszamolasokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.AlvallalkozoiElszamolasok}.
 */
@Service
@Transactional
public class AlvallalkozoiElszamolasokService {

    private static final Logger LOG = LoggerFactory.getLogger(AlvallalkozoiElszamolasokService.class);

    private final AlvallalkozoiElszamolasokRepository alvallalkozoiElszamolasokRepository;
    private final AlvallalkozoiElszamolasokMapper alvallalkozoiElszamolasokMapper;

    public AlvallalkozoiElszamolasokService(
        AlvallalkozoiElszamolasokRepository alvallalkozoiElszamolasokRepository,
        AlvallalkozoiElszamolasokMapper alvallalkozoiElszamolasokMapper
    ) {
        this.alvallalkozoiElszamolasokRepository = alvallalkozoiElszamolasokRepository;
        this.alvallalkozoiElszamolasokMapper = alvallalkozoiElszamolasokMapper;
    }

    public AlvallalkozoiElszamolasokDTO save(AlvallalkozoiElszamolasokDTO dto) {
        LOG.debug("Request to save AlvallalkozoiElszamolasok : {}", dto);
        AlvallalkozoiElszamolasok entity = alvallalkozoiElszamolasokMapper.toEntity(dto);
        entity = alvallalkozoiElszamolasokRepository.save(entity);
        return alvallalkozoiElszamolasokMapper.toDto(entity);
    }

    public AlvallalkozoiElszamolasokDTO update(AlvallalkozoiElszamolasokDTO dto) {
        LOG.debug("Request to update AlvallalkozoiElszamolasok : {}", dto);
        AlvallalkozoiElszamolasok entity = alvallalkozoiElszamolasokMapper.toEntity(dto);
        entity = alvallalkozoiElszamolasokRepository.save(entity);
        return alvallalkozoiElszamolasokMapper.toDto(entity);
    }

    public Optional<AlvallalkozoiElszamolasokDTO> partialUpdate(AlvallalkozoiElszamolasokDTO dto) {
        LOG.debug("Request to partially update AlvallalkozoiElszamolasok : {}", dto);
        return alvallalkozoiElszamolasokRepository
            .findById(dto.getId())
            .map(existing -> {
                alvallalkozoiElszamolasokMapper.partialUpdate(existing, dto);
                return existing;
            })
            .map(alvallalkozoiElszamolasokRepository::save)
            .map(alvallalkozoiElszamolasokMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AlvallalkozoiElszamolasokDTO> findOne(Long id) {
        LOG.debug("Request to get AlvallalkozoiElszamolasok : {}", id);
        return alvallalkozoiElszamolasokRepository.findById(id).map(alvallalkozoiElszamolasokMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete AlvallalkozoiElszamolasok : {}", id);
        alvallalkozoiElszamolasokRepository.deleteById(id);
    }
}
