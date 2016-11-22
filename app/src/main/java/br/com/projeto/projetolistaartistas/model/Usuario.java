package br.com.projeto.projetolistaartistas.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carli on 11/14/2016.
 */

public class Usuario implements Parcelable {
    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
    private int usu_id;
    private String usu_nome = "";
    private String usu_email = "";
    private String usu_imagem = "";
    private String usu_id_google = "";

    public Usuario() {

    }

    protected Usuario(Parcel in) {
        usu_id = in.readInt();
        usu_nome = in.readString();
        usu_email = in.readString();
        usu_imagem = in.readString();
        usu_id_google = in.readString();
    }

    public String getUsu_nome() {
        return usu_nome;
    }

    public void setUsu_nome(String usu_nome) {
        this.usu_nome = usu_nome;
    }

    public String getUsu_email() {
        return usu_email;
    }

    public void setUsu_email(String usu_email) {
        this.usu_email = usu_email;
    }

    public String getUsu_imagem() {
        return usu_imagem;
    }

    public void setUsu_imagem(String usu_imagem) {
        this.usu_imagem = usu_imagem;
    }

    public String getUsu_id_google() {
        return usu_id_google;
    }

    public void setUsu_id_google(String usu_id_google) {
        this.usu_id_google = usu_id_google;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(usu_id);
        dest.writeString(usu_nome);
        dest.writeString(usu_email);
        dest.writeString(usu_imagem);
        dest.writeString(usu_id_google);
    }

    public int getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(int usu_id) {
        this.usu_id = usu_id;
    }
}
