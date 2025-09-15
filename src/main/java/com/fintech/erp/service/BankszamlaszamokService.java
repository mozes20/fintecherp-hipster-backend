package com.fintech.erp.service;

import com.fintech.erp.domain.Bankszamlaszamok;
import com.fintech.erp.repository.BankszamlaszamokRepository;
import com.fintech.erp.service.dto.BankszamlaszamokDTO;
import com.fintech.erp.service.mapper.BankszamlaszamokMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Bankszamlaszamok}.
 */
@Service
@Transactional
public class BankszamlaszamokService {

    private static final Logger LOG = LoggerFactory.getLogger(BankszamlaszamokService.class);

    private final BankszamlaszamokRepository bankszamlaszamokRepository;

    private final BankszamlaszamokMapper bankszamlaszamokMapper;

    public BankszamlaszamokService(BankszamlaszamokRepository bankszamlaszamokRepository, BankszamlaszamokMapper bankszamlaszamokMapper) {
        this.bankszamlaszamokRepository = bankszamlaszamokRepository;
        this.bankszamlaszamokMapper = bankszamlaszamokMapper;
    }

    /**
     * Save a bankszamlaszamok.
     *
     * @param bankszamlaszamokDTO the entity to save.
     * @return the persisted entity.
     */
    public BankszamlaszamokDTO save(BankszamlaszamokDTO bankszamlaszamokDTO) {
        LOG.debug("Request to save Bankszamlaszamok : {}", bankszamlaszamokDTO);
        Bankszamlaszamok bankszamlaszamok = bankszamlaszamokMapper.toEntity(bankszamlaszamokDTO);
        bankszamlaszamok = bankszamlaszamokRepository.save(bankszamlaszamok);
        return bankszamlaszamokMapper.toDto(bankszamlaszamok);
    }

    /**
     * Update a bankszamlaszamok.
     *
     * @param bankszamlaszamokDTO the entity to save.
     * @return the persisted entity.
     */
    public BankszamlaszamokDTO update(BankszamlaszamokDTO bankszamlaszamokDTO) {
        LOG.debug("Request to update Bankszamlaszamok : {}", bankszamlaszamokDTO);
        Bankszamlaszamok bankszamlaszamok = bankszamlaszamokMapper.toEntity(bankszamlaszamokDTO);
        bankszamlaszamok = bankszamlaszamokRepository.save(bankszamlaszamok);
        return bankszamlaszamokMapper.toDto(bankszamlaszamok);
    }

    /**
     * Partially update a bankszamlaszamok.
     *
     * @param bankszamlaszamokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankszamlaszamokDTO> partialUpdate(BankszamlaszamokDTO bankszamlaszamokDTO) {
        LOG.debug("Request to partially update Bankszamlaszamok : {}", bankszamlaszamokDTO);

        return bankszamlaszamokRepository
            .findById(bankszamlaszamokDTO.getId())
            .map(existingBankszamlaszamok -> {
                bankszamlaszamokMapper.partialUpdate(existingBankszamlaszamok, bankszamlaszamokDTO);

                return existingBankszamlaszamok;
            })
            .map(bankszamlaszamokRepository::save)
            .map(bankszamlaszamokMapper::toDto);
    }

    /**
     * Get one bankszamlaszamok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankszamlaszamokDTO> findOne(Long id) {
        LOG.debug("Request to get Bankszamlaszamok : {}", id);
        return bankszamlaszamokRepository.findById(id).map(bankszamlaszamokMapper::toDto);
    }

    /**
     * Delete the bankszamlaszamok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Bankszamlaszamok : {}", id);
        bankszamlaszamokRepository.deleteById(id);
    }
}
