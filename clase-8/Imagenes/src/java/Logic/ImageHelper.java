package Logic;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Imagen;
import model.MimeType;

/**
 * Clase para buscar y almacenar imágenes en la base de datos.
 */
public class ImageHelper {

    private Session _session;

    public ImageHelper() {
        if (HibernateUtil.getSessionFactory().isClosed()) {
            _session = HibernateUtil.getSessionFactory().openSession();
        } else {
            _session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
    }

    Session session() {
        if (HibernateUtil.getSessionFactory().isClosed()) {
            _session = HibernateUtil.getSessionFactory().openSession();
        } else {
            _session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        return _session;
    }

    public Imagen getImagenPorNombre(String nombre) {
        Transaction tx = session().beginTransaction();
        Query q = session().createSQLQuery("select * from documentos.imagen where nombre = :nombre")
                           .addEntity(Imagen.class)
                           .setString("nombre", nombre);
        Imagen img = (Imagen) q.uniqueResult();
        tx.commit();
        return img;
    }

    public void almacenaImagen(Imagen img) {
        Transaction tx = session().beginTransaction();
        session().save(img);
        tx.commit();
    }

    public MimeType getMimeTypePorNombre(String nombre) {
        Transaction tx = session().beginTransaction();
        Query q = session().createSQLQuery("select * from documentos.mime_type where nombre = :nombre")
                           .addEntity(MimeType.class)
                           .setString("nombre", nombre);
        MimeType mime = (MimeType) q.uniqueResult();
        tx.commit();
        return mime;
    }

}
