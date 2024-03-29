package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    public final TagDAO tagDAO;
    public final CertificateDAO certificateDAO;
    public final MapperDTO mapperDTO;

    @Override
    public List<TagDTO> findAll(Page page) {
        return tagDAO.findAll(page)
            .stream()
            .map(mapperDTO::convertTagToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TagDTO findById(Long id) {
        return mapperDTO.convertTagToDTO(tagDAO.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO tagDTO) {
        return mapperDTO.convertTagToDTO(tagDAO.find(mapperDTO.convertDTOToTag(tagDTO)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagDAO.delete(tagDAO.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<TagDTO> findAllByCertificateId(Long id, Page page) {
        Optional<Certificate> optional = certificateDAO.findById(id);
        if (optional.isEmpty() || !optional.get().isActive()) {
            throw new EntityNotFoundException(id.toString());
        }
        return optional.get().getTags()
            .stream()
            .skip((page.getPage() * page.getSize()) - page.getSize())
            .limit(page.getSize())
            .map(mapperDTO::convertTagToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TagDTO findMostPopularTag() {
        return mapperDTO.convertTagToDTO(tagDAO.findMostPopularTag());
    }
}
