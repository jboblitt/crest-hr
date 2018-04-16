package edu.vt.crest.hr.services;

import edu.vt.crest.hr.entity.DepartmentEntity;
import edu.vt.crest.hr.entity.EmployeeEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

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
    if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
      // Employees have been reassigned to this new department
      // TODO sync department object with DB before returning
    }
    return department;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity findById(Long id) {
    String strQuery = "Select d from " + DepartmentEntity.ENTITY_NAME + " d left join fetch d.employees where d.id = :id";
    TypedQuery<DepartmentEntity> query = em.createQuery(strQuery, DepartmentEntity.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DepartmentEntity> listAll(Integer startPosition, Integer maxResult) {
    String strQuery = "Select distinct d from " + DepartmentEntity.ENTITY_NAME + " d left join fetch d.employees order by d.id";
    TypedQuery<DepartmentEntity> query = em.createQuery(strQuery, DepartmentEntity.class);
    List<DepartmentEntity> departmentEntities = query.getResultList();
    if (startPosition == null) startPosition = 0;
    if (maxResult == null) maxResult = Integer.MAX_VALUE;
    int endPosition = Math.min(startPosition + maxResult, departmentEntities.size());
    return departmentEntities.subList(startPosition, endPosition);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity update(Long id, DepartmentEntity department) throws OptimisticLockException {
    DepartmentEntity departmentToUpdate = findById(id);
    String newIdentifier = department.getIdentifier();
    if (newIdentifier != null && !newIdentifier.isEmpty()) departmentToUpdate.setIdentifier(newIdentifier);
    String newName = department.getName();
    if (newName != null && !newName.isEmpty()) departmentToUpdate.setName(newName);
    Set<EmployeeEntity> newEmployees = department.getEmployees();
    if (newEmployees != null) departmentToUpdate.setEmployees(newEmployees);
    return findById(departmentToUpdate.getId());
  }

}
