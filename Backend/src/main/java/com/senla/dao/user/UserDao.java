package com.senla.dao.user;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.user.User;
import com.senla.model.user.User_;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao extends AbstractLongDao<User> implements IUserDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = User.class;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query q = em.createNativeQuery("select * from users where email = ?", User.class);
        q.setParameter(1, email);

        List<User> users = q.getResultList();

        if(users.size() >= 2) throw new RuntimeException("Database error: 2 emails found");
        else if(users.size() == 1) return Optional.of(users.getFirst());
        else return Optional.empty();
    }

    @Override
    public User findCustomer(Long userId) {
        TypedQuery<User> q = em.createQuery("""
                                            select u from User u
                                            join u.role r
                                            where r.roleName ilike 'customer'
                                                and u.userId = :userId
                                            """ ,User.class);
        q.setParameter("userId", userId);
        return q.getSingleResult();
    }
    @Override
    public Optional<User> findUserByEmailWithAuthorities(String email){
        TypedQuery<User> q = em.createQuery("""
                                            select u from User u
                                            join fetch u.role r
                                            join fetch r.privileges
                                            where u.email = :email""", User.class);
        q.setParameter("email", email);
        return q.getResultStream().findFirst();
    }

    @Override
    public Optional<User> findMe(Long id) {
        TypedQuery<User> q = em.createQuery("""
                                select u from User u
                                join fetch u.role
                                where u.id = :id""", User.class);
        q.setParameter("id", id);
        return q.getResultStream().findFirst();
    }

    @Override
    public List<User> getAllWithFilterAndPagination(Specification<User> specification, PaginationDetails paginationDetails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        root.fetch(User_.role);

        cq.select(root);
        if(specification != null){
            cq.where(specification.toPredicate(root, cq , cb));
        }
        TypedQuery<User> query = em.createQuery(cq);
        query.setFirstResult(paginationDetails.getOffset());
        query.setMaxResults(paginationDetails.getLimit());

        return query.getResultList();
    }

}
