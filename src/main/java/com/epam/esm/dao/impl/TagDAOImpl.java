package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.Tag;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TagDAOImpl implements TagDAO {

    private final EntityManager entityManager;
    private static final String ATTRIBUTE_NAME = "name";
    private static final String SELECT_ALL_TAGS = "SELECT tag FROM tag tag";
    private static final String FIND_BY_NAME = "select e from tag e where e.name = :name";
    private static final String SELECT_POPULAR_TAG = "select tag.id,tag.name FROM gift_order " +
        "INNER JOIN order_has_gift_certificate ON gift_order.id = gift_order_id " +
        "INNER JOIN relationship_certificates_and_tags ON order_has_gift_certificate.gift_certificate_id = "
        + "relationship_certificates_and_tags.gift_certificate_id "
        + "INNER JOIN tag ON tag_id = tag.id GROUP BY user_id,tag_id, tag.id ORDER BY sum(cost) DESC, "
        + "count(tag_id) DESC LIMIT 1";

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(SELECT_ALL_TAGS, Tag.class)
            .setFirstResult((page.getPage() * page.getSize()) - page.getSize())
            .setMaxResults(page.getSize())
            .getResultList();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tags = entityManager.createQuery(FIND_BY_NAME, Tag.class)
            .setParameter(ATTRIBUTE_NAME, name)
            .getResultList();
        if (ObjectUtils.isEmpty(tags)) {
            return Optional.empty();
        }
        return Optional.of(tags.get(0));
    }

    @Override
    public Tag find(Tag tag) {
        return findByName(tag.getName())
            .orElseGet(() -> {
                entityManager.persist(tag);
                return tag;
            });
    }

    @Transactional
    @Override
    public Tag findMostPopularTag() {
        return (Tag) entityManager.unwrap(Session.class)
            .createSQLQuery(SELECT_POPULAR_TAG)
            .addEntity(Tag.class)
            .getSingleResult();
    }
}
