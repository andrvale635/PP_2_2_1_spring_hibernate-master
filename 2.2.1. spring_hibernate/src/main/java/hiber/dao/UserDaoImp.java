package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series){
      Query query = sessionFactory.getCurrentSession()
              .createQuery("from Car c where c.model = :model and c.series = :series");
      query.setParameter("model",model);
      query.setParameter("series",series);
      Car car = (Car) query.uniqueResult();
      Query getUser = sessionFactory.getCurrentSession().createQuery("from User where id = :id");
      getUser.setParameter("id", car.getId());
      return (User) getUser.uniqueResult();
   }

}
