package edu.vt.crest.hr.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.vt.crest.hr.entity.DepartmentEntity;

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
//	  System.out.println("Inside Create department service");
	  DepartmentEntity de = new DepartmentEntity();
	  de.setName(department.getName());
	  de.setIdentifier(department.getIdentifier());
//	  System.out.println("de is :" + de.getId() + "," + de.getVersion() + "," + de.getName() + "," + de.getIdentifier() + ";");
	  em.persist(de);
	  return de;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity findById(Long id) {
//	  System.out.println("In findById department service");
	  DepartmentEntity departmentEntity = null;
	  String sql = "select id, version, name, identifier from department where id = :deptId";
	  Query query = em.createNativeQuery(sql);
	  query.setParameter("deptId", id);
	  
	  List<Object[]> objectList = query.getResultList();
	  if(objectList != null && !objectList.isEmpty()) {
		  departmentEntity = new DepartmentEntity();
		  Object[] object = objectList.get(0);
		  departmentEntity.setId(object[0] != null ? ((BigInteger) object[0]).longValue() : null);
		  departmentEntity.setVersion(object[1]!=null ? ((Integer) object[1]).intValue() : null);
		  departmentEntity.setName(((String) object[2])!=null ? ((String) object[2]) : null);
		  departmentEntity.setIdentifier(((String) object[3])!=null ? ((String) object[3]) : null);
	  }
	  return departmentEntity;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
@Override
  public List<DepartmentEntity> listAll(Integer startPosition, Integer maxResult) {
//	  System.out.println("In listAll deparment service");
	  String sql = "select id, version, name, identifier from department";
	  Query query = em.createNativeQuery(sql);
//	  query.setFirstResult(startPosition);
//	  query.setMaxResults(maxResult);
	  List<Object[]> objectList = query.getResultList();
	  List<DepartmentEntity> departmentEntities = new ArrayList<DepartmentEntity>();
	  for(Object[] object : objectList) {
		  DepartmentEntity departmentEntity = new DepartmentEntity();
		  try {
			  departmentEntity.setId(object[0] != null ? ((BigInteger) object[0]).longValue() : null);
			  departmentEntity.setVersion(object[1]!=null ? ((Integer) object[1]).intValue() : null);
			  departmentEntity.setName(((String) object[2])!=null ? ((String) object[2]) : null);
			  departmentEntity.setIdentifier(((String) object[3])!=null ? ((String) object[3]) : null);
			  departmentEntities.add(departmentEntity);
//			  System.out.println(departmentEntity.getId() + "," + departmentEntity.getVersion() + "," + departmentEntity.getName() + "," + departmentEntity.getIdentifier() + ";");
		  }catch(Exception e) {
			  e.printStackTrace(); 
//            System.out.println(e); 
		  }
	  }
	  return departmentEntities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity update(Long id, DepartmentEntity department) throws OptimisticLockException {
//	  System.out.println("In update deparment service");
//	  System.out.println(department.getId() + "," + department.getVersion() + "," + department.getName() + "," + department.getIdentifier() + ";");
	  String sql = "Update department set name = :newName, identifier = :newIdentifier where id = :deptId";
	  Query query = em.createNativeQuery(sql);
	  query.setParameter("newName", department.getName());
	  query.setParameter("newIdentifier", department.getIdentifier());
	  query.setParameter("deptId", id);
	  int rowsUpdated = query.executeUpdate();
//	  System.out.println("rows updated : " + rowsUpdated);
//	  System.out.println(department.getId() + "," + department.getVersion() + "," + department.getName() + "," + department.getIdentifier() + ";");
	  if(rowsUpdated == 0) {
		  return null;
	  }
	  return department;
  }

}
