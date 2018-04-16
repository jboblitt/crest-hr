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
    String strQuery = "Select e from " + EmployeeEntity.ENTITY_NAME + " e order by e.id";
    TypedQuery<EmployeeEntity> query = em.createQuery(strQuery, EmployeeEntity.class);
    List<EmployeeEntity> employeeEntities = query.getResultList();
    if (startPosition == null) startPosition = 0;
    if (maxResult == null) maxResult = employeeEntities.size();
    int endPosition = Math.min(startPosition + maxResult, employeeEntities.size());
    return employeeEntities.subList(startPosition, endPosition);
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
