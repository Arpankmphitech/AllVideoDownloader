package com.example.allvideodownloaderrevolt.interfacesClass;

import java.io.Serializable;
import java.util.ArrayList;

public interface InstaGetData extends Serializable {

    void getData(ArrayList<String> linkList, String message, boolean isData);

}
