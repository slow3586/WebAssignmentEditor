package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.demskv.webapplicationproject.employee.Employee;

@Stateless(name="AssignmentDAOEJB")
public class AssignmentDAO implements AssignmentDAOLocal {

    @PersistenceContext(unitName="mysql")
    EntityManager entityManager;
    
    @Override
    public Long countAll(Integer filterId, String filterTopic, String filterText, 
                Integer filterAuthor, String filterExecuteby, 
                String filterExecuteattr,Set<Integer> filterExecutors) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Assignment> root = cq.from(Assignment.class);
        cq.select(cb.count(root));
        if(filterId!=null)
            cq.where(cb.equal(root.get("id"),filterId));
        if(filterTopic!=null)
            cq.where(cb.like(root.<String>get("topic").as(String.class),"%"+filterTopic+"%"));
        if(filterText!=null)
            cq.where(cb.like(root.<String>get("text").as(String.class),"%"+filterText+"%"));
        return entityManager.createQuery(cq).getSingleResult();
    }
    
    @Override
    public List<AssignmentDTO> findAll(int from, int limit, String orderBy, boolean desc, 
            Integer filterId, String filterTopic, String filterText, 
            Integer filterAuthor, String filterExecuteby, 
            String filterExecuteattr,Set<Integer> filterExecutors) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
        Root<Assignment> root = cq.from(Assignment.class);
        if(desc)
            cq.orderBy(cb.desc(root.get(orderBy)));
        else
            cq.orderBy(cb.asc(root.get(orderBy)));
        if(filterId!=null)
            cq.where(cb.equal(root.get("id"),filterId));
        if(filterTopic!=null)
            cq.where(cb.like(root.<String>get("topic").as(String.class),"%"+filterTopic+"%"));
        if(filterText!=null)
            cq.where(cb.like(root.<String>get("text").as(String.class),"%"+filterText+"%"));
        List<Assignment> assignments = entityManager.createQuery(cq).setFirstResult(from).setMaxResults(limit).getResultList();
        List<AssignmentDTO> dtos = new ArrayList<>(assignments.size());
        for (Assignment a : assignments) {
            dtos.add(new AssignmentDTO(
                    a.getId(), a.getTopic(), a.getExecuteby(), 
                    a.getExecuteattr(),a.getControlattr(), a.getText(), 
                    a.getAuthor().getId(), a.getExecutors()));
        }
        return dtos;
     }
    
    @Override
    public void create(AssignmentDTO dto){    
        entityManager.getTransaction().begin();
        
        Assignment assignment = new Assignment();
        assignment.setTopic(dto.getTopic());
        assignment.setExecuteby(dto.getExecuteby());
        assignment.setControlattr(dto.getControlattr());
        assignment.setExecuteattr(dto.getExecuteattr());
        assignment.setText(dto.getText());
        assignment.setAuthor(entityManager.getReference(Employee.class, dto.getAuthor_id()));
        Set<Employee> l = new HashSet<>(dto.getExecutors_ids().size());
        for (Integer eid : dto.getExecutors_ids())
            l.add(entityManager.getReference(Employee.class, eid));
        assignment.setExecutors(l);
        assignment.setAuthor_id(dto.getAuthor_id());
        
        System.out.println(assignment.getTopic());
        System.out.println(assignment.getExecuteby());
        System.out.println(assignment.getControlattr());
        System.out.println(assignment.getExecuteattr());
        System.out.println(assignment.getText());
        System.out.println(assignment.getAuthor().getFirstname());
        System.out.println(assignment.getExecutors().size());
        
        entityManager.persist(assignment);
        entityManager.getTransaction().commit();
    }
    
    @Override
    public void update(AssignmentDTO dto){
        entityManager.getTransaction().begin();
        
        Assignment assignment = entityManager.find(Assignment.class, dto.getId());
        assignment.setTopic(dto.getTopic());
        assignment.setExecuteby(dto.getExecuteby());
        assignment.setControlattr(dto.getControlattr());
        assignment.setExecuteattr(dto.getExecuteattr());
        assignment.setText(dto.getText());
        assignment.setAuthor(entityManager.getReference(Employee.class, dto.getAuthor_id()));
        Set<Employee> l = new HashSet<>(dto.getExecutors_ids().size());
        for (Integer eid : dto.getExecutors_ids())
            l.add(entityManager.getReference(Employee.class, eid));
        assignment.setExecutors(l);
        
        entityManager.merge(assignment);
        entityManager.getTransaction().commit();
    }
    
    @Override
    public void deleteById(int id){
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Assignment.class, id));
        entityManager.getTransaction().commit();
    }
}

