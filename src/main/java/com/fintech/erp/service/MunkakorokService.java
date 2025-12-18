package com.fintech.erp.service;

import com.fintech.erp.domain.Munkakorok;
import com.fintech.erp.repository.MunkakorokRepository;
import com.fintech.erp.service.dto.MunkakorokDTO;
import com.fintech.erp.service.mapper.MunkakorokMapper;
import com.fintech.erp.web.rest.errors.BadRequestAlertException;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.fintech.erp.domain.Munkakorok}.
 */
@Service
@Transactional
public class MunkakorokService {

    private static final Logger LOG = LoggerFactory.getLogger(MunkakorokService.class);

    private static final String ENTITY_NAME = "munkakorok";

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");

    private static final DecimalFormat CODE_FORMAT = new DecimalFormat("000");

    private final MunkakorokRepository munkakorokRepository;

    private final MunkakorokMapper munkakorokMapper;

    public MunkakorokService(MunkakorokRepository munkakorokRepository, MunkakorokMapper munkakorokMapper) {
        this.munkakorokRepository = munkakorokRepository;
        this.munkakorokMapper = munkakorokMapper;
    }

    /**
     * Save a munkakorok.
     *
     * @param munkakorokDTO the entity to save.
     * @return the persisted entity.
     */
    public MunkakorokDTO save(MunkakorokDTO munkakorokDTO) {
        LOG.debug("Request to save Munkakorok : {}", munkakorokDTO);
        Munkakorok munkakorok = munkakorokMapper.toEntity(munkakorokDTO);
        ensureMunkakorKod(munkakorok);
        munkakorok = munkakorokRepository.save(munkakorok);
        return munkakorokMapper.toDto(munkakorok);
    }

    /**
     * Update a munkakorok.
     *
     * @param munkakorokDTO the entity to save.
     * @return the persisted entity.
     */
    public MunkakorokDTO update(MunkakorokDTO munkakorokDTO) {
        LOG.debug("Request to update Munkakorok : {}", munkakorokDTO);
        Munkakorok munkakorok = munkakorokMapper.toEntity(munkakorokDTO);
        if (munkakorok.getMunkakorKod() == null || munkakorok.getMunkakorKod().isBlank()) {
            throw new BadRequestAlertException("A munkakör azonosító nem hagyható üresen", ENTITY_NAME, "kodhiany");
        }
        ensureKodUnique(munkakorok);
        munkakorok = munkakorokRepository.save(munkakorok);
        return munkakorokMapper.toDto(munkakorok);
    }

    /**
     * Partially update a munkakorok.
     *
     * @param munkakorokDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MunkakorokDTO> partialUpdate(MunkakorokDTO munkakorokDTO) {
        LOG.debug("Request to partially update Munkakorok : {}", munkakorokDTO);

        return munkakorokRepository
            .findById(munkakorokDTO.getId())
            .map(existingMunkakorok -> {
                munkakorokMapper.partialUpdate(existingMunkakorok, munkakorokDTO);
                ensureKodUnique(existingMunkakorok);
                return existingMunkakorok;
            })
            .map(munkakorokRepository::save)
            .map(munkakorokMapper::toDto);
    }

    /**
     * Get all the munkakoroks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MunkakorokDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Munkakoroks");
        return munkakorokRepository.findAll(pageable).map(munkakorokMapper::toDto);
    }

    /**
     * Get one munkakorok by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MunkakorokDTO> findOne(Long id) {
        LOG.debug("Request to get Munkakorok : {}", id);
        return munkakorokRepository.findById(id).map(munkakorokMapper::toDto);
    }

    /**
     * Delete the munkakorok by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Munkakorok : {}", id);
        munkakorokRepository.deleteById(id);
    }

    private void ensureMunkakorKod(Munkakorok entity) {
        if (entity.getMunkakorKod() != null && !entity.getMunkakorKod().isBlank()) {
            ensureKodUnique(entity);
            return;
        }

        int lastNumber = munkakorokRepository
            .findFirstByOrderByMunkakorKodDesc()
            .map(Munkakorok::getMunkakorKod)
            .filter(code -> NUMERIC_PATTERN.matcher(code).matches())
            .map(Integer::parseInt)
            .orElse(0);
        String nextCode = CODE_FORMAT.format(lastNumber + 1);
        entity.setMunkakorKod(nextCode);
    }

    private void ensureKodUnique(Munkakorok entity) {
        if (entity.getMunkakorKod() == null || entity.getMunkakorKod().isBlank()) {
            return;
        }
        munkakorokRepository
            .findByMunkakorKod(entity.getMunkakorKod())
            .filter(found -> entity.getId() == null || !found.getId().equals(entity.getId()))
            .ifPresent(found -> {
                throw new BadRequestAlertException("A megadott munkakör azonosító már létezik", ENTITY_NAME, "duplakod");
            });
    }
}
