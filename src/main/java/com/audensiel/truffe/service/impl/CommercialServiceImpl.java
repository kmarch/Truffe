package com.audensiel.truffe.service.impl;

import com.audensiel.truffe.domain.Commercial;
import com.audensiel.truffe.repository.CommercialRepository;
import com.audensiel.truffe.service.CommercialService;
import com.audensiel.truffe.service.dto.CommercialDTO;
import com.audensiel.truffe.service.mapper.CommercialMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.audensiel.truffe.domain.Commercial}.
 */
@Service
public class CommercialServiceImpl implements CommercialService {

    private final Logger log = LoggerFactory.getLogger(CommercialServiceImpl.class);

    private final CommercialRepository commercialRepository;

    private final CommercialMapper commercialMapper;

    public CommercialServiceImpl(CommercialRepository commercialRepository, CommercialMapper commercialMapper) {
        this.commercialRepository = commercialRepository;
        this.commercialMapper = commercialMapper;
    }

    @Override
    public CommercialDTO save(CommercialDTO commercialDTO) {
        log.debug("Request to save Commercial : {}", commercialDTO);
        Commercial commercial = commercialMapper.toEntity(commercialDTO);
        commercial = commercialRepository.save(commercial);
        return commercialMapper.toDto(commercial);
    }

    @Override
    public CommercialDTO update(CommercialDTO commercialDTO) {
        log.debug("Request to update Commercial : {}", commercialDTO);
        Commercial commercial = commercialMapper.toEntity(commercialDTO);
        commercial = commercialRepository.save(commercial);
        return commercialMapper.toDto(commercial);
    }

    @Override
    public Optional<CommercialDTO> partialUpdate(CommercialDTO commercialDTO) {
        log.debug("Request to partially update Commercial : {}", commercialDTO);

        return commercialRepository
            .findById(commercialDTO.getId())
            .map(existingCommercial -> {
                commercialMapper.partialUpdate(existingCommercial, commercialDTO);

                return existingCommercial;
            })
            .map(commercialRepository::save)
            .map(commercialMapper::toDto);
    }

    @Override
    public List<CommercialDTO> findAll() {
        log.debug("Request to get all Commercials");
        return commercialRepository.findAll().stream().map(commercialMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<CommercialDTO> findOne(String id) {
        log.debug("Request to get Commercial : {}", id);
        return commercialRepository.findById(id).map(commercialMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Commercial : {}", id);
        commercialRepository.deleteById(id);
    }
}
