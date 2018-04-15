package edu.vt.crest.hr.services;

import edu.vt.crest.hr.entity.DepartmentEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implements a DepartmentService
 */
@ApplicationScoped
public class DepartmentServiceBean implements DepartmentService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity createDepartment(DepartmentEntity department) {
    em.persist(department);
    return department;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity findById(Long id) {
    return em.find(DepartmentEntity.class, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DepartmentEntity> listAll(Integer startPosition, Integer maxResult) {
    String strQuery = "Select d from " + DepartmentEntity.ENTITY_NAME + " d where d.id >= :id";
    TypedQuery<DepartmentEntity> query = em.createQuery(strQuery, DepartmentEntity.class);
    query.setParameter("id", startPosition != null ? Long.valueOf(startPosition) : 0l);
    if (maxResult != null) query.setMaxResults(maxResult);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity update(Long id, DepartmentEntity department) throws OptimisticLockException {
    DepartmentEntity departmentToUpdate = em.find(DepartmentEntity.class, id);
    if (departmentToUpdate != null) {
      String newIdentifier = department.getIdentifier();
      if (newIdentifier != null && !newIdentifier.isEmpty()) departmentToUpdate.setIdentifier(newIdentifier);
      String newName = department.getName();
      if (newName != null && !newName.isEmpty()) departmentToUpdate.setName(newName);
    }
    return departmentToUpdate; // null if could not find department by id
  }

}
