package com.test.dev.exam.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.test.dev.exam.model.LoginM;
import com.test.dev.exam.service.SakaiLogin;
import com.test.dev.exam.service.SakaiLoginService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class LoginController {
    
    private LoginM model;
    
    @PostConstruct
    public void init(){
        model = new LoginM();
    }
    
    public void actionButton(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (model.getUser().isBlank() || model.getPassword().isBlank()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo iniciar sesión", "El usuario y/o contraseña no pueden estar vacíos"));
        } else {
            try {
                //Inicialización del servicio
                SakaiLoginService service = new SakaiLoginService();
                SakaiLogin login = service.getSakaiLoginPort();
                //Petición al servicio
                model.setSesion(login.login(model.getUser(), model.getPassword()));
                model.setTimestamp(LocalDateTime.now());
                generateJson();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logeado", model.getSesion()));
            } catch (Exception e) {
                if (e.getMessage().contains("Unable to login")) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo iniciar sesión", "El usuario y/o contraseña son incorrectos"));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", e.getMessage()));
                }
                
            }
        }
    }
    
    private void generateJson() {
        List<LoginM> list = readJson();
        list.add(model);
        //creación del Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);
        System.out.println(json);
        //escritura del Json
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("model.json"));
            output.writeObject(json);
            output.close();
        } catch (IOException e) {}
    }
    
    private List<LoginM> readJson(){
        ;
        //lectura del Json
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("model.json"));
            String aux = (String) input.readObject();
            input.close();
            Gson gson = new Gson();
            return gson.fromJson(aux, new TypeToken<List<LoginM>>(){}.getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public LoginM getModel() {
        return model;
    }

    public void setModel(LoginM model) {
        this.model = model;
    }    
}
