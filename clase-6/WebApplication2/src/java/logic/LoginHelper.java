/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Login;

/**
 *
 * @author miguel
 */
public class LoginHelper {

    private Session session;

    public LoginHelper() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Login getLoginPorNombre(String nombre) {
        Login usuario;
        try {
            Transaction tx = session.beginTransaction();
            Query q = session.getNamedQuery("BuscaPorNombre").setString("usuario", nombre);
            return (Login) q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void guardaContacto(Login l) {
        session.beginTransaction();
        session.persist(l);
        session.getTransaction().commit();
    }

}
