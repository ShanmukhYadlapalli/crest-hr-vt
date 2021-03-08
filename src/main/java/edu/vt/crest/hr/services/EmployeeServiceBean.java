package edu.vt.crest.hr.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.vt.crest.hr.entity.EmployeeEntity;

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
//	  System.out.println("Inside Create emp service");
	  EmployeeEntity ee = new EmployeeEntity();
	  ee.setFirstName(employee.getFirstName());
	  ee.setLastName(employee.getLastName());
//	  System.out.println("ee is :" + ee.getId() + "," + ee.getVersion() + "," + ee.getFirstName() + "," + ee.getLastName() + ";");
	  em.persist(ee);
	  return ee;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity findById(Long id) {
//	  System.out.println("In findById emp service");
	  EmployeeEntity employeeEntity = null;
	  String sql = "select id, version, first_name, last_name from employee where id = :empId";
	  Query query = em.createNativeQuery(sql);
	  query.setParameter("empId", id);
	  
	  List<Object[]> objectList = query.getResultList();
	  if(objectList != null && !objectList.isEmpty()) {
		  employeeEntity = new EmployeeEntity();
		  Object[] object = objectList.get(0);
		  employeeEntity.setId(object[0] != null ? ((BigInteger) object[0]).longValue() : null);
		  employeeEntity.setVersion(object[1]!=null ? ((Integer) object[1]).intValue() : null);
		  employeeEntity.setFirstName(((String) object[2])!=null ? ((String) object[2]) : null);
		  employeeEntity.setLastName(((String) object[3])!=null ? ((String) object[3]) : null);
	  }
	  return employeeEntity;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
@Override
  public List<EmployeeEntity> listAll(Integer startPosition, Integer maxResult) {
//	  System.out.println("In listAll emp service");
	  String sql = "select id, version, first_name, last_name from employee";
	  Query query = em.createNativeQuery(sql);
//	  query.setFirstResult(startPosition);
//	  query.setMaxResults(maxResult);
	  List<Object[]> objectList = query.getResultList();
	  List<EmployeeEntity> EmployeeEntities = new ArrayList<EmployeeEntity>();
	  for(Object[] object : objectList) {
		  EmployeeEntity employeeEntity = new EmployeeEntity();
		  try {
			  employeeEntity.setId(object[0] != null ? ((BigInteger) object[0]).longValue() : null);
			  employeeEntity.setVersion(object[1]!=null ? ((Integer) object[1]).intValue() : null);
			  employeeEntity.setFirstName(((String) object[2])!=null ? ((String) object[2]) : null);
			  employeeEntity.setLastName(((String) object[3])!=null ? ((String) object[3]) : null);
			  EmployeeEntities.add(employeeEntity);
//			  System.out.println(employeeEntity.getId() + "," + employeeEntity.getVersion() + "," + employeeEntity.getFirstName() + "," + employeeEntity.getLastName() + ";");
		  }catch(Exception e) {
			  e.printStackTrace(); 
//            System.out.println(e); 
		  }
	  }
	  return EmployeeEntities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity update(Long id, EmployeeEntity employee) throws OptimisticLockException {
//	  System.out.println("In update emp service");
//	  System.out.println(employee.getId() + "," + employee.getVersion() + "," + employee.getFirstName() + "," + employee.getLastName() + ";");
	  String sql = "Update employee set first_name = :newFirstName, last_Name = :newLastName where id = :empId";
	  Query query = em.createNativeQuery(sql);
	  query.setParameter("newFirstName", employee.getFirstName());
	  query.setParameter("newLastName", employee.getLastName());
	  query.setParameter("empId", id);
	  int rowsUpdated = query.executeUpdate();
//	  System.out.println("rows updated : " + rowsUpdated);
//	  System.out.println(employee.getId() + "," + employee.getVersion() + "," + employee.getFirstName() + "," + employee.getLastName() + ";");
	  if(rowsUpdated == 0) {
		  return null;
	  }
	  return employee;
  }
}
