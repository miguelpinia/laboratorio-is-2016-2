package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean manejado qué se utiliza para el manejo de inicio de Sesión en
 * la aplicación web.
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Login {

    private String usuario; // Representa el nombre de usuario.
    private String contrasena; // Representa la contraseña. NO DEBERÍA de ser manejada como texto plano.
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message; // Permite el envio de mensajes entre el bean y la vista.

    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public Login() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    /**
     * Método encargado de validar el inicio de sesión.
     *
     * @return El nombre de la vista que va a responder.
     */
    public String login() {
        if (usuario.equals("FOO") && contrasena.equals("bar")) { // Las contraseñas nunca deben de ir en texto plano.
            httpServletRequest.getSession().setAttribute("sessionUsuario", usuario);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
            faceContext.addMessage(null, message);
            return "acceso";
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
            faceContext.addMessage(null, message);
            return "index";
        }
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param usuario El nombre de usuario a establecer.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Regresa la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La contraseña del usuario a establecer.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
