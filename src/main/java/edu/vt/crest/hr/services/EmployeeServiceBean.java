package edu.vt.crest.hr.services;

import edu.vt.crest.hr.entity.EmployeeEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implements an EmployeeService
 */
@ApplicationScoped
public class EmployeeServiceBean implements EmployeeService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity createEmployee(EmployeeEntity employee) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity findById(Long id) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<EmployeeEntity> listAll(Integer startPosition, Integer maxResult) {
    String strQuery = "Select e from " + EmployeeEntity.ENTITY_NAME + " e where e.id >= :id";
    TypedQuery<EmployeeEntity> query = em.createQuery(strQuery, EmployeeEntity.class);
    query.setParameter("id", Long.valueOf(startPosition));
    query.setMaxResults(maxResult);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity update(Long id, EmployeeEntity employee) throws OptimisticLockException {
    return null;
  }
}
