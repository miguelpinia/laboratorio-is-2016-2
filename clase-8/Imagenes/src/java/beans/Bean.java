/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import Logic.ImageHelper;
import model.Imagen;
import model.MimeType;

/**
 *
 * @author miguel
 */
@ManagedBean
public class Bean {

    private Part file;
    private String nombreArchivo;

    public void upload() {
        try {
            ImageHelper img = new ImageHelper();
            //file.
            byte[] f = new byte[(int) file.getSize()];
            String nombreMime = file.getContentType();
            MimeType mime = img.getMimeTypePorNombre(nombreMime);
            nombreArchivo = file.getSubmittedFileName();
            file.getInputStream().read(f);
            Imagen i = new Imagen();
            i.setContenido(f);
            i.setNombre(nombreArchivo);
            i.setMimeType(mime);
            i.setRuta("/imagenes/" + nombreArchivo);
            img.almacenaImagen(i);
        } catch (IOException e) {
            // Error handling
        }
    }

    public void validateFile(FacesContext ctx,
    UIComponent comp,
    Object value) {
        List<FacesMessage> msgs = new ArrayList<>();
        Part file = (Part) value;
        if (file.getSize() > 1024 * 1024 * 8) {
            msgs.add(new FacesMessage("file too big"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

}
