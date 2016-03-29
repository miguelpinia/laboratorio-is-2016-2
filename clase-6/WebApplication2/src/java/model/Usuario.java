package model;
// Generated 28/03/2016 08:54:23 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name="usuario"
    ,schema="login"
)
public class Usuario  implements java.io.Serializable {


     private int id;
     private String nombre;
     private String correo;
     private Character sexo;

    public Usuario() {
    }

	
    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Usuario(int id, String nombre, String correo, Character sexo) {
       this.id = id;
       this.nombre = nombre;
       this.correo = correo;
       this.sexo = sexo;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="nombre", nullable=false)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @Column(name="correo")
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    
    @Column(name="sexo", length=1)
    public Character getSexo() {
        return this.sexo;
    }
    
    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }




}


