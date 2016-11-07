package br.com.projeto.projetolistaartistas.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by f.soares.de.farias on 11/7/2016.
 */

public class Coordenadas implements Parcelable {

    public String latitude;
    public String longitude;
    public String cidade;
    public String rua;

    public Coordenadas(){}

    public Coordenadas(String latitude, String longitude, String cidade, String rua) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cidade = cidade;
        this.rua = rua;
    }

    protected Coordenadas(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
        cidade = in.readString();
        rua = in.readString();
    }

    public static final Creator<Coordenadas> CREATOR = new Creator<Coordenadas>() {
        @Override
        public Coordenadas createFromParcel(Parcel in) {
            return new Coordenadas(in);
        }

        @Override
        public Coordenadas[] newArray(int size) {
            return new Coordenadas[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(cidade);
        dest.writeString(rua);
    }
}
