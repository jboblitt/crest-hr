package edu.vt.crest.hr.services;

import edu.vt.crest.hr.entity.EmployeeEntity;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * An EmployeeService defines the contract for a class that will provide
 * service access to database entities.
 */
public interface EmployeeService {
  /**
   * @param employee the EmployeeEntity to create
   * @return the new EmployeeEntity
   */
  EmployeeEntity createEmployee(EmployeeEntity employee);

  /**
   * @param id of the EmployeeEntity to return
   * @return the matching EmployeeEntity
   */
  EmployeeEntity findById(Long id) throws NoResultException;

  /**
   * @param startPosition the index of the first EmployeeEntity to return
   * @param maxResult the maximum number of EmployeeEntity(s) to return
   *                  beyond the startPosition
   * @return a list of EmployeeEntity(s)
   */
  List<EmployeeEntity> listAll(Integer startPosition, Integer maxResult);

  /**
   * @param id the id of the EmployeeEntity to update
   * @param employee the entity used to update
   * @return the updated EmployeeEntity
   */
  EmployeeEntity update(Long id, EmployeeEntity employee);
}
