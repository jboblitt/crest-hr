package edu.vt.crest.hr.services;

import edu.vt.crest.hr.entity.EmployeeEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
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
    em.persist(employee);
    return employee;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity findById(Long id) throws NoResultException {
    return em.find(EmployeeEntity.class, id);
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
    EmployeeEntity employeeToUpdate = em.find(EmployeeEntity.class, id);
    if (employeeToUpdate != null) {
      String firstName = employee.getFirstName();
      if (firstName != null && !firstName.isEmpty()) employeeToUpdate.setFirstName(firstName);
      String lastName = employee.getLastName();
      if (lastName != null && !lastName.isEmpty()) employeeToUpdate.setLastName(lastName);
    }
    return employeeToUpdate; // null if could not find employee by id
  }
}
