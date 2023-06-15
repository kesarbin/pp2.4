package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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
   @SuppressWarnings("unchecked")
   public User getUserByCar(int series, String model) {
      Query findCarQuery = sessionFactory.getCurrentSession().createQuery("from Car where series = :series and model = :model");
      findCarQuery.setParameter("series", series);
      findCarQuery.setParameter("model", model);
      List<Car> findCarList = findCarQuery.getResultList();

      System.out.println("---------------------------------");
      System.out.println(findCarList.toString());
      System.out.println("---------------------------------");

      if (!findCarList.isEmpty()) {
         Car findCar = findCarList.get(0);
         List<User> userList = listUsers();
         User findUser = null;

         for (User user : userList) {
            if (user.getCar().equals(findCar)) {
               findUser = user;
               break;
            }
         }
         return findUser;
      }
      return null;
   }
}
